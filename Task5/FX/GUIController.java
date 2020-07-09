package sample.FX;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.DataBase.DBManager;
import sample.DataBase.DataDB;
import sample.FX.ConverterDataView;
import sample.FX.DataBaseToView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public final class GUIController {
    private static int minValue;
    private static int maxValue;
    private static int minSliderValue;
    private static int maxSliderValue;

    private GUIController() {
    }

    public static void init(Stage stage, DBManager dbManager) throws SQLException {
        TableView<DataBaseToView> table = new TableView<DataBaseToView>();
        updateData(table, dbManager);
        Label titleInfo = new Label("Title:");
        Label costInfo = new Label("Cost:");
        Label filterMinInfo = new Label("Min Cost:");
        Label filterMaxInfo = new Label("Max Cost:");
        TextField titleField = new TextField();
        NumberTextField costField = new NumberTextField();
        Label lbl = new Label();
        Button addButton = new Button("Add");
        Button delButton = new Button("Delete");
        Button changeButton = new Button("Change");
        Button filerButton = new Button("Apply Filter");
        Slider minSlider = new Slider(minValue, maxValue, minValue);
        Slider maxSlider = new Slider(minValue, maxValue, maxValue);

        maxSliderValue = maxValue;
        minSlider.setShowTickMarks(true);
        minSlider.setShowTickLabels(true);
        maxSlider.setShowTickMarks(true);
        maxSlider.setShowTickLabels(true);
        minSlider.setPrefWidth(150);
        maxSlider.setPrefWidth(150);

        DataBaseToView selectedItem = new DataBaseToView();

        addButton.setPrefWidth(150);
        delButton.setPrefWidth(150);
        changeButton.setPrefWidth(150);
        filerButton.setPrefWidth(150);

        delButton.setStyle("-fx-text-fill: #ff0000");

        lbl.setPrefHeight(25);
        lbl.setPrefWidth(300);
        lbl.setFont(new Font("SegoeUI", 15));
        lbl.setAlignment(Pos.CENTER);
        lbl.setText("Select item");

        table.setPrefWidth(250);
        table.setPrefHeight(320);
        Insets padding = new Insets(10, 10, 10, 10);
        table.setPadding(padding);

        TableColumn<DataBaseToView, Integer> prodIdColumn = new TableColumn<DataBaseToView, Integer>("ProdId");
        prodIdColumn.setCellValueFactory(new PropertyValueFactory<DataBaseToView, Integer>("prodId"));
        table.getColumns().add(prodIdColumn);

        TableColumn<DataBaseToView, String> titleColumn = new TableColumn<DataBaseToView, String>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<DataBaseToView, String>("title"));
        table.getColumns().add(titleColumn);

        TableColumn<DataBaseToView, Integer> costColumn = new TableColumn<DataBaseToView, Integer>("Cost");
        costColumn.setCellValueFactory(new PropertyValueFactory<DataBaseToView, Integer>("cost"));
        table.getColumns().add(costColumn);

        TableView.TableViewSelectionModel<DataBaseToView> selectionModel = table.getSelectionModel();

        FlowPane root = new FlowPane(Orientation.VERTICAL, 5, 5, lbl, table, filterMinInfo, minSlider, filterMaxInfo, maxSlider, titleInfo, titleField, costInfo, costField, addButton, changeButton, filerButton, delButton);
        root.setPadding(new Insets(0, 0, 0, 10));

        Scene scene = new Scene(root, 475, 360);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Task4 with GUI");
        stage.show();


        selectionModel.selectedItemProperty().addListener(new ChangeListener<DataBaseToView>() {
            @Override
            public void changed(ObservableValue<? extends DataBaseToView> observableValue, DataBaseToView dataBaseView, DataBaseToView t1) {
                if (t1 != null) {
                    lbl.setText("Selected: " + t1.getTitle());
                    selectedItem.setCost(t1.getCost());
                    selectedItem.setProdId(t1.getProdId());
                    selectedItem.setTitle(t1.getTitle());
                }
            }
        });


        minSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> changed, Number oldValue, Number newValue) {
                minSliderValue = newValue.intValue();
            }
        });

        maxSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> changed, Number oldValue, Number newValue) {
                maxSliderValue = newValue.intValue();
            }
        });

        filerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Alert messageAlert = new Alert(Alert.AlertType.INFORMATION);
                messageAlert.setHeaderText(null);
                try {
                    if (maxSliderValue >= minSliderValue) {
                        String filter = dbManager.getTableName() + ".cost >= " + minSliderValue + " AND " + dbManager.getTableName() + ".cost <= " + maxSliderValue;
                        ArrayList<DataDB> data = dbManager.getItemsFromDB(filter);
                        setData(table, data);
                        messageAlert.setContentText("Filter applied");
                    } else {
                        minSlider.setValue(minValue);
                        maxSlider.setValue(maxValue);
                        messageAlert.setTitle("Error message");
                        messageAlert.setContentText("Min slider > max slider");
                        messageAlert.setTitle("Successfully!");
                    }
                } catch (SQLException e) {
                    messageAlert.setTitle("Successfully!");
                    minSlider.setValue(minValue);
                    maxSlider.setValue(maxValue);
                    Alert exceptionAlert = new Alert(Alert.AlertType.ERROR);
                    exceptionAlert.setTitle("Error");
                    exceptionAlert.setHeaderText(null);
                    exceptionAlert.setContentText(e.getMessage());
                    exceptionAlert.showAndWait();
                }
                messageAlert.showAndWait();
            }
        });

        delButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!selectedItem.getProdId().equals("")) {
                    Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmAlert.setTitle("Delete " + selectedItem.getProdId());
                    confirmAlert.setHeaderText("Deleted data cannot be recovered!");
                    confirmAlert.setContentText("Do you want to continue?");
                    Optional<ButtonType> result = confirmAlert.showAndWait();

                    if (result.get() == ButtonType.OK) {
                        try {
                            dbManager.deleteItem(ConverterDataView.convert(selectedItem));
                            updateData(table, dbManager);
                            updateSlider(minSlider, maxSlider, minValue, maxValue);
                        } catch (SQLException e) {
                            Alert exceptionAlert = new Alert(Alert.AlertType.ERROR);
                            exceptionAlert.setTitle("Error");
                            exceptionAlert.setHeaderText(null);
                            exceptionAlert.setContentText(e.getMessage());
                            exceptionAlert.showAndWait();
                        }
                    }
                }
            }
        });

        changeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert messageAlert = new Alert(Alert.AlertType.INFORMATION);
                messageAlert.setHeaderText(null);
                try {
                    if (!selectedItem.getProdId().equals("") && !titleField.getText().equals("") && !costField.getText().equals("")) {
                        DataDB newData = new DataDB();
                        newData.title = titleField.getText();
                        newData.cost = Integer.parseInt(costField.getText());
                        newData.prodid = Integer.parseInt(selectedItem.getProdId());
                        dbManager.changeItem(ConverterDataView.convert(selectedItem), newData);
                        updateData(table, dbManager);
                        updateSlider(minSlider, maxSlider, minValue, maxValue);
                        messageAlert.setContentText("Item update");
                        messageAlert.setTitle("Successfully!");
                    } else {
                        if (costField.getText().equals("")) messageAlert.setContentText("Cost cannot be empty!");
                        if (titleField.getText().equals("")) messageAlert.setContentText("Title cannot be empty!");
                        if (selectedItem.getProdId().equals("")) messageAlert.setContentText("Select Item!");
                        messageAlert.setTitle("Error!");
                    }
                    messageAlert.showAndWait();
                } catch (SQLException e) {
                    Alert exceptionAlert = new Alert(Alert.AlertType.ERROR);
                    exceptionAlert.setTitle("Error");
                    exceptionAlert.setHeaderText(null);
                    exceptionAlert.setContentText(e.getMessage());
                    exceptionAlert.showAndWait();
                }
            }
        });
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Alert messageAlert = new Alert(Alert.AlertType.INFORMATION);
                    messageAlert.setHeaderText(null);
                    if (!titleField.getText().equals("") && !costField.getText().equals("")) {
                        dbManager.addItemToDB(titleField.getText(), Integer.parseInt(costField.getText()));
                        updateData(table, dbManager);
                        updateSlider(minSlider, maxSlider, minValue, maxValue);
                        messageAlert.setTitle("Successfully!");
                        messageAlert.setContentText("1 Item Added");
                    } else {
                        messageAlert.setTitle("Error message");
                        messageAlert.setContentText("Empty cost or title");
                    }
                    messageAlert.showAndWait();
                } catch (SQLException | IllegalArgumentException e) {
                    Alert exceptionAlert = new Alert(Alert.AlertType.ERROR);
                    exceptionAlert.setTitle("Error");
                    exceptionAlert.setHeaderText(null);
                    exceptionAlert.setContentText(e.getMessage());
                    exceptionAlert.showAndWait();
                }
            }
        });
    }

    private static void updateData(TableView<DataBaseToView> table, DBManager dbManager) throws SQLException {
        ArrayList<DataDB> dataFromDB = dbManager.getItemsFromDB();
        ArrayList<DataBaseToView> dataArray = ConverterDataView.convert(dataFromDB);
        ObservableList<DataBaseToView> data = FXCollections.observableArrayList(dataArray);
        table.setItems(data);
        minValue = getMinValue(dataFromDB);
        maxValue = getMaxValue(dataFromDB);
    }

    private static void updateSlider(Slider minSlider, Slider maxSlider, int minValue, int maxValue) {
        minSlider.setMax(maxValue);
        maxSlider.setMax(maxValue);
        minSlider.setMin(minValue);
        maxSlider.setMin(minValue);
        minSlider.setValue(minValue);
        maxSlider.setValue(maxValue);
    }

    private static void setData(TableView<DataBaseToView> table, ArrayList<DataDB> data) {
        ArrayList<DataBaseToView> dataArray = ConverterDataView.convert(data);
        ObservableList<DataBaseToView> dataView = FXCollections.observableArrayList(dataArray);
        table.setItems(dataView);
    }

    private static int getMinValue(ArrayList<DataDB> data) {
        if (data.size() != 0) {
            int min = data.get(0).cost;
            for (DataDB item : data) {
                if (item.cost < min) {
                    min = item.cost;
                }
            }
            return min;
        }
        return 0;
    }

    private static int getMaxValue(ArrayList<DataDB> data) {
        if (data.size() != 0) {
            int max = data.get(0).cost;
            for (DataDB item : data) {
                if (item.cost > max) {
                    max = item.cost;
                }
            }
            return max;
        }
        return 0;
    }
}
