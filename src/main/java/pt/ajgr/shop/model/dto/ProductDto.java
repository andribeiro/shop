package pt.ajgr.shop.model.dto;

import lombok.Builder;
import lombok.Value;
import pt.ajgr.shop.model.ProductType;

import java.math.BigDecimal;

@Value
@Builder
public class ProductDto {

    Long id;
    String name;
    ProductType productType;
    Integer stock;
    BigDecimal unitPrice;
    String description;
}
