package de.htwg.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Touristenattraktion {
    private String touristenattraktionsname;
    private String beschreibung;

    @JsonCreator
    public Touristenattraktion(
            @JsonProperty("touristenattraktionsname") String touristenattraktionsname,
            @JsonProperty("beschreibung") String beschreibung) {

        this.touristenattraktionsname = touristenattraktionsname;
        this.beschreibung = beschreibung;
    }

    public String getTouristenattraktionsname() {
        return touristenattraktionsname;
    }

    public void setTouristenattraktionsname(String touristenattraktionsname) {
        this.touristenattraktionsname = touristenattraktionsname;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }
}
