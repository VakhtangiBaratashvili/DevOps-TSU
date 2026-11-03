package ge.tsu.devopstsu.repository;

import ge.tsu.devopstsu.domain.Employee;
import ge.tsu.devopstsu.util.EmployeeFileReader;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRepository {

    public List<Employee> findAll() {
        return EmployeeFileReader.readEmployees();
    }

}
