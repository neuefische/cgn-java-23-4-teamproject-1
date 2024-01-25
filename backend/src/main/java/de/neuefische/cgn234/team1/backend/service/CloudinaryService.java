package de.neuefische.cgn234.team1.backend.service;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Service
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadFile(MultipartFile file) throws IOException {
        File fileToUpload = File.createTempFile("file", null);
        file.transferTo(fileToUpload);
        var cloudinaryResponse = cloudinary.uploader().upload(fileToUpload, Map.of(
                "resource_type", "auto",
                "public_id", Objects.requireNonNull(file.getOriginalFilename()),
                "folder", "cloudinary_file_test"
        ));
        return cloudinaryResponse.get("secure_url").toString();
    }

}
