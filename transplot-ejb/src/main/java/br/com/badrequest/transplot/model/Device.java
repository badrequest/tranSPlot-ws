package br.com.badrequest.transplot.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@SuppressWarnings("serial")
@Entity
@Table(name = "device")
@Data
public class Device implements Serializable {

	@EmbeddedId
	private DeviceId id;
	
	private Boolean status;
	
}
