package ProductCatalogServiceProxy.DTO;

import ProductCatalogServiceProxy.Models.SortParam;
import lombok.Getter;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SearchRequestDTO {
    private String query;

    private int pageSize;

    private int pageNumber;

    private List<SortParam> sortParamList = new ArrayList<>();
}
