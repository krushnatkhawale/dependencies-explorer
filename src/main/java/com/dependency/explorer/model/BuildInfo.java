package com.dependency.explorer.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Application")
public class BuildInfo {

    @Id
    @GeneratedValue
    private Long id;
    private String applicationName;
    private String groupId;
    private String artifact;
    private String version;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "application_id")
    private List<Library> dependencies = new ArrayList<>();

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public void setApplicationName() {
        this.applicationName = String.format("%s:%s", groupId, artifact);
    }

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

    public List<Library> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<Library> dependencies) {
        this.dependencies = dependencies;
    }

    @Override
    public String toString() {
        return String.format("{ applicationName: %s, groupId: %s, artifact: %s, version: %s, dependencies: %s }",
                applicationName, groupId, artifact, version, dependencies);
    }
}