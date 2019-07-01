package com.dragovorn.dragonbot.api.gui.scene;

import com.dragovorn.dragonbot.api.bot.AbstractIRCBot;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public abstract class AbstractScene implements IScene {

    protected Parent parent;

    private Scene scene;

    @Override
    public final void initialize(URL location, ResourceBundle resources) {
        Splitter urlSplitter = Splitter.on("/")
                .trimResults()
                .omitEmptyStrings();

        Iterable<String> jarSplit = urlSplitter
                .split(AbstractScene.class.getProtectionDomain().getCodeSource().getLocation().toString());

        ArrayList<String> locationList = Lists.newArrayList(urlSplitter.split(location.toString()));
        List<String> result = locationList.subList(Lists.newArrayList(jarSplit).size() + 1, locationList.size());
        String fxmlName = result.get(result.size() - 1);
        fxmlName = fxmlName.substring(0, fxmlName.lastIndexOf("."));

        AbstractIRCBot.getInstance().getGuiManager().registerScene(this, fxmlName);

        initialize();
    }

    @Override
    public void setParent(Parent parent) {
        if (this.parent != null) {
            throw new IllegalStateException(getClass().getCanonicalName() + "Already has a parent!");
        }

        this.parent = parent;
    }

    @Override
    public Parent getParent() {
        return this.parent;
    }

    @Override
    public Scene toJFXScene() {
        return (this.scene == null ? this.scene = new Scene(getParent(), getWidth(), getHeight()) : this.scene);
    }
}
