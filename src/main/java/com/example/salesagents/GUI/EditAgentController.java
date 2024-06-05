package com.example.salesagents.GUI;

import com.example.salesagents.Domain.Agent;
import com.example.salesagents.Service.ParticipantAlert;
import com.example.salesagents.Service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class EditAgentController {
    @FXML
    public TextField textFieldUsername;

    @FXML
    public TextField textFieldPassword;

    private Service service;

    private Stage stage;

    private Agent agent;

    public void handleAdd(ActionEvent actionEvent) {
        Agent agent = new Agent(null,textFieldUsername.getText(), textFieldPassword.getText());
        try{
            if(service.addAgent(agent) == null){
                ParticipantAlert.showMessage(null, Alert.AlertType.INFORMATION, "Agent not added", "Choose another password");
            }
            ParticipantAlert.showMessage(null, Alert.AlertType.INFORMATION, "Agent added", "Agent added successfully");
        }catch (Exception e){
            ParticipantAlert.showErrorMessage(null, e.getMessage());
        }
    }

    public void handleCancel(ActionEvent actionEvent) {
        stage.close();
    }

    public void setService(Service service, Agent agent,Stage stage) {
        this.service = service;
        this.agent = agent;
        this.stage = stage;
    }

    private void setFields(Agent agent) {
        textFieldUsername.setText(agent.getUsername());
        textFieldPassword.setText(agent.getPassword());
    }

    private void clearFields() {
        textFieldUsername.setText("");
        textFieldPassword.setText("");
    }

    public void handleUpdate(ActionEvent actionEvent) {
        Agent agent2 = new Agent(agent.getId(),textFieldUsername.getText(), textFieldPassword.getText());
        try{
            if(service.updateAgent(agent2) == null){
                ParticipantAlert.showMessage(null, Alert.AlertType.INFORMATION, "Agent not updated", "Choose another password");
            }
            else{
            ParticipantAlert.showMessage(null, Alert.AlertType.INFORMATION, "Agent updated", "Agent updated successfully");}
        }catch (Exception e){
            ParticipantAlert.showErrorMessage(null, e.getMessage());
        }
    }
}
