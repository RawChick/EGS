package sample.web.ui;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import java.util.ArrayList;

/**
 * Created by whees on 07-Feb-17.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue
    private Long id;

  //  @NotEmpty(message = "Item name is required.")
    private String name;

   // @NotEmpty(message = "Price is required.")
    private int price;

public Product(String name, int price){
 this.name = name;
 this.price = price;
}

public Product(Product p) {
    this.name = p.name;
    this.price = p.price;
}

}
