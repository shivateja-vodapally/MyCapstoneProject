package ProductCatalogServiceProxy.Service;

import ProductCatalogServiceProxy.DTO.UserDTO;
import ProductCatalogServiceProxy.Models.Product;
import ProductCatalogServiceProxy.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@Service
public class StorageProductService implements iProductService{

    private ProductRepository productRepository;

    @Autowired
    private RestTemplate restTemplate;

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

    //This is to call the User Service to get product details based on user role
    @Override
    public Product getProductDetails(Long userId, Long productId) {
        Product product = productRepository.findProductById(productId);

//      RestTemplate restTemplate = new RestTemplate();
        UserDTO userDTO = restTemplate.getForEntity("http://USERSERVICE/users/{id}", UserDTO.class,userId).getBody();
        System.out.println(userDTO.getEmail());
        return product;
    }
}
