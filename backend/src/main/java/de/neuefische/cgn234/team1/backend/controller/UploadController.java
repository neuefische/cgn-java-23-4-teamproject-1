package de.neuefische.cgn234.team1.backend.controller;

import de.neuefische.cgn234.team1.backend.service.CloudinaryService;
import de.neuefische.cgn234.team1.backend.service.WorkoutService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/upload")
public class UploadController {
    private final CloudinaryService cloudinaryService;

    private final WorkoutService workoutService;

    public UploadController(CloudinaryService cloudinaryService, WorkoutService workoutService) {
        this.cloudinaryService = cloudinaryService;
        this.workoutService = workoutService;
    }

    @PostMapping("/image/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadImage(@RequestPart(name = "file") MultipartFile file,
                              @PathVariable String id) throws IOException {
        String photoUrl = cloudinaryService.uploadFile(file);
        workoutService.attachPhoto(id, photoUrl);
        return photoUrl;
    }
}
