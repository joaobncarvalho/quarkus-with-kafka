package org.br.mineradora.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyPriceDTO {

    @JsonProperty("USDBRL")
    private USDBRL usdbrl;

    // Add getter for USDBRL
    public USDBRL getUSDBRL() {
        return usdbrl;
    }
}
