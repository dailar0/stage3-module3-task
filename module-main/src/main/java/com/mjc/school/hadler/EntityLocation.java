package com.mjc.school.hadler;

public enum EntityLocation {
    NEWS("com.mjc.school.repository.model.News"),
    AUTHOR("com.mjc.school.repository.model.Author");
    private final String path;

    EntityLocation(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
