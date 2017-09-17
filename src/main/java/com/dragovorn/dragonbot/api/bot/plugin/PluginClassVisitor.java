package com.dragovorn.dragonbot.api.bot.plugin;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

class PluginClassVisitor extends ClassVisitor {

    private PluginInfo.Builder builder;

    private String path;

    PluginClassVisitor(PluginInfo.Builder builder, String path) {
        super(Opcodes.ASM6);
        this.builder = builder;
        this.path = path;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if (desc.equals("Lcom/dragovorn/dragonbot/api/bot/plugin/Plugin;") && visible) {
            this.builder.setMain(this.path.replaceAll("/", ".").replace(".class", ""));
        }

        return new PluginAnnotationVisitor(this.builder);
    }
}