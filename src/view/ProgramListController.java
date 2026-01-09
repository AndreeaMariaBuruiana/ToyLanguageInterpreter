package view;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.state.ProgramState;
import model.statement.IStatement;
import repository.ArrayListRepository;
import repository.IRepository;
import java.util.ArrayList;
import java.util.List;

public class ProgramListController {
    @FXML private ListView<String> programListView;
    private List<IStatement> programs;

    @FXML
    public void initialize() {
        programs = new ArrayList<>();
        programs.add(View.createExample1());
        programs.add(View.createExample2());
        programs.add(View.createExample3());
        programs.add(View.createExample4());
        programs.add(View.createExample5());
        programs.add(View.createExample6());
        programs.add(View.createExample7());
        programs.add(View.createExample8());
        programs.add(View.createExample9());
        programs.add(View.createExample10());
        programs.add(View.createExampleFail());

        List<String> programStrings = new ArrayList<>();
        for (IStatement st : programs) {
            programStrings.add(st.toString());
        }
        programListView.setItems(FXCollections.observableArrayList(programStrings));
    }

    @FXML
    private void handleRunButton() {
        int index = programListView.getSelectionModel().getSelectedIndex();
        if (index < 0) {
            new Alert(Alert.AlertType.ERROR, "Please select a program!").show();
            return;
        }

        IStatement selected = programs.get(index);

        try {
            ProgramState initialPrg = View.createPrgState(selected);
            List<ProgramState> list = new ArrayList<>();
            list.add(initialPrg);
            IRepository repo = new ArrayListRepository(list, "log_gui_" + (index + 1) + ".txt");
            Controller controller = new Controller(repo, true);


            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));

            MainWindowController mainCtrl = loader.getController();
            mainCtrl.setController(controller);

            stage.setTitle("Interpreter - Main Window");
            stage.show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }
}