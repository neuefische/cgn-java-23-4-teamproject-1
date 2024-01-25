package de.neuefische.cgn234.team1.backend;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ChatGptApiTest {
    private static MockWebServer mockWebServer;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void beforeAll() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @DynamicPropertySource
    static void backendProperties(DynamicPropertyRegistry registry) {
        registry.add("api.url", () -> mockWebServer.url("/").toString());
    }

    @Test
    void testApiCall() throws Exception {
        mockWebServer.enqueue(new MockResponse()
                .setBody("""
                        {
                            "title": "Make a training"
                        }
                    """)
                .addHeader("Content-Type", "application/json"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/workouts/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                     {
                                        "title": "Make a training"
                                     }
                                """))
                .andExpect(status().isOk());
    }
}
