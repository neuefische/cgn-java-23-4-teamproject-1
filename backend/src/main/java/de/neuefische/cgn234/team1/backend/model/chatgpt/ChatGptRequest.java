package de.neuefische.cgn234.team1.backend.model.chatgpt;

import java.util.Collections;
import java.util.List;

public record ChatGptRequest(
        String model,
        List<ChatGptMessage> messages
) {
    public ChatGptRequest(String message) {
        this("gpt-3.5-turbo-0613", Collections.singletonList(new ChatGptMessage("user", message)));
    }
}
