package pt.ajgr.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ajgr.shop.model.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByActiveTrue();

    Optional<Product> findByName(String name);
}
