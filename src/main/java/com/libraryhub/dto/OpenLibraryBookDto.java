package com.libraryhub.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenLibraryBookDto {
    private String title;

    private List<Author> authors;

    private Cover cover;

    @JsonProperty("publish_date")
    private String publishDate;

    private List<Subject> subjects;

    private String notes;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Author {
        private String name;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Subject {
        private String name;
    }

    // Metodo helper: estrae il primo autore
    public String getFirstAuthorName() {
        if (authors != null && !authors.isEmpty()) {
            return authors.get(0).getName();
        }
        return "Unknown Author";
    }

    // Metodo helper: estrae il primo genere (subject)
    public String getFirstSubjectName() {
        if (subjects != null && !subjects.isEmpty()) {
            return subjects.get(0).getName();
        }
        return null;
    }

    // Metodo helper: estrae l'anno da publishDate (es. "January 1, 2001" → 2001)
    public Integer extractYear() {
        if (publishDate == null) return null;
        try {
            String[] parts = publishDate.split("[,\\s]+");
            for (String part : parts) {
                if (part.matches("\\d{4}")) {
                    return Integer.parseInt(part);
                }
            }
        } catch (Exception ignored) {}
        return null;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Cover {
        private String small;
        private String medium;
        private String large;
    }

    // Aggiungi anche questo metodo helper:
    public String getCoverMediumUrl() {
        if (cover != null && cover.getMedium() != null) return cover.getMedium();
        if (cover != null && cover.getLarge() != null) return cover.getLarge();
        return null;
    }
}
