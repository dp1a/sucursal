package com.dp.fravega.sucursal.model;

import lombok.*;

import javax.persistence.Embeddable;
import java.sql.Time;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @ToString
@Embeddable
public class CustomerServiceHour {
	private Time startService;
	private Time endService;
}
