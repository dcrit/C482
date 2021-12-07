package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Parts;
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

public class ModifyParts implements Initializable {

    @FXML
    private Button saveModPart;
    @FXML
    private Button cancel;

    @FXML
    private TextField modPartId;
    @FXML
    private TextField modPartCost;
    @FXML
    private TextField modPartName;
    @FXML
    public TextField modPartStock;
    @FXML
    private TextField modPartMax;
    @FXML
    private TextField modPartMin;
    @FXML
    private TextField modPartMachineIdOrOutsourced;

    @FXML
    private ToggleGroup modPartRadioGroup;

    @FXML
    private RadioButton modInHouseRadio;
    @FXML
    private RadioButton modOutsourceRadio;

    @FXML
    private Label inHouseAndOutsourceLabel;


    //Passing the part
    private static Parts passPart = null;

    public static void setPassPart(Parts passPart) {

        ModifyParts.passPart = passPart;
    }

    //Initializer
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        showPassPart(passPart);

    }

    //In House and Outsource radio buttons
    @FXML
    private void onModInHouseRadio(ActionEvent actionEvent) {

        inHouseAndOutsourceLabel.setText("In House ");
    }

    @FXML
    private void onModOutsourceRadio(ActionEvent actionEvent) {

        inHouseAndOutsourceLabel.setText(("Outsourced "));
    }


    //Method to show passed part
    private void showPassPart(Parts passPart) {

        modPartId.setText((String.valueOf(passPart.getPartID())));
        modPartName.setText(String.valueOf(passPart.getPartName()));
        modPartStock.setText(String.valueOf(passPart.getPartStock()));
        modPartCost.setText(String.valueOf(passPart.getPartCost()));
        modPartMax.setText(String.valueOf(passPart.getPartsMax()));
        modPartMin.setText(String.valueOf(passPart.getPartsMin()));

        if (passPart instanceof InHouse) {

            modPartMachineIdOrOutsourced.setText(Integer.toString(((InHouse) passPart).getMachineId()));
            inHouseAndOutsourceLabel.setText("Machine ID ");
            modInHouseRadio.setSelected(true);


        } else {

            modPartMachineIdOrOutsourced.setText(String.valueOf(((Outsourced) passPart).getCompanyName()));
            inHouseAndOutsourceLabel.setText("Company Name ");
            modOutsourceRadio.setSelected(true);

        }


    }


    //Updates the changes made to part
    public void onSaveModPart(ActionEvent actionEvent) throws IOException {

        try {

            //Checks if the user has selected In House
            if (modInHouseRadio.isSelected()) {
                DecimalFormat df = new DecimalFormat("#.##");
                int id = passPart.getPartID();
                String partName = modPartName.getText();
                int partStock = Integer.parseInt(modPartStock.getText());
                double partCost = Double.parseDouble(modPartCost.getText());
                int partMax = Integer.parseInt(modPartMax.getText());
                int partMin = Integer.parseInt(modPartMin.getText());
                int machineID = Integer.parseInt(modPartMachineIdOrOutsourced.getText());
                String companyName = modPartMachineIdOrOutsourced.getText();
                InHouse inHouse = new InHouse(id, partName, partStock, partCost, partMax, partMin, machineID);

                //Checks is the user has entered valid fields
                if (inHouse.checkUserValuesID()) {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Adding:");
                    alert.setContentText(
                            "ID: " + id + "  Part Name: " + partName + "  Stock: " + partStock +
                                    "  Cost: " + df.format(partCost) + "  Max: " + partMax + "  Min: " + partMin + "  Machine ID: " + machineID);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        Inventory.updatePart(id, inHouse);
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
                }

            }

            // Checks is the user has selected Outsource
            if (modOutsourceRadio.isSelected()) {
                DecimalFormat df = new DecimalFormat("#.##");
                int id = passPart.getPartID();
                String partName = modPartName.getText();
                int partStock = Integer.parseInt(modPartStock.getText());
                double partCost = Double.parseDouble(modPartCost.getText());
                int partMax = Integer.parseInt(modPartMax.getText());
                int partMin = Integer.parseInt(modPartMin.getText());
                String companyName = modPartMachineIdOrOutsourced.getText();
                Outsourced outsourced = new Outsourced(id, partName, partStock, partCost, partMax, partMin, companyName);

                //Checks if the user has entered valid fields
                if (outsourced.checkUserValuesCN()) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Adding:");
                    alert.setContentText(
                            "ID: " + id + "  Part Name: " + partName + "  Stock: " + partStock +
                                    "  Cost: " + df.format(partCost) + "  Max: " + partMax + "  Min: " + partMin + "  Company Name: " + companyName);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        Inventory.updatePart(id, outsourced);
                        outsourced = new Outsourced(0, null, 0, 0.00, 0, 0, null);
                        System.out.println("Company Name: " + companyName);
                        Parent root = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
                        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root, 1400, 800);
                        stage.setTitle("Main Form");
                        stage.setScene(scene);
                        stage.show();
                    }

                }

            }

        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter a valid input ");
            alert.showAndWait();
            System.out.println(" LocalizedMessage " + e.getLocalizedMessage());

        }
    }

    //Returns to main screen
    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1400, 800);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();
    }
}





