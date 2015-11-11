/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keyboard;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Screen;

/**
 *
 * @author Aniket
 */
public class InputScene extends BorderPane {

    private static InputScene scene;
    private static TextInputControl current;

    public static InputScene getInputScene(TextInputControl cur) {
        current = cur;
        //System.out.println(cur.getLayoutY());
        //System.out.println(cur.get)
        //System.out.println("set");
        if (scene == null) {
            scene = new InputScene();
        }
        return scene;
    }

    private final HBox[] layers;
    private final VBox total;

    private InputScene() {
        Rectangle2D vb = Screen.getPrimary().getVisualBounds();
        double a = vb.getWidth() - 130;
        double b = (vb.getHeight() *0.4);
        setStyle("-fx-background-color:white;");
        total = new VBox(10);
        setCenter(total);
        total.setPadding(new Insets(5, 10, 5, 10));
        layers = new HBox[5];
        for (int x = 0; x < layers.length; x++) {
            layers[x] = new HBox(10);
            layers[x].setPadding(new Insets(5, 10, 5, 10));
            layers[x].setAlignment(Pos.CENTER);
        }
        layers[1].getChildren().addAll(Q, W, E, R, T, Y, U, I, O, P);
        layers[2].getChildren().addAll(A, S, D, F, G, H, J, K, L);
        layers[3].getChildren().addAll(SHIFT, Z, X, C, V, B, N, M, BACK);
        for (int x = 0; x < letters.size(); x++) {
            letters.get(x).setMaxWidth(a / 10);
            letters.get(x).setMinWidth(a / 10);
            letters.get(x).setMinHeight(b / 5);
            letters.get(x).setMaxHeight(b / 5);
            letters.get(x).setFont(new Font(Math.sqrt(a * b) / 25));
            letters.get(x).setFocusTraversable(false);
        }
        SHIFT.setMaxWidth(a / 10);
        SHIFT.setMinWidth(a / 10);
        SHIFT.setMinHeight(b / 5);
        SHIFT.setMaxHeight(b / 5);
        SHIFT.setFont(new Font(Math.sqrt(a * b) / 25));
        SHIFT.setOnMouseClicked((e) -> {
            int clic = e.getClickCount();
            if (clic == 1) {
                char c = A.getText().charAt(0);
                if (c == 'A') {
                    shiftToLower();
                } else {
                    shiftToUpper();
                }
                if (SHIFT.getStyle().contains("-fx-background-color:blue;")) {
                    SHIFT.setStyle("");
                }
            } else if (clic == 2) {
                shiftToUpper();
                SHIFT.setStyle("-fx-background-color:blue;");
            }
        });
        BACK.setMaxWidth(a / 10);
        BACK.setMinWidth(a / 10);
        BACK.setMinHeight(b / 5);
        BACK.setMaxHeight(b / 5);
        BACK.setFont(new Font(Math.sqrt(a * b) / 25));
        BACK.setOnAction((e) -> {
            if (current != null) {
                int car = current.getCaretPosition();
                if (car > 0) {
                    current.setText(current.getText().substring(0, car - 1) + current.getText().substring(car));
                    current.positionCaret(car - 1);
                }
            }
        });
        layers[4].getChildren().addAll(CHANGE, SPACE, DOWN, ENTER);
        CHANGE.setOnAction((e) -> {
            if (CHANGE.getText().equals("123")) {
                CHANGE.setText("abc");
            } else {
                CHANGE.setText("123");
            }
        });
        ENTER.setOnAction((e) -> {
            if (current != null) {
                if (current instanceof TextField) {
                    TextField tf = (TextField)current;
                    tf.getOnAction().handle(new ActionEvent(null, tf));
                } else {
                    current.insertText(current.getCaretPosition(), "\n");
                }
            }
        });
        SPACE.setOnAction((e) -> {
            if (current != null) {
                current.insertText(current.getCaretPosition(), " ");
            }
        });
        DOWN.setOnAction((e) -> {
            FocusHandler.getInputScene().removeKeyboard();
        });
        CHANGE.setFocusTraversable(false);
        SPACE.setFocusTraversable(false);
        ENTER.setFocusTraversable(false);
        BACK.setFocusTraversable(false);
        DOWN.setFocusTraversable(false);

        CHANGE.setMaxWidth(a / 5);
        CHANGE.setMinWidth(a / 5);
        CHANGE.setMinHeight(b / 5);
        CHANGE.setMaxHeight(b / 5);
        CHANGE.setFont(new Font(Math.sqrt(a * b) / 25));

        SPACE.setMinWidth(a / 3);
        SPACE.setMaxWidth(a / 3);
        SPACE.setMinHeight(b / 5);
        SPACE.setMaxHeight(b / 5);
        SPACE.setFont(new Font(Math.sqrt(a * b) / 25));

        ENTER.setMaxWidth(a / 5);
        ENTER.setMinWidth(a / 5);
        ENTER.setMinHeight(b / 5);
        ENTER.setMaxHeight(b / 5);
        ENTER.setFont(new Font(Math.sqrt(a * b) / 25));
        
        DOWN.setMaxWidth(a / 5);
        DOWN.setMinWidth(a / 5);
        DOWN.setMinHeight(b / 5);
        DOWN.setMaxHeight(b / 5);
        DOWN.setFont(new Font(Math.sqrt(a * b) / 25));

        total.getChildren().addAll(layers[1], layers[2], 
                layers[3], layers[4]);
        setMaxWidth(vb.getWidth());
        setMinWidth(vb.getWidth());
        setMaxHeight(vb.getHeight() / 2);
        setMinHeight(vb.getHeight() / 2);

    }

