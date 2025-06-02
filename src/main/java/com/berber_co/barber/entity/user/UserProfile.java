package com.berber_co.barber.entity.user;

import com.berber_co.barber.entity.abstracts.BaseEntity;
import com.berber_co.barber.enums.ActivityStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_profile_seq")
    @SequenceGenerator(name = "user_profile_seq", sequenceName = "user_profile_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column
    private String profilePictureUrl;

    @Column
    private String city;

    @Column
    private String district;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @Column
    private String address;

    @Enumerated(EnumType.STRING)
    private ActivityStatus status;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
