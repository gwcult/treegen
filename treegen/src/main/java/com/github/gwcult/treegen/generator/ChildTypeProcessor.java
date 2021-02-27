package com.github.gwcult.treegen.generator;

import com.github.gwcult.treegen.TreeGen;
import lombok.RequiredArgsConstructor;
import com.github.gwcult.treegen.generator.models.TypeModel;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import java.util.List;
import java.util.Optional;

import static com.github.gwcult.treegen.generator.utils.Utils.firstNonNull;
import static com.github.gwcult.treegen.generator.utils.Utils.importOrQualified;

@RequiredArgsConstructor
public class ChildTypeProcessor {
    private final Types types;
    private final Elements elements;
    private final List<String> imports;

    public TypeModel getNodeType(TypeMirror getterReturnType) {
        if (getterReturnType instanceof DeclaredType) {
            DeclaredType type = (DeclaredType) getterReturnType;
            return firstNonNull(
                    () -> handleExtendedType(type),
                    () -> handleListType(type),
                    () -> handleSimpleType(type)
            );
        }
        return handleSimpleType(getterReturnType);
    }

    public TypeModel handleExtendedType(DeclaredType type) {
        TreeGen treeGen = type.asElement().getAnnotation(TreeGen.class);
        if (treeGen != null) {
            TypeModel typeModel = new TypeModel();
            typeModel.setName(type.asElement().getSimpleName().toString());
            typeModel.setExtended(true);
            return typeModel;
        }
        return null;
    }

    public TypeModel handleListType(DeclaredType getterReturnType) {
        TypeElement listElement = elements.getTypeElement(List.class.getCanonicalName());
        return asType(getterReturnType, listElement.asType()).map(list -> {
            TypeModel typeModel = new TypeModel();
            typeModel.setName(shortenImport(list.asElement().toString()));
            typeModel.setList(true);
            TypeMirror listParameterType = list.getTypeArguments().get(0);
            typeModel.setSubType(getNodeType(listParameterType));
            return typeModel;
        }).orElse(null);
    }

    public Optional<DeclaredType> asType(TypeMirror originalType, TypeMirror castTo) {
        if (types.isSameType(types.erasure(castTo), types.erasure(originalType))) {
            return Optional.of((DeclaredType) originalType);
        }
        return types.directSupertypes(originalType).stream().map(i -> asType(i, castTo))
                .filter(Optional::isPresent).map(Optional::get).findAny();
    }

    public TypeModel handleSimpleType(TypeMirror type) {
        TypeModel typeModel = new TypeModel();
        if (type instanceof PrimitiveType) {
            type = types.boxedClass((PrimitiveType) type).asType();
        }
        typeModel.setName(shortenImport(type.toString()));
        typeModel.setSimple(true);
        return typeModel;
    }

    public String shortenImport(String name) {
        return importOrQualified(imports, name);
    }
}
