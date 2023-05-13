package itstoony.com.github.customermicroservice.controller;

import itstoony.com.github.customermicroservice.dto.CustomerDTO;
import itstoony.com.github.customermicroservice.dto.RegisteringCustomerDTO;
import itstoony.com.github.customermicroservice.dto.SearchingEmailRecord;
import itstoony.com.github.customermicroservice.entity.Customer;
import itstoony.com.github.customermicroservice.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<CustomerDTO> register(@RequestBody @Valid RegisteringCustomerDTO dto) {
        Customer customer = customerService.register(dto);
        CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(customerDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(customerDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> findByID(@PathVariable Long id) {
        Customer customer = customerService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found by ID: " + id));

        CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);

        return ResponseEntity.ok(customerDTO);
    }

    @GetMapping(params = "email")
    public ResponseEntity<CustomerDTO> findByEmail(@RequestParam("email") @Email String email) {
        Customer customer = customerService.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found by email "));

        CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);

        return ResponseEntity.ok(customerDTO);
    }
}
