package pt.ajgr.shop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.ajgr.shop.model.ProductType;
import pt.ajgr.shop.model.dto.ItemDto;
import pt.ajgr.shop.model.dto.PurchaseDto;
import pt.ajgr.shop.model.dto.ResponseDto;
import pt.ajgr.shop.model.entity.Product;
import pt.ajgr.shop.model.entity.Purchase;
import pt.ajgr.shop.repository.ProductRepository;
import pt.ajgr.shop.repository.PurchaseItemsRepository;
import pt.ajgr.shop.repository.PurchaseRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceTest {

    private PurchaseService unitUnderTest;

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private PurchaseItemsRepository purchaseItemsRepository;

    @Mock
    private ProductRepository productRepository;

    @Captor
    private ArgumentCaptor<Purchase> captor;

    @BeforeEach
    void init() {
        final Product product = new Product();
                product.setName("Orange");
                product.setProductType(ProductType.FRUIT);
                product.setActive(Boolean.TRUE);
                product.setStock(100);
                product.setUnitPrice(BigDecimal.valueOf(33.33));
        doReturn(Optional.of(product))
                .when(productRepository)
                .findById(any(Long.class));

        final Purchase purchase = Purchase.builder()
                .id(1L)
                .total(BigDecimal.ONE)
                .build();
        doReturn(purchase).when(purchaseRepository).save(captor.capture());

        unitUnderTest = new PurchaseService(purchaseRepository, purchaseItemsRepository, productRepository);
    }

    @Test
    void givenPurchase_whenAllDataIsOK_thenSavePurchaseInRepoWithGoodTotal() {

        List<ItemDto> build = List.of(
                ItemDto.builder().id(1L).quantity(10).build(),
                ItemDto.builder().id(2L).quantity(10).build(),
                ItemDto.builder().id(3L).quantity(10).build()
        );
        PurchaseDto purchaseDto = PurchaseDto.builder()
                .userName("andre")
                .itemsToBuy(build)
                .build();

        ResponseDto responseDto = unitUnderTest.savePurchase(purchaseDto);

        assertThat(responseDto).isNotNull();
        assertThat(captor.getValue().getTotal())
                .usingComparator(BigDecimal::compareTo)
                .isEqualTo(BigDecimal.valueOf(999.90D));
    }
}