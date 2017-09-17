package com.dragovorn.dragonbot.api.bot.plugin;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Opcodes;

class PluginAnnotationVisitor extends AnnotationVisitor {

    private PluginInfo.Builder builder;

    PluginAnnotationVisitor(PluginInfo.Builder builder) {
        super(Opcodes.ASM6);
        this.builder = builder;
    }

    @Override
    public void visit(String fieldName, Object value) {
        switch (fieldName) {
            case "name":
                this.builder.setName((String) value);
                break;
            case "version":
                this.builder.setVersion((String) value);
                break;
            case "author":
                this.builder.setAuthor((String) value);
                break;
            case "dependencies":
                this.builder.setDependencies((String[]) value);
                break;
        }

        super.visit(fieldName, value);
    }
}