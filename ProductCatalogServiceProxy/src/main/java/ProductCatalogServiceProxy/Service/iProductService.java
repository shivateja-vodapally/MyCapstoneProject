package ProductCatalogServiceProxy.Service;

import ProductCatalogServiceProxy.Models.Product;

import java.util.List;

public interface iProductService {
    List<Product> getProducts();

    Product getProduct(Long productId);

    Product createProduct(Product product);

    Product updateProduct(long id, Product product);
}
