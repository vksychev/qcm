package ru.vksychev.qchoice;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import ru.vksychev.qchoice.utils.Criteria;
import ru.vksychev.qchoice.utils.MyQuestions;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SetEqualsController implements Initializable {

    List<String> selected;

    @FXML
    Label title;

    @FXML
    Button continueButton;

    @FXML
    ListView criterias;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selected = new ArrayList<>();
    }

    public void setTitles(List<String> list){
        ObservableList<String> items = FXCollections.observableArrayList(list);
        criterias.setItems(items);

        criterias.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        criterias.setOnMouseClicked((EventHandler<Event>) event -> {
            ObservableList<String> selectedItems =  criterias.getSelectionModel().getSelectedItems();
            selected.clear();
            selected.addAll(selectedItems);
        });
    }

    public Button getContinueButton() {
        return continueButton;
    }

    public List<String> getSelected(){
        return selected;
    }

}






