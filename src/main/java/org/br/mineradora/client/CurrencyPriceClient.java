package org.br.mineradora.client;

import org.br.mineradora.dto.CurrencyPriceDTO;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import java.util.List;

@RegisterRestClient(baseUri = "https://economia.awesomeapi.com.br")
public interface CurrencyPriceClient {
    @GET
    @Path("/usd")
    List<CurrencyPriceDTO> getPriceByCurrency();

}
