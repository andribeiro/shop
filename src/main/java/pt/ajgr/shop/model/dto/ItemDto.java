package pt.ajgr.shop.model.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ItemDto {

    Long id;
    int quantity;
}
