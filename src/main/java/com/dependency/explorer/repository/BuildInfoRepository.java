package com.dependency.explorer.repository;

import com.dependency.explorer.model.BuildInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildInfoRepository extends CrudRepository<BuildInfo, Long> {
    public BuildInfo findByApplicationName(String applicationName);
}