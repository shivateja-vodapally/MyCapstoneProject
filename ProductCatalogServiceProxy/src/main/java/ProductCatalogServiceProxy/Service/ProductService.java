package ProductCatalogServiceProxy.Service;

import ProductCatalogServiceProxy.DTO.ProductDTO;
import ProductCatalogServiceProxy.Models.Category;
import ProductCatalogServiceProxy.Models.Product;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProductService implements iProductService {
    private RestTemplateBuilder restTemplateBuilder;

    public ProductService(RestTemplateBuilder restTemplateBuilder)
    {
        this.restTemplateBuilder=restTemplateBuilder;
    }
    @Override
    public List<Product> getProducts()
    {
        return null;
    }

    //Getting the product data from fakestoreapi using Rest Template.
    //Make sure ProductDTO should exactly match with product entity in fakestoreapi
    @Override
    public Product getProduct(Long productId)
    {
        RestTemplate restTemplate=restTemplateBuilder.build();
        ProductDTO productDTO=restTemplate.getForEntity("https://fakestoreapi.com/products/{id}", ProductDTO.class,productId).getBody();
        return getProduct(productDTO);
    }

    @Override
    public Product createProduct(Product product)
    {
        return null;
    }

    @Override
    public Product updateProduct(long id, Product product)
    {
        return null;
    }

    private Product getProduct(ProductDTO productDTO)
    {
        Product product=new Product();
        product.setTitle(productDTO.getTitle());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        Category category=new Category();
        category.setName(productDTO.getCategory());
        product.setCategory(category);
        product.setImageUrl(productDTO.getImage());
        product.setId(productDTO.getId());
        return product;
    }

}
