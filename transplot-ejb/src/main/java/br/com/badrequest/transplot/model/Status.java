package br.com.badrequest.transplot.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
public class Status implements Serializable {

	private Position position;
	
	private Float meanVelocity;
	
}
