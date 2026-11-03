package ge.tsu.devopstsu.dto;

import ge.tsu.devopstsu.domain.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EmployeeResponseDTO {

    private Integer id;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private Integer age;

    private LocalDate startDate;

    private Status status;

}
