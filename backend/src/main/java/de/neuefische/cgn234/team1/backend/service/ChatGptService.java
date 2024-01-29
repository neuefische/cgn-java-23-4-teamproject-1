package de.neuefische.cgn234.team1.backend.service;

import de.neuefische.cgn234.team1.backend.model.chatgpt.ChatGptRequest;
import de.neuefische.cgn234.team1.backend.model.chatgpt.ChatGptResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
                .body(new ChatGptRequest("Rolle: Fittnesstrainer\n" +
                        "Aufgabe: 1Trainingseinheit (nur eine Aufgabe) für " + message + " max 300zeichen \n" +
                        "Ausgabe:\n" +
                        "Beschreibung:"))

                .retrieve()
                .body(ChatGptResponse.class);

        if (response != null) return response.text();

        return "";
    }

    public String chatGptTitel(String description) {
        ChatGptResponse response = restClient.post()
                .uri("/completions")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ChatGptRequest("Erstelle Titel für " + description + ". Max 15 zeichen. Benutze keine sonderzeichen"))
                .retrieve()
                .body(ChatGptResponse.class);

        if (response != null) return response.text();

        return "";
    }
}
