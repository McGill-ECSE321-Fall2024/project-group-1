package ca.mcgill.ecse321group1.gamestore.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321group1.gamestore.model.Category;
import ca.mcgill.ecse321group1.gamestore.repository.CategoryRepository;
import jakarta.transaction.Transactional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepo;

    /**Retrieves a Category object based on lookup by id*/
    @Transactional
    public Category getCategory(int cid) {
        Category c = categoryRepo.findCategoryById(cid);
        if (c == null) {
            throw new IllegalArgumentException("There is no category with ID " + cid + ".");
        }
        return c;
    }

    /**Creates a new Category object with given attributes, and stores it into the databases*/
    @Transactional
    public Category createCategory(String name, String description) {
        if (name == null || name.length() < 3) {
            throw new IllegalArgumentException("Category names must be at least 3 characters long!");
        }
        if (description == null || description.length() < 3) {
            throw new IllegalArgumentException("Category descriptions must be at least 3 characters long!");
        }
        Iterator<Category> iter = categoryRepo.findAll().iterator();
        iter.forEachRemaining(a -> {
            if (a.getName().equals(name)) throw new IllegalArgumentException("Category with name \"" + name + "\" already exists!");
        });

        Category categoryToCreate = new Category();
        categoryToCreate.setName(name);
        categoryToCreate.setDescription(description);
        return categoryRepo.save(categoryToCreate);
    }

    /**Retrives a Category by ID, and modifies its attributes according to editCategory's arguments before re-storing*/
    @Transactional
    public Category editCategory(int id, String name, String description) {
        Category category = (Category) categoryRepo.findById(id).orElse(null);

        if (category == null)
            throw new IllegalArgumentException(id + " does not correspond to an extant Category!");

        if (name == null || name.length() < 3) {
            throw new IllegalArgumentException("Category names must be at least 3 characters long!");
        }
        if (description == null || description.length() < 3) {
            throw new IllegalArgumentException("Category descriptions must be at least 3 characters long!");
        }

        category.setName(name);
        category.setDescription(description);
        return categoryRepo.save(category);
    }
    /**Deletes a Category, permanently.*/
    @Transactional
    public void deleteCategory(int id) {
        if (!categoryRepo.existsById(id))
            throw new IllegalArgumentException(id + " cannot be deleted as it does not correspond to an extant Category!");
        categoryRepo.deleteById(id);//no error checking technically necessary but it's best to let people know when they are wrong
    }
}