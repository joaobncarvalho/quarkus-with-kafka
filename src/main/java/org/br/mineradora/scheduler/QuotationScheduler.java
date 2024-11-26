package org.br.mineradora.scheduler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.br.mineradora.service.QuotationService;
import io.quarkus.scheduler.Scheduled;

@ApplicationScoped
public class QuotationScheduler {

    @Inject
    QuotationService quotationService;

    @Transactional
    @Scheduled(every = "35s", identity = "task-job")
    public void schedule() {
        quotationService.processCurrencyPrice(); // Updated to the correct method
    }
}
