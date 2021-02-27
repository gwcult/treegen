package com.github.gwcult.treegen.generator.models;

import lombok.Data;

@Data
public class TypeModel {
    private String name;
    private TypeModel subType;
    private boolean list;
    private boolean extended;
    private boolean simple;
}
