package ca.mcgill.ecse321group1.gamestore.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321group1.gamestore.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

	Customer findCustomerById(int id);

}