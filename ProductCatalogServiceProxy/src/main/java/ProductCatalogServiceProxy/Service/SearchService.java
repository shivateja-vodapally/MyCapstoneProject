package ProductCatalogServiceProxy.Service;

import ProductCatalogServiceProxy.Models.Product;
import ProductCatalogServiceProxy.Models.SortParam;
import ProductCatalogServiceProxy.Models.SortType;
import ProductCatalogServiceProxy.Repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    private ProductRepository productRepository;

    public SearchService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


//    public Page<Product> searchProducts(String query,int pageNumber,int pageSize)
//    {
//      Sort will arrange the results by price ascending and then id descending
//      Sort sort = Sort.by("price").and(Sort.by("id").descending());
//        return productRepository.findByTitleEquals(query,PageRequest.of(pageNumber,pageSize,sort));
//    }

//    Page<Product> will return meta data info like page number,page size,sorted or not sorted etc..
    public Page<Product> searchProducts(String query, int pageNumber, int pageSize, List<SortParam> sortParams) {

        Sort sort = null;
//  Here we use custom sorting based on user values given
        if(!sortParams.isEmpty()) {
            if(sortParams.get(0).getSortType().equals(SortType.ASC)) {
                sort = Sort.by(sortParams.get(0).getParamName());
            } else {
                sort = Sort.by(sortParams.get(0).getParamName()).descending();
            }
        }

        for(int i=1;i< sortParams.size();i++) {
            if(sortParams.get(i).getSortType().equals(SortType.ASC)) {
                sort = sort.and(Sort.by(sortParams.get(i).getParamName()));
            } else {
                sort = sort.and(Sort.by(sortParams.get(i).getParamName()).descending());
            }
        }

        return productRepository.findByTitleEquals(query, PageRequest.of(pageNumber,pageSize,sort));
    }
}
