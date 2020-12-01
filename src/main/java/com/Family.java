package com;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Family {
    @JsonProperty
    public String name;

    public Family() {
        this.name = "Unknown";
    }

    public Family(String name, String description) {
        this.name = name;
    }
}
