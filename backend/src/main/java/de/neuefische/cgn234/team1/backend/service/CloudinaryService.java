package de.neuefische.cgn234.team1.backend.service;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;

@Service
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadFile(MultipartFile file) throws IOException {
        File fileToUpload = convertMultiPartToFile(file);
        var cloudinaryResponse = cloudinary.uploader().upload(fileToUpload, Map.of(
                "resource_type", "auto",
                "public_id", Objects.requireNonNull(fileToUpload.getName()) + Instant.now(),
                "folder", "cloudinary_file_test"
        ));

        return cloudinaryResponse.get("secure_url").toString();
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException();
        }
        String originalFilename = file.getOriginalFilename();

        File convFile = new File(Objects.requireNonNull(originalFilename));
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }
        return convFile;
    }


}
