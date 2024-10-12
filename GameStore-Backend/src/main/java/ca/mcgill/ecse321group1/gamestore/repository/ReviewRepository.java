package ca.mcgill.ecse321group1.gamestore.repository;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321group1.gamestore.model.Review;
import java.util.Optional;

public interface ReviewRepository extends CrudRepository<Review, Long> {
	Review findReviewById(Long id);
}