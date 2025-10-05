package ProductCatalogServiceProxy.Service;

import ProductCatalogServiceProxy.Clients.FakeStore.DTO.FakeStoreProductDTO;
import ProductCatalogServiceProxy.DTO.ProductDTO;
import ProductCatalogServiceProxy.Models.Product;

import java.util.List;

public interface iProductService {
    List<Product> getProducts();

    Product getProduct(Long productId);

    Product createProduct(Product product);

    Product updateProduct(Long id, Product product);

    Product getProductDetails(Long userId, Long productId);
}
