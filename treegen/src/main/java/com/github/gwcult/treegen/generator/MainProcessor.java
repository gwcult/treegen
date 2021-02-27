package com.github.gwcult.treegen.generator;

import com.github.gwcult.treegen.generator.models.NodeModel;
import com.google.auto.service.AutoService;
import lombok.extern.slf4j.Slf4j;
import com.github.gwcult.treegen.generator.models.FieldModel;
import com.github.gwcult.treegen.generator.utils.Utils;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.*;
import java.util.stream.Collectors;

import static com.github.gwcult.treegen.generator.utils.Utils.getterToFieldName;

@SupportedAnnotationTypes("com.github.gwcult.treegen.TreeGen")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class MainProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends TypeElement> elements = annotations.stream()
                .map(roundEnv::getElementsAnnotatedWith)
                .flatMap(Collection::stream)
                .map(el -> (TypeElement) el)
                .collect(Collectors.toSet());
        List<NodeModel> list = elements.stream().map(this::toNodeModel).collect(Collectors.toList());
        NodeWriter nodeWriter = new NodeWriter(processingEnv.getFiler());
        list.forEach(nodeWriter::write);
        return false;
    }

    public NodeModel toNodeModel(TypeElement element) {
        NodeModel nodeModel = NodeModel.builder()
                .packageName(Configuration.GENERATED_PACKAGE_NAME)
                .nodeName(element.getSimpleName().toString() + Configuration.GENERATED_CLASS_SUFFIX)
                .valueType(element.getSimpleName().toString())
                .build();
        nodeModel.getImports().add(element.getQualifiedName().toString());
        List<FieldModel> fields = element.getEnclosedElements().stream()
                .filter(elem -> elem instanceof ExecutableElement)
                .map(elem -> (ExecutableElement) elem)
                .filter(Utils::isGetter)
                .map(i -> toFieldModel(nodeModel.getImports(), i))
                .collect(Collectors.toList());
        nodeModel.getFields().addAll(fields);
        return nodeModel;
    }

    public FieldModel toFieldModel(List<String> imports, ExecutableElement getter) {
        ChildTypeProcessor childTypeProcessor = new ChildTypeProcessor(
                processingEnv.getTypeUtils(),
                processingEnv.getElementUtils(),
                imports
        );
        return FieldModel.builder()
                .name(getterToFieldName(getter.getSimpleName().toString()))
                .getter(getter.getSimpleName().toString())
                .type(childTypeProcessor.getNodeType(getter.getReturnType()))
                .build();
    }

}


