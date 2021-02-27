package com.github.gwcult.treegen.generator.models;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class NodeModel {
    private final String packageName;
    private final String nodeName;
    private final String valueType;
    private final List<String> imports = new ArrayList<>();
    private final List<FieldModel> fields = new ArrayList<>();
}
