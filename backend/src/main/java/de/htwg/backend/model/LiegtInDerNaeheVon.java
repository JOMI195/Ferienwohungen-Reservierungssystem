package de.htwg.backend.model;

public record LiegtInDerNaeheVon(
    Long ferienwohnungsId,
    String touristenattraktionsname,
    int entfernung
) {
}
