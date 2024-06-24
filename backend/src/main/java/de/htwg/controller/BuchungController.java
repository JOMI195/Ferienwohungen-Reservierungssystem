package de.htwg.controller;

import de.htwg.model.Buchung;
import de.htwg.repository.BuchungRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.sql.SQLException;
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
        try {
            List<Buchung> buchungen = buchungRepository.fetchAllBuchungen();
            return Response.ok(buchungen).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while fetching bookings: " + e.getMessage())
                    .build();
        }
    }

    @POST
    public Response postBuchung(Buchung buchung) {
        try {
            buchungRepository.insertBuchung(buchung);
            return Response.status(Response.Status.CREATED).entity(buchung).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("An error occurred while inserting the booking: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An unexpected error occurred: " + e.getMessage())
                    .build();
        }
    }
}
