package com.example.salesagents;

import com.example.salesagents.GUI.LoginController;
import com.example.salesagents.Repository.*;
import com.example.salesagents.Service.Service;
import com.example.salesagents.Service.ServiceImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


public class HelloApplication extends Application {
    private Service service;

    @Override
    public void start(Stage stage) throws IOException {
        Properties serverProps=new Properties();
        try {
            serverProps.load(new FileReader("bd.config"));
            System.out.println("Properties set.");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }
        AgentRepository agentRepository = new AgentRepositoryImpl(serverProps);
        ProdusRepository produsRepository = new ProdusRepositoryImpl(serverProps);
        ComandaRepository comandaRepository = new ComandaRepositoryImpl(serverProps);
        service = new ServiceImpl(agentRepository, produsRepository, comandaRepository);
        initView(stage);
        stage.setTitle("Sales Agents");
        stage.show();
    }

    private void initView(Stage primaryStage) throws IOException {
        FXMLLoader userLoader = new FXMLLoader();
        userLoader.setLocation(getClass().getResource("/com/example/salesagents/login-view.fxml"));
        primaryStage.setScene(new Scene(userLoader.load()));

        LoginController loginController = userLoader.getController();
        loginController.setService(service);
    }

    public static void main(String[] args) {
        launch();
    }
}