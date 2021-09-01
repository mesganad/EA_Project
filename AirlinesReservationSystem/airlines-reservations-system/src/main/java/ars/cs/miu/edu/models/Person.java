package ars.cs.miu.edu.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

import static ars.cs.miu.edu.models.Role.PASSENGER;

@Data
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Person {
    @Id
    @GeneratedValue
    private long id;
    @NonNull
    @Size(min = 2,max = 20)
    private String firstName;
    @NonNull
    @Size(min = 2, max = 20)
    private String lastName;
    @Email
    @NonNull
    private String emailAddress;

    @Column(unique=true)@NonNull
    private String username;

    @Size(min = 2, max = 20)
    private String password;
   @Past
    private LocalDate dateOfBirth;
    private Role role= PASSENGER;
    @ManyToOne
    private Address address;
}




//public abstract class Person {
//    @Id
//    @GeneratedValue
//    private long id;
//
//    private String firstName;
//
//    private String lastName;
//
//    private String emailAddress;
//
//
//    private String username;
//
//    private String password;
//
//    private LocalDate dateOfBirth;
//    private Role role= PASSENGER;
//
//    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    private Address address;
//}
