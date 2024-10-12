package ca.mcgill.ecse321group1.gamestore.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321group1.gamestore.model.Owner;

public interface OwnerRepository extends CrudRepository<Owner, String> {

	Owner findOwnerByUsername(String username);

}