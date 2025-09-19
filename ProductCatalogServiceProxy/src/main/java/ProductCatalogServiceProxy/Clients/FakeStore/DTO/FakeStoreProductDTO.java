package ProductCatalogServiceProxy.Clients.FakeStore.DTO;

import ProductCatalogServiceProxy.DTO.RatingDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class FakeStoreProductDTO implements Serializable {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String image;
    private String category;
    private FakeStoreRatingDTO ratingDTO;
}
