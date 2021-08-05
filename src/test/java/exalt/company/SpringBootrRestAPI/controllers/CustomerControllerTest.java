package exalt.company.SpringBootrRestAPI.controllers;

import exalt.company.SpringBootrRestAPI.models.Customer;
import exalt.company.SpringBootrRestAPI.repo.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;


public class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setup() {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getCustomers_returnNull_Fail() {

        List<Customer> testCustomerList = new ArrayList<>();
        Customer customer1 = new Customer(1, "mahran", "yacoub");
        Customer customer2 = new Customer(2, "mahran2", "yacoub2");
        testCustomerList.add(customer1);
        testCustomerList.add(customer2);

        // Mocking out the Customer service
        Mockito.when(customerRepository.findAll()).thenReturn(testCustomerList);

        List<Customer> customerList = customerController.getCustomers();

        Assertions.assertEquals(testCustomerList, customerList);

    }

    @Test
    void getCustomerDetails_NotCorrectResponse_Fail() {

        Customer testCustomer = new Customer(1, "mahran", "yacoub");
        Optional<Customer> optionalCustomer = Optional.of(testCustomer);

        Mockito.when(customerRepository.findById(anyInt())).thenReturn(optionalCustomer);
        Mockito.when(customerRepository.existsById(anyInt())).thenReturn(true);

        Customer customer1 = customerController.getCustomerDetails(anyInt()).getBody();
        Assertions.assertEquals(optionalCustomer.get(), customer1);

        Mockito.when(customerRepository.existsById(anyInt())).thenReturn(false);
        HttpStatus customer2 = customerController.getCustomerDetails(anyInt()).getStatusCode();
        Assertions.assertEquals(HttpStatus.NOT_FOUND, customer2);

    }

    @Test
    void createNewCustomer_NotCorrectResponse_Fail() {

        Customer newCustomer = new Customer(1, "Mahran", "Yacoub");
        Mockito.when(customerRepository.save(any())).thenReturn(newCustomer);
        Mockito.when(customerRepository.existsById(anyInt())).thenReturn(false);

        Customer customer = customerController.createNewCustomer(newCustomer).getBody();
        Assertions.assertEquals(newCustomer, customer);

        Mockito.when(customerRepository.existsById(anyInt())).thenReturn(true);
        HttpStatus status = customerController.createNewCustomer(newCustomer).getStatusCode();
        Assertions.assertEquals(HttpStatus.CONFLICT, status);

    }

    @Test
    void updateExistCustomer_NotCorrectResponse_Fail() {

        Customer customer = new Customer(1,"mahran","yacoub");
        Mockito.when(customerRepository.existsById(anyInt())).thenReturn(false);
        HttpStatus notFoundStatus = customerController.updateExistCustomer(customer,1).getStatusCode();
        Assertions.assertEquals(HttpStatus.NOT_FOUND,notFoundStatus);

        Mockito.when(customerRepository.existsById(anyInt())).thenReturn(true);
        Mockito.when(customerRepository.save(any())).thenReturn(customer);
        HttpStatus okStatus = customerController.updateExistCustomer(customer,1).getStatusCode();
        Assertions.assertEquals(HttpStatus.OK,okStatus);

    }

    @Test
    void deleteCustomer_NotCorrectResponse_Fail() {

        Mockito.when(customerRepository.existsById(anyInt())).thenReturn(false);
        ResponseEntity<Customer> responseEntity1 = customerController.deleteCustomer(anyInt());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity1.getStatusCode());

        Mockito.when(customerRepository.existsById(anyInt())).thenReturn(true);
        Customer customer = new Customer(1, "meho", "yacoub");
        Optional<Customer> optionalCustomer = Optional.of(customer);

        Mockito.when(customerRepository.findById(anyInt())).thenReturn(optionalCustomer);
        customerController.deleteCustomer(anyInt());
        Mockito.verify(customerRepository).deleteById(anyInt());

    }

}