package ProductCatalogServiceProxy.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RatingDTO {
    private Double rate;
    private Long count;
}
