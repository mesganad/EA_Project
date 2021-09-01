package ars.cs.miu.edu.models;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor

@Entity
public class Reservation implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @NotNull
    @Column(unique = true)
    @Size(min=3,max=3)
    private String code;
    @NonNull
    @Size(min=2,max = 20)
    private String departure;
    @NonNull
    @Size(min=2,max = 20)
    private String destination;
    private Status status=Status.PENDING;
    private Double reserveAmount;
    private String reserveBy;
    @ManyToOne(cascade ={CascadeType.PERSIST})
    @JoinColumn(name="passenger_Id")
    private Passenger passenger;
    @Transient
    private String type;
}
