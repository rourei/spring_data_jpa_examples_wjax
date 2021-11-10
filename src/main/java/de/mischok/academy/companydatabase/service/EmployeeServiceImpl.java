package de.mischok.academy.companydatabase.service;

import de.mischok.academy.companydatabase.domain.Company;
import de.mischok.academy.companydatabase.domain.Company_;
import de.mischok.academy.companydatabase.domain.Employee;
import de.mischok.academy.companydatabase.domain.Employee_;
import de.mischok.academy.companydatabase.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> filterEmployees(Optional<String> firstnameFilter, Optional<String> lastnameFilter, Optional<String> companyNameFilter) {
        Employee probe = new Employee();

        firstnameFilter.ifPresent(probe::setFirstname);
        lastnameFilter.ifPresent(probe::setLastname);
        companyNameFilter.ifPresent(companyNameFilterString -> probe.setCompany(Company.builder().name(companyNameFilterString).build()));

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase("firstname", "lastname", "company.name");

        Example<Employee> example = Example.of(probe, exampleMatcher);

        return employeeRepository.findAll(example);
    }

    @Override
    public List<Employee> filterEmployees(Optional<String> firstnameFilter, Optional<String> lastnameFilter, Optional<String> companyNameFilter, Optional<Integer> olderThan) {
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

            if (olderThan.isPresent()) {
                result = builder.and(result, builder.greaterThan(root.get("age"), olderThan.get()));
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
