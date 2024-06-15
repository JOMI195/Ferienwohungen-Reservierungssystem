package de.htwg.controller;

import de.htwg.model.Land;
import de.htwg.repository.LandRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/land")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LandController {

    private LandRepository landRepository;

    public LandController() {
        this.landRepository = new LandRepository();
    }

    @GET
    public Response getLaender() {
        List<Land> laender = landRepository.fetchAllLaender();
        return Response.ok(laender).build();
    }

    @POST
    public Response postLand(Land land) {
        landRepository.insertLand(land);
        return Response.status(Response.Status.CREATED).entity(land).build();
    }
}
