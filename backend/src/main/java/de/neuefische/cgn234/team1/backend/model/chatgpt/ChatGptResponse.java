package de.neuefische.cgn234.team1.backend.model.chatgpt;

import java.util.List;

public record ChatGptResponse(
        List<ChatGptChoice> choices
) {
    public String text() {
        return choices.get(0).message().content();
    }
}
