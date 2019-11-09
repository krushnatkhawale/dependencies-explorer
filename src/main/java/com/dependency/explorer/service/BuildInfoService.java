package com.dependency.explorer.service;

import com.dependency.explorer.model.BuildInfo;
import com.dependency.explorer.repository.BuildInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class BuildInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BuildInfoService.class);
    private final BuildInfoRepository buildInfoRepository;

    public BuildInfoService(BuildInfoRepository buildInfoRepository) {
        this.buildInfoRepository = buildInfoRepository;
    }

    public void saveBuildInfo(BuildInfo newBuildInfo) {
        BuildInfo existingBuildInfo = buildInfoRepository.findByApplicationName(newBuildInfo.getApplicationName());
        if (isNull(existingBuildInfo)) {
            LOGGER.info("New buildInfo received for application: {}", newBuildInfo.getApplicationName());
            buildInfoRepository.save(newBuildInfo);
        }
    }
}