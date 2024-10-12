package ca.mcgill.ecse321group1.gamestore.repository;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321group1.gamestore.model.VideoGame;

public interface VideoGameRepository extends CrudRepository<VideoGame, String> {

	public VideoGame findPersonByName(String name);
}