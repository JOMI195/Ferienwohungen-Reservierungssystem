package de.htwg.controller;

import de.htwg.model.Buchung;
import de.htwg.repository.BuchungRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/buchung")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BuchungController {

    private BuchungRepository buchungRepository;

    public BuchungController() {
        this.buchungRepository = new BuchungRepository();
    }

    @GET
    public Response getBuchungen() {
        List<Buchung> buchungen = buchungRepository.fetchAllBuchungen();
        return Response.ok(buchungen).build();
    }

    @POST
    public Response postBuchung(Buchung buchung) {
        buchungRepository.insertBuchung(buchung);
        return Response.status(Response.Status.CREATED).entity(buchung).build();
    }
}
