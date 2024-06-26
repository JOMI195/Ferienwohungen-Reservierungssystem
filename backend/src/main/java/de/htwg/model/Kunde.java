package de.htwg.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Kunde {

    private String email;
    private String passwort;
    private String vorname;
    private String nachname;
    private String iban;
    private char newsletter;
    private String straße;
    private String hausnummer;
    private String postleitzahl;
    private String ort;
    private String landname;

    @JsonCreator
    public Kunde(
            @JsonProperty("email") String email,
            @JsonProperty("passwort") String passwort,
            @JsonProperty("vorname") String vorname,
            @JsonProperty("nachname") String nachname,
            @JsonProperty("iban") String iban,
            @JsonProperty("newsletter") char newsletter,
            @JsonProperty("straße") String straße,
            @JsonProperty("hausnummer") String hausnummer,
            @JsonProperty("postleitzahl") String postleitzahl,
            @JsonProperty("ort") String ort,
            @JsonProperty("landname") String landname) {
                
        this.email = email;
        this.passwort = passwort;
        this.vorname = vorname;
        this.nachname = nachname;
        this.iban = iban;
        this.newsletter = newsletter;
        this.straße = straße;
        this.hausnummer = hausnummer;
        this.postleitzahl = postleitzahl;
        this.ort = ort;
        this.landname = landname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public char getNewsletter() {
        return newsletter;
    }

    public void setNewsletter(char newsletter) {
        this.newsletter = newsletter;
    }

    public String getStraße() {
        return straße;
    }

    public void setStraße(String straße) {
        this.straße = straße;
    }

    public String getHausnummer() {
        return hausnummer;
    }

    public void setHausnummer(String hausnummer) {
        this.hausnummer = hausnummer;
    }

    public String getPostleitzahl() {
        return postleitzahl;
    }

    public void setPostleitzahl(String postleitzahl) {
        this.postleitzahl = postleitzahl;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getLandname() {
        return landname;
    }

    public void setLandname(String landname) {
        this.landname = landname;
    }
}
