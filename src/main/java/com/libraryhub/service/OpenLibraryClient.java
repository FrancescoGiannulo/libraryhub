package com.libraryhub.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.libraryhub.dto.OpenLibraryBookDto;
import com.libraryhub.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class OpenLibraryClient {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final String BASE_URL =
            "https://openlibrary.org/api/books?bibkeys=ISBN:{isbn}&format=json&jscmd=data";

    @Cacheable(value = "openLibraryBooks", key = "#isbn")
    public OpenLibraryBookDto fetchByIsbn(String isbn) {
        log.info("Calling Open Library API for ISBN: {}", isbn);

        String url = BASE_URL.replace("{isbn}", isbn);
        String key = "ISBN:" + isbn;

        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);
            JsonNode bookNode = root.get(key);

            if (bookNode == null || bookNode.isNull()) {
                throw new ResourceNotFoundException("Libro non trovato per ISBN: " + isbn);
            }

            return objectMapper.treeToValue(bookNode, OpenLibraryBookDto.class);

        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Errore chiamando Open Library per ISBN {}: {} - Causa: {}",
                    isbn, e.getMessage(), e.getClass().getSimpleName(), e);
            throw new ResourceNotFoundException("Impossibile recuperare il libro per ISBN: " + isbn);
        }
    }
}
