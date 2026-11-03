package ge.tsu.devopstsu.util;

import ge.tsu.devopstsu.domain.Employee;
import ge.tsu.devopstsu.domain.Status;
import ge.tsu.devopstsu.exception.AppException;
import ge.tsu.devopstsu.exception.ErrorCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeFileReader {

    public static List<Employee> readEmployees() {
        try {
            List<Employee> employees = new ArrayList<>();

            try (BufferedReader reader =
                         Files.newBufferedReader(Path.of("employees.txt"))) {

                String line;
                boolean header = true;

                while ((line = reader.readLine()) != null) {

                    if (header) {
                        header = false;
                        continue;
                    }

                    if (line.isBlank()) {
                        continue;
                    }

                    String[] data = line.trim().split("\\s+");

                    if (data.length != 6) {
                        throw new AppException(ErrorCode.SERVER_ERROR);
                    }

                    Employee employee = new Employee(
                            Integer.valueOf(data[0]),
                            data[1],
                            data[2],
                            LocalDate.parse(data[3]),
                            LocalDate.parse(data[4]),
                            Status.valueOf(data[5].toUpperCase())
                    );

                    employees.add(employee);
                }
            }

            return employees;
        } catch (IOException e) {
            throw new AppException(ErrorCode.SERVER_ERROR);
        }
    }

}
