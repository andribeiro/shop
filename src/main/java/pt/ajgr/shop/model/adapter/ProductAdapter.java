package pt.ajgr.shop.model.adapter;

import org.springframework.stereotype.Component;
import pt.ajgr.shop.model.dto.ProductDto;
import pt.ajgr.shop.model.entity.Product;

@Component
public class ProductAdapter {

    public ProductDto toDto(Product product) {

        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .productType(product.getProductType())
                .stock(product.getStock())
                .unitPrice(product.getUnitPrice())
                .description(product.getDescription())
                .build();
    }

    public Product toEntity(ProductDto productDto) {

        Product product = new Product();
                product.setName(productDto.getName());
                product.setProductType(productDto.getProductType());
                product.setStock(productDto.getStock());
                product.setActive(Boolean.TRUE);
                product.setUnitPrice(productDto.getUnitPrice());
                product.setDescription(productDto.getDescription());
        return product;
    }
}
