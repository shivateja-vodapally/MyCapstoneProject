package ProductCatalogServiceProxy.Controller;

import ProductCatalogServiceProxy.Models.Product;
import ProductCatalogServiceProxy.Service.iProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductControllerTest {
    @Autowired
    ProductController productController;
    @MockBean
    iProductService productService;

    //Here we are just mocking the productservice to get any product based on any id and
    //test whether am able to receive the product details in getProduct method
    @Test
    @DisplayName("Getting product successfully")
    public void Test_GetProduct_ReturnProduct()
    {
        Product product=new Product();
        product.setPrice(10000d);
        product.setTitle("Iphone17");
        //Arrange
        when(productService.getProduct(any(Long.class))).thenReturn(product);

        //Act
        ResponseEntity<Product> productResponseEntity=productController.getProduct(1L);

        //Assert
        //Here we are testing whether we are receiving the product
        assertNotNull(productResponseEntity);
        //Here we are testing whether we are receiving the product with Title Iphone17 & price 10000
        assertEquals(10000,productResponseEntity.getBody().getPrice());
        assertEquals("Iphone17",productResponseEntity.getBody().getTitle());
    }

    //Now we will test the same method to show that exception will be caught if getProduct
    //throws exception when searched by any id
    @Test
    @DisplayName("Dependency Threw an exception")
    public void Test_getProduct_InternalDependencyThrowException()
    {
        //Arrange
        when(productService.getProduct(any(Long.class))).thenThrow(new RuntimeException("Something went wrong"));

        //Act & Assert in one statement itself
        assertThrows(RuntimeException.class,()->productController.getProduct(1L));
    }

    //We will test the same method to throw Illegal argument exception when we give invalid id
    @Test
    @DisplayName("Invalid product id threw an exception")
    public void Test_getProudctWithInvalidId_ThrowException()
    {
        //Arrange,Act,Assert in one statement only
        assertThrows(IllegalArgumentException.class,()->productController.getProduct(0L));
    }

}