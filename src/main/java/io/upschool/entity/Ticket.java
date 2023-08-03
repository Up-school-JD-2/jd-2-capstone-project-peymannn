package io.upschool.entity;


import io.upschool.utils.AirlineSystemConstant;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.Where;

import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_active = true")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    private String ticketNumber  = UUID.randomUUID().toString();

    @Column(nullable = false)
    @NotNull
    private String passengerNameSurname;

    @Column(nullable = false)
    @NotNull
    private String passengerPhoneNumber;

    @Column(nullable = false)
    @Size(min = AirlineSystemConstant.CARD_NUMBER_LENGTH, max = AirlineSystemConstant.CARD_NUMBER_LENGTH)
    @NotNull
    private String CardNumber ;

    @Builder.Default
    @Column(name = "is_active", columnDefinition="tinyint(1) default 1")
    private Boolean isActive = true;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Flight flight ;

}
