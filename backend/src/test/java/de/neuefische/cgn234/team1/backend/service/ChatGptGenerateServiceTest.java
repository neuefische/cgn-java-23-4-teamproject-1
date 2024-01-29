package de.neuefische.cgn234.team1.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.when;

class ChatGptGenerateServiceTest {

    private ChatGptService chatGptService;
    private ChatGptGenerateService chatGptGenerateService;

    @BeforeEach
    void setUp() {
        chatGptService = Mockito.mock(ChatGptService.class);
        chatGptGenerateService = new ChatGptGenerateService(chatGptService);
    }

    @Test
    void testGenerateExercise() {
        String inputContent = "nothing";
        String mockDescription = "test2";
        String mockTitle = "test1";

        when(chatGptService.chatGptDescription(inputContent)).thenReturn(mockDescription);
        when(chatGptService.chatGptTitel(mockDescription)).thenReturn(mockTitle);

        String[] expected = new String[]{"test", "test"};
        String[] actual = chatGptGenerateService.generateExercise(inputContent);

        assertArrayEquals(expected, actual, "The generated exercise should match the expected format.");
    }
}
