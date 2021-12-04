package com.example.todobulgaria.web;

import com.example.todobulgaria.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void login() {
    }

    @Test
    void failedLogin() {
    }

    @Test
    void showRegisterForm() throws Exception {
            mockMvc.
                    perform(MockMvcRequestBuilders.get("/users/register"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("register"));
        }

    @Test
    void userRegisterBindingModel() {
    }

    @Test
    void registerUser() {

    }
}