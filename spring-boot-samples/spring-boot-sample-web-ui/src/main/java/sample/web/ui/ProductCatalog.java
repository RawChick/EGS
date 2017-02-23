package sample.web.ui;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by whees on 07-Feb-17.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProductCatalog {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = javax.persistence.CascadeType.ALL)
    private List<Product> products = new ArrayList<>();


    public void add(Product product) {
        products.add(product);
    }

    public Product find(Long id){
        for(Product p: products ){
            if(id == p.getId()){
                return p;
            }
        return null;
        }
        return null;
    }
}
