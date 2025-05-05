package com.berber_co.barber.admin.entity;

import com.berber_co.barber.entity.abstracts.BaseEntity;
import com.berber_co.barber.enums.ActivityStatus;
import com.berber_co.barber.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "admins")
public class Admin extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admin_sequence")
    @SequenceGenerator(name = "admin_sequence", sequenceName = "admin_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(nullable = false, length = 120)
    private String password;

    @Enumerated(EnumType.STRING)
    private ActivityStatus status;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "admin_roles",
            joinColumns = @JoinColumn(name = "admin_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<RoleType> roles;
}
