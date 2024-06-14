package de.htwg.backend.model;

import java.time.LocalDate;

public record Buchung(
    Long buchnungsnummer,
    Long ferienwohnungsId,
    String email,
    LocalDate buchungsdatum,
    LocalDate startdatum,
    LocalDate enddatum,
    Integer sterne,
    LocalDate bewertungsdatum,
    Integer rechnungsnummer,
    Double rechnungsbetrag,
    LocalDate rechnungsdatum
) {
}
