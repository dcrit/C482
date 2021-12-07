package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddParts implements Initializable {



    @FXML
    private Label machineIdOrCompanyNameLabel;

    @FXML
    private ToggleGroup addPartRadioGroup;
    @FXML
    private RadioButton inHouseRadio;
    @FXML
    private RadioButton outSourcedRadio;

    @FXML
    private Button cancel;
    @FXML
    private Button savePart;

    @FXML
    private TextField partIDField;
    @FXML
    private TextField partNameField;
    @FXML
    private TextField partStockField;
    @FXML
    private TextField partCostField;
    @FXML
    private TextField partMaxField;
    @FXML
    private TextField partMinField;
    @FXML
    private TextField partMachineIdOrCompanyNameField;


    /**
     * Variable for auto-generating part ID's
     */
     private int partID;

    /**
     * Initializer
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    /**
     * Radio selection to change label to Machine ID
     * @param actionEvent
     */
    @FXML
    private void onAddPartInHouse(ActionEvent actionEvent) {

        machineIdOrCompanyNameLabel.setText("Machine ID ");

    }

    /**
     * Radio selection to change label text to Company Name
     * @param actionEvent
     */
    @FXML
    private void onAddPartOutsourced(ActionEvent actionEvent) {
        machineIdOrCompanyNameLabel.setText("Company Name ");

    }

    /**
     * Cancel button to return the main screen
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void onCancelToMain( ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1400, 800);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Saves parts to the Inventory, additional comments are in the action event
     * @param actionEvent
     * @throws Exception
     */
    @FXML
    private void onSavePart(ActionEvent actionEvent) throws Exception {

        try {

            //Auto-Increment ID's
            partID = Inventory.getPartAutoGenID();

            //Checks is InHouse radio is selected and saves to InHouse model
            if (inHouseRadio.isSelected()) {
                DecimalFormat df = new DecimalFormat("#.##");
                int id = partID;
                String partName = partNameField.getText();
                int partStock = Integer.parseInt(partStockField.getText());
                double partCost = Double.parseDouble(partCostField.getText());
                int partMax = Integer.parseInt(partMaxField.getText());
                int partMin = Integer.parseInt(partMinField.getText());
                int machineID = Integer.parseInt(partMachineIdOrCompanyNameField.getText());
                InHouse inHouse = new InHouse(id, partName, partStock, partCost, partMax, partMin, machineID);

                //Checks users values
                if (inHouse.checkUserValuesID()) {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Adding:");
                    alert.setContentText(
                            "ID: " + id + "  Part Name: " + partName + "  Stock: " + partStock +
                                    "  Cost: " + df.format(partCost) + "  Max: " + partMax + "  Min: " + partMin + "  Machine ID: " + machineID);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        Inventory.addParts(inHouse);
                        //Emptying Object
                        inHouse = new InHouse(0, null, 0, 0.00, 0, 0, 0);
                        Stage stage;
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/View/MainForm.fxml"));
                        loader.load();
                        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                        Parent scene = loader.getRoot();
                        stage.setScene(new Scene(scene));
                        stage.show();
                    }
                    //Resets Auto-gen
                    else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                        partID = Inventory.resetPartAutoGenID();
                    }
                 }
                }
                //Checks if Outsource Radio is selected and then saves to Outsource model
                if (outSourcedRadio.isSelected()) {
                    DecimalFormat df = new DecimalFormat("#.##");
                    int id = partID;
                    String partName = partNameField.getText();
                    int partStock = Integer.parseInt(partStockField.getText());
                    double partCost = Double.parseDouble(partCostField.getText());
                    int partMax = Integer.parseInt(partMaxField.getText());
                    int partMin = Integer.parseInt(partMinField.getText());
                    String companyName = partMachineIdOrCompanyNameField.getText();
                    Outsourced outsourced = new Outsourced(id, partName, partStock, partCost, partMax, partMin, companyName);

                    //Checks user values
                    if (outsourced.checkUserValuesCN()) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Adding:");
                        alert.setContentText(
                                "ID: " + id + "  Part Name: " + partName + "  Stock: " + partStock +
                                        "  Cost: " + df.format(partCost) + "  Max: " + partMax + "  Min: " + partMin + "  Company Name: " + companyName);
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            Inventory.addParts(outsourced);
                            //Emptying Object
                            outsourced = new Outsourced(0, null, 0, 0.00, 0, 0, null);
                            System.out.println("Company Name: " + companyName);
                            Parent root = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
                            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                            Scene scene = new Scene(root, 1400, 800);
                            stage.setTitle("Main Form");
                            stage.setScene(scene);
                            stage.show();
                        }
                        //Resets Auto-Gen
                        else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                            partID = Inventory.resetPartAutoGenID();
                        }

                    }

                }

            }
        catch(Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a valid input. ");
                alert.showAndWait();
                //Resets Auto-Gen
                partID = Inventory.resetPartAutoGenID();
                System.out.println(" LocalizedMessage " + e.getLocalizedMessage());
            }

        }

    }
