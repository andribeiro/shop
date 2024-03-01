package pt.ajgr.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ajgr.shop.model.entity.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {


}
