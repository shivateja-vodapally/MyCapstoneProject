package ProductCatalogServiceProxy.Controller;

import ProductCatalogServiceProxy.Models.Product;
import ProductCatalogServiceProxy.Service.iProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductControllerTest {
    @Autowired
    ProductController productController;

    @MockBean
    iProductService productService;

    //Hamcrest Maven Dependency added for this annotation.This is to verify whether the
    //id that we gave as parameter in getProduct(id)(Product Controller) is correctly going to productService
    //call i.e. Product product=productService.getProduct(productId);
    //It is verify path variable with actual parameter going to that corresponding service layer parameter.
    @Captor
    private ArgumentCaptor<Long> idCaptor;
    //Here we are mocking the productservice to get any/some product based on any id and
    //test whether am able to receive the product details in getProduct method
    //since this is considered as external dependency hence mocking it.
    //Autowired is used where we need to test the class but MockBean is to mock the external dependency
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
        verify(productService,times(1)).getProduct(1L);
    }

    //Now we will test the same method to show that exception will be caught if getProduct
    //throws some exception when searched by any id meaning some issue encountered in external dependency
    @Test
    @DisplayName("Dependency Threw an exception")
    public void Test_getProduct_InternalDependencyThrowException()
    {
        //Arrange
        when(productService.getProduct(any(Long.class))).thenThrow(new RuntimeException("Something went wrong"));

        //Act & Assert in one statement itself
        //Junit implements functional interface Executable.It has a single abstract method (execute()).
        //Hence we executed lamba expression to implement it.
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

    @Test
    @DisplayName("Verify product id in getProduct id")
    public void Test_ProductControllerCallsProductServiceWithSameId()
    {
        //Arrange
        Long id=2L;

        //Act
        productController.getProduct(2L);

        //Assert
        verify(productService).getProduct(idCaptor.capture());
        assertEquals(id,idCaptor.getValue());
    }
}