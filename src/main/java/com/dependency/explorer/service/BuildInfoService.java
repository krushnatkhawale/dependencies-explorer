package com.dependency.explorer.service;

import com.dependency.explorer.model.BuildInfo;
import com.dependency.explorer.model.Library;
import com.dependency.explorer.repository.BuildInfoRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.Objects.isNull;

@Service
public class BuildInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BuildInfoService.class);
    private final BuildInfoRepository buildInfoRepository;
    private final ObjectMapper objectMapper;

    public BuildInfoService(BuildInfoRepository buildInfoRepository, ObjectMapper objectMapper) {
        this.buildInfoRepository = buildInfoRepository;
        this.objectMapper = objectMapper;
    }

    public void saveBuildInfo(BuildInfo newBuildInfo) {
        BuildInfo existingBuildInfo = buildInfoRepository.findByApplicationName(newBuildInfo.getApplicationName());
        if (isNull(existingBuildInfo)) {
            LOGGER.info("New buildInfo received for application: {}", newBuildInfo.getApplicationName());
            if(isNull(newBuildInfo.getApplicationName()) || newBuildInfo.getApplicationName().isEmpty()){
                newBuildInfo.setApplicationName();
            }
            buildInfoRepository.save(newBuildInfo);
        }
    }

    public JsonNode getConceptMap() {
        LOGGER.info("Returning a conceptMap");
        return createConceptMap(buildInfoRepository.findAll());
    }

    private JsonNode createConceptMap(Iterable<BuildInfo> allBuildInfo) {
        ArrayNode perspectives = getPerspectives(allBuildInfo);
        ArrayNode ditems = getDitems(allBuildInfo);
        ArrayNode themes = objectMapper.createArrayNode();

        return getAsJsonNode(perspectives, ditems, themes);
    }

    private ObjectNode getAsJsonNode(ArrayNode perspectives, ArrayNode ditems, ArrayNode themes) {
        ObjectNode perspectiveNode = objectMapper.createObjectNode();

        perspectiveNode.set("ditems", ditems);
        perspectiveNode.set("themes", themes);
        perspectiveNode.set("perspectives", perspectives);
        return perspectiveNode;
    }

    private ArrayNode getPerspectives(Iterable<BuildInfo> allBuildInfo) {
        return StreamSupport.stream(allBuildInfo.spliterator(), false)
                .map(BuildInfo::getDependencies)
                .flatMap(Collection::stream)
                .map(this::toPerspectives)
                .distinct()
                .collect(objectMapper::createArrayNode, ArrayNode::add, ArrayNode::addAll);
    }

    private ArrayNode getDitems(Iterable<BuildInfo> allBuildInfo) {
        return StreamSupport.stream(allBuildInfo.spliterator(), false)
                .map(this::toDitem)
                .collect(objectMapper::createArrayNode, ArrayNode::add, ArrayNode::addAll);
    }

    private JsonNode toPerspectives(Library library) {
        ObjectNode perspectiveNode = objectMapper.createObjectNode();
        perspectiveNode.put("name", library.getApplicationName());
        return perspectiveNode;
    }

    private JsonNode toDitem(BuildInfo buildInfo) {
        ObjectNode perspectiveNode = objectMapper.createObjectNode();
        ArrayNode links = getLinks(buildInfo.getDependencies());

        perspectiveNode.put("name", buildInfo.getApplicationName());
        perspectiveNode.set("links", links);
        return perspectiveNode;
    }

    private ArrayNode getLinks(List<Library> dependencies) {
        return dependencies.stream()
                .map(Library::getApplicationName)
                .collect(objectMapper::createArrayNode, ArrayNode::add, ArrayNode::addAll);
    }
}