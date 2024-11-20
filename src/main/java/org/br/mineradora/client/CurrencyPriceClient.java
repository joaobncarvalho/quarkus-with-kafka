package org.br.mineradora.client;



import org.br.mineradora.dto.CurrencyPriceDTO;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@RegisterRestClient(baseUri = "https://economia.awesomeapi.com.br")

public interface CurrencyPriceClient {

    @GET
    @Path("/currency-price")
    CurrencyPriceDTO getPriceByPair(String pair);
}

