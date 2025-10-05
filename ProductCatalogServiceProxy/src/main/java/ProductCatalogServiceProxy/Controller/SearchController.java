package ProductCatalogServiceProxy.Controller;

import ProductCatalogServiceProxy.DTO.ProductDTO;
import ProductCatalogServiceProxy.DTO.SearchRequestDTO;
import ProductCatalogServiceProxy.Models.Product;
import ProductCatalogServiceProxy.Service.SearchService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

    @RestController
    @RequestMapping("/search")
    public class SearchController {
        private SearchService searchService;

        public SearchController(SearchService searchService) {
            this.searchService = searchService;
        }

        @PostMapping
        public Page<Product> searchProducts(@RequestBody SearchRequestDTO searchRequestDTO) {
//        Search Products and return them page number wise with concrete size of one or more results
//        List<Product> products = searchService.searchProducts(searchRequestDto.getQuery(), searchRequestDto.getPageNumber(), searchRequestDto.getPageSize());
//        List<ProductDto> searchResults = new ArrayList<>();
//        for(Product product : products) {
//            searchResults.add(getProductDto(product));
//        }
//        return searchResults;
            return searchService.searchProducts(searchRequestDTO.getQuery(), searchRequestDTO.getPageNumber(), searchRequestDTO.getPageSize(), searchRequestDTO.getSortParamList());
        }

        ProductDTO getProductDto(Product product) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setTitle(product.getTitle());
            productDTO.setDescription(product.getDescription());
            productDTO.setCategory(product.getCategory().getName());
            productDTO.setPrice(product.getPrice());
            return productDTO;
        }
    }
