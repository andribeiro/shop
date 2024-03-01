package pt.ajgr.shop.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pt.ajgr.shop.exception.InvalidProductException;
import pt.ajgr.shop.model.adapter.ProductAdapter;
import pt.ajgr.shop.model.dto.ProductDto;
import pt.ajgr.shop.model.dto.ResponseDto;
import pt.ajgr.shop.model.entity.Product;
import pt.ajgr.shop.repository.ProductRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository productRepository;
    private ProductAdapter productAdapter;

    public List<ProductDto> findAll() {

        List<Product> allByActiveTrue = productRepository.findAllByActiveTrue();

        return allByActiveTrue.stream().map(t -> productAdapter.toDto(t)).toList();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public ResponseDto saveProduct(ProductDto productDto) {

        productRepository.findByName(productDto.getName())
                .ifPresent(t -> { throw new InvalidProductException("A product with that name already exists", 4000); });

        Product product = productAdapter.toEntity(productDto);

        Product save = productRepository.save(product);
        return ResponseDto.builder().id(save.getId()).build();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public void updateProduct(ProductDto productDto) {

        Optional<Product> productOp = productRepository.findById(productDto.getId());
        if (productOp.isEmpty()) {
            throw new EntityNotFoundException("The requested ID does not exists: " + productDto.getId());
        }

        Product product = productOp.get();
        if (StringUtils.isNotBlank(productDto.getName())) {
            product.setName(productDto.getName());
        }
        if (Objects.nonNull(productDto.getStock())) {
            product.setStock(productDto.getStock());
        }
        if (Objects.nonNull(productDto.getUnitPrice())) {
            product.setUnitPrice(productDto.getUnitPrice());
        }
        if (StringUtils.isNotBlank(productDto.getDescription())) {
            product.setDescription(productDto.getDescription());
        }
        productRepository.save(product);
    }
}
