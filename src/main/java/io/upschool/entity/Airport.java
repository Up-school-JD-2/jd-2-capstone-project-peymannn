package io.upschool.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.Where;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_active = true")
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "airport name may not be blank")
    private String airportName;

    @Column(nullable = false)
    @NotBlank(message = "address may not be blank")
    private String address;

    @Builder.Default
    @Column(name = "is_active", columnDefinition = "tinyint(1) default 1")
    private Boolean isActive = true;
}
