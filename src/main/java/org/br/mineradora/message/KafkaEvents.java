package org.br.mineradora.message;


import jakarta.enterprise.context.ApplicationScoped;
import org.br.mineradora.dto.QuotationDTO;
import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

@ApplicationScoped
public class KafkaEvents {

    private final Logger LOG = LoggerFactory.getLogger(KafkaEvents.class);

    @Channel("quotation-channel")
    Emitter<QuotationDTO> quotationDTOEmitter;

    public void sendNewKafkaEvent(QuotationDTO quotation){

        LOG.info

    }


}
