package com.finalProject.SoftUniProject.web;

import com.finalProject.SoftUniProject.model.dto.UserRegistrationDTO;
import com.finalProject.SoftUniProject.model.entity.Role;
import com.finalProject.SoftUniProject.model.enums.UserRoleENUM;
import com.finalProject.SoftUniProject.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIT {
    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRegisterView() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    public void testRegistrationSuccessful() throws Exception {
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(csrf())
                .param("username", "test")
                .param("role",UserRoleENUM.TRAINEE.name())
                .param("email", "test@test.com")
                .param("password", "test")
                .param("confirmPassword", "test")
                .param("fullName","Test Test")
                .param("bio", "test bio")
                .param("photoUrl","https://test/test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void testRegisterBindingErrors() throws Exception {
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(csrf())
                        .param("username", "")
                        .param("role",UserRoleENUM.TRAINEE.name())
                        .param("password", "")
                        .param("confirmPassword", "")
                        .param("email", "invalidemail")
                        .param("fullName","")
                        .param("bio", "")
                        .param("photoUrl","test/test"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeHasFieldErrors("registerDTO", "username", "email", "password",
                        "fullName", "bio", "photoUrl"));
    }

    @Test
    public void testRegisterUserAlreadyExists() throws Exception {

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(csrf())
                .param("username", "test")
                .param("role",UserRoleENUM.TRAINEE.name())
                .param("email", "test@test.com")
                .param("password", "test")
                .param("confirmPassword", "test")
                .param("fullName","Test Test")
                .param("bio", "test bio")
                .param("photoUrl","https://test/test"));

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(csrf())
                        .param("username", "test")
                        .param("password", "password")
                        .param("role",UserRoleENUM.TRAINEE.name())
                        .param("confirmPassword", "password")
                        .param("email", "test@test.com")
                        .param("fullName","Exists")
                        .param("bio", "Exists")
                        .param("photoUrl","https://test/test"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attribute("hasRegistrationError", true));
    }
}
