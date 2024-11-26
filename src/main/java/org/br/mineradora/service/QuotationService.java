package org.br.mineradora.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.br.mineradora.client.CurrencyPriceClient;
import org.br.mineradora.dto.CurrencyPriceDTO;
import org.br.mineradora.dto.QuotationDTO;
import org.br.mineradora.entity.QuotationEntity;
import org.br.mineradora.message.KafkaEvents;
import org.br.mineradora.repository.QuotationRepository;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class QuotationService {

    @RestClient
    @Inject
    CurrencyPriceClient currencyPriceClient;

    @Inject
    QuotationRepository quotationRepository;

    @Inject
    KafkaEvents kafkaEvents;

    private static final Logger LOG = LoggerFactory.getLogger(QuotationService.class);

    public void sendTestMessage() {
        QuotationDTO quotationDTO = QuotationDTO.builder()
                .date(new Date())
                .currencyPrice(BigDecimal.valueOf(123.45))
                .build();

        kafkaEvents.sendNewKafkaEvent(quotationDTO);
    }

    public void processCurrencyPrice() {
        List<CurrencyPriceDTO> currencyPriceInfoList = currencyPriceClient.getPriceByCurrency();
        LOG.info("Raw response from CurrencyPriceClient: {}", currencyPriceInfoList);

        if (currencyPriceInfoList != null && !currencyPriceInfoList.isEmpty()) {
            CurrencyPriceDTO currencyPriceInfo = currencyPriceInfoList.getFirst();
            LOG.info("Processing first entry: {}", currencyPriceInfo);

            if (currencyPriceInfo.getUSDBRL() != null && updateCurrentInfoPrice(currencyPriceInfo)) {
                kafkaEvents.sendNewKafkaEvent(
                        QuotationDTO.builder()
                                .currencyPrice(new BigDecimal(currencyPriceInfo.getUSDBRL().getBid()))
                                .date(new Date())
                                .build()
                );
                LOG.info("Message sent to Kafka for currency price: {}", currencyPriceInfo.getUSDBRL());
            } else {
                LOG.warn("Bid is null or updateCurrentInfoPrice returned false.");
            }
        } else {
            LOG.error("CurrencyPriceInfo list is empty or null. Cannot process currency price.");
        }
    }




    private boolean updateCurrentInfoPrice(CurrencyPriceDTO currencyPriceInfo) {
        // Retrieve the bid as a BigDecimal
        BigDecimal currentPrice = new BigDecimal(currencyPriceInfo.getUSDBRL().getBid());
        AtomicBoolean updatePrice = new AtomicBoolean(false);

        // Retrieve the existing quotations
        List<QuotationEntity> quotationList = quotationRepository.findAll().list();

        if (quotationList.isEmpty()) {
            // Save the new quotation if there are no existing ones
            saveQuotation(currencyPriceInfo);
            updatePrice.set(true);
        } else {
            // Check if the price has changed
            QuotationEntity latestQuotation = quotationList.get(0);
            if (latestQuotation.getCurrencyPrice().compareTo(currentPrice) != 0) {
                saveQuotation(currencyPriceInfo);
                updatePrice.set(true);
            }
        }
        return updatePrice.get();
    }



    private void saveQuotation(CurrencyPriceDTO currencyInfo) {
        QuotationEntity quotation = new QuotationEntity();

        // Set the fields for the new quotation
        quotation.setDate(new Date());
        quotation.setCurrencyPrice(new BigDecimal(currencyInfo.getUSDBRL().getBid()));
        quotation.setPctChange(currencyInfo.getUSDBRL().getPctChange());
        quotation.setPair("USD-BRL");

        // Persist the new quotation
        quotationRepository.persist(quotation);
    }


}
