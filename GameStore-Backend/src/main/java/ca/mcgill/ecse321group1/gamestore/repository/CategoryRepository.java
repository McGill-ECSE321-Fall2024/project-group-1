package ca.mcgill.ecse321group1.gamestore.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321group1.gamestore.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

	Category findCategoryById(int id);

}