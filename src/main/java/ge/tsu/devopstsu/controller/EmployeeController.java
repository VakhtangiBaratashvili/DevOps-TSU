package ge.tsu.devopstsu.controller;

import ge.tsu.devopstsu.dto.EmployeeResponseDTO;
import ge.tsu.devopstsu.dto.SearchEmployeeRequestDTO;
import ge.tsu.devopstsu.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rest/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<Page<EmployeeResponseDTO>> search(SearchEmployeeRequestDTO searchRequest) {
        return new ResponseEntity<>(employeeService.search(searchRequest), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getById(@PathVariable Integer id) {
        return new ResponseEntity<>(employeeService.findEmployeeById(id), HttpStatus.OK);
    }

}
