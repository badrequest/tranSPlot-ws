package br.com.badrequest.transplot.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Embeddable
public class TrafficId implements Serializable {

	private Integer x;
	
	private Integer y;
	
	private Date date;

}
