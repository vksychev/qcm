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

public class SetOneLessController implements Initializable {

    List<String> selected;
    List<String> all;
    @FXML
    Label title;

    @FXML
    Button continueButton;

    @FXML
    ListView criterias;

    @FXML
    ComboBox<String> comboBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selected = new ArrayList<>();
    }

    public void setTitles(List<String> list){
        this.all = list;
        ObservableList<String> names = FXCollections.observableArrayList();
        names.addAll(list);
        comboBox.setItems(names);
        comboBox.getSelectionModel().select(0);
        comboBox.setOnAction(event -> {
            setListView();
        });
        setListView();
        criterias.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        criterias.setOnMouseClicked((EventHandler<Event>) event -> {
            ObservableList<String> selectedItems =  criterias.getSelectionModel().getSelectedItems();
            selected.clear();
            selected.addAll(selectedItems);
        });
    }

    private void setListView(){
        ArrayList<String> list = new ArrayList<>(all);
        list.remove(getCurrentTitle());
        ObservableList<String> items = FXCollections.observableArrayList(list);
        criterias.setItems(items);
    }

    public void setTitle(String s){
        title.setText(s);
    }
    public Button getContinueButton() {
        return continueButton;
    }

    public List<String> getResult(){
        ArrayList<String> result = new ArrayList<>();
        result.add(getCurrentTitle());
        result.addAll(selected);
        return result;
    }

    public String getCurrentTitle() {
        return comboBox.getValue();
    }
}


