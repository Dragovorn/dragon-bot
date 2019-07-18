package com.dragovorn.ircbot.impl.config.test;

import com.dragovorn.ircbot.api.bot.IRCBot;
import com.dragovorn.ircbot.impl.bot.AbstractIRCBot;
import com.dragovorn.ircbot.impl.config.FileConfiguration;
import com.dragovorn.ircbot.api.factory.IFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(IRCBot.class)
public class TestFileConfiguration {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private FileConfiguration configuration;

    private StringWriter writer;

    private StringReader reader;

    @Before
    public void before() throws Exception {
        IRCBot ircBot = mock(IRCBot.class);

        mockStatic(IRCBot.class);
        when(IRCBot.getInstance()).thenReturn(ircBot);
        when(ircBot.getGSON()).thenReturn(new GsonBuilder().setPrettyPrinting().create());

        this.writer = new StringWriter();
        this.reader = new StringReader("{\n\"test\": \"testing\",\n\"more\": {\n\"testing\": \"fun\",\n\"work\": \"yay!\"\n}\n}");

        File file = mock(File.class);
        when(file.exists()).thenReturn(true);

        Path path = mock(Path.class);
        when(path.toFile()).thenReturn(file);

        IFactory<Writer, File> fileWriter = mock(IFactory.class);
        when(fileWriter.create(any(File.class))).thenReturn(this.writer);

        IFactory<Reader, File> fileReader = mock(IFactory.class);
        when(fileReader.create(any(File.class))).thenReturn(this.reader);

        this.configuration = new FileConfiguration(path, fileWriter, fileReader);
    }

    @Test
    public void testConfigurationSave() {
        this.configuration.reset();
        this.configuration.set("test", "testing");
        this.configuration.set("more.testing", "fun");
        this.configuration.set("more.work", "yay!");
        this.configuration.save();

        JsonObject more = new JsonObject();
        more.addProperty("testing", "fun");
        more.addProperty("work", "yay!");

        JsonObject expected = new JsonObject();
        expected.addProperty("test", "testing");
        expected.add("more", more);

        assertEquals(GSON.toJson(expected), this.writer.toString());
    }

    @Test
    public void testConfigurationLoad() {
        this.configuration.reset();
        this.configuration.load();

        assertEquals("testing", this.configuration.get("test"));
        assertEquals("fun", this.configuration.get("more.testing"));
        assertEquals("yay!", this.configuration.get("more.work"));
    }
}
