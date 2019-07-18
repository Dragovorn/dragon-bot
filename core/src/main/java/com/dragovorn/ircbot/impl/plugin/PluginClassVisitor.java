package com.dragovorn.ircbot.impl.plugin;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

public class PluginClassVisitor extends ClassVisitor {

    private LoadedPlugin.Builder builder;

    private String path;

    PluginClassVisitor(String path, LoadedPlugin.Builder builder) {
        super(Opcodes.ASM6);
        this.builder = builder;
        this.path = path;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        // If the annotation we want to scan is found go ahead and scan it and log the class it's on.
        if (desc.equals("Lcom/dragovorn/ircbot/api/plugin/Plugin;") && visible) {
            this.builder.setMain(this.path.replaceAll("/", ".").replace(".class", ""));

            return new PluginAnnotationVisitor(this.builder);
        }

        return super.visitAnnotation(desc, visible);
    }
}
