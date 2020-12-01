package com;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Templates {
    @JsonProperty
    public String threat = "";
    @JsonProperty
    public String family = "";
    @JsonProperty
    public String teaser = "";
    @JsonProperty
    public String alert = "";
    @JsonProperty
    public String tooltip = "";

    public Templates() {
    }
}

