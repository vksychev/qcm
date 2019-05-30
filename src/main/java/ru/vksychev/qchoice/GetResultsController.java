package ru.vksychev.qchoice;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.util.Callback;
import ru.vksychev.qchoice.utils.Criteria;
import ru.vksychev.qchoice.utils.MyQuestions;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class GetResultsController implements Initializable {

    @FXML
    Label title;

    @FXML
    Button continueButton;

    @FXML
    private TableView<List<String>> table;

    private List<String> criterias;
    private List<String> alternatives;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table.getColumns().clear();
    }


    public Button getContinueButton(){
        return continueButton;
    }

    public void setTableTitles(List<String> criterias){
        for (int i = table.getColumns().size(); i < criterias.size(); i++) {
            TableColumn<List<String>, String> col = new TableColumn<>(criterias.get(i));
            col.setMinWidth(80);
            final int colIndex = i ;
            col.setCellValueFactory(data -> {
                List<String> rowValues = data.getValue();
                String cellValue ;
                if (colIndex < rowValues.size()) {
                    cellValue = rowValues.get(colIndex);
                } else {
                    cellValue = "" ;
                }
                return new ReadOnlyStringWrapper(cellValue);
            });
            table.getColumns().add(col);
        }

    }

    public void setTableValues(String name, List<String> alternatives){
        title.setText(String.format(MyQuestions.QUESTION_RESULT,name));

        ObservableList<String> list = FXCollections.observableArrayList();
        list.add(name);
        list.addAll(alternatives);

        table.getItems().addAll(list);
    }

}






