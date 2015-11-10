/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keyboard;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.StackPane;

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
    private StackPane keyboard;
    
    public void addTextControl(TextInputControl tl) {
        all.add(tl);
        tl.focusedProperty().addListener((ob, older, newer) ->{
            if (newer) {
                previousRoot = tl.getScene().getRoot();
                lastScene = tl.getScene();
                keyboard = installKeyboard(previousRoot, tl);
                lastScene.setRoot(keyboard);
            } else {
                keyboard.getChildren().remove(previousRoot);
                lastScene.setRoot(previousRoot);
            }
        });
    }
    
    private static StackPane installKeyboard(Parent pa, TextInputControl tl) {
        StackPane sp = new StackPane();
        sp.getChildren().add(pa);
        InputScene inputScene = InputScene.getInputScene(tl);
        StackPane.setAlignment(inputScene, Pos.BOTTOM_CENTER);
        sp.getChildren().add(inputScene);
        
        return sp;
    }
    
}
