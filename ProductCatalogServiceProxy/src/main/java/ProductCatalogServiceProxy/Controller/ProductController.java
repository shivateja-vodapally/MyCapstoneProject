package ProductCatalogServiceProxy.Controller;

import ProductCatalogServiceProxy.DTO.ProductDTO;
import ProductCatalogServiceProxy.Models.Category;
import ProductCatalogServiceProxy.Models.Product;
import ProductCatalogServiceProxy.Service.iProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
        return productService.getProducts();
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long productId)
    {
        try{
            if (productId < 1)
            {
                throw new IllegalArgumentException("Product id "+productId+" is not found");
            }
            MultiValueMap<String,String> headers=new LinkedMultiValueMap<>();
            headers.add("Function called by","Controller");
            Product product=productService.getProduct(productId);
            return new ResponseEntity<>(product, headers,HttpStatus.OK);
        }
        catch(Exception exception)
        {
            throw exception;
        }
    }

    @PostMapping("")
    public Product createProduct(@RequestBody ProductDTO productDTO)
    {
        Product product=getProduct(productDTO);
        return productService.createProduct(product);
    }

    @PatchMapping("{id}")
    public Product updateProduct(@PathVariable Long id,@RequestBody ProductDTO productDTO)
    {
        Product product=getProduct(productDTO);
        Product resultantProduct=productService.updateProduct(id,product);
        return resultantProduct;
    }

    public Product getProduct(ProductDTO productDTO)
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
