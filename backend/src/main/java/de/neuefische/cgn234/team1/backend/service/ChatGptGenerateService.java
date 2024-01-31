package de.neuefische.cgn234.team1.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatGptGenerateService {
    private final ChatGptService chatGptService;

    public String[] generateExercise(String content) {
        String description = chatGptService.chatGptDescription(content);
        String title = chatGptService.chatGptTitel(description);

        return new String[]{title, description};
    }
}
