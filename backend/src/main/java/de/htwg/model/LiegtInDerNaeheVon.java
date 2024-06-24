package de.htwg.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LiegtInDerNaeheVon {
    private Long ferienwohnungs_id;
    private String touristenattraktionsname;
    private int entfernung;

    @JsonCreator
    public LiegtInDerNaeheVon(
            @JsonProperty("ferienwohnungs_id") Long ferienwohnungs_id,
            @JsonProperty("touristenattraktionsname") String touristenattraktionsname,
            @JsonProperty("entfernung") int entfernung) {

        this.ferienwohnungs_id = ferienwohnungs_id;
        this.touristenattraktionsname = touristenattraktionsname;
        this.entfernung = entfernung;
    }

    public Long getFerienwohnungs_id() {
        return ferienwohnungs_id;
    }

    public void setFerienwohnungs_id(Long ferienwohnungs_id) {
        this.ferienwohnungs_id = ferienwohnungs_id;
    }

    public String getTouristenattraktionsname() {
        return touristenattraktionsname;
    }

    public void setTouristenattraktionsname(String touristenattraktionsname) {
        this.touristenattraktionsname = touristenattraktionsname;
    }

    public int getEntfernung() {
        return entfernung;
    }

    public void setEntfernung(int entfernung) {
        this.entfernung = entfernung;
    }
}
