package ru.vksychev.qchoice;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import ru.vksychev.qchoice.utils.MyQuestions;

import java.net.URL;
import java.util.ResourceBundle;

public class SetValueTypeController implements Initializable {

    String criteria;

    @FXML
    Label title;

    @FXML
    Button yesButton,noButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setCriteria(String cr) {
        criteria = cr;
        title.setText(String.format(MyQuestions.QUESTION_INFORMATION_RATING, criteria));
    }

    public Button getYesButton(){
        return yesButton;
    }

    public Button getNoButton(){
        return noButton;
    }

}
