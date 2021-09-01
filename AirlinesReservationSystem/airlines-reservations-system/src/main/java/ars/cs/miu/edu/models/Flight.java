package ars.cs.miu.edu.models;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
public class Flight {
    @Id
    @GeneratedValue
    private long id;
    @NotNull
    @Column(unique = true)
    @Size(min=6,max=6)
    private String flightNumber;
    @Size(min = 0, max = 1000)
    private int capacity;
    @NotNull @Future
    private LocalTime departureTime;
    @NotNull @Future
    private LocalTime arrivalTime;
    @NotNull @Future
    private LocalDate departureDate;
    @NotNull @Future
    private LocalDate arrivalDate;
    @NotNull
    @ManyToOne(cascade = CascadeType.PERSIST)
    @NonNull
    private Airport arrivalAirport;
    @NonNull
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Airport departureAirport;
    @NonNull
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Airline airline;

}
