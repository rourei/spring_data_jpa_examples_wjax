package de.mischok.academy.companydatabase.service;

import de.mischok.academy.companydatabase.domain.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    List<Employee> filterEmployees(Optional<String> firstnameFilter, Optional<String> lastnameFilter, Optional<String> companyNameFilter);

    List<Employee> getEmployeesWithName(String firstname, String lastname);
}
