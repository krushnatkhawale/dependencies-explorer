package com.dependency.explorer.controller;

import com.dependency.explorer.model.BuildInfo;
import com.dependency.explorer.service.BuildInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConceptMapController {

    private final BuildInfoService buildInfoService;

    public ConceptMapController(BuildInfoService buildInfoService) {
        this.buildInfoService = buildInfoService;
    }

    @GetMapping("/conceptMap")
    public ResponseEntity getConceptMap() {
        return ResponseEntity.ok(buildInfoService.getConceptMap());
    }
}