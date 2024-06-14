package de.htwg.backend.model;

import java.time.LocalDate;

public record Stornierung(
    Long stornierungsnummer,
    Long ferienwohnungsId,
    String email,
    LocalDate stornodatum,
    LocalDate buchungsdatum,
    LocalDate startdatum,
    LocalDate enddatum,
    Integer rechnungsnummer,
    Double rechnungsbetrag,
    LocalDate rechnungsdatum
) {
}