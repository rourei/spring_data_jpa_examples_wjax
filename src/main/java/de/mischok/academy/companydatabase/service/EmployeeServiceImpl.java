package de.mischok.academy.companydatabase.service;

import de.mischok.academy.companydatabase.domain.Company_;
import de.mischok.academy.companydatabase.domain.Employee;
import de.mischok.academy.companydatabase.domain.Employee_;
import de.mischok.academy.companydatabase.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> filterEmployees(Optional<String> firstnameFilter, Optional<String> lastnameFilter, Optional<String> companyNameFilter) {
        Specification<Employee> specification = (root, query, builder) -> {
            Predicate result = builder.conjunction();

            if (firstnameFilter.isPresent()) {
                result = builder.and(result, builder.like(root.get("firstname"), "%" + firstnameFilter.get() + "%"));
            }

            if (lastnameFilter.isPresent()) {
                result = builder.and(result, builder.like(root.get("lastname"), "%" + lastnameFilter.get() + "%"));
            }

            if (companyNameFilter.isPresent()) {
                result = builder.and(result, builder.like(root.get(Employee_.company).get(Company_.name), "%" + companyNameFilter.get() + "%"));
            }

            return result;
        };

        return employeeRepository.findAll(specification);
    }

    @Override
    public List<Employee> getEmployeesWithName(String firstname, String lastname) {
        return employeeRepository.findByFirstnameAndLastname(firstname, lastname);
    }
}
