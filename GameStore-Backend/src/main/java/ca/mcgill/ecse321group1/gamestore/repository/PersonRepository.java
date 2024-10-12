package ca.mcgill.ecse321group1.gamestore.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321group1.gamestore.model.Person;

public interface PersonRepository extends CrudRepository<Person, String> {

	Person findPersonByUsername(String username);

}