package com;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Unit {
    @JsonProperty
    public String name;
    @JsonProperty
    public String description;
    @JsonProperty
    public String type;

    public Unit() {
        this.name = "Unknown";
        this.description = "Unknown";
        this.type = "Unknown";
    }

    public Unit(String name, String description, String type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }
}
