package de.mischok.academy.companydatabase.service;

import de.mischok.academy.companydatabase.domain.Company;
import de.mischok.academy.companydatabase.domain.Employee;
import de.mischok.academy.companydatabase.repository.CompanyRepository;
import de.mischok.academy.companydatabase.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Optional;

@Service
public class QueryServiceImpl implements QueryService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public List<Employee> getEmployeesOfCompany(Company company) {
        return employeeRepository.findByCompany(company);
    }

    @Override
    public List<Company> getCompaniesWithOfficeIn(String city) {
        return companyRepository.getCompaniesInCity(city);
    }

    @Override
    public List<Employee> getEmployeesWorkingInCompanyWithOfficeIn(String city) {
        return employeeRepository.getAllWorkingInCity(city);
    }

    @Override
    public List<Employee> filterEmployees(Optional<String> firstnameFilter, Optional<String> lastnameFilter, Optional<String> companyNameFilter) {
        Specification<Employee> spec = (Specification<Employee>) (root, query, builder) -> {
            Predicate result = builder.conjunction();

            if (firstnameFilter.isPresent()) {
                result = builder.and(result, builder.like(root.get("firstname"), "%" + firstnameFilter.get() + "%"));
            }

            if (lastnameFilter.isPresent()) {
                result = builder.and(result, builder.like(root.get("lastname"), "%" + lastnameFilter.get() + "%"));
            }

            if (companyNameFilter.isPresent()) {
                result = builder.and(result, builder.like(root.get("company").get("name"), "%" + companyNameFilter.get() + "%"));
            }

            query.distinct(true);

            return result;
        };

        return employeeRepository.findAll(spec);
    }

    @Override
    public Page<Employee> getEmployeePage(int pageSize, int pageIndex) {
        PageRequest pageRequest = PageRequest.of(pageIndex, pageSize);

        return employeeRepository.findAll(pageRequest);
    }

    @Override
    public List<Employee> getEmployeesWithName(String firstname, String lastname) {
        return employeeRepository.findByFirstnameAndLastname(firstname, lastname);
    }
}
