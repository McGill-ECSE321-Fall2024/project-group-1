package ca.mcgill.ecse321group1.gamestore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ca.mcgill.ecse321group1.gamestore.model.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, String> {

	public Person findPersonByUsername(String username);
}