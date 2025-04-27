package ProductCatalogServiceProxy.DTO;

import ProductCatalogServiceProxy.Models.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductDTO {
    private Long id;
    private String title;
    private String description;
    private String image;
    private Double price;
    private String category;
    private RatingDTO ratingDTO;
}
