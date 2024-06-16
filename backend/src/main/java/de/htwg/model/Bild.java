package de.htwg.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Bild {

    private String linkURL;
    private Long ferienwohnungs_id;

    @JsonCreator
    public Bild(
            @JsonProperty("linkURL") String linkURL,
            @JsonProperty("ferienwohnungs_id") Long ferienwohnungs_id) {

        this.linkURL = linkURL;
        this.ferienwohnungs_id = ferienwohnungs_id;
    }

    public String getLinkURL() {
        return linkURL;
    }

    public void setLinkURL(String linkURL) {
        this.linkURL = linkURL;
    }

    public Long getFerienwohnungs_Id() {
        return ferienwohnungs_id;
    }

    public void setFerienwohnungs_Id(Long ferienwohnungs_id) {
        this.ferienwohnungs_id = ferienwohnungs_id;
    }
}
