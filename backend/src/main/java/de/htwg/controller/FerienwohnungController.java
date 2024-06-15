package de.htwg.controller;

import de.htwg.model.Ferienwohnung;
import de.htwg.repository.FerienwohnungRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/ferienwohnung")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FerienwohnungController {

    private FerienwohnungRepository ferienwohnungRepository;

    public FerienwohnungController() {
        this.ferienwohnungRepository = new FerienwohnungRepository();
    }

    @GET
    public Response getFerienwohnungen() {
        List<Ferienwohnung> ferienwohnungen = ferienwohnungRepository.fetchAllFerienwohnungen();
        return Response.ok(ferienwohnungen).build();
    }

    @POST
    public Response postFerienwohnung(Ferienwohnung ferienwohnung) {
        ferienwohnungRepository.insertFerienwohnung(ferienwohnung);
        return Response.status(Response.Status.CREATED).entity(ferienwohnung).build();
    }
}
