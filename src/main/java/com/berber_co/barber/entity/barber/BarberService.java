package com.berber_co.barber.entity.barber;

import com.berber_co.barber.entity.abstracts.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "barber_services")
public class BarberService extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "barber_service_seq")
    @SequenceGenerator(name = "barber_service_seq", sequenceName = "barber_service_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private BigDecimal price;

    @Column
    private Integer durationInMinutes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private ServiceCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

    // İleride eklenebilecek: Berber çalışanlarıyla ilişki için hazır yapı
//    @ManyToMany(mappedBy = "services")
//    private Set<Employee> employees = new HashSet<>();
}
