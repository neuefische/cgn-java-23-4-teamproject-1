package de.neuefische.cgn234.team1.backend.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import de.neuefische.cgn234.team1.backend.model.Workout;
import de.neuefische.cgn234.team1.backend.repo.WorkoutRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.File;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UploadIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private WorkoutRepository workoutRepository;

    @MockBean
    private Cloudinary cloudinary;

    @Test
    void uploadImage_whenFileIsProvided_thenUrlIsReturned() throws Exception {
        // ARRANGE
        Workout workout = new Workout("1", "test1", "test1");
        workoutRepository.save(workout);

        String id = "1";
        String photoUrl = "http://example.com/image.png";
        MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "some xml".getBytes());
        Map<String, Object> mockResponse = Map.of("secure_url", photoUrl);

        when(cloudinary.uploader()).thenReturn(mock(Uploader.class));
        when(cloudinary.uploader().upload(any(File.class), anyMap())).thenReturn(mockResponse);

        // ACT
        mvc.perform(multipart("/api/upload/image/" + id).file(file).contentType(MediaType.MULTIPART_FORM_DATA))
                // ASSERT
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(photoUrl));

        assertEquals(1, workoutRepository.findById("1").get().workoutPhotos().size());
    }
}

