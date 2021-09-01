package ars.cs.miu.edu.models;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class Address {
    @Id
    @GeneratedValue
    private long id;
    @NotNull
    @Size(min = 2, max = 20)
    private String street;
    @NonNull
    @Size(min = 2, max = 20)
    private String city;
    @NonNull
    @Size(min = 2, max = 20)
    private String state;
    @NonNull
    @Size(min = 2, max = 10)
    @Column(unique = true)
    private String zip;
}
