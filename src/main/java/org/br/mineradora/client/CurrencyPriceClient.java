package org.br.mineradora.client;


import jakarta.enterprise.context.ApplicationScoped;
import org.br.mineradora.dto.CurrencyPriceDTO;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;


@Path("/last")
@RegisterRestClient
@ApplicationScoped
public interface CurrencyPriceClient {


    @GET
    @Path("/{pair}")
    CurrencyPriceDTO getPriceByPair(@PathParam("pair") String pair);
}
