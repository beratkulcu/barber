package com.berber_co.barber.service.impl.user;

import com.berber_co.barber.configuration.constans.ApiResponse;
import com.berber_co.barber.data.request.UserProfileRequest;
import com.berber_co.barber.data.response.UserProfileResponse;
import com.berber_co.barber.entity.user.User;
import com.berber_co.barber.entity.user.UserProfile;
import com.berber_co.barber.enums.ActivityStatus;
import com.berber_co.barber.exception.AppException;
import com.berber_co.barber.repository.user.UserProfileRepository;
import com.berber_co.barber.repository.user.UserRepository;
import com.berber_co.barber.service.barber.FileStorageService;
import com.berber_co.barber.service.location.LocationService;
import com.berber_co.barber.service.user.UserProfileService;
import com.berber_co.barber.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.berber_co.Validations.ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;
    private final LocationService locationService;


    @Override
    public ApiResponse<Boolean> createProfile(UserProfileRequest request) {
        Long userId = SecurityUtil.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ERROR, "User not found"));

        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElse(new UserProfile());

        profile.setUser(user);
        profile.setFirstName(request.firstName() != null ? request.firstName() : profile.getFirstName());
        profile.setLastName(request.lastName() != null ? request.lastName() : profile.getLastName());
        profile.setPhoneNumber(request.phoneNumber() != null ? request.phoneNumber() : profile.getPhoneNumber());

        if (request.city() != null && request.district() != null) {
            String cityName = locationService.getCityNameByCode(request.city());
            String districtName = locationService.getDistrictNameByCode(request.district());

            if (cityName == null || districtName == null) {
                throw new AppException(ERROR, "Invalid city or district");
            }

            profile.setCity(request.city());
            profile.setDistrict(request.district());
        }

        profile.setAddress(request.address() != null ? request.address() : profile.getAddress());
        profile.setProfilePictureUrl(request.profilePictureUrl() != null ? request.profilePictureUrl() : profile.getProfilePictureUrl());
        profile.setLatitude(request.latitude() != null ? request.latitude() : profile.getLatitude());
        profile.setLongitude(request.longitude() != null ? request.longitude() : profile.getLongitude());
        profile.setStatus(ActivityStatus.ACTIVE);

        userProfileRepository.save(profile);
        return ApiResponse.success(Boolean.TRUE);
    }


    @Override
    public ApiResponse<Boolean> updateProfile(Long id, UserProfileRequest request) {
        UserProfile profile = userProfileRepository.findById(id)
                .orElseThrow(() -> new AppException(ERROR, "Profile not found"));

        profile.setFirstName(request.firstName() == null ? profile.getFirstName() : request.firstName());
        profile.setLastName(request.lastName() == null ? profile.getLastName() : request.lastName());
        profile.setPhoneNumber(request.phoneNumber() == null ? profile.getPhoneNumber() : request.phoneNumber());
        profile.setCity(request.city() == null ? profile.getCity() : request.city());
        profile.setDistrict(request.district() == null ? profile.getDistrict() : request.district());
        profile.setLatitude(request.latitude() == null ? profile.getLatitude() : request.latitude());
        profile.setLongitude(request.longitude() == null ? profile.getLongitude() : request.longitude());

        userProfileRepository.save(profile);
        log.info("User profile updated for id: {}", id);
        return ApiResponse.success(Boolean.TRUE);
    }

    @Override
    public ApiResponse<UserProfileResponse> getProfile() {
        Long userId = SecurityUtil.getUserId();

        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new AppException(ERROR, "Profile not found"));

        UserProfileResponse response = new UserProfileResponse(
                profile.getId(),
                profile.getFirstName(),
                profile.getLastName(),
                profile.getPhoneNumber(),
                profile.getCity(),
                profile.getDistrict(),
                profile.getLatitude(),
                profile.getLongitude()
        );

        log.info("Retrieved profile for userId: {}", userId);
        return ApiResponse.success(response);
    }
}
