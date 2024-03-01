package pt.ajgr.shop.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pt.ajgr.shop.exception.InvalidProductException;
import pt.ajgr.shop.model.dto.ProductDto;
import pt.ajgr.shop.model.dto.ResponseDto;
import pt.ajgr.shop.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> findAll() {

        return productService.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto createProduct(@RequestBody ProductDto productDto) {
        validateCreate(productDto);

        return productService.saveProduct(productDto);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@RequestBody ProductDto productDto) {
        validateUpdate(productDto);

        productService.updateProduct(productDto);
    }

    private void validateUpdate(ProductDto productDto) {
        if (productDto.getId() == null) {
            throw new InvalidProductException("ID is mandatory for update", 2000);
        }
        if (productDto.getProductType() != null) {
            throw new InvalidProductException("Product Type can not be changed for existing product", 3000);
        }
    }

    private void validateCreate(ProductDto productDto) {
        if (productDto.getProductType() == null) {
            throw new InvalidProductException("Product Type is mandatory", 1000);
        }
    }
}
