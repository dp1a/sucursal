package com.dp.fravega.sucursal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@DiscriminatorColumn(name="type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data @AllArgsConstructor @NoArgsConstructor
@JsonIgnoreProperties(value = { "pos" })
public abstract class Node {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="lat")
	private Double lat;

	@Column(name="lng")
	private Double lng;
	
	public double[] getPos() {
		return new double[]{lat, lng};
	}
}
