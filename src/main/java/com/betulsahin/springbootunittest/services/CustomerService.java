package com.betulsahin.springbootunittest.services;

import com.betulsahin.springbootunittest.dto.CustomerDto;
import com.betulsahin.springbootunittest.models.Customer;
import com.betulsahin.springbootunittest.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public Optional<Customer> create(CustomerDto request){

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
        return customerRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CustomerDto findById(long id){
        final Customer customer =  customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        return this.convertToDto(customer);
    }

    private CustomerDto convertToDto(Customer customer){
        CustomerDto customerDto = new CustomerDto();

        if(customer != null){
            customerDto.setFirstName(customer.getFirstName());
            customerDto.setLastName(customer.getLastName());
            customerDto.setIdentificationNumber(customerDto.getIdentificationNumber());
            customerDto.setPhoneNumber(customer.getPhoneNumber());
        }

        return null;
    }

    @Transactional
    public void deleteById(long id){
        final Customer customer =  customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        customerRepository.deleteById(id);
    }
}
