package ca.mcgill.ecse321group1.gamestore.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321group1.gamestore.model.Staff;

public interface StaffRepository extends CrudRepository<Staff, Integer> {

	Staff findStaffById(int id);

}