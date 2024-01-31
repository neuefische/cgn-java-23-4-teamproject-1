package de.neuefische.cgn234.team1.backend.model.dto;

import java.util.Map;

public record GithubUser(
        String id,
        String name
) {

    public GithubUser(Map<String, Object> attributes) {
        this(attributes.get("id").toString(), attributes.get("login").toString());
    }
}