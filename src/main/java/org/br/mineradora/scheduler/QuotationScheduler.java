package org.br.mineradora.scheduler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.br.mineradora.service.QuotationService;
import io.quarkus.scheduler.Scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class QuotationScheduler {

    private final Logger LOG = LoggerFactory.getLogger(QuotationScheduler.class);

    @Inject
    QuotationService quotationService;

    @Transactional
    @Scheduled(every = "35s", identity="task-job")
    void schedule(){
        LOG.info("-- Executando scheduler --");
        quotationService.getCurrencyPrice();
    }
}
