package ca.mcgill.ecse321group1.gamestore.repository;

import ca.mcgill.ecse321group1.gamestore.model.Offer;
import org.springframework.data.repository.CrudRepository;

public interface OfferRepository extends CrudRepository<Offer, Integer> {

	Offer findOfferById(int id);

}