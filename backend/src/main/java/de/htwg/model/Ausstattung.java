package de.htwg.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Ausstattung {
    private String ausstattungsname;

    @JsonCreator
    public Ausstattung(
        @JsonProperty("ausstattungsname") String ausstattungsname) {

        this.ausstattungsname = ausstattungsname;
    }

    public String getAusstattungsname() {
        return ausstattungsname;
    }

    public void setAusstattungsname(String ausstattungsname) {
        this.ausstattungsname = ausstattungsname;
    }
}