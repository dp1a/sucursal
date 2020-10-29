package com.dp.fravega.sucursal.model;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="WITHDRAWAL_POINT")
@NoArgsConstructor
@Setter @Getter
@ToString(callSuper=true, includeFieldNames=true)
public class WithdrawalPoint extends Node {
	private Long capacity;
	
	@Builder
	public WithdrawalPoint(final Long id, final Double lat, final Double lng, final Long capacity) {
		super(id, lat, lng);
		this.capacity = capacity;
	}
}
