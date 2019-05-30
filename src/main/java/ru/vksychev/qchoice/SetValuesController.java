package ru.vksychev.qchoice;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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

public class SetValuesController implements Initializable {

    final IntegerProperty dragFromIndex = new SimpleIntegerProperty(-1);


    Criteria criteria;

    private List<String> values;

    @FXML
    Label title;

    @FXML
    Button continueButton;

    @FXML
    ListView list;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setCriteria(Criteria cr) {
        criteria = cr;
        title.setText(String.format(MyQuestions.QUESTION_INFORMATION_RATE_VALUES, criteria.getName()));
        values = criteria.getValues();
        ObservableList<String> obsValues = FXCollections.observableArrayList(values);

        list.setItems(obsValues);

        //list.setCellFactory(param -> new Cell());
        list.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {

            @Override
            public ListCell<String> call(ListView<String> lv) {
                final ListCell<String> cell = new ListCell<String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item,  empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(item);
                        }
                    }
                };

                cell.setOnDragDetected(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (! cell.isEmpty()) {
                            dragFromIndex.set(cell.getIndex());
                            Dragboard db = cell.startDragAndDrop(TransferMode.MOVE);
                            ClipboardContent cc = new ClipboardContent();
                            cc.putString(cell.getItem());
                            db.setContent(cc);
                            // Java 8 only:
//                          db.setDragView(cell.snapshot(null, null));
                        }
                    }
                });

                cell.setOnDragOver(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        if (dragFromIndex.get() >= 0 && dragFromIndex.get() != cell.getIndex()) {
                            event.acceptTransferModes(TransferMode.MOVE);
                        }
                    }
                });


                // highlight drop target by changing background color:
                cell.setOnDragEntered(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        if (dragFromIndex.get() >= 0 && dragFromIndex.get() != cell.getIndex()) {
                            // should really set a style class and use an external style sheet,
                            // but this works for demo purposes:
                            cell.setStyle("-fx-background-color: gold;");
                        }
                    }
                });

                // remove highlight:
                cell.setOnDragExited(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        cell.setStyle("");
                    }
                });

                cell.setOnDragDropped(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {

                        int dragItemsStartIndex ;
                        int dragItemsEndIndex ;
                        int direction ;
                        if (cell.isEmpty()) {
                            dragItemsStartIndex = dragFromIndex.get();
                            dragItemsEndIndex = list.getItems().size();
                            direction = -1;
                        } else {
                            if (cell.getIndex() < dragFromIndex.get()) {
                                dragItemsStartIndex = cell.getIndex();
                                dragItemsEndIndex = dragFromIndex.get() + 1 ;
                                direction = 1 ;
                            } else {
                                dragItemsStartIndex = dragFromIndex.get();
                                dragItemsEndIndex = cell.getIndex() + 1 ;
                                direction = -1 ;
                            }
                        }

                        List<String> rotatingItems = list.getItems().subList(dragItemsStartIndex, dragItemsEndIndex);
                        List<String> rotatingItemsCopy = new ArrayList<>(rotatingItems);
                        Collections.rotate(rotatingItemsCopy, direction);
                        rotatingItems.clear();
                        rotatingItems.addAll(rotatingItemsCopy);
                        dragFromIndex.set(-1);
                    }
                });

                cell.setOnDragDone(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        dragFromIndex.set(-1);
                        list.getSelectionModel().select(event.getDragboard().getString());
                    }
                });


                return cell ;
            }


        });

    }

    public Button getContinueButton() {
        return continueButton;
    }

    public ArrayList<String> getList(){
        List<String> consultations = list.getItems();

        ArrayList<String> result;
        if (consultations instanceof ArrayList<?>) {
            result = (ArrayList<String>) consultations;
        } else {
            result = new ArrayList<>(consultations);
        }
        return result;
    }
}






