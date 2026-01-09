package view;

import controller.Controller;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.state.IHeap;
import model.state.ProgramState;
import model.value.IValue;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MainWindowController {
    private Controller controller;

    @FXML private TextField numberOfPrgStatesTextField;
    @FXML private TableView<Map.Entry<Integer, IValue>> heapTableView;
    @FXML private TableColumn<Map.Entry<Integer, IValue>, String> heapAddressColumn;
    @FXML private TableColumn<Map.Entry<Integer, IValue>, String> heapValueColumn;
    @FXML private ListView<String> outputListView;
    @FXML private ListView<String> fileTableListView;
    @FXML private ListView<Integer> prgStateIdentifiersListView;
    @FXML private TableView<Map.Entry<String, IValue>> symTableView;
    @FXML private TableColumn<Map.Entry<String, IValue>, String> symTableVarNameColumn;
    @FXML private TableColumn<Map.Entry<String, IValue>, String> symTableValueColumn;
    @FXML private ListView<String> exeStackListView;

    public void setController(Controller controller) {
        this.controller = controller;
        populate();
    }

    @FXML
    public void initialize() {
        heapAddressColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey().toString()));
        heapValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));
        symTableVarNameColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        symTableValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));

        prgStateIdentifiersListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) populateSpecificTables(newVal);
        });
    }

    private void populate() {
        List<ProgramState> programs = controller.getRepo().getProgramList();
        numberOfPrgStatesTextField.setText(String.valueOf(programs.size()));

        List<Integer> ids = programs.stream().map(ProgramState::getId).collect(Collectors.toList());

        Integer selectedId = prgStateIdentifiersListView.getSelectionModel().getSelectedItem();

        prgStateIdentifiersListView.setItems(FXCollections.observableArrayList(ids));

        if (ids.isEmpty()) {
            return;
        }

        if (selectedId != null && ids.contains(selectedId)) {
            prgStateIdentifiersListView.getSelectionModel().select(selectedId);
            populateSpecificTables(selectedId);
        } else {
            prgStateIdentifiersListView.getSelectionModel().select(0);
            populateSpecificTables(ids.get(0));
        }
    }


    private void populateSpecificTables(int id) {
        ProgramState prg = controller.getRepo().getProgramList().stream()
                .filter(p -> p.getId() == id).findFirst().orElse(null);
        if (prg == null) return;

        heapTableView.setItems(FXCollections.observableArrayList(prg.heap().getHeap().entrySet()));
        heapTableView.refresh();

        outputListView.setItems(FXCollections.observableArrayList(
                prg.out().getValues().stream().map(Object::toString).collect(Collectors.toList())
        ));
        fileTableListView.setItems(FXCollections.observableArrayList(
                prg.fileTable().getContent().keySet().stream().map(Object::toString).collect(Collectors.toList())
        ));

        symTableView.setItems(FXCollections.observableArrayList(prg.symbolTable().getContent().entrySet()));
        symTableView.refresh();

        exeStackListView.setItems(FXCollections.observableArrayList(
                prg.executionStack().getReversedList().stream().map(Object::toString).collect(Collectors.toList())
        ));
    }

    @FXML
    private void runOneStep() {
        try {
            List<ProgramState> allPrograms = controller.getRepo().getProgramList();
            List<ProgramState> activePrograms = controller.removeCompletedPrograms(allPrograms);

            if (activePrograms.isEmpty()) {
                populate();
                new Alert(Alert.AlertType.INFORMATION, "Program execution finished.").show();
                return;
            }

            // 2. Run Garbage Collector on active programs
            IHeap<Integer, IValue> heap = activePrograms.get(0).heap();
            Set<Integer> usedAddresses = activePrograms.stream()
                    .flatMap(p -> p.getUsedAddresses().stream())
                    .collect(Collectors.toSet());
            heap.setHeap(heap.safeGarbageCollector(usedAddresses, heap.getHeap()));

            controller.oneStepForAllPrograms(activePrograms);

            populate();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }
}