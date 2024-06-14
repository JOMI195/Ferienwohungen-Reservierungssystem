package de.htwg.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Ferienwohnung {
    private Long ferienwohnungs_id;
    private String ferienwohnungsname;
    private double mietpreis;
    private int zimmer;
    private int größe;
    private String straße;
    private String hausnummer;
    private String postleitzahl;
    private String ort;
    private String landname;

    @JsonCreator
    public Ferienwohnung(
            @JsonProperty("ferienwohnungs_id") Long ferienwohnungs_id,
            @JsonProperty("ferienwohnungsname") String ferienwohnungsname,
            @JsonProperty("mietpreis") double mietpreis,
            @JsonProperty("zimmer") int zimmer,
            @JsonProperty("größe") int größe,
            @JsonProperty("straße") String straße,
            @JsonProperty("hausnummer") String hausnummer,
            @JsonProperty("postleitzahl") String postleitzahl,
            @JsonProperty("ort") String ort,
            @JsonProperty("landname") String landname) {

        this.ferienwohnungs_id = ferienwohnungs_id;
        this.ferienwohnungsname = ferienwohnungsname;
        this.mietpreis = mietpreis;
        this.zimmer = zimmer;
        this.größe = größe;
        this.straße = straße;
        this.hausnummer = hausnummer;
        this.postleitzahl = postleitzahl;
        this.ort = ort;
        this.landname = landname;
    }

    public Long getFerienwohnungs_Id() {
        return ferienwohnungs_id;
    }

    public void setFerienwohnungs_Id(Long ferienwohnungs_id) {
        this.ferienwohnungs_id = ferienwohnungs_id;
    }

    public String getFerienwohnungsname() {
        return ferienwohnungsname;
    }

    public void setFerienwohnungsname(String ferienwohnungsname) {
        this.ferienwohnungsname = ferienwohnungsname;
    }

    public double getMietpreis() {
        return mietpreis;
    }

    public void setMietpreis(double mietpreis) {
        this.mietpreis = mietpreis;
    }

    public int getZimmer() {
        return zimmer;
    }

    public void setZimmer(int zimmer) {
        this.zimmer = zimmer;
    }

    public int getGröße() {
        return größe;
    }

    public void setGröße(int größe) {
        this.größe = größe;
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
