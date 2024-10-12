package ca.mcgill.ecse321group1.gamestore.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321group1.gamestore.model.CustomerOrder;

public interface CustomerOrderRepository extends CrudRepository<CustomerOrder, Integer> {

	CustomerOrder findCustomerOrderById(int id);

}