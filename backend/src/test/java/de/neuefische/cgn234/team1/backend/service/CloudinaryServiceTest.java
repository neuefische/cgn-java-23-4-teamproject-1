package de.neuefische.cgn234.team1.backend.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;

class CloudinaryServiceTest {

    private final Cloudinary mockCloudinary = mock(Cloudinary.class);

    @Test
    void uploadFileTest_whenFileIsProvided_whenReturnUrlToFile() throws IOException {
        // ARRANGE
        MultipartFile mockFile = mock(MultipartFile.class);
        Map<String, Object> mockResponse = Map.of("secure_url", "http://example.com/image");

        when(mockFile.getOriginalFilename()).thenReturn("testfile");
        when(mockCloudinary.uploader()).thenReturn(mock(Uploader.class));
        when(mockCloudinary.uploader().upload(any(File.class), anyMap())).thenReturn(mockResponse);

        CloudinaryService serviceUnderTest = new CloudinaryService(mockCloudinary);
        // ACT
        String result = serviceUnderTest.uploadFile(mockFile);

        // ASSERT
        assertEquals("http://example.com/image", result);
    }
}