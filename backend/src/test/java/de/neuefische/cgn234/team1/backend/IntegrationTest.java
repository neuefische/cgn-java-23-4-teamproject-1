package de.neuefische.cgn234.team1.backend;

import de.neuefische.cgn234.team1.backend.model.Workout;
import de.neuefische.cgn234.team1.backend.repo.WorkoutRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WorkoutRepository workoutRepository;


    @Test
    @DirtiesContext
    void getAll() throws Exception {
        //ARRANGE
        Workout workout = new Workout("1", "test1", "test1");
        workoutRepository.save(workout);

        //ACT
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/workouts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                                       [{
                                       "id": "1",
                                       "workoutName": "test1",
                                       "workoutDescription": "test1"
                                       }
                                       ]
                        """)).andReturn();

                //ASSERT


        assertEquals(200, result.getResponse().getStatus());

    }

    @Test
    @DirtiesContext
    void getWorkoutById() throws Exception {
        // ARRANGE
        Workout workout = new Workout("1", "test1", "test1");
        workoutRepository.save(workout);

        // ACT
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/workouts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                                       {
                                       "id": "1",
                                       "workoutName": "test1",
                                       "workoutDescription": "test1"
                                       }
                        """)).andReturn();

        //ASSERT
        assertEquals(200, result.getResponse().getStatus());
    }

}
