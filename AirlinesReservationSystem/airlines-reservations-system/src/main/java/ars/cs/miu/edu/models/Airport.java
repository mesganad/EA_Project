package ars.cs.miu.edu.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
public class Airport {
    @Id
    @GeneratedValue
    private long id;
    @Column(unique = true)
    @Size(min=3,max=3)
    @NonNull
    private String code;
    @NonNull
    @Size(min=2,max = 20)
    private String name;

    @NonNull
    @OneToOne(cascade = {CascadeType.PERSIST})
    private Address address;



}
