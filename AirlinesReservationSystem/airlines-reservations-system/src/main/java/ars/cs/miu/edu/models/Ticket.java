package ars.cs.miu.edu.models;

import lombok.*;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@NoArgsConstructor
@Data
@Entity
public class Ticket {
    @Id
    @GeneratedValue
    private long id;
    @NotNull
    @Column(unique = true)
    @Digits(fraction = 0, integer = 20)

    private String number;
    @NonNull
    @Future
    private LocalDate flightDate;
    @NonNull
    @ManyToOne()
    @JoinColumn(name="reservationCode",referencedColumnName = "code")
    private Reservation reservation;
    @NonNull
    @ManyToOne()
    private Flight flight;
}
