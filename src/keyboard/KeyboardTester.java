/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keyboard;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Aniket
 */
public class KeyboardTester extends Application {

    @Override
    public void start(Stage st) {
        st.initStyle(StageStyle.UNDECORATED);
        st.setTitle("Virtual Keyboard");
        Rectangle2D vb = Screen.getPrimary().getVisualBounds();
        BorderPane ap;
        st.setScene(new Scene(ap = new BorderPane(), vb.getWidth(), vb.getHeight(), Color.WHITE));
        st.getScene().setOnKeyPressed((e) -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                st.close();
                Platform.exit();
                System.exit(0);
            }
        });
        TextField tf, tfg;
        ap.setTop(tf = new TextField());
        ap.setBottom(tfg = new TextField());
        FocusHandler.getFocusHandler().addTextControl(tf);
        FocusHandler.getFocusHandler().addTextControl(tfg);
        ap.setCenter(new VBox(new Button("Hello"),
                new Button("Hello"), new Button("Hello"), new Button("Hello"), new Button("Hello")));
        st.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
