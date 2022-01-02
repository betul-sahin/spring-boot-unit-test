package com.betulsahin.springbootunittest.services;

import com.betulsahin.springbootunittest.dto.CustomerDto;
import com.betulsahin.springbootunittest.exceptions.IdentificationNumberNotValidException;
import com.betulsahin.springbootunittest.models.Customer;
import com.betulsahin.springbootunittest.repositories.CustomerRepository;
import com.betulsahin.springbootunittest.services.validators.IdentificationNumberValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    CustomerRepository mockCustomerRepository;

    @Mock
    IdentificationNumberValidator mockIdentificationNumberValidator;

    @InjectMocks
    CustomerService underTest;

    @Test
    void create_shoulCreateNewCustomer(){

        // given
        Customer expected = new Customer();
        expected.setIdentificationNumber("25252567348");
        expected.setPhoneNumber("5554443322");

        CustomerDto request = new CustomerDto();

        // valid identification number
        when(mockIdentificationNumberValidator.test(any())).
                thenReturn(true);

        // no customer with this identificationNumber
        when(mockCustomerRepository.findByIdentificationNumber(any())).
                thenReturn(Optional.empty());

        // mocking save method
        when(mockCustomerRepository.save(any())).thenReturn(expected);

        // when
        Customer actual = underTest.create(request).get();

        // then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected, actual),
                () -> assertEquals(expected.getIdentificationNumber(), actual.getIdentificationNumber()),
                () -> assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber())
        );
    }

    @Test
    void create_shoulThrowException_whenIdentificationNumberNotValid(){
        // given
        CustomerDto request = new CustomerDto();

        // NOT valid identification number
        when(mockIdentificationNumberValidator.test(any())).
                thenReturn(false);

        // when
        Executable executable = () -> underTest.create(request).get();

        // then
        assertThrows(IdentificationNumberNotValidException.class, executable);
    }

    @Test
    void create_shouldThrowException_whenCustomerIsAlreadyExist(){
        // given
        Customer customer = new Customer();
        customer.setIdentificationNumber("25252567348");
        customer.setPhoneNumber("5554443322");

        CustomerDto request = new CustomerDto();

        // valid identification number
        when(mockIdentificationNumberValidator.test(any())).
                thenReturn(true);

        // no customer with this identificationNumber
        when(mockCustomerRepository.findByIdentificationNumber(any())).
                thenReturn(Optional.of(customer));

        // when
        Executable executable = () -> underTest.create(request).get();

        // then
        assertThrows(EntityExistsException.class, executable);
    }

    @Test
    void getAll_itShouldReturnCustomerDtoList(){
        // given
        Customer customer = new Customer();
        customer.setIdentificationNumber("25252567348");
        customer.setPhoneNumber("5554443322");
        List<Customer> customerList = Collections.singletonList(customer);

        CustomerDto response = new CustomerDto();
        response.setIdentificationNumber(customer.getIdentificationNumber());
        response.setPhoneNumber(customer.getPhoneNumber());

        // mocking findAll method
        when(mockCustomerRepository.findAll()).thenReturn(customerList);

        // when
        List<CustomerDto> actual = underTest.findAll();

        // then
        assertEquals(customer.getIdentificationNumber(), actual.get(0).getIdentificationNumber());
        assertEquals(customer.getPhoneNumber(), actual.get(0).getPhoneNumber());
    }

    @Test
    void getById_itShouldReturnCustomerDto(){
        // given
        Customer customer = new Customer();
        customer.setIdentificationNumber("25252567348");
        customer.setPhoneNumber("5554443322");

        CustomerDto response = new CustomerDto();
        response.setIdentificationNumber(customer.getIdentificationNumber());
        response.setPhoneNumber(customer.getPhoneNumber());


        when(mockCustomerRepository.findById(anyLong())).
                thenReturn(Optional.of(customer));

        // when
        CustomerDto actual = underTest.findById(1L);

        // then
        assertEquals(response, actual);
    }

    @Test
    void getById_itShouldThrowNotFound_whenCustomerIdNotFound(){
        // given
        when(mockCustomerRepository.findById(anyLong())).
                thenReturn(Optional.empty());

        // when
        Executable executable = () -> underTest.findById(1L);

        // then
        assertThrows(EntityNotFoundException.class, executable);
    }

    @Test
    void deleteById_itShouldReturnStatusNotFound_whenCustomerIdNotExist(){
        // given
        when(mockCustomerRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        Executable executable = () -> underTest.deleteById(1L);

        // then
        assertThrows(EntityNotFoundException.class, executable);
    }

}