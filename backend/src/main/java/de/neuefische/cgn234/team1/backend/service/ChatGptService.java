package de.neuefische.cgn234.team1.backend.service;

import de.neuefische.cgn234.team1.backend.model.chatgpt.ChatGptRequest;
import de.neuefische.cgn234.team1.backend.model.chatgpt.ChatGptResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ChatGptService {
    private final RestClient restClient;

    public ChatGptService(@Value("${app.chatgpt.api.url}") String url,
                          @Value("${app.chatgpt.api.key}") String apiKey,
                          @Value("${app.chatgpt.api.org}") String org) {

        restClient = RestClient.builder()
                .baseUrl(url)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("OpenAI-Organization", org)
                .build();
    }

    public String chatGptDescription(String message) {
        ChatGptResponse response = restClient.post()
                .uri("/completions")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ChatGptRequest("Role: Fitness coach\n" +
                        "Create a training unit for " + message + " max 300letters be very specific \n" +
                        "The answer should be formatted as follows:\n" +
                        "Description: [what does the training look like]\n" +
                        "Repeat : [choose the number of Repeats between 10-25] \n" +
                        "Set: [choose number between 3-8]\n" +
                        "Break: [choose number between 1-10 mins] \n" +
                        "Time: [choose how long this training should last]\n"))

                .retrieve()
                .body(ChatGptResponse.class);

        if (response != null) return response.text();

        return "";
    }

    public String chatGptTitel(String description) {
        ChatGptResponse response = restClient.post()
                .uri("/completions")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ChatGptRequest("Create a title for " + description + ". max 15 letters"))
                .retrieve()
                .body(ChatGptResponse.class);

        if (response != null) return response.text();

        return "";
    }
}
