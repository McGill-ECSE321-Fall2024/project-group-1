package ca.mcgill.ecse321group1.gamestore.repository;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321group1.gamestore.model.Order;

public interface OrderRepository extends CrudRepository<Order, String> {

	public Order findOrderById(String id);
}