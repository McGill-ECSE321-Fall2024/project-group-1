package ca.mcgill.ecse321group1.gamestore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ca.mcgill.ecse321group1.gamestore.model.VideoGame;

@Repository
public interface VideoGameRepository extends CrudRepository<VideoGame, String> {

	public VideoGame findPersonByName(String name);
}