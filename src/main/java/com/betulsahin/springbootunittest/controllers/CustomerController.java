package com.betulsahin.springbootunittest.controllers;

import com.betulsahin.springbootunittest.dto.CustomerDto;
import com.betulsahin.springbootunittest.models.Customer;
import com.betulsahin.springbootunittest.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RequestMapping("api/v1/customers")
@RestController
@RequiredArgsConstructor
public class CustomerController {

    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody CustomerDto request){
        Optional<Customer> customerOptional = customerService.create(request);

        if(!customerOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(customerOptional.get(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAll(){
        final List<CustomerDto> customerDtoList = customerService.findAll();

        return new ResponseEntity<>(customerDtoList, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomerDto> getById(@PathVariable long id){
        final CustomerDto customerDto = customerService.findById(id);
        if(customerDto == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(customerDto, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id){
        try{
            customerService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
