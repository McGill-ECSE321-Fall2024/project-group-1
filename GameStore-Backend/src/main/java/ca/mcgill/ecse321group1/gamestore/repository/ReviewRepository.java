package ca.mcgill.ecse321group1.gamestore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ca.mcgill.ecse321group1.gamestore.model.Review;

@Repository
public interface ReviewRepository extends CrudRepository<Review, String> {

	public Review findPersonById(String id);
}