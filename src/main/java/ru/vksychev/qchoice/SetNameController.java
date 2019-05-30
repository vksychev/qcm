package ru.vksychev.qchoice;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import ru.vksychev.qchoice.utils.MyQuestions;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SetNameController implements Initializable {

    @FXML
    Label label;
    @FXML
    ComboBox<String> comboNames;
    @FXML
    Button buttonConfirm;

    private QuickChoice qc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        label.setText(MyQuestions.DB_SET_NAME);
    }

    public void setTitles(List<String> list) {
        ObservableList<String> names = FXCollections.observableArrayList();
        names.addAll(list);
        comboNames.setItems(names);
        comboNames.getSelectionModel().select(0);
    }

    public Button getButtonConfirm() {
        return buttonConfirm;
    }

    public String getCurrentTitle() {
        return comboNames.getValue();
    }


}
