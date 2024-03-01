package pt.ajgr.shop.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class ResponseDto {

    Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    BigDecimal totalAmount;
}
