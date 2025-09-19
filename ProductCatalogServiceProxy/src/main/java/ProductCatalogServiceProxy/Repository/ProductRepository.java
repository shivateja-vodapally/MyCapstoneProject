package ProductCatalogServiceProxy.Repository;

import ProductCatalogServiceProxy.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Product save(Product product);
}
