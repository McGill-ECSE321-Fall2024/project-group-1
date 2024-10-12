package ca.mcgill.ecse321group1.gamestore.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321group1.gamestore.model.Reply;

public interface ReplyRepository extends CrudRepository<Reply, Long> {

	Reply findReplyById(Long id);

}