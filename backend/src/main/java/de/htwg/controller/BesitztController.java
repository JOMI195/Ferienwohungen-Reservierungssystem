package de.htwg.controller;

import de.htwg.model.Besitzt;
import de.htwg.repository.BesitztRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/besitzt")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BesitztController {

    private BesitztRepository besitztRepository;

    public BesitztController() {
        this.besitztRepository = new BesitztRepository();
    }

    @GET
    public Response getBesitzt() {
        List<Besitzt> besitztList = besitztRepository.fetchAllBesitzt();
        return Response.ok(besitztList).build();
    }

    @POST
    public Response postBesitzt(Besitzt besitzt) {
        besitztRepository.insertBesitzt(besitzt);
        return Response.status(Response.Status.CREATED).entity(besitzt).build();
    }
}
