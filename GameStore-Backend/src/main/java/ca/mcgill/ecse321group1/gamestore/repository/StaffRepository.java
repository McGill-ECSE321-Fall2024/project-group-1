package ca.mcgill.ecse321group1.gamestore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ca.mcgill.ecse321group1.gamestore.model.Staff;

@Repository
public interface StaffRepository extends CrudRepository<Staff, String> {

	public Staff findStaffByUsername(String username);
}