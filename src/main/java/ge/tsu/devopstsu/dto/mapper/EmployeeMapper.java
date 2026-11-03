package ge.tsu.devopstsu.dto.mapper;

import ge.tsu.devopstsu.domain.Employee;
import ge.tsu.devopstsu.dto.EmployeeResponseDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class EmployeeMapper {

    public EmployeeResponseDTO mapToEmployeeResponseDTO(Employee employee) {
        if (employee == null) {
            return null;
        }

        EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO();
        employeeResponseDTO.setId(employee.getId());
        employeeResponseDTO.setFirstName(employee.getFirstName());
        employeeResponseDTO.setLastName(employee.getLastName());
        employeeResponseDTO.setStatus(employee.getStatus());
        employeeResponseDTO.setDateOfBirth(employee.getDateOfBirth());
        employeeResponseDTO.setAge(getYear(employee.getDateOfBirth()));
        employeeResponseDTO.setStartDate(employee.getStartDate());

        return employeeResponseDTO;
    }

    private Integer getYear(LocalDate date) {
        return Period.between(date, LocalDate.now()).getYears();
    }

}
