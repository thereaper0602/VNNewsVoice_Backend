package com.pmq.vnnewsvoice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pmq.vnnewsvoice.pojo.Generator;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link Generator}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeneratorDto implements Serializable {
    private Long id;
    private String name;
    private String logoUrl;
    private String url;
    private Boolean isActive;

    public GeneratorDto() {
    }

    public GeneratorDto(Long id, String name, String logoUrl, String url, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.logoUrl = logoUrl;
        this.url = url;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeneratorDto entity = (GeneratorDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.name, entity.name) &&
                Objects.equals(this.logoUrl, entity.logoUrl) &&
                Objects.equals(this.url, entity.url) &&
                Objects.equals(this.isActive, entity.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, logoUrl, url, isActive);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "logoUrl = " + logoUrl + ", " +
                "url = " + url + ", " +
                "isActive = " + isActive + ")";
    }
}