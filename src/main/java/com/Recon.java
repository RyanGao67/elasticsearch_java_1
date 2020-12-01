package com;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Recon {
    @JsonProperty
    public String reconSearchQuery = "";
    @JsonProperty
    public long startTime = 0L;
    @JsonProperty
    public long endTime = 0L;

    public Recon() {
    }
}
