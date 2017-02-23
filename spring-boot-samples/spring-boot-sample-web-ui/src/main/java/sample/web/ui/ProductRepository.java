package sample.web.ui;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by Renee on 2/8/2017.
 */
public interface ProductRepository extends CrudRepository<Product, Long> {
}
