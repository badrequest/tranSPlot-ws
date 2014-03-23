package br.com.badrequest.transplot.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.badrequest.transplot.exception.TransplotException;
import br.com.badrequest.transplot.model.Device;
import br.com.badrequest.transplot.model.DeviceId;

@Stateless
public class DeviceService {

	@Inject
	EntityManager em;
	
	public void insert(Device device) throws TransplotException {
		
		Device d = this.get(device.getId().getUid(), device.getId().getOs());
		
		if (d==null) {
			em.persist(device);
		}
		
	}
	
	public Device get(DeviceId id) throws TransplotException {
		return em.find(Device.class, id);
	}
	
	public Device get(String uid, String os) throws TransplotException {
		try {
			Device device = (Device) em
					.createQuery("SELECT d from Device d where d.id.uid = :uid and d.id.os = :os")
					.setParameter("uid", uid)
					.setParameter("os", os)
					.getSingleResult();
			
			return device;
		} catch (NoResultException noResultException) {
			return null;
		}
	}
	
}
