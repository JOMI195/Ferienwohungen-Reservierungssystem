package de.htwg.controller;

import de.htwg.model.LiegtInDerNaeheVon;
import de.htwg.repository.LiegtInDerNaeheVonRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/liegtindernähevon")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LiegtInDerNaeheVonController {

    private LiegtInDerNaeheVonRepository repository;

    public LiegtInDerNaeheVonController() {
        this.repository = new LiegtInDerNaeheVonRepository();
    }

    @GET
    public Response getAll() {
        List<LiegtInDerNaeheVon> list = repository.fetchAll();
        return Response.ok(list).build();
    }

    @POST
    public Response post(LiegtInDerNaeheVon item) {
        repository.insert(item);
        return Response.status(Response.Status.CREATED).entity(item).build();
    }
}
