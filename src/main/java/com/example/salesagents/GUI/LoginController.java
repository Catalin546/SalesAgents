package com.example.salesagents.GUI;

import com.example.salesagents.Domain.Agent;
import com.example.salesagents.Service.ParticipantAlert;
import com.example.salesagents.Service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginController {
    public Label RefereeLabel;
    public ListView participantsListView;

    @FXML
    private TextField passwordField;
    @FXML
    private TextField usernameField;

    Service service;

    public LoginController() { }

    public void handleLogin(ActionEvent actionEvent) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/salesagents/main-window-view.fxml"));

            AnchorPane root = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Agent");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            Agent wantedAgent = service.login(usernameField.getText(), passwordField.getText());
            if (wantedAgent != null){
                MainController mainController = loader.getController();
                mainController.setService(service,wantedAgent);
                mainController.initialize(wantedAgent.getUsername());
                dialogStage.show();
            }
            else
            {
                ParticipantAlert.showErrorMessage(null, "Wrong username or password! ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setService(Service service) {
        this.service = service;
    }

    public void handleSignUp(ActionEvent actionEvent) {
        showUserEditDialog(null);
    }

    public void showUserEditDialog(Agent agent) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/salesagents/edit-agent-view.fxml"));

            AnchorPane root = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Agent");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            EditAgentController editUserController = loader.getController();
            editUserController.setService(service,agent,dialogStage);
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
