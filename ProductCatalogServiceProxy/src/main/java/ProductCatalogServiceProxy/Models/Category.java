package ProductCatalogServiceProxy.Models;

import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Category {
    private String name;
    private String description;
    @OneToMany
    private List<Product> products;
}
