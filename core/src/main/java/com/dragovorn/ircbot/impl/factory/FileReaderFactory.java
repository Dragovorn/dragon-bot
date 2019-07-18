package com.dragovorn.ircbot.impl.factory;

import com.dragovorn.ircbot.api.factory.IFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class FileReaderFactory implements IFactory<Reader, File> {

    @Override
    public Reader create(File param) throws IOException {
        return new FileReader(param);
    }
}
