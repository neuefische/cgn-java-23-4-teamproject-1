package de.neuefische.cgn234.team1.backend;

import de.neuefische.cgn234.team1.backend.model.Workout;
import de.neuefische.cgn234.team1.backend.repo.WorkoutRepository;
import de.neuefische.cgn234.team1.backend.service.ChatGptGenerateService;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class IntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private ChatGptGenerateService chatGptGenerateService = mock(ChatGptGenerateService.class);

    private static MockWebServer mockWebServer;

    @DynamicPropertySource
    public static void configureUrl(DynamicPropertyRegistry registry) {
        registry.add("app.chatgpt.api.url", () -> mockWebServer.url("/").toString());
        registry.add("app.openai.api.key", () -> mockWebServer.url("/").toString());
        registry.add("app.openai.api.org", () -> mockWebServer.url("/").toString());
    }

    @BeforeAll
    static void beforeAll() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }


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

    @Test
    void deleteWorkout_existingWorkout_shouldDeleteAndReturnTrue() throws Exception {
        // ARRANGE
        Workout workout = new Workout("1", "test1", "test1");
        workoutRepository.save(workout);

        // ACT
        MvcResult result = mockMvc.perform(delete("/api/workouts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(oidcLogin().userInfoToken(token -> token.claim("login", "test-user"))))
                .andExpect(status().isOk())
                .andReturn();

        // ASSERT
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void deleteWorkout_nonExistingWorkout_shouldReturnFalse() throws Exception {
        // ACT
        MvcResult result = mockMvc.perform(delete("/api/workouts/nonexistent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(oidcLogin().userInfoToken(token -> token.claim("login", "test-user"))))
                .andExpect(status().isOk())
                .andReturn();

        // ASSERT
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @DirtiesContext
    void updateWorkout_whenPutNewWorkout_updatesCurrentWorkout() throws Exception {
        // ARRANGE
        Workout originalWorkout = new Workout("1", "test1", "test1");
        workoutRepository.save(originalWorkout);

        String updatedWorkoutJson = """
                {
                    "id": "1",
                    "workoutName": "value2",
                    "workoutDescription": "value2"
                }
                """;

        // ACT
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/workouts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(oidcLogin().userInfoToken(token -> token.claim("login", "test-user")))
                        .content(updatedWorkoutJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(updatedWorkoutJson))
                .andReturn();

        // ASSERT
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    void update_shoudThrowBadRequestException_whenIdNotMatcherIdOdWorkout() throws Exception {
        // ACT
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/workouts/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(oidcLogin().userInfoToken(token -> token.claim("login", "test-user")))
                        .content("""
                                {
                                    "id": "1",
                                    "workoutName": "value2",
                                    "workoutDescription": "value2"
                                }
                                """
                        ))
                .andExpect(status().isBadRequest())
                .andReturn();

        // ASSERT
        assertEquals(400, result.getResponse().getStatus());
    }

    @Test
    @DirtiesContext
    void createWorkoutTest() throws Exception {
        //ACT
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/workouts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(oidcLogin().userInfoToken(token -> token.claim("login", "test-user")))
                        .content("""
                                        {"id": "1",
                                "workoutName": "test1",
                                "workoutDescription": "test1"
                                                       }
                                                       """))
                .andExpect(status().isCreated())
                .andReturn();

        //ASSERT
        assertEquals(201, result.getResponse().getStatus());
    }
}
