package ca.mcgill.ecse321group1.gamestore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ca.mcgill.ecse321group1.gamestore.model.Owner;

@Repository
public interface OwnerRepository extends CrudRepository<Owner, String>{

	public Owner findOwnerByUsername(String username);
	}