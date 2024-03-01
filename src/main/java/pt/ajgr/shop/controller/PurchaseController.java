package pt.ajgr.shop.controller;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pt.ajgr.shop.exception.InvalidProductException;
import pt.ajgr.shop.model.dto.ItemDto;
import pt.ajgr.shop.model.dto.PurchaseDto;
import pt.ajgr.shop.model.dto.ResponseDto;
import pt.ajgr.shop.service.PurchaseService;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/purchase")
@AllArgsConstructor
public class PurchaseController {

    private PurchaseService purchaseService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto createPurchase(@RequestBody PurchaseDto purchaseDto) {
        validateCreate(purchaseDto);

        return purchaseService.savePurchase(purchaseDto);
    }

    private void validateCreate(PurchaseDto purchaseDto) {
        if (StringUtils.isBlank(purchaseDto.getUserName())) {
            throw new InvalidProductException("UserName is mandatory for purchase", 6000);
        }
        List<ItemDto> itemsToBuy = purchaseDto.getItemsToBuy();
        if (Objects.isNull(itemsToBuy) || itemsToBuy.isEmpty()) {
            throw new InvalidProductException("ItemsToBuy is mandatory for purchase", 7000);
        }

        List<ItemDto> list = itemsToBuy.stream().filter(i -> i.getQuantity() <= 0).toList();
        if (!list.isEmpty()) {
            throw new InvalidProductException("ItemsToBuy can not take 0 or less quantity for purchase", 8000);
        }
    }
}
