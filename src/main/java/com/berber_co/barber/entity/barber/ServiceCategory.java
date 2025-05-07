package com.berber_co.barber.entity.barber;

import com.berber_co.barber.entity.abstracts.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "service_categories")
public class ServiceCategory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "service_category_seq")
    @SequenceGenerator(name = "service_category_seq", sequenceName = "service_category_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @OneToMany(mappedBy = "category")
    private List<BarberService> services;
}
