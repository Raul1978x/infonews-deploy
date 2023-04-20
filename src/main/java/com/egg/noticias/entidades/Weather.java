package com.egg.noticias.entidades;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Weather {

    @JsonProperty("description")
    private String description;

    @JsonProperty("icon")
    private String icon;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIconUrl() {
        return "https://www.weatherbit.io/static/img/icons/" + icon + ".png";
    }
}
