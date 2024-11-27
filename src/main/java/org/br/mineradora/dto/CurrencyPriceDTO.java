package org.br.mineradora.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
public class CurrencyPriceDTO {

    @JsonProperty("USDBRL")
    private USDBRL usdbrl;

    // Add getter for USDBRL
    public USDBRL getUSDBRL() {
        return usdbrl;
    }
}
