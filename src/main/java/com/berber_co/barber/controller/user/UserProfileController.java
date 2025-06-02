package com.berber_co.barber.controller.user;

import com.berber_co.barber.configuration.constans.ApiResponse;
import com.berber_co.barber.data.request.UserProfileRequest;
import com.berber_co.barber.data.response.UserProfileResponse;
import com.berber_co.barber.service.barber.FileStorageService;
import com.berber_co.barber.service.user.UserProfileService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.berber_co.barber.configuration.constans.ApiPathConstants.USER_API;

@RestController
@RequiredArgsConstructor
@RequestMapping(USER_API + "/profile")
@SecurityRequirement(name = "BearerAuth")
public class UserProfileController {
    private final UserProfileService userProfileService;
    private final FileStorageService fileStorageService;

    @PostMapping
    public ResponseEntity<ApiResponse<Boolean>> createProfile(@RequestBody UserProfileRequest request) {
        return ResponseEntity.ok(userProfileService.createProfile(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> updateProfile(@PathVariable Long id, @RequestBody UserProfileRequest request) {
        return ResponseEntity.ok(userProfileService.updateProfile(id, request));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<UserProfileResponse>> getProfile() {
        return ResponseEntity.ok(userProfileService.getProfile());
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<String>> uploadUserPhoto(@RequestParam("file") MultipartFile file) {
        String photoUrl = fileStorageService.uploadFile(file).getData();
        return ResponseEntity.ok(ApiResponse.success(photoUrl));
    }
}
