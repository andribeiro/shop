package pt.ajgr.shop.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Entity
@Table(name = "purchase")
@Data
@Builder
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    long id;

    @Column(name = "user_name")
    String userName;

    @Column(name = "total")
    BigDecimal total;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL)
    List<PurchaseItems> purchaseItems;

    @Column(name = "created_date_time")
    LocalDateTime createdDateTime;


    @PrePersist
    private void setCreatedDateTime() {
        createdDateTime = LocalDateTime.now(ZoneOffset.UTC);
    }

}
