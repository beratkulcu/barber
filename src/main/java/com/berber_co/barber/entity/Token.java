package com.berber_co.barber.entity;

import com.berber_co.barber.admin.entity.Admin;
import com.berber_co.barber.entity.abstracts.BaseEntity;
import com.berber_co.barber.entity.barber.Seller;
import com.berber_co.barber.entity.user.User;
import com.berber_co.barber.enums.TokenStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "refresh_tokens")
public class Token extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tokens_seq")
    @SequenceGenerator(name = "tokens_seq", sequenceName = "tokens_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String accessToken;

    @Column(nullable = false)
    private String refreshToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    private ZonedDateTime expiresAt;

    @Enumerated(EnumType.STRING)
    private TokenStatusEnum status;
}
