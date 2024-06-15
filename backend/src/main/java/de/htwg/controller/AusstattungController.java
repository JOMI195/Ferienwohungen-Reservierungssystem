package de.htwg.controller;

import de.htwg.model.Ausstattung;
import de.htwg.repository.AusstattungRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/ausstattung")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AusstattungController {

    private AusstattungRepository ausstattungRepository;

    public AusstattungController() {
        this.ausstattungRepository = new AusstattungRepository();
    }

    @GET
    public Response getAusstattungen() {
        List<Ausstattung> ausstattungen = ausstattungRepository.fetchAllAusstattungen();
        return Response.ok(ausstattungen).build();
    }

    @POST
    public Response postAusstattung(Ausstattung ausstattung) {
        ausstattungRepository.insertAusstattung(ausstattung);
        return Response.status(Response.Status.CREATED).entity(ausstattung).build();
    }
}
