package ru.vksychev.qchoice;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import ru.vksychev.qchoice.utils.MyQuestions;

import java.net.URL;
import java.util.ResourceBundle;

public class SetNewInformationController implements Initializable {

    @FXML
    Label question;
    @FXML
    RadioButton oneMore, oneLess,  someEquals;

    @FXML
    Button buttonContinue;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        question.setText(MyQuestions.QUESTION_NEW_INFORMATION);
        ToggleGroup newInformation = new ToggleGroup();
        oneLess.setToggleGroup(newInformation);
        oneMore.setToggleGroup(newInformation);
        someEquals.setToggleGroup(newInformation);
        newInformation.selectToggle(oneMore);
    }

    public Button getButtonContinue(){
        return buttonContinue;
    }

    public boolean isOneMore(){
        return oneMore.isSelected();
    }

    public boolean isOneLess(){
        return oneLess.isSelected();
    }

    public boolean isSomeEquals(){
        return someEquals.isSelected();
    }

}
