package de.mischok.academy.companydatabase.repository;

import de.mischok.academy.companydatabase.domain.Company;
import de.mischok.academy.companydatabase.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

	@Query("select e from Employee e where e.company = :company")
	List<Employee> getByCompany(@Param("company") Company company);
	
	List<Employee> findByCompany(Company company);

	List<Employee> findByFirstnameAndLastname(String firstname, String lastname);

	List<Employee> findByFirstnameLike(String firstname);

	List<Employee> findByFirstnameContaining(String firstname);

	List<Employee> findByFirstnameStartingWith(String firstname);

	List<Employee> findByCompanyAndFirstnameLikeOrLastnameLike(Company company, String firstname, String lastname);

	@Query("select e from Employee e where (e.firstname like :firstname or e.lastname like :lastname) and e.company = :company")
	List<Employee> getByCompanyAndNameLike(@Param("company") Company company, @Param("firstname") String firstname, @Param("lastname") String lastname);

	@Query("select e from Employee e where exists (select o from Office o where o.company = e.company and o.city = :city)")
	List<Employee> getAllWorkingInCompanyWithOfficeInCity(@Param("city") String city);
}
