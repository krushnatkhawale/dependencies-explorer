package com.dependency.explorer;

import com.dependency.explorer.model.BuildInfo;
import com.dependency.explorer.service.BuildInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BuildInfoController {

    private final BuildInfoService buildInfoService;

    public BuildInfoController(BuildInfoService buildInfoService) {
        this.buildInfoService = buildInfoService;
    }

    @PostMapping("/buildInfo")
    public ResponseEntity saveBuildInfo(@RequestBody BuildInfo buildInfo) {
        buildInfoService.saveBuildInfo(buildInfo);
        return ResponseEntity.ok().build();
    }
}