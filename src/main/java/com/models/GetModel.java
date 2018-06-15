package com.models;

import org.springframework.context.annotation.Bean;

public class GetModel {
    private String id;
    private String secondId;
    private String typeE;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeE() {
        return typeE;
    }

    public void setTypeE(String typeE) {
        this.typeE = typeE;
    }

    public String getSecondId() {
        return secondId;
    }

    public void setSecondId(String secondId) {
        this.secondId = secondId;
    }

    @Override
    public String toString() {
        return "GetModel{" +
                "id='" + id + '\'' +
                ", typeE='" + typeE + '\'' +
                '}';
    }
}
