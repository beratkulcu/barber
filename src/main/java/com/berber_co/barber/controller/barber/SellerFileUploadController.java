package com.berber_co.barber.controller.barber;

import com.berber_co.barber.configuration.constans.ApiResponse;
import com.berber_co.barber.service.barber.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.berber_co.barber.configuration.constans.ApiPathConstants.BARBER_API;

@Validated
@RestController
@RequestMapping(BARBER_API + "/files")
@RequiredArgsConstructor
public class SellerFileUploadController {
    private final FileStorageService fileStorageService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<String>> uploadFile(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(fileStorageService.uploadFile(file));
    }
}
