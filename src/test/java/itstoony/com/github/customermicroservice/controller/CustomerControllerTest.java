package itstoony.com.github.customermicroservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import itstoony.com.github.customermicroservice.dto.RegisteringCustomerDTO;
import itstoony.com.github.customermicroservice.dto.RegisteringUserDTO;
import itstoony.com.github.customermicroservice.dto.SearchingEmailRecord;
import itstoony.com.github.customermicroservice.entity.Customer;
import itstoony.com.github.customermicroservice.entity.Users;
import itstoony.com.github.customermicroservice.service.CustomerService;
import itstoony.com.github.customermicroservice.service.UsersService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static itstoony.com.github.customermicroservice.utils.Utils.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    static String CUSTOMER_API = "/api/customer";

    @MockBean
    CustomerService customerService;

    @MockBean
    UsersService usersService;

    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("Should register a new customer")
    void registerTest() throws Exception {
        // scenery
        RegisteringCustomerDTO customerDTO = createRegisteringCustomerDTO();

        Users users = createUsers();
        Customer customer = createCustomer();
        customer.setUsers(users);

        String json = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .writeValueAsString(customerDTO);

        given(customerService.register(any(RegisteringCustomerDTO.class))).willReturn(customer);
        given(usersService.register(any(RegisteringUserDTO.class))).willReturn(users);

        // execution
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(CUSTOMER_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // validation
        mvc
                .perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(customer.getId()))
                .andExpect(jsonPath("name").value(customerDTO.name()))
                .andExpect(jsonPath("cpf").value(customerDTO.cpf()))
                .andExpect(jsonPath("users.id").value(users.getId()))
                .andExpect(jsonPath("users.email").value(users.getEmail()))
                .andExpect(jsonPath("users.creationDate").value(users.getCreationDate().toString()))
                .andExpect(jsonPath("users.lastModified").value(users.getLastModified().toString()))
                .andExpect(jsonPath("address").value(customer.getAddress()))
                .andExpect(jsonPath("zipcode").value(customer.getZipcode()))
                .andExpect(jsonPath("cellPhone").value(customer.getCellPhone()))
                .andExpect(jsonPath("creationDate").value(customer.getCreationDate().toString()))
                .andExpect(jsonPath("lastModified").value(customer.getLastModified().toString()));

    }

    @Test
    @DisplayName("Should return 400 when passed dto doesn't have enough valid information")
    void registerValidatedTest() throws Exception {
        // scenery
        RegisteringCustomerDTO customerDTO = new RegisteringCustomerDTO("", "", null, "", "", "");

        String json = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .writeValueAsString(customerDTO);

        // execution
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(CUSTOMER_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // validation
        mvc
                .perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize(10)));

    }

    @Test
    @DisplayName("Should find customer by id")
    void findByIdTest() throws Exception {
        // scenery
        long id = 1L;

        Customer customer = createCustomer();

        given(customerService.findById(any(Long.class))).willReturn(Optional.of(customer));

        // execution
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(CUSTOMER_API.concat("/id/" + id))
                .accept(MediaType.APPLICATION_JSON);

        // validation
        mvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(customer.getId()))
                .andExpect(jsonPath("name").value(customer.getName()))
                .andExpect(jsonPath("cpf").value(customer.getCpf()))
                .andExpect(jsonPath("users.id").value(customer.getUsers().getId()))
                .andExpect(jsonPath("users.email").value(customer.getUsers().getEmail()))
                .andExpect(jsonPath("users.creationDate").value(customer.getUsers().getCreationDate().toString()))
                .andExpect(jsonPath("users.lastModified").value(customer.getUsers().getLastModified().toString()))
                .andExpect(jsonPath("address").value(customer.getAddress()))
                .andExpect(jsonPath("zipcode").value(customer.getZipcode()))
                .andExpect(jsonPath("cellPhone").value(customer.getCellPhone()))
                .andExpect(jsonPath("creationDate").value(customer.getCreationDate().toString()))
                .andExpect(jsonPath("lastModified").value(customer.getLastModified().toString()));

    }

    @Test
    @DisplayName("Should return 404 not found when passed ID is invalid")
    void findByInvalidIdTest() throws Exception {
        // scenery
        long id = 1L;

        given(customerService.findById(any(Long.class))).willReturn(Optional.empty());

        // execution
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(CUSTOMER_API.concat("/id/" + id))
                .accept(MediaType.APPLICATION_JSON);

        // validation
        mvc
                .perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should find Customer by email")
    void findByEmailTest() throws Exception {
        // scenery
        String email = "Fulano@email.com";
        SearchingEmailRecord dto = new SearchingEmailRecord(email);

        Customer customer = createCustomer();

        given(customerService.findByEmail(anyString())).willReturn(Optional.of(customer));

        String json = new ObjectMapper().writeValueAsString(dto);

        // execution
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(CUSTOMER_API.concat("/email"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        // validation
        mvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(customer.getId()))
                .andExpect(jsonPath("name").value(customer.getName()))
                .andExpect(jsonPath("cpf").value(customer.getCpf()))
                .andExpect(jsonPath("users.id").value(customer.getUsers().getId()))
                .andExpect(jsonPath("users.email").value(customer.getUsers().getEmail()))
                .andExpect(jsonPath("users.creationDate").value(customer.getUsers().getCreationDate().toString()))
                .andExpect(jsonPath("users.lastModified").value(customer.getUsers().getLastModified().toString()))
                .andExpect(jsonPath("address").value(customer.getAddress()))
                .andExpect(jsonPath("zipcode").value(customer.getZipcode()))
                .andExpect(jsonPath("cellPhone").value(customer.getCellPhone()))
                .andExpect(jsonPath("creationDate").value(customer.getCreationDate().toString()))
                .andExpect(jsonPath("lastModified").value(customer.getLastModified().toString()));
    }

    @Test
    @DisplayName("Should return 404 not found when trying to find Customer by an invalid email")
    void findByInvalidEmailTest() throws Exception {
        // scenery
        String email = "Fulano@email.com";
        SearchingEmailRecord dto = new SearchingEmailRecord(email);

        given(customerService.findByEmail(anyString())).willReturn(Optional.empty());

        String json = new ObjectMapper().writeValueAsString(dto);

        // execution
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(CUSTOMER_API.concat("/email"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        // validation
        mvc
                .perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("errors", hasSize(1)));

    }

}
