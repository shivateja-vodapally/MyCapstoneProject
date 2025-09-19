package ProductCatalogServiceProxy.Service;

import ProductCatalogServiceProxy.Models.Product;
import ProductCatalogServiceProxy.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StorageProductService implements iProductService{

    private ProductRepository productRepository;
    public StorageProductService(ProductRepository productRepository)
    {
        this.productRepository=productRepository;
    }
    @Override
    public List<Product> getProducts() {
        return List.of();
    }

    @Override
    public Product getProduct(Long productId) {
        return null;
    }

    @Override
    public Product createProduct(Product product) {
        Product resultantProduct=productRepository.save(product);
        return resultantProduct;
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        return null;
    }
}
