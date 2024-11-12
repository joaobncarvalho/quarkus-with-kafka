package org.br.mineradora.repository;


import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class QuotationRepository implements PanacheRepository<QuotationRepository> {

}
