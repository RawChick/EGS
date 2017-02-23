package sample.web.ui;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by Renee on 2/8/2017.
 */
public interface OrderRepository extends CrudRepository<Order, Long> {
}
