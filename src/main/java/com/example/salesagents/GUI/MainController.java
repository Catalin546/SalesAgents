package com.example.salesagents.GUI;

import com.example.salesagents.Domain.Agent;
import com.example.salesagents.Service.ParticipantAlert;
import com.example.salesagents.Service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class MainController {
    FXMLLoader loader;
    @FXML
    public Label helloLabel;
    private Service service;
    private Agent agent;

    public void handleDeleteAccount(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete your account?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                service.deleteAgent(agent.getId());
                ParticipantAlert.showErrorMessage(null, "Account deleted successfully!");
            }
        });
    }

    public void handleUpdateAccount(ActionEvent actionEvent) {
        showUserEditDialog();
    }

    public void handleSeeAgents(ActionEvent actionEvent) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/salesagents/all-agents-view.fxml"));

            AnchorPane root = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("View Agents");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            if (agent != null){
                AgentsController agentsController = loader.getController();
                agentsController.setService(service,agent);
                agentsController.initializeTable();
                agentsController.initModel(agent);
                dialogStage.show();
            }
            else
            {
                ParticipantAlert.showErrorMessage(null, "You are not logged in!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setLoader(FXMLLoader loader) {
        this.loader = loader;
    }

    public void handleShowProductList(ActionEvent actionEvent) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/salesagents/products-view.fxml"));

            AnchorPane root = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("View Agents");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            if (agent != null) {
                ProductsController productsController = loader.getController();
                productsController.setService(service,agent);
                productsController.initializeTable();
                productsController.initModel();
                dialogStage.show();
            }
            else
            {
                ParticipantAlert.showErrorMessage(null, "You are not logged in!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setService(Service service, Agent agent) {
        this.service = service;
        this.agent = agent;
    }

    public void initialize(String username) {
        helloLabel.setText("Hello, " + username + "!");
    }

    public void showUserEditDialog() {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/salesagents/edit-agent-view-update.fxml"));

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

    public void handleLogout(ActionEvent actionEvent) {
        Stage stage = (Stage) helloLabel.getScene().getWindow();
        stage.close();
    }
}
