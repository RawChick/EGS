package sample.web.ui;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by whees on 07-Feb-17.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="Orders")
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = javax.persistence.CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    public void add(Product product) {
        products.add(product);
    }

}
