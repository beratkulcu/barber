package com.berber_co.barber.service.impl.barber;

import com.berber_co.barber.configuration.constans.ApiResponse;
import com.berber_co.barber.exception.AppException;
import com.berber_co.barber.service.barber.FileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static com.berber_co.Validations.ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {
    private static final String UPLOAD_DIRECTORY = "uploads";


    @Override
    public ApiResponse<String> uploadFile(MultipartFile file) {
        try {
            String uploadDir = System.getProperty("user.home") + "/barber-uploads";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            file.transferTo(filePath);

            String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
            String fileUrl = baseUrl + "/uploads/" + fileName;

            return ApiResponse.success(fileUrl);

        } catch (IOException e) {
            throw new AppException(ERROR, "File upload failed: " + e.getMessage());
        }
    }
}
