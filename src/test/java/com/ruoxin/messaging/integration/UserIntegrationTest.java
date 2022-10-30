package com.ruoxin.messaging.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.ruoxin.messaging.dao.UserDAO;
import com.ruoxin.messaging.dao.UserValidationCodeDAO;
import com.ruoxin.messaging.enums.Gender;
import com.ruoxin.messaging.model.User;
import com.ruoxin.messaging.model.UserValidationCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

// Integration Testing 集成测试 -> API(input, output)
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserValidationCodeDAO userValidationCodeDAO;

    @BeforeEach
    public void cleanUpOldData() {
        this.userDAO.deleteAll();
        this.userValidationCodeDAO.deleteAll();
    }

    @Test
    public void testRegister_validInput_successful() throws Exception {
        String requestBody = "{\n" +
                "    \"username\": \"123123xx\",\n" +
                "    \"nickname\": \"George\",\n" +
                "    \"address\": \"Canada\",\n" +
                "    \"email\": \"fffxxxxx@gmail.com\",\n" +
                "    \"gender\": \"MALE\",\n" +
                "   \"password\": \"11111111111111111111111111111111111\",\n" +
                "   \"repeatPassword\": \"11111111111111111111111111111111111\"\n" +
                "}";
        this.mockMvc.perform(post("/users/register")
                                     .content(requestBody)
                                     .contentType("application/json"))
                .andExpect(status().isOk()) // HTTP status == 200 ?
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.code").value(1000))
                .andExpect(jsonPath("$.message").value("Successful"));

        List<User> users = this.userDAO.selectByUsername("123123xx");
        assertNotNull(users);
        assertEquals(1, users.size());

        User user = users.get(0);
        assertEquals("123123xx", user.getUsername());
        assertEquals("11111111111111111111111111111111111", user.getPassword());
        assertEquals("Canada", user.getAddress());
        assertEquals("fffxxxxx@gmail.com", user.getEmail());
        assertEquals(Gender.MALE, user.getGender());
    }

    @Test
    public void testRegister_differentPasswords_returnsBadRequest() throws Exception {
        String requestBody = "{\n" +
                "    \"username\": \"123123xx\",\n" +
                "    \"nickname\": \"George\",\n" +
                "    \"address\": \"Canada\",\n" +
                "    \"email\": \"fffxxxxx@gmail.com\",\n" +
                "    \"gender\": \"MALE\",\n" +
                "   \"password\": \"111111111111111111111\",\n" +
                "   \"repeatPassword\": \"11111111111111111111111111111111111\"\n" +
                "}";
        this.mockMvc.perform(post("/users/register")
                                     .content(requestBody)
                                     .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.code").value(1001))
                .andExpect(jsonPath("$.message").value("Passwords are not matched"));

        List<User> users = this.userDAO.selectByUsername("123123xx");
        assertNotNull(users);
        assertTrue(users.isEmpty());
    }

    @Test
    public void testRegister_passwordIsTooShort_returnsBadRequest() throws Exception {
        //
    }

    //...

    @Test
    public void testActivate_validInput_successful() throws Exception {
        User user = new User();
        user.setUsername("123123xx");
        user.setNickname("George");
        user.setAddress("Canada");
        user.setEmail("fffxxxxx@gmail.com");
        user.setGender(Gender.MALE);
        user.setPassword("11111111111111111111111111111111111");
        this.userDAO.insert(user);

        UserValidationCode userValidationCode = new UserValidationCode();
        userValidationCode.setUserId(user.getId());
        userValidationCode.setValidationCode("654327");
        this.userValidationCodeDAO.insert(userValidationCode);

        String requestBody = "{\n" +
                "    \"username\": \"123123xx\",\n" +
                "    \"validationCode\": \"654327\"\n" +
                "}";
        this.mockMvc.perform(post("/users/activate")
                                     .contentType("application/json")
                                     .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.code").value(1000))
                .andExpect(jsonPath("$.message").value("Successful"));

        // database: 1. user.is_valid -> true 2. userValidationCode was deleted


    }

    @Test
    public void testActivate_wrongValidationCode_returnsBadRequest() throws Exception {
        User user = new User();
        user.setUsername("123123xx");
        user.setNickname("George");
        user.setAddress("Canada");
        user.setEmail("fffxxxxx@gmail.com");
        user.setGender(Gender.MALE);
        user.setPassword("11111111111111111111111111111111111");
        this.userDAO.insert(user);

        UserValidationCode userValidationCode = new UserValidationCode();
        userValidationCode.setUserId(user.getId());
        userValidationCode.setValidationCode("654327");
        this.userValidationCodeDAO.insert(userValidationCode);

        String requestBody = "{\n" +
                "    \"username\": \"123123xx\",\n" +
                "    \"validationCode\": \"65432x\"\n" +
                "}";
        this.mockMvc.perform(post("/users/activate")
                                     .contentType("application/json")
                                     .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.code").value(1007))
                .andExpect(jsonPath("$.message").value("Validation code is wrong"));

        // database: 1. user.is_valid -> false 2. userValidationCode is existing


    }
}
