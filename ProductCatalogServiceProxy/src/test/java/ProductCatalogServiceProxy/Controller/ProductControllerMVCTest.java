package ProductCatalogServiceProxy.Controller;

import ProductCatalogServiceProxy.Models.Product;
import ProductCatalogServiceProxy.Service.iProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//Focus only on testing controller logic but not entire application
@WebMvcTest(ProductController.class)
public class ProductControllerMVCTest {

    //MockMvc is a Spring-provided class for testing MVC controllers without starting an actual server.
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    iProductService productService;

    @Autowired
    ObjectMapper objectMapper; //Json to String Convertor

    //Here we are testing the end points i.e. /products whether if a user tries to call the
    //getProducts service via web, it is able to respond with correct response i.e. list of products
    @Test
    @DisplayName("Testing the get call getProducts")
    public void Test_getProducts_ReceiveSuccessfulResponse() throws Exception
    {
        //Arrange
        //We are intentionally giving two sample products for testing because getProducts is
        //expecting list of products to return when called /Products.
        List<Product> products=new ArrayList<>();
        Product product1=new Product();
        product1.setTitle("Iphone17");
        Product product2=new Product();
        product2.setTitle("Iphone18");
        products.add(product1);
        products.add(product2);
        //Memory Hack: when Class call the method(parameters) thenReturn object
        when(productService.getProducts()).thenReturn(products);
        //Act & Assert
        //mockMvc performs get request on /products andExpect status is ok
        mockMvc.perform(get("/Products"))
                //we are expecting here just 200 response from Products service
                .andExpect(status().isOk())
                //we are expecting content (body of response) should be list of products. Here we
                //receive Object as a string during the transmission hence used String content
                //But the actual content is list of objects as we see in controller.
                //we are using object mapper to convert the Object list(products) to string
                .andExpect(content().string(objectMapper.writeValueAsString(products)))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Iphone17"));
    }

    //Here we are going to test the response of post call i.e. /Products
    @Test
    @DisplayName("Testing the Post Call CreateProduct")
    public void Test_createProduct_ReceiveSuccessfulResponse() throws Exception
    {
        //Arrange
        Product productToCreate=new Product();
        productToCreate.setId(1000L);
        productToCreate.setTitle("Orange");
        productToCreate.setDescription("Fresh and Juicy");

        Product productToExpect=new Product();
        productToExpect.setTitle("Orange");
        productToExpect.setDescription("Fresh and Juicy");

        //Act
        when(productService.createProduct(any(Product.class))).thenReturn(productToExpect);

        //Assert
        mockMvc.perform(post("/Products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productToCreate)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(productToExpect)))
                //content gives object in the form of string which becomes challenge to get the
                //number of attributes and attribute names from an object. Instead we have added
                //assertJ maven Dependency and can easily find no. of attributes we received from
                //an object and attribute values
                .andExpect(jsonPath("$.length()").value(9))
                .andExpect(jsonPath("$.title").value("Orange"));
    }
}
