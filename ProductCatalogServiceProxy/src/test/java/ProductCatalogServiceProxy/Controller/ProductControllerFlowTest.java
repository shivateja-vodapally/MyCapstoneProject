package ProductCatalogServiceProxy.Controller;

import ProductCatalogServiceProxy.DTO.ProductDTO;
import ProductCatalogServiceProxy.Models.Product;
import ProductCatalogServiceProxy.Service.iProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.apache.coyote.http11.Constants.a;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProductControllerFlowTest {
    @Autowired
    ProductController productController;

    //Here we will call productServiceStub in Stub package to test the product flow. We have
    //commented @Service annotation for actual services i.e. StorageProductService and FakeProductService
    //Once the Testing finished uncomment the service you want to call. Comment the stub ProductService.
    @Autowired
    iProductService productservice;

    @Test
    public void Test_CreateFetchUpdate_RunSuccessfully()
    {
        //Arrange
        ProductDTO productDTO=new ProductDTO();
        productDTO.setId(1000L);
        productDTO.setTitle("Iphone17");
        productDTO.setDescription("Next Gen Smart Phone");

        //Act
        productController.createProduct(productDTO);
        ResponseEntity<Product> productResponseEntity=productController.getProduct(1000L);
        productDTO.setTitle("Iphone18");
        productDTO.setDescription("GenZ smart Phone");
        productDTO.setPrice(10000.0);
        productController.updateProduct(1000L,productDTO);
        ResponseEntity<Product> updatedProductResponseEntity=productController.getProduct(1000L);

        //Assert
        assertEquals("Iphone18",updatedProductResponseEntity.getBody().getTitle());
        assertEquals(10000.0,updatedProductResponseEntity.getBody().getPrice());
        assertEquals("GenZ smart Phone",updatedProductResponseEntity.getBody().getDescription());
    }
}
