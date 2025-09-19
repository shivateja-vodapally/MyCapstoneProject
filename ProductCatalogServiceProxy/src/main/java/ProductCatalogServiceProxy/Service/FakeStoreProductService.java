package ProductCatalogServiceProxy.Service;

import ProductCatalogServiceProxy.Clients.FakeStore.Client.FakeStoreAPIClient;
import ProductCatalogServiceProxy.Clients.FakeStore.DTO.FakeStoreProductDTO;
import ProductCatalogServiceProxy.DTO.ProductDTO;
import ProductCatalogServiceProxy.Models.Category;
import ProductCatalogServiceProxy.Models.Product;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

//@Service
public class FakeStoreProductService implements iProductService {
    private FakeStoreAPIClient fakeStoreAPIClient;
    private RestTemplateBuilder restTemplateBuilder;

    public FakeStoreProductService(RestTemplateBuilder restTemplateBuilder,FakeStoreAPIClient fakeStoreAPIClient)
    {
        this.restTemplateBuilder=restTemplateBuilder;
        this.fakeStoreAPIClient=fakeStoreAPIClient;
    }
    @Override
    public List<Product> getProducts()
    {
        FakeStoreProductDTO[] fakeStoreProductDTOS= fakeStoreAPIClient.getProducts();
        ArrayList<Product> products=new ArrayList<>();
        for(FakeStoreProductDTO fakeStoreProductDTO:fakeStoreProductDTOS)
        {
            Product product=getProduct(fakeStoreProductDTO);
            products.add(product);
        }
        return products;
    }

    //Getting the product data from fakestoreapi using Rest Template.
    //Make sure ProductDTO should exactly match with product entity in fakestoreapi
    @Override
    public Product getProduct(Long productId)
    {
        FakeStoreProductDTO fakeStoreProductDTO= fakeStoreAPIClient.getProduct(productId);
        return getProduct(fakeStoreProductDTO);
    }

    @Override
    public Product createProduct(Product product)
    {
        FakeStoreProductDTO fakeStoreProductDTO=getFakeStoreProductDTO(product);
        FakeStoreProductDTO responseFakeStoreProductDTO=fakeStoreAPIClient.createProduct(fakeStoreProductDTO);
        return getProduct(responseFakeStoreProductDTO);
    }

    @Override
    public Product updateProduct(Long id, Product product)
    {
        FakeStoreProductDTO fakeStoreProductDTORequest=getFakeStoreProductDTO(product);
        FakeStoreProductDTO fakeStoreProductDTO=fakeStoreAPIClient.updateProduct(id,fakeStoreProductDTORequest);
        Product resultantProduct = getProduct(fakeStoreProductDTO);
        return resultantProduct;
    }

    //Converts FakeStoreProductDTO to product
    private Product getProduct(FakeStoreProductDTO fakeStoreProductDTO)
    {
        Product product=new Product();
        product.setTitle(fakeStoreProductDTO.getTitle());
        product.setDescription(fakeStoreProductDTO.getDescription());
        product.setPrice(fakeStoreProductDTO.getPrice());
        Category category=new Category();
        category.setName(fakeStoreProductDTO.getCategory());
        product.setCategory(category);
        product.setImageUrl(fakeStoreProductDTO.getImage());
        product.setId(fakeStoreProductDTO.getId());
        return product;
    }

    //Converts product to FakeStoreProductDTO
    private FakeStoreProductDTO getFakeStoreProductDTO(Product product)
    {
        FakeStoreProductDTO fakeStoreProductDTO=new FakeStoreProductDTO();
        fakeStoreProductDTO.setTitle(product.getTitle());
        fakeStoreProductDTO.setDescription(product.getDescription());
        fakeStoreProductDTO.setPrice(product.getPrice());
        fakeStoreProductDTO.setCategory(product.getCategory().getName());
        fakeStoreProductDTO.setImage(product.getImageUrl());
        fakeStoreProductDTO.setId(product.getId());
        return fakeStoreProductDTO;
    }

}
