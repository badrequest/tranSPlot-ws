package br.com.badrequest.transplot.rest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.com.badrequest.transplot.model.Device;
import br.com.badrequest.transplot.model.Envio;
import br.com.badrequest.transplot.model.HeatMapRequest;
import br.com.badrequest.transplot.model.HeatMapResponse;
import br.com.badrequest.transplot.model.Response;
import br.com.badrequest.transplot.model.Status;
import br.com.badrequest.transplot.model.Traffic;
import br.com.badrequest.transplot.model.TrafficId;
import br.com.badrequest.transplot.service.DeviceService;
import br.com.badrequest.transplot.service.TrafficService;

import com.google.gson.Gson;

@Path("/traffic")
@RequestScoped
public class TrafficRest {

	@Inject
	private TrafficService trafficService;
	
	@Inject
	private DeviceService deviceService;
	
	@POST
	@Path("/send")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String registro(String json) {
		
		try {
			Envio envio = new Gson().fromJson(json, Envio.class);

			Device device = deviceService.get(envio.uid, envio.os);
			
			if (device == null) {
				return new Gson().toJson(new Response(Response.FAIL));
			}
			
			TrafficId id = new TrafficId();
			id.setX(envio.position.x);
			id.setY(envio.position.y);
			id.setDate(new Date());
			
			Traffic t = new Traffic();
			t.setOrientation(envio.orientation);
			t.setVelocity(envio.velocity);
			t.setId(id);
			t.setDevice(device);
			
			trafficService.insert(t);
			
			return new Gson().toJson(new Response(Response.OK));
		} catch (Exception e) {
			return new Gson().toJson(new Response(Response.FAIL));
		}
	}
	
	@POST
	@Path("/status")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String status(String json) {
		
		try {

			HeatMapRequest heatmap = new Gson().fromJson(json, HeatMapRequest.class);
			
			List<Status> t = trafficService.status(heatmap.i.x, heatmap.f.x, heatmap.i.y, heatmap.f.y, 1);
			
			System.out.println(t.size());
			
			HeatMapResponse hmr = new HeatMapResponse();
			
			hmr.data = t;
			hmr.time = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date());
			hmr.ans = Response.OK;
			
			return new Gson().toJson(hmr);
		} catch (Exception e) {
			return new Gson().toJson(new Response(Response.FAIL));
		}
	}
	
	@GET
	@Path("/status")
	@Produces(MediaType.APPLICATION_JSON)
	public String status2(@QueryParam("ix") Integer ix, @QueryParam("fx") Integer fx,
			@QueryParam("iy") Integer iy, @QueryParam("fy") Integer fy) {
		
		try {

			List<Status> t = trafficService.status(ix, fx, iy, fy, 1);
			
			System.out.println(t.size());
			
			HeatMapResponse hmr = new HeatMapResponse();
			
			hmr.data = t;
			hmr.time = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date());
			hmr.ans = Response.OK;
			
			return new Gson().toJson(hmr);
		} catch (Exception e) {
			return new Gson().toJson(new Response(Response.FAIL));
		}
	}

}
