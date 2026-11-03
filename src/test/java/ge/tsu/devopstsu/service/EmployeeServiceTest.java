package ge.tsu.devopstsu.service;


import ge.tsu.devopstsu.domain.Employee;
import ge.tsu.devopstsu.dto.EmployeeResponseDTO;
import ge.tsu.devopstsu.dto.SearchEmployeeRequestDTO;
import ge.tsu.devopstsu.dto.mapper.EmployeeMapper;
import ge.tsu.devopstsu.exception.AppException;
import ge.tsu.devopstsu.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee1;
    private Employee employee2;
    private Employee employee3;

    private EmployeeResponseDTO response1;
    private EmployeeResponseDTO response2;
    private EmployeeResponseDTO response3;

    @BeforeEach
    void setUp() {
        employee1 = mock(Employee.class);
        employee2 = mock(Employee.class);
        employee3 = mock(Employee.class);

        response1 = mock(EmployeeResponseDTO.class);
        response2 = mock(EmployeeResponseDTO.class);
        response3 = mock(EmployeeResponseDTO.class);

        when(employee1.getId()).thenReturn(1);
        when(employee1.getFirstName()).thenReturn("John");
        when(employee1.getLastName()).thenReturn("Smith");
        when(employee1.getDateOfBirth()).thenReturn(
                LocalDate.of(1990, 5, 15));
        when(employee1.getStartDate()).thenReturn(
                LocalDate.of(2020, 1, 10));

        when(employee2.getId()).thenReturn(2);
        when(employee2.getFirstName()).thenReturn("Jane");
        when(employee2.getLastName()).thenReturn("Doe");
        when(employee2.getDateOfBirth()).thenReturn(
                LocalDate.of(1995, 8, 20));
        when(employee2.getStartDate()).thenReturn(
                LocalDate.of(2021, 3, 15));

        when(employee3.getId()).thenReturn(3);
        when(employee3.getFirstName()).thenReturn("Johnny");
        when(employee3.getLastName()).thenReturn("Brown");
        when(employee3.getDateOfBirth()).thenReturn(
                LocalDate.of(1988, 2, 10));
        when(employee3.getStartDate()).thenReturn(
                LocalDate.of(2019, 6, 1));

        when(employeeMapper.mapToEmployeeResponseDTO(employee1))
                .thenReturn(response1);
        when(employeeMapper.mapToEmployeeResponseDTO(employee2))
                .thenReturn(response2);
        when(employeeMapper.mapToEmployeeResponseDTO(employee3))
                .thenReturn(response3);

        when(employeeRepository.findAll())
                .thenReturn(List.of(employee1, employee2, employee3));
    }

    @Test
    void searchShouldReturnAllEmployees_whenNoFiltersProvided() {

        SearchEmployeeRequestDTO request =
                mock(SearchEmployeeRequestDTO.class);

        Page<EmployeeResponseDTO> result =
                employeeService.search(request);

        assertEquals(3, result.getTotalElements());
        assertEquals(3, result.getContent().size());

        verify(employeeRepository).findAll();
    }

    @Test
    void searchShouldFilterByFirstName() {

        SearchEmployeeRequestDTO request =
                mock(SearchEmployeeRequestDTO.class);

        when(request.getFirstName()).thenReturn("john");

        Page<EmployeeResponseDTO> result =
                employeeService.search(request);

        assertEquals(2, result.getTotalElements());
        assertTrue(result.getContent().contains(response1));
        assertTrue(result.getContent().contains(response3));
    }

    @Test
    void searchShouldFilterByLastName() {

        SearchEmployeeRequestDTO request =
                mock(SearchEmployeeRequestDTO.class);

        when(request.getLastName()).thenReturn("smith");

        Page<EmployeeResponseDTO> result =
                employeeService.search(request);

        assertEquals(1, result.getTotalElements());
        assertEquals(response1, result.getContent().getFirst());
    }

    @Test
    void searchShouldIgnoreBlankFirstName() {

        SearchEmployeeRequestDTO request =
                mock(SearchEmployeeRequestDTO.class);

        when(request.getFirstName()).thenReturn("   ");

        Page<EmployeeResponseDTO> result =
                employeeService.search(request);

        assertEquals(3, result.getTotalElements());
    }

    @Test
    void searchShouldFilterByDateOfBirthFrom() {

        SearchEmployeeRequestDTO request =
                mock(SearchEmployeeRequestDTO.class);

        when(request.getDateOfBirthFrom())
                .thenReturn(LocalDate.of(1990, 1, 1));

        Page<EmployeeResponseDTO> result =
                employeeService.search(request);

        assertEquals(2, result.getTotalElements());
        assertTrue(result.getContent().contains(response1));
        assertTrue(result.getContent().contains(response2));
    }

    @Test
    void searchShouldFilterByDateOfBirthTo() {

        SearchEmployeeRequestDTO request =
                mock(SearchEmployeeRequestDTO.class);

        when(request.getDateOfBirthTo())
                .thenReturn(LocalDate.of(1990, 5, 15));

        Page<EmployeeResponseDTO> result =
                employeeService.search(request);

        assertEquals(2, result.getTotalElements());
    }

    @Test
    void searchShouldFilterByStartDateFrom() {

        SearchEmployeeRequestDTO request =
                mock(SearchEmployeeRequestDTO.class);

        when(request.getStartDateFrom())
                .thenReturn(LocalDate.of(2020, 1, 1));

        Page<EmployeeResponseDTO> result =
                employeeService.search(request);

        assertEquals(2, result.getTotalElements());
    }

    @Test
    void searchShouldFilterByStartDateTo() {

        SearchEmployeeRequestDTO request =
                mock(SearchEmployeeRequestDTO.class);

        when(request.getStartDateTo())
                .thenReturn(LocalDate.of(2020, 12, 31));

        Page<EmployeeResponseDTO> result =
                employeeService.search(request);

        assertEquals(2, result.getTotalElements());
    }

    @Test
    void searchShouldCombineMultipleFilters() {

        SearchEmployeeRequestDTO request =
                mock(SearchEmployeeRequestDTO.class);

        when(request.getFirstName()).thenReturn("john");
        when(request.getLastName()).thenReturn("smith");

        Page<EmployeeResponseDTO> result =
                employeeService.search(request);

        assertEquals(1, result.getTotalElements());
        assertEquals(response1, result.getContent().getFirst());
    }

    @Test
    void searchShouldReturnCorrectPage() {

        SearchEmployeeRequestDTO request =
                mock(SearchEmployeeRequestDTO.class);

        when(request.getPage()).thenReturn(1);
        when(request.getSize()).thenReturn(2);

        Page<EmployeeResponseDTO> result =
                employeeService.search(request);

        assertEquals(3, result.getTotalElements());
        assertEquals(1, result.getNumber());
        assertEquals(2, result.getSize());
        assertEquals(1, result.getNumberOfElements());
        assertEquals(response3, result.getContent().getFirst());
    }

    @Test
    void searchShouldReturnEmptyPageWhenPageIsOutOfRange() {

        SearchEmployeeRequestDTO request =
                mock(SearchEmployeeRequestDTO.class);

        when(request.getPage()).thenReturn(10);
        when(request.getSize()).thenReturn(2);

        Page<EmployeeResponseDTO> result =
                employeeService.search(request);

        assertTrue(result.isEmpty());
        assertEquals(3, result.getTotalElements());
    }

    @Test
    void searchShouldUseDefaultPageSizeWhenSizeIsInvalid() {

        SearchEmployeeRequestDTO request =
                mock(SearchEmployeeRequestDTO.class);

        when(request.getSize()).thenReturn(0);

        Page<EmployeeResponseDTO> result =
                employeeService.search(request);

        assertEquals(20, result.getSize());
        assertEquals(3, result.getTotalElements());
    }

    @Test
    void searchShouldNormalizeNegativePageToZero() {

        SearchEmployeeRequestDTO request =
                mock(SearchEmployeeRequestDTO.class);

        when(request.getPage()).thenReturn(-1);

        Page<EmployeeResponseDTO> result =
                employeeService.search(request);

        assertEquals(0, result.getNumber());
        assertEquals(3, result.getTotalElements());
    }

    @Test
    void findEmployeeByIdShouldReturnEmployeeWhenEmployeeExists() {

        EmployeeResponseDTO result =
                employeeService.findEmployeeById(1);

        assertEquals(response1, result);

        verify(employeeRepository).findAll();
        verify(employeeMapper)
                .mapToEmployeeResponseDTO(employee1);
    }

    @Test
    void findEmployeeByIdShouldThrowExceptionWhenEmployeeDoesNotExist() {

        assertThrows(
                AppException.class,
                () -> employeeService.findEmployeeById(999)
        );

        verify(employeeMapper, never())
                .mapToEmployeeResponseDTO(any());
    }
}
