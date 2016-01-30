/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keyboard;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextInputControl;
import javafx.stage.Popup;

/**
 *
 * @author Aniket
 */
public class FocusHandler {

    private static FocusHandler handle;

    public static FocusHandler getFocusHandler() {
        if (handle == null) {
            handle = new FocusHandler();
        }
        return handle;
    }

    private final ObservableList<TextInputControl> all;

    private FocusHandler() {
        all = FXCollections.observableArrayList();
    }

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
                hideExistingKeyboard();
                installKeyboard(tl);
            } else {
                removeKeyboard();
            }
        });
    }
    
    private static Popup popup;

    void removeKeyboard() {
        if (popup!=null){
            popup.hide();
        }
    }

    private static void installKeyboard(TextInputControl tl) {
        Popup p = new Popup();
        p.getContent().add(InputScene.getInputScene(tl));
        double layoutY = tl.getLayoutY();
        System.out.println(layoutY);
        double height = tl.getScene().getWindow().getHeight();
        if (layoutY>height/2) {
            p.show(tl.getScene().getWindow(), tl.getScene().getWindow().getX()+8, layoutY-height/2);
        } else {
            p.show(tl.getScene().getWindow(), tl.getScene().getWindow().getX()+8, height/2);
        }
//        tl.getScene().getWindow().xProperty().addListener((ob, older, newer) -> {
//            p.hide();
//            p.getContent().clear();
//            p.getContent().add(InputScene.getInputScene(tl));
//        });
//        tl.getScene().getWindow().yProperty().addListener((ob, older, newer) -> {
//            p.hide();
//            p.getContent().clear();
//            p.getContent().add(InputScene.getInputScene(tl));
//        });
        popup = p;
    }

    public void hideExistingKeyboard() {

    }

}
