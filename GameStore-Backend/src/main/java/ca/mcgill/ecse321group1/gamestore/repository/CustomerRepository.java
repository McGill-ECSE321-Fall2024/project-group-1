package ca.mcgill.ecse321group1.gamestore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ca.mcgill.ecse321group1.gamestore.model.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, String> {

	public Customer findCustomerByUsername(String username);
}