package com.dragovorn.dragonbot.api.factory;

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
