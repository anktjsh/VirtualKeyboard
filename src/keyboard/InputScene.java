/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keyboard;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Window;
import javafx.util.Duration;

/**
 *
 * @author Aniket
 */
public class InputScene extends BorderPane {

    private static InputScene scene;
    private static TextInputControl current;

    static InputScene getInputScene(TextInputControl cur) {
        current = cur;
        if (scene == null) {
            scene = new InputScene();
        }
        scene.resize(cur.getScene().getWindow());
        return scene;
    }
    
    private void resize(Window w) {
        resizeScene(w.getWidth()-150, w.getHeight()*0.3);
        setMaxHeight(w.getHeight()/2);
    }
    
    private void resizeScene(double a, double b) {
        for (int x = 0; x < letters.size(); x++) {
            letters.get(x).setMaxWidth(a / 10);
            letters.get(x).setMinWidth(a / 10);
            letters.get(x).setMinHeight(b / 4);
            letters.get(x).setMaxHeight(b / 4);
            letters.get(x).setFont(new Font(Math.sqrt(a * b) / 25));
            letters.get(x).setFocusTraversable(false);
        }
        for (int x = 0; x < numbers.size(); x++) {
            numbers.get(x).setMaxWidth(a / 10);
            numbers.get(x).setMinWidth(a / 10);
            numbers.get(x).setMinHeight(b / 4);
            numbers.get(x).setMaxHeight(b / 4);
            numbers.get(x).setFont(new Font(Math.sqrt(a * b) / 25));
            numbers.get(x).setFocusTraversable(false);
        }
        for (int x = 0; x < symbols1.size(); x++) {
            symbols1.get(x).setMaxWidth(a / 10);
            symbols1.get(x).setMinWidth(a / 10);
            symbols1.get(x).setMinHeight(b / 4);
            symbols1.get(x).setMaxHeight(b / 4);
            symbols1.get(x).setFont(new Font(Math.sqrt(a * b) / 25));
            symbols1.get(x).setFocusTraversable(false);
        }
        for (int x = 0; x < punctuations.size(); x++) {
            punctuations.get(x).setMaxWidth(a / 7);
            punctuations.get(x).setMinWidth(a / 7);
            punctuations.get(x).setMinHeight(b / 4);
            punctuations.get(x).setMaxHeight(b / 4);
            punctuations.get(x).setFont(new Font(Math.sqrt(a * b) / 25));
            punctuations.get(x).setFocusTraversable(false);
        }        

        SHIFT.setMaxWidth(a / 7);
        SHIFT.setMinWidth(a / 7);
        SHIFT.setMinHeight(b / 4);
        SHIFT.setMaxHeight(b / 4);
        SHIFT.setFont(new Font(Math.sqrt(a * b) / 25));
        
        BACK.setMaxWidth(a / 7);
        BACK.setMinWidth(a / 7);
        BACK.setMinHeight(b / 4);
        BACK.setMaxHeight(b / 4);
        BACK.setFont(new Font(Math.sqrt(a * b) / 25));
        
        CHANGE.setMaxWidth(a / 8);
        CHANGE.setMinWidth(a / 8);
        CHANGE.setMinHeight(b / 4);
        CHANGE.setMaxHeight(b / 4);
        CHANGE.setFont(new Font(Math.sqrt(a * b) / 25));

        CHANGE2.setMaxWidth(a / 8);
        CHANGE2.setMinWidth(a / 8);
        CHANGE2.setMinHeight(b / 4);
        CHANGE2.setMaxHeight(b / 4);
        CHANGE2.setFont(new Font(Math.sqrt(a * b) / 25));

        SPACE.setMinWidth(a / 1.5);
        SPACE.setMaxWidth(a / 1.5);
        SPACE.setMinHeight(b / 4);
        SPACE.setMaxHeight(b / 4);
        SPACE.setFont(new Font(Math.sqrt(a * b) / 25));

        ENTER.setMaxWidth(a / 8);
        ENTER.setMinWidth(a / 8);
        ENTER.setMinHeight(b / 4);
        ENTER.setMaxHeight(b / 4);
        ENTER.setFont(new Font(Math.sqrt(a * b) / 25));

        DOWN.setMaxWidth(a / 8);
        DOWN.setMinWidth(a / 8);
        DOWN.setMinHeight(b / 4);
        DOWN.setMaxHeight(b / 4);
        DOWN.setFont(new Font(Math.sqrt(a * b) / 25));
    }

    private final HBox[] layers;
    private final VBox total;

