package de.htwg.controller;

import de.htwg.model.Bild;
import de.htwg.repository.BildRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/bild")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BildController {

    private BildRepository bildRepository;

    public BildController() {
        this.bildRepository = new BildRepository();
    }

    @GET
    public Response getBilder() {
        List<Bild> bilder = bildRepository.fetchAllBilder();
        return Response.ok(bilder).build();
    }

    @POST
    public Response postBild(Bild bild) {
        bildRepository.insertBild(bild);
        return Response.status(Response.Status.CREATED).entity(bild).build();
    }
}
