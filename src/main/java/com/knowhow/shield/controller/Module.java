package com.knowhow.shield.controller;

public enum Module {

    SHIELD_V1("/shield", "/api/1.0");

    private final String modulePath;
    private final String apiVersion;

    Module(String modulePath, String apiVersion) {
        this.modulePath = modulePath;
        this.apiVersion = apiVersion;
    }

    public String path() {
        return modulePath + apiVersion;
    }

    public String getModulePath() {
        return modulePath;
    }

}
