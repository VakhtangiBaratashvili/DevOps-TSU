package ge.tsu.devopstsu.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Employee {

    private Integer id;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private LocalDate startDate;

    private Status status;

}
