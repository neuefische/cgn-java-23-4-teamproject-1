package de.neuefische.cgn234.team1.backend;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
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
    public static void configureUrl(DynamicPropertyRegistry registry) {
        registry.add("app.chatgpt.api.url", () -> mockWebServer.url("/").toString());
        registry.add("app.openai.api.key", () -> mockWebServer.url("/").toString());
        registry.add("app.openai.api.org", () -> mockWebServer.url("/").toString());
    }

    @Test
    @DirtiesContext
    void testApiCall() throws Exception {
        mockWebServer.enqueue(new MockResponse()
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .setBody("""
                        {
                            "id": "chatcmpl-8fZhl72ZsoE0zPwYE5GUulCbB35Ch",
                            "object": "chat.completion",
                            "created": 1704919389,
                            "model": "gpt-3.5-turbo-0613",
                            "choices": [
                                {
                                    "index": 0,
                                            "message": {
                                                "role": "assistant",
                                                "content": "Beine sind wichtig!"
                                            },
                                    "logprobs": null,
                                    "finish_reason": "stop"
                                }
                            ],
                            "usage": {
                                "prompt_tokens": 13,
                                "completion_tokens": 5,
                                "total_tokens": 18
                            },
                            "system_fingerprint": null
                        }
                         """));

        mockWebServer.enqueue(new MockResponse()
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .setBody("""
                        {
                            "id": "chatcmpl-8fZhl72ZsoE0zPwYE5GUulCbB35Ch",
                            "object": "chat.completion",
                            "created": 1704919389,
                            "model": "gpt-3.5-turbo-0613",
                            "choices": [
                                {
                                    "index": 0,
                                            "message": {
                                                "role": "assistant",
                                                "content": "Gute Beine Training"
                                            },
                                    "logprobs": null,
                                    "finish_reason": "stop"
                                }
                            ],
                            "usage": {
                                "prompt_tokens": 13,
                                "completion_tokens": 5,
                                "total_tokens": 18
                            },
                            "system_fingerprint": null
                        }
                         """));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/workouts/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                                {
                                                    "title": "Beine Training"
                                                }
                                """
                        ))

                .andExpect(status().isOk());
    }


}
