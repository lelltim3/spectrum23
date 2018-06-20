package widget.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ResourceBundle;

public class ObservationController implements Initializable{

    @FXML
    private LineChart<?, ?> chart;

    @FXML
    private NumberAxis axisSpectr;

    @FXML
    private NumberAxis axisChannels;




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chart.setAnimated(false);
        axisChannels.setAutoRanging(false);
        axisSpectr.setAutoRanging(false);

        axisChannels.setLowerBound(-10);
        axisChannels.setUpperBound(10);

    }



    @FXML
    void onBtnStart(ActionEvent event) {
        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data(1, 10));
        series.getData().add(new XYChart.Data(2, 11));
        series.getData().add(new XYChart.Data(3, 12));
        series.getData().add(new XYChart.Data(4, 13));
        series.getData().add(new XYChart.Data(5, 14));

        chart.getData().clear();
        axisSpectr.setLowerBound(0);
        axisSpectr.setUpperBound(15);
        chart.getData().addAll(series);
    }


    @FXML
    void onBtnFinish(ActionEvent event) {
        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data(1, 20));
        series.getData().add(new XYChart.Data(2, 21));
        series.getData().add(new XYChart.Data(3, 22));
        series.getData().add(new XYChart.Data(4, 23));
        series.getData().add(new XYChart.Data(5, 24));

        chart.getData().clear();
        axisSpectr.setLowerBound(0);
        axisSpectr.setUpperBound(30);
        chart.getData().addAll(series);
    }





}
