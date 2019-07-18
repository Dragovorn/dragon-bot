package com.dragovorn.ircbot.impl.gui.fxml;

import com.dragovorn.ircbot.api.IAPIManager;
import com.dragovorn.ircbot.impl.bot.AbstractIRCBot;
import com.dragovorn.ircbot.api.bot.IIRCBot;
import com.dragovorn.ircbot.api.gui.IGuiManager;
import com.dragovorn.ircbot.api.gui.IScene;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public abstract class AbstractFXMLScene implements IScene {

    protected static final IIRCBot bot = AbstractIRCBot.getInstance();
    protected static final IGuiManager guiManager = bot.getGuiManager();
    protected static final IAPIManager apiManager = bot.getAPIManager();

    protected Parent parent;

    private Scene scene;

    @Override
    public final void initialize(URL location, ResourceBundle resources) {
        Splitter urlSplitter = Splitter.on("/")
                .trimResults()
                .omitEmptyStrings();

        // Split the path to our jarfile on it's /'s
        Iterable<String> jarSplit = urlSplitter
                .split(AbstractFXMLScene.class.getProtectionDomain().getCodeSource().getLocation().toString());

        // Do list operations to distill it down to the fxml directory & file name
        ArrayList<String> locationList = Lists.newArrayList(urlSplitter.split(location.toString()));
        List<String> result = locationList.subList(Lists.newArrayList(jarSplit).size() + 1, locationList.size());

        // Remove file extension.
        String fxmlName = createFileStr(result);
        fxmlName = fxmlName.substring(0, fxmlName.lastIndexOf("."));

        // Make this object known to the GuiManager
        AbstractIRCBot.getInstance().getGuiManager().registerScene(this, fxmlName);

        initialize();
    }

    /**
     * Convert the given list to a string with '/'s.
     *
     * @param list The list to convert.
     * @return The string with '/'s
     */
    private String createFileStr(List<String> list) {
        StringBuilder builder = new StringBuilder();

        for (String str : list) {
            if (builder.length() != 0) {
                builder.append("/");
            }

            builder.append(str);
        }

        return builder.toString();
    }

    @Override
    public void setParent(Parent parent) {
        if (this.parent != null) {
            throw new IllegalStateException(getClass().getCanonicalName() + "Already has a parent!");
        }

        this.parent = parent;
    }

    public Parent getParent() {
        return this.parent;
    }

    public Scene toJFXScene() {
        return (this.scene == null ? this.scene = new Scene(getParent(), getWidth(), getHeight()) : this.scene);
    }
}
