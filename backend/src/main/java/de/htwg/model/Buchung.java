package de.htwg.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public class Buchung {
    private Long buchnungsnummer;
    private Long ferienwohnungs_id;
    private String email;
    private LocalDate buchungsdatum;
    private LocalDate startdatum;
    private LocalDate enddatum;
    private Integer sterne;
    private LocalDate bewertungsdatum;
    private Integer rechnungsnummer;
    private Double rechnungsbetrag;
    private LocalDate rechnungsdatum;

    @JsonCreator
    public Buchung(
            @JsonProperty("buchnungsnummer") Long buchnungsnummer,
            @JsonProperty("ferienwohnungs_id") Long ferienwohnungs_id,
            @JsonProperty("email") String email,
            @JsonProperty("buchungsdatum") LocalDate buchungsdatum,
            @JsonProperty("startdatum") LocalDate startdatum,
            @JsonProperty("enddatum") LocalDate enddatum,
            @JsonProperty("sterne") Integer sterne,
            @JsonProperty("bewertungsdatum") LocalDate bewertungsdatum,
            @JsonProperty("rechnungsnummer") Integer rechnungsnummer,
            @JsonProperty("rechnungsbetrag") Double rechnungsbetrag,
            @JsonProperty("rechnungsdatum") LocalDate rechnungsdatum) {

        this.buchnungsnummer = buchnungsnummer;
        this.ferienwohnungs_id = ferienwohnungs_id;
        this.email = email;
        this.buchungsdatum = buchungsdatum;
        this.startdatum = startdatum;
        this.enddatum = enddatum;
        this.sterne = sterne;
        this.bewertungsdatum = bewertungsdatum;
        this.rechnungsnummer = rechnungsnummer;
        this.rechnungsbetrag = rechnungsbetrag;
        this.rechnungsdatum = rechnungsdatum;
    }

    public Long getBuchnungsnummer() {
        return buchnungsnummer;
    }

    public Long getFerienwohnungs_Id() {
        return ferienwohnungs_id;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBuchungsdatum() {
        return buchungsdatum;
    }

    public LocalDate getStartdatum() {
        return startdatum;
    }

    public LocalDate getEnddatum() {
        return enddatum;
    }

    public Integer getSterne() {
        return sterne;
    }

    public LocalDate getBewertungsdatum() {
        return bewertungsdatum;
    }

    public Integer getRechnungsnummer() {
        return rechnungsnummer;
    }

    public Double getRechnungsbetrag() {
        return rechnungsbetrag;
    }

    public LocalDate getRechnungsdatum() {
        return rechnungsdatum;
    }

    public void setBuchnungsnummer(Long buchnungsnummer) {
        this.buchnungsnummer = buchnungsnummer;
    }

    public void setFerienwohnungs_Id(Long ferienwohnungs_id) {
        this.ferienwohnungs_id = ferienwohnungs_id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBuchungsdatum(LocalDate buchungsdatum) {
        this.buchungsdatum = buchungsdatum;
    }

    public void setStartdatum(LocalDate startdatum) {
        this.startdatum = startdatum;
    }

    public void setEnddatum(LocalDate enddatum) {
        this.enddatum = enddatum;
    }

    public void setSterne(Integer sterne) {
        this.sterne = sterne;
    }

    public void setBewertungsdatum(LocalDate bewertungsdatum) {
        this.bewertungsdatum = bewertungsdatum;
    }

    public void setRechnungsnummer(Integer rechnungsnummer) {
        this.rechnungsnummer = rechnungsnummer;
    }

    public void setRechnungsbetrag(Double rechnungsbetrag) {
        this.rechnungsbetrag = rechnungsbetrag;
    }

    public void setRechnungsdatum(LocalDate rechnungsdatum) {
        this.rechnungsdatum = rechnungsdatum;
    }
}