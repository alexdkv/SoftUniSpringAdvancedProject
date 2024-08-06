package com.finalProject.SoftUniProject.web;

import com.finalProject.SoftUniProject.model.dto.ExerciseAddBindingModel;
import com.finalProject.SoftUniProject.model.entity.Equipment;
import com.finalProject.SoftUniProject.model.entity.Exercise;
import com.finalProject.SoftUniProject.model.entity.Role;
import com.finalProject.SoftUniProject.model.entity.User;
import com.finalProject.SoftUniProject.model.enums.UserRoleENUM;
import com.finalProject.SoftUniProject.repository.UserRepository;
import com.finalProject.SoftUniProject.service.EquipmentService;
import com.finalProject.SoftUniProject.service.ExerciseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ExerciseControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExerciseService exerciseService;

    @MockBean
    private EquipmentService equipmentService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testExerciseAddView() throws Exception {
        Equipment testEquipment = new Equipment();
        testEquipment.setId(1L);
        testEquipment.setName("testOne");
        testEquipment.setWeight(10);
        testEquipment.setPhotoUrl("https://test/test");

        List<Equipment> equipmentList = List.of(testEquipment
        );

        when(equipmentService.getAllEquipment()).thenReturn(equipmentList);

        mockMvc.perform(get("/exercise-add")
                        .with(user("coach").roles("COACH")))
                .andExpect(status().isOk())
                .andExpect(view().name("exercise-add"))
                .andExpect(model().attributeExists("equipmentList"))
                .andExpect(model().attribute("equipmentList", hasSize(1)))
                .andExpect(model().attribute("equipmentList", hasItem(
                        allOf(
                                hasProperty("id", is(1L)),
                                hasProperty("name", is("testOne")),
                                hasProperty("weight",is(10)),
                                hasProperty("photoUrl",is("https://test/test"))
                        )
                )));
    }

    @Test
    public void testExerciseAdd() throws Exception {
        ExerciseAddBindingModel bindingModel = testExercise();

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_COACH");

        Role coachRole = new Role();
        coachRole.setId(2L);
        coachRole.setName(UserRoleENUM.COACH);

        User coach = new User();
        coach.setUsername("testCoach");
        coach.setEmail("coach@test.com");
        coach.setPassword("test");
        coach.setUsername("test");
        coach.setBio("test");
        coach.setFullName("test");
        coach.setPhotoUrl("https://test/test");
        coach.setRole(List.of(coachRole));


        Authentication authentication = new UsernamePasswordAuthenticationToken(coach.getEmail(), null, Collections.singletonList(authority));
        SecurityContextHolder.getContext().setAuthentication(authentication);


        mockMvc.perform(post("/exercise-add")
                        .with(csrf())
                .flashAttr("exerciseAddBindingModel",bindingModel))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));

    }

    @Test
    public void testPreviewExercise() throws Exception {
        Exercise testExercise = new Exercise();
        testExercise.setId(1L);
        testExercise.setName("Test");
        testExercise.setDescription("Test description");

        when(exerciseService.findById(1L)).thenReturn(testExercise);
        mockMvc.perform(get("/exercise/preview/1")
                        .with(user("user").roles("TRAINEE"))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("exercise-preview"))
                .andExpect(model().attributeExists("exercise"))
                .andExpect(model().attribute("exercise", testExercise));
    }

    @Test
    public void testExercisesTrainee() throws Exception {
        Exercise testExercise = new Exercise();
        testExercise.setId(1L);
        testExercise.setName("Test one");
        testExercise.setDescription("Test description one");

        Exercise testExerciseTwo = new Exercise();
        testExerciseTwo.setId(2L);
        testExerciseTwo.setName("Test two");
        testExerciseTwo.setDescription("Test description two");

        Role traineeRole = new Role();
        traineeRole.setId(1L);
        traineeRole.setName(UserRoleENUM.TRAINEE);

        User trainee = new User();
        trainee.setUsername("test");
        trainee.setEmail("trainee@test.com");
        trainee.setPassword("test");
        trainee.setUsername("test");
        trainee.setBio("test");
        trainee.setFullName("test");
        trainee.setPhotoUrl("https://test/test");
        trainee.setExercises(List.of(testExercise, testExerciseTwo));
        trainee.setRole(List.of(traineeRole));

        when(userRepository.findByEmail("trainee@test.com")).thenReturn(Optional.of(trainee));

        Authentication authentication = new UsernamePasswordAuthenticationToken("trainee@test.com", null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(get("/exercises-trainee"))
                .andExpect(status().isOk())
                .andExpect(view().name("exercises-trainee"))
                .andExpect(model().attributeExists("myExercises"))
                .andExpect(model().attribute("myExercises",hasSize(2)))
                .andExpect(model().attribute("myExercises",hasItem(
                        allOf(
                                hasProperty("id",is(1L)),
                                hasProperty("name",is("Test one")),
                                hasProperty("description", is("Test description one"))
                        )
                )))
                .andExpect(model().attribute("myExercises",hasItem(
                        allOf(
                                hasProperty("id",is(2L)),
                                hasProperty("name",is("Test two")),
                                hasProperty("description", is("Test description two"))
                        )
                )));
    }

    @Test
    public void testExercisesCoach() throws Exception {
        Exercise testExercise = new Exercise();
        testExercise.setId(1L);
        testExercise.setName("Test one");
        testExercise.setDescription("Test description one");

        Exercise testExerciseTwo = new Exercise();
        testExerciseTwo.setId(2L);
        testExerciseTwo.setName("Test two");
        testExerciseTwo.setDescription("Test description two");

        Role coachRole = new Role();
        coachRole.setId(2L);
        coachRole.setName(UserRoleENUM.COACH);

        User coach = new User();
        coach.setUsername("test");
        coach.setEmail("coach@test.com");
        coach.setPassword("test");
        coach.setUsername("test");
        coach.setBio("test");
        coach.setFullName("test");
        coach.setPhotoUrl("https://test/test");
        coach.setRole(List.of(coachRole));

        List<Exercise> myExercises = List.of(testExercise);
        List<Exercise> otherExercises = List.of(testExerciseTwo);

        when(userRepository.findByEmail("coach@test.com")).thenReturn(Optional.of(coach));
        when(exerciseService.findByCoach(coach)).thenReturn(myExercises);
        when(exerciseService.findOthers(coach)).thenReturn(otherExercises);

        Authentication authentication = new UsernamePasswordAuthenticationToken("coach@test.com", null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(get("/exercises-coach"))
                .andExpect(status().isOk())
                .andExpect(view().name("exercises-coach"))
                .andExpect(model().attributeExists("myExercises"))
                .andExpect(model().attribute("myExercises",hasSize(1)))
                .andExpect(model().attribute("myExercises",hasItem(
                        allOf(
                                hasProperty("id",is(1L)),
                                hasProperty("name",is("Test one")),
                                hasProperty("description", is("Test description one"))
                        )
                )))
                .andExpect(model().attribute("otherExercises",hasSize(1)))
                .andExpect(model().attribute("otherExercises",hasItem(
                        allOf(
                                hasProperty("id",is(2L)),
                                hasProperty("name",is("Test two")),
                                hasProperty("description", is("Test description two"))
                        )
                )));
    }

    @Test
    public void testUsernameNotFoundException() throws Exception {
        when(userRepository.findByEmail("nonexistent@test.com")).thenReturn(Optional.empty());

        Authentication authentication = new UsernamePasswordAuthenticationToken("nonexistent@test.com", null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(get("/exercises-coach"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    public void testDeleteExercise() throws Exception{
        Long id = 1L;

        mockMvc.perform(delete("/exercise/delete/{id}", id)
                        .with(user("coach").roles("COACH"))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/exercises-coach"));
    }

    private ExerciseAddBindingModel testExercise(){
        ExerciseAddBindingModel bindingModel = new ExerciseAddBindingModel();
        bindingModel.setName("test");
        bindingModel.setDescription("test description");
        bindingModel.setIntensity(10);
        bindingModel.setPhotoUrl("https://test/test");
        return bindingModel;
    }
}
