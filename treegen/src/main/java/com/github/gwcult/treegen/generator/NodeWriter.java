package com.github.gwcult.treegen.generator;

import com.github.jknack.handlebars.EscapingStrategy;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.helper.ConditionalHelpers;
import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import lombok.extern.slf4j.Slf4j;
import com.github.gwcult.treegen.generator.models.NodeModel;

import javax.annotation.processing.Filer;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;

import static com.github.gwcult.treegen.generator.Configuration.GENERATED_PACKAGE_NAME;

@Slf4j
public class NodeWriter {
    private final Filer filer;
    private final Template template;

    public NodeWriter(Filer filer) {
        this.filer = filer;
        try {
            Handlebars handlebars = new Handlebars()
                    .with(EscapingStrategy.NOOP)
                    .registerHelpers(ConditionalHelpers.eq)
                    .infiniteLoops(true);
            template = handlebars.compile("node");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void write(NodeModel model) {
        try {
            String qualifiedName = GENERATED_PACKAGE_NAME + "." + model.getNodeName();
            JavaFileObject builderFile = filer.createSourceFile(qualifiedName);
            try (Writer out = builderFile.openWriter()) {
                String code = template.apply(model);
                try {
                    code = new Formatter().formatSource(code);
                } catch (FormatterException exception) {
                    log.error("Template formatting error", exception);
                }
                out.write(code);
            }
        } catch (IOException e) {
            log.error("Template write error", e);
        }
    }
}
