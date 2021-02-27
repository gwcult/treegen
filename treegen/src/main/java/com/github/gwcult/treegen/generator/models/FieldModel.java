package com.github.gwcult.treegen.generator.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FieldModel {
    private String name;
    private String getter;
    private TypeModel type;
}
