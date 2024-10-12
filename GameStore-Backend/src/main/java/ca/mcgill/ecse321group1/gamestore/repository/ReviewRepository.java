package ca.mcgill.ecse321group1.gamestore.repository;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321group1.gamestore.model.Review;

public interface ReviewRepository extends CrudRepository<Review, String> {

	public Review findPersonById(String id);
}