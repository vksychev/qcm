package ru.vksychev.qchoice;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import ru.vksychev.qchoice.utils.MyQuestions;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SetInterestedController implements Initializable {

    List<String> selected;

    @FXML
    Label question;

    @FXML
    Button buttonContinue;

    @FXML
    Button buttonNone;

    @FXML
    ListView criterias;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        question.setText(MyQuestions.QUESTION_NOT_INTEREST);
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
            System.out.println(selected);
        });
    }

    public List<String> getNotInterested(){
        return selected;
    }

    public Button getButtonContinue(){
        return buttonContinue;
    }

    public Button getButtonNone(){
        return buttonNone;
    }
}
