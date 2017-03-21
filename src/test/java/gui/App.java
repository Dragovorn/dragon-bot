/*
 * Copyright (c) 2017. Andrew Burr
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 *  associated documentation files (the "Software"), to deal in the Software without restriction,
 *  including without limitation the rights to use, copy, modify, merge, publish, distribute,
 *  sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or
 *  substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 *  INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 *  PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 *  COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 *  ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 *  THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class App extends Application {

    private Map<String, Parent> parents;

    private Stage stage;

    private static App instance;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        instance = this;
        this.parents = new HashMap<>();
        this.parents.put("main", FXMLLoader.load(getClass().getResource("/gui/main.fxml")));
        this.parents.put("options", FXMLLoader.load(getClass().getResource("/gui/options.fxml")));

        stage.setResizable(false);
        stage.setTitle("GUI Test");
        stage.setScene(new Scene(this.parents.get("main"), 500, 50));
        stage.show();

        this.stage = stage;
    }

    public void set(String key) {
        this.stage.getScene().setRoot(this.parents.get(key));
    }

    public void resize(double width, double height) {
        this.stage.setWidth(width);
        this.stage.setHeight(height);
    }

    public static App getInstance() {
        return instance;
    }
}