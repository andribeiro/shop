package pt.ajgr.shop.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pt.ajgr.shop.exception.InvalidProductException;
import pt.ajgr.shop.model.dto.ItemDto;
import pt.ajgr.shop.model.dto.PurchaseDto;
import pt.ajgr.shop.model.dto.ResponseDto;
import pt.ajgr.shop.model.entity.Product;
import pt.ajgr.shop.model.entity.Purchase;
import pt.ajgr.shop.model.entity.PurchaseItems;
import pt.ajgr.shop.repository.ProductRepository;
import pt.ajgr.shop.repository.PurchaseItemsRepository;
import pt.ajgr.shop.repository.PurchaseRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PurchaseService {

    private PurchaseRepository purchaseRepository;
    private PurchaseItemsRepository purchaseItemsRepository;
    private ProductRepository productRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public ResponseDto savePurchase(PurchaseDto purchaseDto) {

        List<ItemDto> itemsToBuy = purchaseDto.getItemsToBuy();
        List<PurchaseItems> purchaseItems = new ArrayList<>();

        itemsToBuy.forEach(item -> {
            Optional<Product> productOp = productRepository.findById(item.getId());
            if (productOp.isEmpty()) {
                throw new EntityNotFoundException("The requested product ID does not exist");
            }
            Product product = productOp.get();
            if (!product.isActive()) {
                throw new EntityNotFoundException("The requested product ID is not available");
            }
            if (item.getQuantity() > product.getStock()) {
                throw new InvalidProductException("The requested amount is not available on store", 5000);
            }
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);

            PurchaseItems pItems = PurchaseItems.builder()
                    .productId(product.getId())
                    .quantity(item.getQuantity())
                    .subTotal(product.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                    .build();
            purchaseItems.add(pItems);
        });

        final BigDecimal total = purchaseItems.stream()
                .map(PurchaseItems::getSubTotal)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

        Purchase purchase = Purchase.builder()
                .userName(purchaseDto.getUserName())
//                .purchaseItems(purchaseItems)
                .total(total)
                .build();

        Purchase save = purchaseRepository.save(purchase);

        purchaseItems.forEach(t -> t.setPurchaseId(save.getId()));
        purchaseItemsRepository.saveAll(purchaseItems);

        return ResponseDto.builder()
                .id(save.getId())
                .totalAmount(save.getTotal())
                .build();
    }
}
