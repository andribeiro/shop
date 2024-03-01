package pt.ajgr.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ajgr.shop.model.entity.PurchaseItems;

public interface PurchaseItemsRepository extends JpaRepository<PurchaseItems, Long> {
}
