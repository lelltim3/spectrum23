package widget;

import detectionModules.BDMGintf;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import transferCanMessages.TransferCanMsgs;

import java.io.IOException;
import java.net.URL;

public class Widget extends Application {

    private static TransferCanMsgs transferCanMsg;
    private static BDMGintf bd;

    public static void main(String[] args) {

        bd = new BDMGintf();
        transferCanMsg = new TransferCanMsgs();
        bd.setTransferCanMsg(transferCanMsg);
        transferCanMsg.setDB(bd);



        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Class<? extends Widget> aClass = getClass();
        Pane rootPane           = getPane(aClass.getResource("../../resources/fxml/Root.fxml"));
        Pane headerPane         = getPane(aClass.getResource("../../resources/fxml/Header.fxml"));
        Pane controlPane        = getPane(aClass.getResource("../../resources/fxml/Control.fxml"));
        Pane measurementPane    = getPane(aClass.getResource("../../resources/fxml/Measurement.fxml"));
        Pane observationPane    = getPane(aClass.getResource("../../resources/fxml/Observation.fxml"));
        Pane storagePane        = getPane(aClass.getResource("../../resources/fxml/Storage.fxml"));
        Pane dataBasePane       = getPane(aClass.getResource("../../resources/fxml/DataBase.fxml"));
        Pane processingPane     = getPane(aClass.getResource("../../resources/fxml/Processing.fxml"));
        Pane idealModelsPane    = getPane(aClass.getResource("../../resources/fxml/IdealModels.fxml"));
        Pane parametersPane     = getPane(aClass.getResource("../../resources/fxml/Parameters.fxml"));

        joinTabs(rootPane, headerPane, controlPane, measurementPane, observationPane, storagePane, dataBasePane, processingPane, idealModelsPane, parametersPane);

        primaryStage.setTitle("Spectrum");
        primaryStage.setScene(new Scene(rootPane, 900, 550));

        primaryStage.show();


    }

    private void joinTabs(Pane rootPane, Pane headerPane, Pane controlPane, Pane measurementPane, Pane observationPane, Pane storagePane, Pane dataBasePane, Pane processingPane, Pane idealModelsPane, Pane parametersPane)
    {
        //TODO add header
        rootPane.getChildren().forEach(node -> {
            String id = node.getId();
            if(id != null) {
                if (id.equals("tabs") && node instanceof TabPane) {
                    ObservableList<Tab> tabs = ((TabPane) node).getTabs();
                    Tab controlTab      = new Tab("Контроль", controlPane);
                    Tab measurementTab  = new Tab("Измерение", measurementPane);
                    Tab observationTab  = new Tab("Наблюдение", observationPane);
                    Tab storageTab      = new Tab("Хранение", storagePane);
                    Tab dataBaseTab     = new Tab("База", dataBasePane);
                    Tab processingTab   = new Tab("Обработка", processingPane);
                    Tab idealModelsTab  = new Tab("Эталоны", idealModelsPane);
                    Tab parametersTab   = new Tab("Эталоны", parametersPane);

                    tabs.add(controlTab);
                    tabs.add(measurementTab);
                    tabs.add(observationTab);
                    tabs.add(storageTab);
                    tabs.add(dataBaseTab);
                    tabs.add(processingTab);
                    tabs.add(idealModelsTab);
                    tabs.add(parametersTab);
                }
                else if(id.equals("header")  && node instanceof StackPane){
                    ((StackPane) node).getChildren().add(headerPane);

                }
            }
        });
    }


    private Pane getPane(URL url) {
        Pane pane;
        FXMLLoader loader = new FXMLLoader(url);

        try {
            pane = loader.load();
        }
        catch (LoadException e) {
            System.out.println("LoadException. Error parsing " + url);//TODO add logger!
            throw new RuntimeException("Error load " + url, e);
        }
        catch (IOException e) {
            System.out.println("IOException. Error input/output " + url);//TODO add logger!
            throw new RuntimeException("Error i/o " + url, e);
        }

        Object controller = loader.getController();
        System.out.println("Controller: " + controller.getClass().getSimpleName());


        return pane;
    }


}