    private InputScene() {
        setStyle("-fx-background-color:white;");
        total = new VBox(10);
        setCenter(total);
        total.setPadding(new Insets(5, 10, 5, 10));
        layers = new HBox[4];
        for (int x = 0; x < layers.length; x++) {
            layers[x] = new HBox(10);
            layers[x].setPadding(new Insets(5, 10, 5, 10));
            layers[x].setAlignment(Pos.CENTER);
        }
        layers[0].getChildren().addAll(Q, W, E, R, T, Y, U, I, O, P);
        layers[1].getChildren().addAll(A, S, D, F, G, H, J, K, L);
        layers[2].getChildren().addAll(SHIFT, Z, X, C, V, B, N, M, BACK);
        
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
        
        BACK.setOnAction((e) -> {
            if (current != null) {
                int car = current.getCaretPosition();
                if (car > 0) {
                    current.setText(current.getText().substring(0, car - 1) + current.getText().substring(car));
                    current.positionCaret(car - 1);
                }
            }
        });
        layers[3].getChildren().addAll(CHANGE, DOWN, SPACE, ENTER);
        CHANGE.setOnAction((e) -> {
            if (CHANGE.getText().equals("123")) {
                toNumbers();
                CHANGE.setText("abc");
            } else {
                toLetters();
                CHANGE.setText("123");
            }
        });
        CHANGE2.setOnAction((e) -> {
            if (CHANGE2.getText().equals("123")) {
                toNumbersFromSymbols();
            } else {
                toSymbols();
            }
        });
        ENTER.setOnAction((e) -> {
            if (current != null) {
                if (current instanceof TextField) {
                    TextField tf = (TextField) current;
                    if (tf.getOnAction() != null) {
                        tf.getOnAction().handle(new ActionEvent(null, tf));
                    }
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
            FocusHandler.getFocusHandler().removeKeyboard();
            current.getParent().requestFocus();
        });
        CHANGE.setFocusTraversable(false);
        CHANGE2.setFocusTraversable(false);
        SPACE.setFocusTraversable(false);
        SHIFT.setFocusTraversable(false);
        ENTER.setFocusTraversable(false);
        BACK.setFocusTraversable(false);
        DOWN.setFocusTraversable(false);        

        total.getChildren().addAll(layers[0], layers[1],
                layers[2], layers[3]);
    }
    private void toLetters() {
        layers[0].getChildren().clear();
        layers[1].getChildren().clear();
        layers[2].getChildren().clear();
        layers[0].getChildren().addAll(top);
        layers[1].getChildren().addAll(mid);
        layers[2].getChildren().addAll(SHIFT, Z, X, C, V, B, N, M, BACK);
    }

    private void toNumbers() {
        layers[0].getChildren().clear();
        layers[1].getChildren().clear();
        layers[2].getChildren().clear();
        layers[0].getChildren().addAll(numbers);
        layers[1].getChildren().addAll(symbols1);
        layers[2].getChildren().addAll(CHANGE2);
        layers[2].getChildren().addAll(punctuations);
        layers[2].getChildren().addAll(BACK);
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

    private void toSymbols() {
        
    }

    private void toNumbersFromSymbols() {

    }

    public void showMultipleSpellings(Button key, double x, double y) {
        key.getContextMenu().show(key, Side.TOP, x, y);
    }

    private void addPressAndHoldHandler(Node node, Duration holdTime,
            EventHandler<MouseEvent> handler) {

        class Wrapper<T> {

            T content;
        }
        Wrapper<MouseEvent> eventWrapper = new Wrapper<>();

        PauseTransition holdTimer = new PauseTransition(holdTime);
        holdTimer.setOnFinished(event -> handler.handle(eventWrapper.content));

        node.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            eventWrapper.content = event;
            holdTimer.playFromStart();
        });
        node.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> holdTimer.stop());
        node.addEventHandler(MouseEvent.DRAG_DETECTED, event -> holdTimer.stop());
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
            ContextMenu cm = new ContextMenu();
            for (String s : alternate) {
                cm.getItems().add(new MenuItem(s));
            }
            for (MenuItem mi : cm.getItems()) {
                mi.setOnAction(getOnAction());
            }
            setContextMenu(cm);
            addPressAndHoldHandler(this, Duration.seconds(1),
                    event -> {
                        showMultipleSpellings(this, getLayoutX(), getLayoutY());
                    });
        }

        public void toUpper() {
            setText(spellings[0]);
        }

        public void toLower() {
            setText(spellings[1]);
        }

    }
    private final Button DOWN = new Button("▼");
    private final Button SHIFT = new Button("Shift");
    private final Button BACK = new Button("<-");
    private final Button ENTER = new Button("Enter");
    private final Button SPACE = new Button("Space");
    private final Button CHANGE = new Button("123");
    private final Button CHANGE2 = new Button("#+=");
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
    private final ObservableList<Key> numbers = FXCollections.observableArrayList(ONE, TWO,
            THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, ZERO);

    private final Key HIPHEN = new Key("-", "-", "-");
    private final Key FORSLASH = new Key("/", "/", "/");
    private final Key COLON = new Key(":", ":", ":");
    private final Key SEMICOLON = new Key(";", ";", ";");
    private final Key FORPAREN = new Key("(", "(", "(");
    private final Key BACKPAREN = new Key(")", ")", ")");
    private final Key DOLLAR = new Key("$", "$", "$");
    private final Key AMPER = new Key("&", "&", "&");
    private final Key AT = new Key("@", "@", "@");
    private final Key QUOTE = new Key("\"", "\"", "\"");

    private final ObservableList<Key> symbols1 = FXCollections.observableArrayList(HIPHEN,
            FORSLASH, COLON, SEMICOLON, FORPAREN, BACKPAREN, DOLLAR,
            AMPER, AT, QUOTE);

    private final Key PERIOD = new Key(".", ".", ".");
    private final Key COMMA = new Key(",", ",", ",");
    private final Key QUESTION = new Key("?", "?", "?");
    private final Key EXCLAMATION = new Key("!", "!", "!");
    private final Key APOSTROPHE = new Key("'", "'", "'");

    private final ObservableList<Key> punctuations = FXCollections.observableArrayList(PERIOD,
            COMMA, QUESTION, EXCLAMATION, APOSTROPHE);
    
//    private final Key 
    private final ObservableList<Key> symbols = FXCollections.observableArrayList();
}
