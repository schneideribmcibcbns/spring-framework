package com.logicbig.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyEditor;
import java.util.Locale;

public class JavaFxDefaultEditorExample extends Application {


    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start (Stage stage) throws Exception {
        Scene scene = new Scene(new Group(), 450, 250);

        TextField localeField = new TextField();

        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setHgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(new Label("Enter locale: "), 0, 0);
        grid.add(localeField, 1, 0);

        Button button = new Button("Submit");
        grid.add(button, 2, 0);

        Label outputLabel = new Label();
        outputLabel.setTextFill(Color.BLUE);

        grid.add(outputLabel, 1, 1);

        button.setOnAction(event -> {
            LocaleClientBean bean = new LocaleClientBean();

            System.out.println(localeField.getText());
            BeanWrapperImpl beanWrapper = new BeanWrapperImpl(bean);

            //spring uses default property editor 'LocaleEditor' for conversion
            beanWrapper.setPropertyValue("locale", localeField.getText().trim());

            bean.workWithLocale();
            outputLabel.setText(bean.getLocale()
                                    .getDisplayCountry());


        });

        Group root = (Group) scene.getRoot();
        root.getChildren()
            .add(grid);
        stage.setScene(scene);
        stage.show();
    }

    public static class LocaleClientBean {
        private Locale locale;

        public void workWithLocale () {
            System.out.println(locale.getLanguage());
        }

        public Locale getLocale () {
            return locale;
        }

        public void setLocale (Locale locale) {
            this.locale = locale;
        }
    }
}