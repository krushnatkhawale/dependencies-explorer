package com.dependency.explorer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Dependency")
public class Library {

    @Id
    @GeneratedValue
    private Long id;
    private String groupId;
    private String artifact;
    private String version;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifact() {
        return artifact;
    }

    public void setArtifact(String artifact) {
        this.artifact = artifact;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getApplicationName() {
        return String.format("%s:%s", groupId, artifact);
    }

    @Override
    public String toString() {
        return String.format("{ applicationName: %s, groupId: %s, artifact: %s, version: %s }", getApplicationName(), groupId, artifact, version);
    }
}