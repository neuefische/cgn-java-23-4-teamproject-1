package de.neuefische.cgn234.team1.backend.service;

import com.cloudinary.Cloudinary;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
public class CloudinaryService {
    private final Cloudinary cloudinary;

    private final WorkoutService workoutService;

    public CloudinaryService(Cloudinary cloudinary, WorkoutService workoutService) {
        this.cloudinary = cloudinary;
        this.workoutService = workoutService;
    }

    public static String generateRandomFileName(String originalFileName) {
        String fileExtension = "";
        int i = originalFileName.lastIndexOf('.');
        if (i > 0) {
            fileExtension = originalFileName.substring(i + 1);
        }
        UUID uuid = UUID.randomUUID();
        return uuid + (fileExtension.isEmpty() ? "" : "." + fileExtension);
    }

    public String uploadFile(@NonNull MultipartFile file, String workoutId) throws IOException {
        String randomFileName = generateRandomFileName(Objects.requireNonNull(file.getOriginalFilename()));
        Path pathToUpload = Files.createTempFile("file", null);
        File fileToUpload = pathToUpload.toFile();
        file.transferTo(fileToUpload);
        var cloudinaryResponse = cloudinary.uploader().upload(fileToUpload, Map.of(
                "resource_type", "auto",
                "public_id", randomFileName,
                "folder", "cloudinary_file_test"
        ));

        String imageUrl = cloudinaryResponse.get("secure_url").toString();
        workoutService.attachPhoto(workoutId, imageUrl);

        return imageUrl;
    }

}
