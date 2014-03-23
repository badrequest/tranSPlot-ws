package br.com.badrequest.transplot.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@SuppressWarnings("serial")
@Entity
@Table(name = "traffic")
@Data
public class Traffic implements Serializable {

	@EmbeddedId
	private TrafficId id;
	
	private Float velocity;
	
	private Float orientation;
	
	@ManyToOne
	private Device device;
	
}
