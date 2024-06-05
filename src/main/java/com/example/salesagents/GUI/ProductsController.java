package com.example.salesagents.GUI;

import com.example.salesagents.Domain.Agent;
import com.example.salesagents.Domain.IObserver;
import com.example.salesagents.Domain.Produs;
import com.example.salesagents.Service.ParticipantAlert;
import com.example.salesagents.Service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;


public class ProductsController implements IObserver {
    @FXML
    public TextField quantityTextField;
    @FXML
    public TableView tableView;
    @FXML
    public TableColumn tableColumnProductsName;
    @FXML
    public TableColumn tableColumnProductsPrices;
    @FXML
    public TableColumn tableColumnProductsQuantity;

    ObservableList<Produs> model = FXCollections.observableArrayList();
    Service service;
    Agent agent;

    public void setService(Service service, Agent agent){
        this.service = service;
        this.agent = agent;
    }

    public void initializeTable() {
        tableColumnProductsName.setCellValueFactory(new PropertyValueFactory<>("nume"));
        tableColumnProductsPrices.setCellValueFactory(new PropertyValueFactory<>("pret"));
        tableColumnProductsQuantity.setCellValueFactory(new PropertyValueFactory<>("cantitate"));
        tableView.setItems(model);
        service.getObservable().addObserver(this);
    }

    public void initModel(){
        model.clear();
        List<Produs> products  = service.getAllProduse();
        for (Produs a : products){
            model.add(a);

        }
    }

    public void orderProduct(ActionEvent actionEvent) {
        Produs produs = (Produs) tableView.getSelectionModel().getSelectedItem();
        int quantity = Integer.parseInt(quantityTextField.getText());
        try{
            if(service.addComanda(agent,produs,quantity) == null){
                ParticipantAlert.showMessage(null, Alert.AlertType.INFORMATION, "Order not added", "Insufficient quantity");
            }
            else {
                ParticipantAlert.showMessage(null, Alert.AlertType.INFORMATION, "Order added", "Order added successfully");
            }
        }
        catch (Exception e){
            ParticipantAlert.showErrorMessage(null, e.getMessage());
        }
    }

    @Override
    public void updateObserver(Object data) {
        ParticipantAlert.showMessage(null, Alert.AlertType.INFORMATION,"New order","The list was modified");
        initModel();
    }
}
