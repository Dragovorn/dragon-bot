package com.dragovorn.ircbot.impl.factory;

import com.dragovorn.ircbot.api.factory.IFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class FileWriterFactory implements IFactory<Writer, File> {

    @Override
    public Writer create(File param) throws IOException {
        return new FileWriter(param);
    }
}
