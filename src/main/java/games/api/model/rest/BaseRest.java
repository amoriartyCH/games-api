package games.api.model.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class BaseRest {

    @JsonProperty("links")
    private Map<String, String> links = new HashMap<>();

    public Map<String, String> getLinks() {
        return links;
    }

    public void setLinks(Map<String, String> links) {
        this.links = links;
    }
}
