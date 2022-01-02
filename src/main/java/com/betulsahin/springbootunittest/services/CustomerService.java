package com.betulsahin.springbootunittest.services;

import com.betulsahin.springbootunittest.dto.CustomerDto;
import com.betulsahin.springbootunittest.exceptions.IdentificationNumberNotValidException;
import com.betulsahin.springbootunittest.models.Customer;
import com.betulsahin.springbootunittest.repositories.CustomerRepository;
import com.betulsahin.springbootunittest.services.validators.IdentificationNumberValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final IdentificationNumberValidator identificationNumberValidator;

    @Transactional
    public Optional<Customer> create(CustomerDto request){

        // Is the identification number valid ?
        boolean isValidIdentificationNumber = identificationNumberValidator.
                test(request.getIdentificationNumber());

        if (!isValidIdentificationNumber) {
            throw new IdentificationNumberNotValidException("Identification number not valid.");
        }

        Optional<Customer> customerOptional = customerRepository
                .findByIdentificationNumber(request.getIdentificationNumber());

        if(customerOptional.isPresent()){
            throw new EntityExistsException("This customer already exist!");
        }

        Customer newCustomer = new Customer();
        newCustomer.setFirstName(request.getFirstName());
        newCustomer.setLastName(request.getLastName());
        newCustomer.setPhoneNumber(request.getPhoneNumber());
        newCustomer.setIdentificationNumber(request.getIdentificationNumber());

        return Optional.of(customerRepository.save(newCustomer));
    }

    @Transactional(readOnly = true)
    public List<CustomerDto> findAll(){
        final List<CustomerDto> customerDtoArrayList = new ArrayList<CustomerDto>();
        customerRepository.findAll()
                .forEach(customer -> {

                    CustomerDto customerDto = new CustomerDto();
                    if(customer != null){
                        customerDto.setFirstName(customer.getFirstName());
                        customerDto.setLastName(customer.getLastName());
                        customerDto.setIdentificationNumber(customer.getIdentificationNumber());
                        customerDto.setPhoneNumber(customer.getPhoneNumber());
                    }

                    customerDtoArrayList.add(customerDto);

                });

        return customerDtoArrayList;
    }

    @Transactional(readOnly = true)
    public CustomerDto findById(long id){
        final Customer customer =  customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        CustomerDto customerDto = new CustomerDto();
        if(customer != null){
            customerDto.setFirstName(customer.getFirstName());
            customerDto.setLastName(customer.getLastName());
            customerDto.setIdentificationNumber(customer.getIdentificationNumber());
            customerDto.setPhoneNumber(customer.getPhoneNumber());
        }

        return customerDto;
    }

    @Transactional
    public void deleteById(long id){
        final Customer customer =  customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        customerRepository.deleteById(id);
    }
}