    private void shiftToLower() {
        for (Key k : letters) {
            k.toLower();
        }
    }

    private void shiftToUpper() {
        for (Key k : letters) {
            k.toUpper();
        }
    }

    private final class Key extends Button {

        private final String[] spellings;

        public Key(String show, String... alternate) {
            setText(show);
            spellings = alternate;
            setOnAction((e) -> {
                if (current != null) {
                    current.insertText(current.getCaretPosition(), getText());
                }
                char c = A.getText().charAt(0);
                if (c == 'A' && SHIFT.getStyle().isEmpty()) {
                    shiftToLower();
                }
            });
        }

        public void toUpper() {
            setText(spellings[0]);
        }

        public void toLower() {
            setText(spellings[1]);
        }

    }
    private final Button DOWN = new Button("Retract");
    private final Button SHIFT = new Button("Shift");
    private final Button BACK = new Button("<-");
    private final Button ENTER = new Button("Enter");
    private final Button SPACE = new Button("Space");
    private final Button CHANGE = new Button("123");
    private final Key A = new Key("a", "A", "a");
    private final Key B = new Key("b", "B", "b");
    private final Key C = new Key("c", "C", "c");
    private final Key D = new Key("d", "D", "d");
    private final Key E = new Key("e", "E", "e");
    private final Key F = new Key("f", "F", "f");
    private final Key G = new Key("g", "G", "g");
    private final Key H = new Key("h", "H", "h");
    private final Key I = new Key("i", "I", "i");
    private final Key J = new Key("j", "J", "j");
    private final Key K = new Key("k", "K", "k");
    private final Key L = new Key("l", "L", "l");
    private final Key M = new Key("m", "M", "m");
    private final Key N = new Key("n", "N", "n");
    private final Key O = new Key("o", "O", "o");
    private final Key P = new Key("p", "P", "p");
    private final Key Q = new Key("q", "Q", "q");
    private final Key R = new Key("r", "R", "r");
    private final Key S = new Key("s", "S", "s");
    private final Key T = new Key("t", "T", "t");
    private final Key U = new Key("u", "U", "u");
    private final Key V = new Key("v", "V", "v");
    private final Key W = new Key("w", "W", "w");
    private final Key X = new Key("x", "X", "x");
    private final Key Y = new Key("y", "Y", "y");
    private final Key Z = new Key("z", "Z", "z");
    private final ObservableList<Key> top = FXCollections.observableArrayList(Q, W, E, R,
            T, Y, U, I, O, P);
    private final ObservableList<Key> mid = FXCollections.observableArrayList(A, S, D, F, G, H, J, K, L);
    private final ObservableList<Key> low = FXCollections.observableArrayList(Z, X, C, V, B, N, M);
    private final ObservableList<Key> letters = FXCollections.observableArrayList(top);

    {
        letters.addAll(mid);
        letters.addAll(low);
    }
    private final Key ONE = new Key("1", "1", "1");
    private final Key TWO = new Key("2", "2", "2");
    private final Key THREE = new Key("3", "3", "3");
    private final Key FOUR = new Key("4", "4", "4");
    private final Key FIVE = new Key("5", "5", "5");
    private final Key SIX = new Key("6", "6", "6");
    private final Key SEVEN = new Key("7", "7", "7");
    private final Key EIGHT = new Key("8", "8", "8");
    private final Key NINE = new Key("9", "9", "9");
    private final Key ZERO = new Key("0", "0", "0");

}
