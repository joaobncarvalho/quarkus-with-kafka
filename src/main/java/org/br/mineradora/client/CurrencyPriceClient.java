package org.br.mineradora.client;



import jakarta.enterprise.context.ApplicationScoped;
import org.br.mineradora.dto.CurrencyPriceDTO;
import org.br.mineradora.dto.USDBRL;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import javax.ws.rs.PathParam;
import java.util.List;
import java.util.Map;

@Path("/last")
@RegisterRestClient(baseUri = "https://economia.awesomeapi.com.br")
@ApplicationScoped
public interface CurrencyPriceClient {

    @GET
    @Path("/{pair}")
    CurrencyPriceDTO getPriceByPair(@PathParam("pair") String pair);



}

