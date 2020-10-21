package de.mischok.academy.companydatabase.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "office")
public class Office implements WithId {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="city")
	private String city;

	@Column(name = "street")
	private String street;

	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;
}
