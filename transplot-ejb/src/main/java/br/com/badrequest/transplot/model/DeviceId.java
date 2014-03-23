package br.com.badrequest.transplot.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Embeddable
public class DeviceId implements Serializable {

	private String uid;
	
	private String os;
	
}
