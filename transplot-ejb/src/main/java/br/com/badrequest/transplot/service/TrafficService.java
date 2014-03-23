package br.com.badrequest.transplot.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.badrequest.transplot.exception.TransplotException;
import br.com.badrequest.transplot.model.Position;
import br.com.badrequest.transplot.model.Status;
import br.com.badrequest.transplot.model.Traffic;

@Stateless
public class TrafficService {

	@Inject
	EntityManager em;
	
	public void insert(Traffic traffic) throws TransplotException {
		em.persist(traffic);
	}
	
	public List<Status> status(Integer xi, Integer xf, 
			Integer yi, Integer yf, Integer scale) throws TransplotException {
		
		try {
			
			//TODO: Adicionar restrição de tempo (por ex.: últimos 15 minutos)
			//TODO: Criar view materializadas com refresh
			
			String sql = "select x,y, avg(velocity) from "
					+ "traffic where x between " + xi + " and " + xf + " and y between " + yi + " and " + yf
					+ " group by x, y";
			
			List<Object[]> objs = em.createNativeQuery(sql).getResultList();
			
			List<Status> s = new ArrayList<Status>();
			for (Object[] result : objs) {
				Integer x = (Integer) result[0];
				Integer y = (Integer) result[1];
				Double v = (Double) result[2];
				s.add(new Status(new Position(x, y), v.floatValue()));
			}
			
			return s;
		} catch (NoResultException noResultException) {
			return null;
		}
		
	}
	
}
