package edu.miu.cs.cs544.model;

import java.time.LocalDate;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Airline {

	private String id;
	private String code;
	private String name;

}
