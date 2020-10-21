package de.mischok.academy.companydatabase.service;

import de.mischok.academy.companydatabase.domain.Company;
import de.mischok.academy.companydatabase.domain.Employee;
import de.mischok.academy.companydatabase.domain.Office;
import de.mischok.academy.companydatabase.repository.CompanyRepository;
import de.mischok.academy.companydatabase.repository.EmployeeRepository;
import de.mischok.academy.companydatabase.repository.OfficeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static de.mischok.academy.companydatabase.service.matchers.HasAnyEntityItem.hasAnyEntityItem;
import static de.mischok.academy.companydatabase.service.matchers.HasEntityItems.hasEntityItems;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
public class QueryServiceTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private OfficeRepository officeRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private QueryService queryService;

    Company neosteel;
    Company replant;
    Company innovatic;

    Office london;
    Office newYork;
    Office berlinUnterDenLinden;
    Office berlinPotsdammerPlatz;
    Office berlinKudamm;
    Office amsterdam;

    Employee victoriaPullman;
    Employee trevorPaige;
    Employee kylieButler;
    Employee gordonMorgan;
    Employee neilUnderwood;
    Employee elizabethLangdon;
    Employee colinOgden;
    Employee pippaRussell;
    Employee maryRoberts;
    Employee alexanderMitchell;

    @BeforeEach
    public void setUp() {
        employeeRepository.deleteAll();
        employeeRepository.flush();
        officeRepository.deleteAll();
        officeRepository.flush();
        companyRepository.deleteAll();
        companyRepository.flush();

        neosteel = Company.builder().name("NEOSTEEL").build();
        neosteel = companyRepository.saveAndFlush(neosteel);
        replant = Company.builder().name("REplant Gardening").build();
        replant = companyRepository.saveAndFlush(replant);
        innovatic = Company.builder().name("innovatic. design.").build();
        innovatic = companyRepository.saveAndFlush(innovatic);

        london = Office.builder().city("London").street("Waterloo Place").company(neosteel).build();
        london = officeRepository.saveAndFlush(london);
        newYork = Office.builder().city("New York").street("Central Avenue").company(neosteel).build();
        newYork = officeRepository.saveAndFlush(newYork);
        berlinPotsdammerPlatz = Office.builder().city("Berlin").street("Potsdammer Platz").company(neosteel).build();
        berlinPotsdammerPlatz = officeRepository.saveAndFlush(berlinPotsdammerPlatz);

        berlinUnterDenLinden = Office.builder().city("Berlin").street("Unter den Linden").company(replant).build();
        berlinUnterDenLinden = officeRepository.saveAndFlush(berlinUnterDenLinden);

        berlinKudamm = Office.builder().city("Berlin").street("Kurfürstendamm").company(innovatic).build();
        berlinKudamm = officeRepository.saveAndFlush(berlinKudamm);
        amsterdam = Office.builder().city("Amsterdam").street("Prinsengracht").company(innovatic).build();
        amsterdam = officeRepository.saveAndFlush(amsterdam);

        victoriaPullman = Employee.builder().firstname("Victoria").lastname("Pullman").company(neosteel).build();
        victoriaPullman = employeeRepository.saveAndFlush(victoriaPullman);
        trevorPaige = Employee.builder().firstname("Trevor").lastname("Paige").company(neosteel).build();
        trevorPaige = employeeRepository.saveAndFlush(trevorPaige);
        kylieButler = Employee.builder().firstname("Kylie").lastname("Butler").company(neosteel).build();
        kylieButler = employeeRepository.saveAndFlush(kylieButler);
        gordonMorgan = Employee.builder().firstname("Gordon").lastname("Morgan").company(neosteel).build();
        gordonMorgan = employeeRepository.saveAndFlush(gordonMorgan);

        neilUnderwood = Employee.builder().firstname("Neil").lastname("Underwood").company(replant).build();
        neilUnderwood = employeeRepository.saveAndFlush(neilUnderwood);
        elizabethLangdon = Employee.builder().firstname("Elizabeth").lastname("Langdon").company(replant).build();
        elizabethLangdon = employeeRepository.saveAndFlush(elizabethLangdon);

        colinOgden = Employee.builder().firstname("Colin").lastname("Ogden").company(innovatic).build();
        colinOgden = employeeRepository.saveAndFlush(colinOgden);
        pippaRussell = Employee.builder().firstname("Pippa").lastname("Russel").company(innovatic).build();
        pippaRussell = employeeRepository.saveAndFlush(pippaRussell);
        maryRoberts = Employee.builder().firstname("Mary").lastname("Roberts").company(innovatic).build();
        maryRoberts = employeeRepository.saveAndFlush(maryRoberts);
        alexanderMitchell = Employee.builder().firstname("Alexander").lastname("Mitchell").company(innovatic).build();
        alexanderMitchell = employeeRepository.saveAndFlush(alexanderMitchell);
    }

    @Test
    public void testGetEmployeesByCompany() {
        List<Employee> employeesOfReplant = queryService.getEmployeesOfCompany(innovatic);

        assertThat(employeesOfReplant, hasSize(4));
        assertThat(employeesOfReplant, hasEntityItems(colinOgden, pippaRussell, maryRoberts, alexanderMitchell));
        assertThat(employeesOfReplant, not(hasAnyEntityItem(victoriaPullman, trevorPaige, kylieButler, gordonMorgan, neilUnderwood, elizabethLangdon)));
    }

    @Test
    public void testGetCompaniesInBerlin() {
        List<Company> companiesWithOfficeInBerlin = queryService.getCompaniesWithOfficeIn("Berlin");

        assertThat(companiesWithOfficeInBerlin, hasSize(3));
        assertThat(companiesWithOfficeInBerlin, hasEntityItems(neosteel, replant, innovatic));
    }

    @Test
    public void testGetCompaniesInNewYork() {
        List<Company> companiesWithOfficeInNewYork = queryService.getCompaniesWithOfficeIn("New York");

        assertThat(companiesWithOfficeInNewYork, hasSize(1));
        assertThat(companiesWithOfficeInNewYork, hasEntityItems(neosteel));
        assertThat(companiesWithOfficeInNewYork, not(hasAnyEntityItem(replant, innovatic)));
    }

    @Test
    public void testEmployeesWorkingInCompanyWithOfficeIn() {
        List<Employee> employeesWorkingInCompanyWithOfficeInAmsterdam = queryService.getEmployeesWorkingInCompanyWithOfficeIn("Amsterdam");

        assertThat(employeesWorkingInCompanyWithOfficeInAmsterdam, hasSize(4));

        assertThat(employeesWorkingInCompanyWithOfficeInAmsterdam, hasEntityItems(colinOgden, pippaRussell, maryRoberts, alexanderMitchell));
        assertThat(employeesWorkingInCompanyWithOfficeInAmsterdam, not(hasAnyEntityItem(victoriaPullman, trevorPaige, kylieButler, gordonMorgan, neilUnderwood, elizabethLangdon)));
    }

    @Test
    public void testEmployeeFilter() {
        List<Employee> filtered = queryService.filterEmployees(Optional.empty(), Optional.of("LL"), Optional.of("inno"));

        assertThat(filtered, hasSize(2));

        assertThat(filtered, hasEntityItems(pippaRussell, alexanderMitchell));
    }

    @Test
    public void testPage() {
        // FIXME: Implementation
    }

}
