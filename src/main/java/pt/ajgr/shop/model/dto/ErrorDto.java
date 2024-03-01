package pt.ajgr.shop.model.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ErrorDto {
    String message;
    int errorCode;
}
