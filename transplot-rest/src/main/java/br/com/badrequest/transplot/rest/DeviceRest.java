package br.com.badrequest.transplot.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.badrequest.transplot.model.Device;
import br.com.badrequest.transplot.model.DeviceId;
import br.com.badrequest.transplot.model.Registro;
import br.com.badrequest.transplot.model.Response;
import br.com.badrequest.transplot.service.DeviceService;

import com.google.gson.Gson;

@Path("/registrar")
@RequestScoped
@SuppressWarnings("unchecked")
public class DeviceRest {

	@Inject
	private DeviceService deviceService;
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String registro(String json) {
		
		try {
			Registro registro = new Gson().fromJson(json, Registro.class);
			
			DeviceId id = new DeviceId();
			id.setOs(registro.os);
			id.setUid(registro.uid);
			
			Device device = new Device();
			device.setStatus(true);
			device.setId(id);
			
			deviceService.insert(device);
			
			return new Gson().toJson(new Response(Response.OK));
		} catch (Exception e) {
			return new Gson().toJson(new Response(Response.FAIL));
		}
	}

}
