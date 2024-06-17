package de.htwg.controller;

import de.htwg.model.Ferienwohnung;
import de.htwg.repository.FerienwohnungRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

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

    @GET
    @Path("/filter")
    public Response getNotReservedFerienwohnungen(
            @QueryParam("landname") String landname,
            @QueryParam("ausstattung") String ausstattung,
            @QueryParam("startdatum") String startdatum,
            @QueryParam("enddatum") String enddatum) {
        List<Map<String, Long>> filteredFerienwohungen = ferienwohnungRepository.fetchFilteredFerienwohnungenIds(
                landname,
                ausstattung,
                startdatum,
                enddatum);
        return Response.ok(filteredFerienwohungen).build();
    }

    @POST
    public Response postFerienwohnung(Ferienwohnung ferienwohnung) {
        ferienwohnungRepository.insertFerienwohnung(ferienwohnung);
        return Response.status(Response.Status.CREATED).entity(ferienwohnung).build();
    }
}
