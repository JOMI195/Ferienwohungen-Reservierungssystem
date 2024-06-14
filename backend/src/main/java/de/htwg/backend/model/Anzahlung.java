package de.htwg.backend.model;

import java.time.LocalDate;

public record Anzahlung(
    Long anzahlungsId,
    Double anzahlungsbetrag,
    LocalDate anzahlungsdatum,
    Long rechnungsnummer
) {
}
