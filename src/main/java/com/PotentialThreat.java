package com;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PotentialThreat {
    @JsonProperty
    public String name;
    @JsonProperty
    public String description;

    public PotentialThreat() {
        this.name = "Unknown";
        this.description = "Unknown";
    }

    public PotentialThreat(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
