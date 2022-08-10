package main.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class LoginScene{

    private static final int W_RATIO = 5;
    private static final int H_RATIO = 3;

    private final BorderPane root;
    private GUIFactory guiFactory;

    public LoginScene(final Stage primaryStage, final Scene mainScene) {
        final GUIFactoryImpl.Builder b = new GUIFactoryImpl.Builder(Screen.getPrimary().getBounds().getWidth(),
                Screen.getPrimary().getBounds().getHeight());
        this.guiFactory = b.build();

        this.root = new BorderPane();

        final Pane textFieldLayout = this.guiFactory.createVerticalPanel();
        final TextField eMail = new TextField();
        eMail.setPromptText("e-Mail");
        final TextField password = new TextField();
        password.setPromptText("password");

        final Pane buttonLayout = this.guiFactory.createHorizontalPanel();
        final Button access = this.guiFactory.createButton("Accedi");
        access.setOnAction(e -> {
            primaryStage.setScene(mainScene);
            primaryStage.centerOnScreen();
        });

        final Button register = this.guiFactory.createButton("Registrati");
        register.setOnAction(e -> {
            primaryStage.setScene(new RegistrationView(primaryStage, mainScene).getScene());
            primaryStage.centerOnScreen();
        });

        buttonLayout.getChildren().addAll(access, register);
        textFieldLayout.getChildren().addAll(eMail, password);
        this.root.setTop(textFieldLayout);
        this.root.setBottom(buttonLayout);
    }

    public Scene getScene() {
        return new Scene(this.root, Screen.getPrimary().getBounds().getWidth() / W_RATIO, Screen.getPrimary().getBounds().getHeight() / H_RATIO);
    }
}