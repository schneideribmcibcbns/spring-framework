package com.logicbig.example.ui;


import com.logicbig.example.Customer;
import com.logicbig.example.service.CustomerService;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class JavaFxCustomerUi implements CustomerUi {

    @Autowired
    private CustomerService customerService;

    @Override
    public void showUi() {
        FxApp.service = customerService;
        Application.launch(FxApp.class);
    }

    public static class FxApp extends Application {
        /**
         * Not very good but this is the only way
         * JavaFx can be launch
         */
        private static CustomerService service;

        @Override
        public void start(Stage primaryStage) throws Exception {
            List<Customer> customers = service.getAllCustomers();
            primaryStage.setTitle("Customer Java FX Window");
            ObservableList<Customer> data = FXCollections.observableArrayList();
            data.addAll(customers.toArray(new Customer[customers.size()]));


            ListView<Customer> listView = new ListView<>(data);
            listView.setPrefSize(500, 500);

            primaryStage.setScene(new Scene(listView, 600, 600));
            primaryStage.show();
        }
    }
}