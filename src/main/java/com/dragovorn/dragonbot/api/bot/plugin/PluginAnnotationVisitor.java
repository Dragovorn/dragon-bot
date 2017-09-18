/*
 * Copyright (c) 2017. Andrew Burr
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 *  associated documentation files (the "Software"), to deal in the Software without restriction,
 *  including without limitation the rights to use, copy, modify, merge, publish, distribute,
 *  sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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