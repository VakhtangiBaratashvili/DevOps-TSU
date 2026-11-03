package ge.tsu.devopstsu.service;

import ge.tsu.devopstsu.domain.Employee;
import ge.tsu.devopstsu.dto.EmployeeResponseDTO;
import ge.tsu.devopstsu.dto.SearchEmployeeRequestDTO;
import ge.tsu.devopstsu.dto.mapper.EmployeeMapper;
import ge.tsu.devopstsu.exception.AppException;
import ge.tsu.devopstsu.exception.ErrorCode;
import ge.tsu.devopstsu.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    public Page<EmployeeResponseDTO> search(SearchEmployeeRequestDTO searchRequest) {

        List<Employee> filteredEmployees = employeeRepository.findAll()
                .stream()
                .filter(employee ->
                        searchRequest.getFirstName() == null ||
                                searchRequest.getFirstName().isBlank() ||
                                employee.getFirstName().toLowerCase()
                                        .contains(searchRequest.getFirstName().trim().toLowerCase())
                )
                .filter(employee ->
                        searchRequest.getLastName() == null ||
                                searchRequest.getLastName().isBlank() ||
                                employee.getLastName().toLowerCase()
                                        .contains(searchRequest.getLastName().trim().toLowerCase())
                )
                .filter(employee ->
                        searchRequest.getDateOfBirthFrom() == null ||
                                !employee.getDateOfBirth()
                                        .isBefore(searchRequest.getDateOfBirthFrom())
                )
                .filter(employee ->
                        searchRequest.getDateOfBirthTo() == null ||
                                !employee.getDateOfBirth()
                                        .isAfter(searchRequest.getDateOfBirthTo())
                )
                .filter(employee ->
                        searchRequest.getStartDateFrom() == null ||
                                !employee.getStartDate()
                                        .isBefore(searchRequest.getStartDateFrom())
                )
                .filter(employee ->
                        searchRequest.getStartDateTo() == null ||
                                !employee.getStartDate()
                                        .isAfter(searchRequest.getStartDateTo())
                )
                .filter(employee ->
                        searchRequest.getStatus() == null ||
                                employee.getStatus() == searchRequest.getStatus()
                )
                .toList();

        int page = Math.max(searchRequest.getPage(), 0);
        int size = searchRequest.getSize() > 0 ? searchRequest.getSize() : 20;

        Pageable pageable = PageRequest.of(page, size);

        int start = (int) pageable.getOffset();
        int end = Math.min(start + size, filteredEmployees.size());

        List<Employee> content = start >= filteredEmployees.size()
                ? List.of()
                : filteredEmployees.subList(start, end);

        return new PageImpl<>(content, pageable, filteredEmployees.size())
                .map(employeeMapper::mapToEmployeeResponseDTO);
    }

    public EmployeeResponseDTO findEmployeeById(Integer id) {

        Employee employee = employeeRepository.findAll()
                .stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new AppException(ErrorCode.NOT_FOUND));

        return employeeMapper.mapToEmployeeResponseDTO(employee);
    }

}
