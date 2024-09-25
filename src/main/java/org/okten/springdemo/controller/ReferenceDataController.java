package org.okten.springdemo.controller;

import lombok.RequiredArgsConstructor;
import org.okten.springdemo.config.properties.Office;
import org.okten.springdemo.config.properties.ReferenceDataProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reference-data")
@RequiredArgsConstructor
public class ReferenceDataController {

    @Value("${reference-data.categories}")
    private List<String> categories;

    private final ReferenceDataProperties referenceDataProperties;

    @GetMapping("/categories")
    public List<String> getCategories() {
        return categories;
    }

    @GetMapping("/offices")
    public List<Office> getOffices() {
        return referenceDataProperties.getOffices();
    }
}
