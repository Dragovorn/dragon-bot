package com.dragovorn.ircbot.impl.plugin;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Opcodes;

public class PluginAnnotationVisitor extends AnnotationVisitor {

    private LoadedPlugin.Builder builder;

    PluginAnnotationVisitor(LoadedPlugin.Builder builder) {
        super(Opcodes.ASM6);
        this.builder = builder;
    }

    @Override
    public void visit(String fieldName, Object value) {
        switch (fieldName) {
            case "name":
                this.builder.setName((String) value);
                break;
            case "author":
                this.builder.setAuthor((String) value);
                break;
            case "version":
                this.builder.setVersion((String) value);
                break;
            case "depends":
                this.builder.setDepends((String[]) value);
                break;
        }

        super.visit(fieldName, value);
    }
}
