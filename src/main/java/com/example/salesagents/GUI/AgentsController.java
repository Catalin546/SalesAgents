package com.example.salesagents.GUI;

import com.example.salesagents.Domain.Agent;
import com.example.salesagents.Service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;


public class AgentsController  {
    @FXML
    public TableView tableView;

    @FXML
    public TableColumn tableColumnAgentsNames;

    ObservableList<Agent> model = FXCollections.observableArrayList();

    Service service;

    Agent agent;

    public void initializeTable() {
        tableColumnAgentsNames.setCellValueFactory(new PropertyValueFactory<>("username"));
        tableView.setItems(model);
    }

    public void initModel(Agent agent){
        List<Agent> agents  = service.getAllAgents(agent);

        for (Agent a : agents){
            model.add(a);
        }
    }

    public void setService(Service service,Agent agent) {
        this.service = service;
        this.agent = agent;

    }
}
