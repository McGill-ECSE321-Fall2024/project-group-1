package ca.mcgill.ecse321group1.gamestore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ca.mcgill.ecse321group1.gamestore.model.Reply;

@Repository
public interface ReplyRepository extends CrudRepository<Reply, String> {

	public Reply findReplyById(String id);
}