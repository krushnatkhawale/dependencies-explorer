package com.dependency.explorer;

import com.dependency.explorer.model.BuildInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BuildInfoController {

    @PostMapping
    public ResponseEntity saveBuildInfo(@RequestBody BuildInfo buildInfo) {

        return ResponseEntity.ok().build();
    }
}
