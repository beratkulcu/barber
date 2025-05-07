package com.berber_co.barber.service.barber;

import com.berber_co.barber.configuration.constans.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    ApiResponse<String> uploadFile(MultipartFile file);
}
