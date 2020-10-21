package de.mischok.academy.companydatabase.repository;

import de.mischok.academy.companydatabase.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

	@Query("select c from Company c where exists (select o from Office o where o.company = c and o.city = :city)")
	List<Company> getCompaniesInCity(@Param("city") String city);
}