package ge.tsu.devopstsu.dto;

import ge.tsu.devopstsu.domain.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SearchEmployeeRequestDTO {

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirthFrom;

    private LocalDate dateOfBirthTo;

    private LocalDate startDateFrom;

    private LocalDate startDateTo;

    private Status status;

    private int page = 0;

    private int size = 20;

}
