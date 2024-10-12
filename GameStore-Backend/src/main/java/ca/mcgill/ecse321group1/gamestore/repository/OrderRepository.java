package ca.mcgill.ecse321group1.gamestore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ca.mcgill.ecse321group1.gamestore.model.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, String> {

	public Order findOrderById(String id);
}