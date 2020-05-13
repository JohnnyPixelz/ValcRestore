package com.valcanium.valcrestore;

public enum RestoreType {

    JOIN("Joins"),
    QUIT("Quits"),
    DEATH("Deaths"),
    WORLD_CHANGE("World Changes");

    private String type;

    RestoreType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
