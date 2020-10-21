package de.mischok.academy.companydatabase.service;

import de.mischok.academy.companydatabase.domain.Company;
import de.mischok.academy.companydatabase.domain.Employee;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface QueryService {

    List<Employee> getEmployeesOfCompany(Company company);

    List<Company> getCompaniesWithOfficeIn(String city);

    List<Employee> getEmployeesWorkingInCompanyWithOfficeIn(String city);

    List<Employee> filterEmployees(Optional<String> firstnameFilter, Optional<String> lastnameFilter, Optional<String> companyNameFilter);

    Page<Employee> getEmployeePage(int pageSize, int pageIndex);

    List<Employee> getEmployeesWithName(String firstname, String lastname);
}
