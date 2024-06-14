package de.htwg.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Besitzt {
    private Long ferienwohnungs_id;
    private String ausstattungsname;

    @JsonCreator
    public Besitzt(
            @JsonProperty("ferienwohnungs_id") Long ferienwohnungs_id,
            @JsonProperty("ausstattungsname") String ausstattungsname) {

        this.ferienwohnungs_id = ferienwohnungs_id;
        this.ausstattungsname = ausstattungsname;
    }

    public Long getFerienwohnungs_Id() {
        return ferienwohnungs_id;
    }

    public void setFerienwohnungs_Id(Long ferienwohnungs_id) {
        this.ferienwohnungs_id = ferienwohnungs_id;
    }

    public String getAusstattungsname() {
        return ausstattungsname;
    }

    public void setAusstattungsname(String ausstattungsname) {
        this.ausstattungsname = ausstattungsname;
    }
}
