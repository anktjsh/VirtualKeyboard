/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keyboard;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

/**
 *
 * @author Aniket
 */
public class FocusHandler {

    private static FocusHandler handle;

    public static FocusHandler getInputScene() {
        if (handle == null) {
            handle = new FocusHandler();
        }
        return handle;
    }

    private final ObservableList<TextInputControl> all;

    public FocusHandler() {
        all = FXCollections.observableArrayList();
    }

    private Parent previousRoot;
    private Scene lastScene;
    private Pane keyboard;

    public void addTextControls(TextInputControl... tl) {
        for (TextInputControl til : tl) {
            addTextControl(til);
        }
    }

    public void addTextControls(List<TextInputControl> tl) {
        for (TextInputControl til : tl) {
            addTextControl(til);
        }
    }

    public void addTextControl(TextInputControl tl) {
        all.add(tl);
        tl.focusedProperty().addListener((ob, older, newer) -> {
            if (newer) {
                previousRoot = tl.getScene().getRoot();
                lastScene = tl.getScene();
                keyboard = installKeyboard(previousRoot, tl);
                lastScene.setRoot(keyboard);
            } else {
                removeKeyboard();
            }
        });
    }

    public void removeKeyboard() {
        if (keyboard.getChildren().get(0).equals(previousRoot)) {
            keyboard.getChildren().remove(previousRoot);
        } else {
            if (previousRoot.getParent() != null) {
                ((VBox) previousRoot.getParent()).getChildren().remove(previousRoot);
            }
        }
        if (previousRoot.getParent() != null) {
            System.out.println(previousRoot.getParent().getClass().getName());
        }
        lastScene.setRoot(previousRoot);
    }

    private static Pane installKeyboard(Parent pa, TextInputControl tl) {
        double loc = tl.getLayoutY();
        double height = Screen.getPrimary().getVisualBounds().getHeight() / 2;
        StackPane sp = new StackPane();
        if (loc < height) {
            sp.getChildren().add(pa);
            InputScene inputScene = InputScene.getInputScene(tl);
            StackPane.setAlignment(inputScene, Pos.BOTTOM_CENTER);
            sp.getChildren().add(inputScene);
            return sp;
        } else {
            HBox hb = new HBox();
            hb.setMinHeight(loc - height);
            VBox vb = new VBox(pa, hb);
            sp.getChildren().add(vb);
            InputScene inputScene = InputScene.getInputScene(tl);
            StackPane.setAlignment(inputScene, Pos.BOTTOM_CENTER);
            sp.getChildren().add(inputScene);
            return sp;
        }
    }

}
