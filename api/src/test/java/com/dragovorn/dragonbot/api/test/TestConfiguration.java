package com.dragovorn.dragonbot.api.test;

import com.dragovorn.dragonbot.api.config.Configuration;
import com.dragovorn.dragonbot.api.factory.IFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.junit.Test;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class TestConfiguration {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Test
    public void testConfigurationSave() throws Exception {
        StringWriter writer = new StringWriter();

        Path path = mock(Path.class);
        when(path.toFile()).thenReturn(mock(File.class));

        IFactory<Writer, File> fileWriter = mock(IFactory.class);
        when(fileWriter.create(any(File.class))).thenReturn(writer);

        Configuration configuration = new Configuration(path, fileWriter);
        configuration.set("test", "testing");
        configuration.save();

        JsonObject expected = new JsonObject();
        expected.addProperty("test", "testing");

        assertEquals(GSON.toJson(expected), writer.toString());
    }
}
