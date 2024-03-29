package de.neuefische.cgn234.team1.backend.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import de.neuefische.cgn234.team1.backend.model.User;
import de.neuefische.cgn234.team1.backend.model.submodel.UserWorkout;
import de.neuefische.cgn234.team1.backend.repo.UserRepo;
import de.neuefische.cgn234.team1.backend.repo.WorkoutRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class UploadIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private UserRepo userRepo;

    @MockBean
    private Cloudinary cloudinary;

    @Test
    void uploadImage_whenFileIsProvided_thenUrlIsReturned() throws Exception {
        // ARRANGE
        String userName = "herbert";
        UserWorkout userWorkout = new UserWorkout("legs", "test2",
                1, 1, 1, 1, 15);
        userRepo.save(new User(userName, List.of(userWorkout)));


        String imageUrl = "http://example.com/image.png";
        Map<String, Object> mockResponse = Map.of("secure_url", imageUrl);
        MockMultipartFile file = new MockMultipartFile("file", "image.png", MediaType.IMAGE_PNG_VALUE, "data".getBytes());
        MockPart filePart = new MockPart("workoutName", "legs".getBytes());

        when(cloudinary.uploader()).thenReturn(mock(Uploader.class));
        when(cloudinary.uploader().upload(any(File.class), anyMap())).thenReturn(mockResponse);

        // ACT
        mvc.perform(multipart("/api/upload/image/" + userName)
                        .file(file)
                        .part(filePart)
                        .with(oidcLogin().userInfoToken(token -> token.claim("login", "test-user")))
                        .contentType(MediaType.MULTIPART_FORM_DATA))

                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(imageUrl));

        assertEquals(1, userRepo.findByUserName(userName).get().userWorkoutList().getFirst().workoutPhotos().size());
    }
}

