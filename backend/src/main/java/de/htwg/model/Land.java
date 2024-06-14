package de.htwg.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Land {
    private String landname;

    @JsonCreator
    public Land(
        @JsonProperty("landname") String landname) {
            
        this.landname = landname;
    }

    public String getLandname() {
        return landname;
    }

    public void setLandname(String landname) {
        this.landname = landname;
    }

}
