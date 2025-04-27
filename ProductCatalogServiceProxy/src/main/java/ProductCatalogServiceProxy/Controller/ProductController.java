package ProductCatalogServiceProxy.Controller;

import ProductCatalogServiceProxy.DTO.ProductDTO;
import ProductCatalogServiceProxy.Models.Product;
import ProductCatalogServiceProxy.Service.iProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Instead of giving the url in each method which is common for all the methods mentioned below
//we can use RequestMapping for the entire class which acts as base url, and you can give the
//specific url for each method.
//eg: Class - /Products  Class.method1 - /id ==> /Products/id

@RequestMapping("/Products")
@RestController
public class ProductController {
    iProductService productService;

    public ProductController(iProductService productService)
    {
        this.productService=productService;
    }
    @GetMapping("")
    public List<Product> getProducts()
    {
        return null;
    }

    @GetMapping("{id}")
    public Product getProduct(@PathVariable("id") Long productId)
    {
        return productService.getProduct(productId);
    }

    @PostMapping("")
    public Product createProduct(@RequestBody ProductDTO productDTO)
    {
        return new Product();
    }

    @PatchMapping("")
    public Product updateProduct(@RequestBody ProductDTO productDTO)
    {
        return null;
    }

}
