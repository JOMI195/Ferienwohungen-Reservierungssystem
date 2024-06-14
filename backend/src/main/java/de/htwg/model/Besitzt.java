package de.htwg.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Besitzt {
    private Long ferienwohnungsId;
    private String ausstattungsname;

    @JsonCreator
    public Besitzt(
        @JsonProperty("ferienwohnungsId") Long ferienwohnungsId,
        @JsonProperty("ausstattungsname") String ausstattungsname) {

        this.ferienwohnungsId = ferienwohnungsId;
        this.ausstattungsname = ausstattungsname;
    }

    public Long getFerienwohnungsId() {
        return ferienwohnungsId;
    }

    public void setFerienwohnungsId(Long ferienwohnungsId) {
        this.ferienwohnungsId = ferienwohnungsId;
    }

    public String getAusstattungsname() {
        return ausstattungsname;
    }

    public void setAusstattungsname(String ausstattungsname) {
        this.ausstattungsname = ausstattungsname;
    }
}
