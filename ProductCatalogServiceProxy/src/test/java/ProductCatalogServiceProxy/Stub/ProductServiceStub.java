package ProductCatalogServiceProxy.Stub;

import ProductCatalogServiceProxy.Models.Product;
import ProductCatalogServiceProxy.Service.iProductService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class ProductServiceStub implements iProductService {
    Map<Long,Product> products;
    public ProductServiceStub()
    {
        products=new HashMap<Long,Product>();
    }
    @Override
    public List<Product> getProducts() {
        return List.of();
    }

    @Override
    public Product getProduct(Long productId) {
        return products.get(productId);
    }

    @Override
    public Product createProduct(Product product) {
        products.put(product.getId(), product);
        return products.get(product.getId());
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        products.put(id,product);
        return products.get(id);
    }
}
