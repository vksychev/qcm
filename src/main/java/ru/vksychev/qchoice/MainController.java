package ru.vksychev.qchoice;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import ru.vksychev.qchoice.utils.*;

import static ru.vksychev.qchoice.utils.AlertWindow.showAlert;

public class MainController implements Initializable {

    private Alternative ideal;
    private ObservableList<ObservableList> data;
    private int counter;
    private Connection connection;
    private TableView tableview;
    private Stage graphStage;
    private QuickChoice qc;

    @FXML
    MenuBar menuBar;

    @FXML
    MenuItem menuOpen;

    @FXML
    TabPane qPane;

    @FXML
    AnchorPane qAnchor;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menuOpen.setDisable(true);
        qPane.setVisible(false);
        counter = 0;
    }

    public void onClickConnect() throws SQLException, IOException {
        ConnectWindow connect = new ConnectWindow();
        ConnectionInfo info = connect.initWindow();
        connect(info);
        menuOpen.setDisable(false);
        tableview = new TableView();
        buildData();

        closeConnection();
        makePreparations();
    }

    public void onClickOpenTable() {

        Stage stage = new Stage();

        Scene scene = new Scene(tableview);
        stage.setScene(scene);

        stage.setTitle("Data Base");
        stage.show();
    }

    public void onClickExit() {
        Stage stage = (Stage) menuBar.getScene().getWindow();
        stage.close();
    }

    public void onClickFileNew() {

    }

    private void makePreparations() throws IOException {
        qPane.setVisible(true);
        qc = new QuickChoice(new TRangClassifier());
        setNameColumn();
        setNameQuestion();
    }

    private void updateGraph() {
        //TODO: метод ограничений
        showAlert("Граф не обновляется", "", "");
    }

    private void setNameQuestion() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/set_name_window.fxml"));
        qAnchor.getChildren().add(loader.load());
        SetNameController controller = loader.getController();
        controller.setTitles(qc.getTitles());
        controller.getButtonConfirm().setOnAction(event -> {
            qc.setNameTitle(controller.getCurrentTitle());
            try {
                setInterestedQuestion();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setInterestedQuestion() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/set_interested.fxml"));
        qAnchor.getChildren().clear();
        qAnchor.getChildren().add(loader.load());
        SetInterestedController controller = loader.getController();
        controller.setTitles(qc.getCriterias());
        controller.getButtonNone().setOnAction(event -> {
            try {
                setCriterias();
                setAlternatives();
                setCriteriaTarget();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        controller.getButtonContinue().setOnAction(event -> {
            qc.setNotInterested(controller.getNotInterested());
            try {
                setCriterias();
                setAlternatives();
                setCriteriaTarget();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setCriteriaTarget() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/set_target.fxml"));
        qAnchor.getChildren().clear();
        qAnchor.getChildren().add(loader.load());
        SetTargetController controller = loader.getController();
        controller.setCriteria(qc.getCriteriasList().get(counter).getName());

        controller.getMaxButton().setOnAction(event -> {
            qc.getCriteriasList().get(counter).setTarget(CriteriaTarget.MAX);
            try {
                setCriteriaValueType();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        controller.getMinButton().setOnAction(event -> {
            qc.getCriteriasList().get(counter).setTarget(CriteriaTarget.MIN);
            try {
                setCriteriaValueType();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setCriteriaValueType() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/set_value_type.fxml"));
        qAnchor.getChildren().clear();
        qAnchor.getChildren().add(loader.load());
        SetValueTypeController controller = loader.getController();
        controller.setCriteria(qc.getCriteriasList().get(counter).getName());

        controller.getNoButton().setOnAction(event -> {
            qc.getCriteriasList().get(counter).setValue(CriteriaValue.RATIO);
            try {
                setCriteriaValueRating();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        controller.getYesButton().setOnAction(event -> {
            qc.getCriteriasList().get(counter).setValue(CriteriaValue.NUMERICALLY);
            try {
                setMinMax();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setCriteriaValueRating() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/set_values.fxml"));
        qAnchor.getChildren().clear();
        qAnchor.getChildren().add(loader.load());
        SetValuesController controller = loader.getController();
        controller.setCriteria(qc.getCriteriasList().get(counter));

        controller.getContinueButton().setOnAction(event -> {
            //TODO: circle
            qc.getCriteriasList().get(counter).setValues(controller.getList());

            System.out.println(qc.getCriteriasList().get(counter));
            try {
                loop();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        System.out.println(qc.getCriteriasList().get(counter));
    }

    private void setExtInformation() throws IOException {
        //TODO: SET EXT
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/set_new_information.fxml"));
        qAnchor.getChildren().clear();
        qAnchor.getChildren().add(loader.load());
        SetNewInformationController controller = loader.getController();
        controller.getButtonContinue().setOnAction(event -> {
            if (controller.isSomeEquals()) {
                try {
                    setEquals();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if (controller.isOneLess()){
                try {
                    setOneLess();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if (controller.isOneMore()) {
                try {
                    setOneMore();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setOneLess() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/set_one_less.fxml"));
        qAnchor.getChildren().clear();
        qAnchor.getChildren().add(loader.load());
        SetOneLessController controller = loader.getController();
        controller.setTitles(qc.getCriterias());
        controller.setTitle(MyQuestions.QUESTION_SET_LESS);
        controller.getContinueButton().setOnAction(event -> {
            List<Comparison> comp = new ArrayList<>();
            List<String> selected = controller.getResult();
            for (int i = 1; i < selected.size(); i++) {
                comp.add(new Comparison(qc.getCriteria(selected.get(0)),
                        Comparison.Operator.LESS,
                        qc.getCriteria(selected.get(i))));
            }
            qc.addComparisons(comp);
            System.out.println(comp);
            try {
                setResults();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setOneMore() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/set_one_less.fxml"));
        qAnchor.getChildren().clear();
        qAnchor.getChildren().add(loader.load());
        SetOneLessController controller = loader.getController();
        controller.setTitles(qc.getCriterias());
        controller.setTitle(MyQuestions.QUESTION_SET_MORE);
        controller.getContinueButton().setOnAction(event -> {
            List<Comparison> comp = new ArrayList<>();
            List<String> selected = controller.getResult();
            for (int i = 1; i < selected.size(); i++) {
                comp.add(new Comparison(qc.getCriteria(selected.get(0)),
                        Comparison.Operator.MORE,
                        qc.getCriteria(selected.get(i))));
            }
            qc.addComparisons(comp);
            System.out.println(comp);
            try {
                setResults();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setEquals() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/set_equals.fxml"));
        qAnchor.getChildren().clear();
        qAnchor.getChildren().add(loader.load());
        SetEqualsController controller = loader.getController();
        controller.setTitles(qc.getCriterias());
        controller.getContinueButton().setOnAction(event -> {
            List<Comparison> comp = new ArrayList<>();
            List<String> selected = controller.getSelected();
            for (int i = 0; i < selected.size()-1; i++) {
                for (int j = i+1; j < selected.size(); j++) {
                    comp.add(new Comparison(qc.getCriteria(selected.get(i)),
                            Comparison.Operator.EQUALS,
                            qc.getCriteria(selected.get(j))));
                }
            }
            qc.addComparisons(comp);
            try {
                setResults();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setMinMax() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/set_min_max.fxml"));
        qAnchor.getChildren().clear();
        qAnchor.getChildren().add(loader.load());
        SetMinMaxController controller = loader.getController();
        controller.setCriteria(qc.getCriteriasList().get(counter));

        controller.getContinueButton().setOnAction(event -> {
            if (controller.isMinMax()) {
                try {
                    minMax();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                //loop
                try {
                    loop();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void minMax() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/min_max.fxml"));
        qAnchor.getChildren().clear();
        qAnchor.getChildren().add(loader.load());
        MinMaxController controller = loader.getController();
        controller.setCriteria(qc.getCriteriasList().get(counter));

        controller.getContinueButton().setOnAction(event -> {
            if (controller.checkMinMax()) {
                qc.getCriteriasList().get(counter).setMinMax(controller.getMin(), controller.getMax());
                System.out.println(qc.getCriteriasList().get(counter).getMin());
                System.out.println(qc.getCriteriasList().get(counter).getMax());
                //loop
                try {
                    loop();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void loop() throws IOException {
        if (counter < qc.getCriteriasList().size() - 1) {
            counter++;
            setCriteriaTarget();
        } else {
            afterLoop();

        }
    }

    private void afterLoop() throws IOException {
        setResults();
    }

    private void setResults() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/get_results.fxml"));
        qAnchor.getChildren().clear();
        qAnchor.getChildren().add(loader.load());
        GetResultsController controller = loader.getController();
        ideal = qc.getTop(1).get(0);
        ArrayList<String> values = new ArrayList<>();
        values.add(qc.getNameTitle());
        values.addAll(qc.getCriterias());
        controller.setTableTitles(values);
        controller.setTableValues(ideal.getName(), ideal.getValues());

        controller.getContinueButton().setOnAction(event -> {
            try {
                setExtInformation();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setCriterias() {
        List<Criteria> criterias = new ArrayList<>();
        for (String name : qc.getCriterias()) {
            TableColumn t = getTableColumnByName(tableview, name);
            List<String> columnData = new ArrayList<>();

            for (int i = 0; i < tableview.getItems().size(); i++) {
                columnData.add(t.getCellData(i).toString());
            }

            System.out.println(columnData.stream().distinct().collect(Collectors.toList()));
            criterias.add(new Criteria(name, columnData.stream().distinct().collect(Collectors.toList())));
        }
        qc.setCriteriasList(criterias);
        System.out.println(qc.getCriteriasList());
    }

    private void setAlternatives() {
        List<Alternative> alternatives = new ArrayList<>();
        for (int i = 0; i < tableview.getItems().size(); i++) {
            List<AltersCriteria> criterias = new ArrayList<>();
            for (String name : qc.getCriterias()) {
                TableColumn t = getTableColumnByName(tableview, name);
                criterias.add(new AltersCriteria(qc.getCriteria(name), t.getCellData(i).toString()));
            }
            alternatives.add(new Alternative(getTableColumnByName(tableview, qc.getNameTitle()).getCellData(i).toString(), criterias));
        }
        qc.setAlternatives(alternatives);
        System.out.println(qc.getAlternatives());
    }

    private void setNameColumn() {
        List<String> columnNames = new ArrayList<>();
        for (Object c : tableview.getColumns()) {
            TableColumn col = (TableColumn) c;
            columnNames.add(col.getText());
        }

        qc.setTitles(columnNames);
    }

    private <T> TableColumn<T, ?> getTableColumnByName(TableView<T> tableView, String name) {
        for (TableColumn<T, ?> col : tableView.getColumns())
            if (col.getText().equals(name)) return col;
        return null;
    }

    private void buildData() throws SQLException {
        String query = "select * from example";
        data = FXCollections.observableArrayList();
        ResultSet rs = connection.createStatement().executeQuery(query);
        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
            //We are using non property style for making dynamic table
            final int j = i;
            TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
            col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));

            tableview.getColumns().addAll(col);
            System.out.println("Column [" + i + "] ");
        }

        while (rs.next()) {
            //Iterate Row
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                //Iterate Column
                row.add(rs.getString(i));
            }
            System.out.println("Row [1] added " + row);
            data.add(row);
        }

        //FINALLY ADDED TO TableView
        tableview.setItems(data);
    }

    private void connect(ConnectionInfo info) {
        try {
            connection = DriverManager.getConnection(info.getUrl(),
                    info.getUser(),
                    info.getPassword());
        } catch (SQLException e) {
            showAlert(MyAlertMessages.TITLE_CONNECTION,
                    MyAlertMessages.HEADER_FAIL_CONNECTION,
                    MyAlertMessages.CONTENT_FAIL_CONNECTION);
        }
        showAlert(MyAlertMessages.TITLE_CONNECTION,
                MyAlertMessages.HEADER_SUCCESS_CONNECTION,
                MyAlertMessages.CONTENT_SUCCESS_CONNECTION);
    }

    private void closeConnection() throws SQLException {
        connection.close();
    }

}
