package de.htwg.controller;

import de.htwg.model.Touristenattraktion;
import de.htwg.repository.TouristenattraktionRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/touristenattraktion")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TouristenattraktionController {

    private TouristenattraktionRepository touristenattraktionRepository;

    public TouristenattraktionController() {
        this.touristenattraktionRepository = new TouristenattraktionRepository();
    }

    @GET
    public Response getTouristenattraktionen() {
        List<Touristenattraktion> touristenattraktionen = touristenattraktionRepository.fetchAllTouristenattraktionen();
        return Response.ok(touristenattraktionen).build();
    }

    @POST
    public Response postTouristenattraktion(Touristenattraktion touristenattraktion) {
        touristenattraktionRepository.insertTouristenattraktion(touristenattraktion);
        return Response.status(Response.Status.CREATED).entity(touristenattraktion).build();
    }
}
