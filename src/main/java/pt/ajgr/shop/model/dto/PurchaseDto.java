package pt.ajgr.shop.model.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class PurchaseDto {

    String userName;
    List<ItemDto> itemsToBuy;
}
