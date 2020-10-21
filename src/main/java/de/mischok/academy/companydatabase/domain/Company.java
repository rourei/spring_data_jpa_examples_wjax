package de.mischok.academy.companydatabase.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "company")
public class Company implements WithId {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@Column(name = "name")
	private String name;

	@OneToMany(mappedBy = "company")
	private List<Office> offices;

	@OneToMany(mappedBy = "company")
	private List<Employee> employees;
}


