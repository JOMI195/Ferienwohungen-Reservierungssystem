package de.htwg.controller;

import de.htwg.model.Kunde;
import de.htwg.repository.KundeRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/kunde")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class KundeController {

    private KundeRepository kundeRepository;

    public KundeController() {
        this.kundeRepository = new KundeRepository();
    }

    @GET
    public Response getKunden() {
        List<Kunde> kunden = kundeRepository.fetchAllKunden();
        return Response.ok(kunden).build();
    }

    @POST
    public Response postKunde(Kunde kunde) {
        kundeRepository.insertKunde(kunde);
        return Response.status(Response.Status.CREATED).entity(kunde).build();
    }
}
