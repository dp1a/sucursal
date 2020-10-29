package com.dp.fravega.sucursal.model;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="BRANCH_OFFICE")
@NoArgsConstructor
@Setter @Getter
@ToString(callSuper=true, includeFieldNames=true)
public class BranchOffice extends Node {
	private String address;
	@Embedded
	private CustomerServiceHour customerServiceHour;

	@Builder
	public BranchOffice(final Long id, final Double lat, final Double lng, final String address, final CustomerServiceHour customerServiceHour) {
		super(id, lat, lng);
		this.address = address;
		this.customerServiceHour = customerServiceHour;
	}
}
