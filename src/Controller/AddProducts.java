package Controller;

import Model.Inventory;
import Model.Parts;
import Model.Products;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddProducts implements Initializable {


    @FXML
    private Button cancelToMain;
    @FXML
    private Button saveProduct;
    @FXML
    private Button AddToAssociatedPartList;

    @FXML
    private TextField searchParts;
    @FXML
    private TextField productIDField;
    @FXML
    private TextField productNameField;
    @FXML
    private TextField productStockField;
    @FXML
    private TextField productPriceField;
    @FXML
    private TextField productMaxField;
    @FXML
    private TextField productMinField;

    @FXML
    private TableView<Parts> partsTableView;
    @FXML
    private TableColumn<Parts, Integer> allPartidCol;
    @FXML
    private TableColumn<Parts, String> allPartNameCol;
    @FXML
    private TableColumn<Parts, Integer> allPartStockCol;
    @FXML
    private TableColumn<Parts, Double> allPartPriceCostPerUnitCol;

    @FXML
    private TableView<Parts> associatedPartToProductTable;
    @FXML
    private TableColumn<Parts, Integer> associatedPartToProductIdCol;
    @FXML
    private TableColumn<Parts, String> associatedPartToProductPartNameCol;
    @FXML
    private TableColumn<Parts, Integer> associatedPartToProductPartStockCol;
    @FXML
    private TableColumn<Parts, Double> associatedPartToProductPartCostCol;
    @FXML
    private Button removeAssociatedPart;

    /**
     * Variable for product Auto-gen ID's
     */
    private int productAutoGenId;

    /**
     * Associated parts list for products
     */
    private ObservableList<Parts> associatedProductList = FXCollections.observableArrayList();

    /**
     * Initializer
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        showParts();

    }

    /**
     * Shows part method
     */
    private void showParts(){
        partsTableView.setItems(Inventory.getAllParts());
        allPartidCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        allPartNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
        allPartStockCol.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        allPartPriceCostPerUnitCol.setCellValueFactory(new PropertyValueFactory<>("partCost"));
    }

    /**
     * Takes user to main screen
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void onCancelToMain(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1400, 800);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Saves product to the Inventory, additional comments are in the action event
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void onSaveProduct(ActionEvent actionEvent) throws IOException {

        try {

            //Auto-Gen product ID's
            productAutoGenId = Inventory.getProductAutoGenID();

            DecimalFormat df = new DecimalFormat("#.##");
            int id = productAutoGenId;
            String productName = productNameField.getText();
            int productStock = Integer.parseInt(productStockField.getText());
            double productCost = Double.parseDouble(productPriceField.getText());
            int productMax = Integer.parseInt(productMaxField.getText());
            int productMin = Integer.parseInt(productMinField.getText());
            Products addProduct = new Products(id, productName, productStock, productCost, productMax, productMin);

            //Checks user values
            if (addProduct.checkUserValuesProduct()) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Adding:");
                alert.setContentText(
                        "ID: " + id + "  Product Name: " + productName + "  Stock Level: " + productStock +
                                "  Cost: " + df.format(productCost) + "  Max: " + productMax + "  Min: " + productMin);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Inventory.addProducts(addProduct);
                    addProduct = new Products();

                    //Adds associated parts to product
                    for (Parts addToList : associatedProductList) addProduct.addAssociatedPart(addToList);
                    Stage stage;
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/View/MainForm.fxml"));
                    loader.load();
                    stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    Parent scene = loader.getRoot();
                    stage.setScene(new Scene(scene));
                    stage.show();

                }
                //Resets Auto-Gen
                if(result.isPresent() && result.get() == ButtonType.CANCEL){
                    productAutoGenId = Inventory.resetProductAutoGenID();

                }
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please Enter A Valid Input ");
            alert.showAndWait();
            //Resets Auto-Gen
            productAutoGenId = Inventory.resetProductAutoGenID();
            System.out.println(e.getLocalizedMessage());
        }
    }


    /**
     * Adds part to the product's associated table
     * @param actionEvent
     * @throws Exception
     */
    @FXML
    private void onAddToAssociatedPartList(ActionEvent actionEvent) throws Exception {

        Parts addToAssociatedProductTable = partsTableView.getSelectionModel().getSelectedItem();

        if (addToAssociatedProductTable != null) {

            associatedProductList.add(addToAssociatedProductTable);
            associatedPartToProductTable.setItems(associatedProductList);
            associatedPartToProductIdCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
            associatedPartToProductPartNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
            associatedPartToProductPartStockCol.setCellValueFactory(new PropertyValueFactory<>("partStock"));
            associatedPartToProductPartCostCol.setCellValueFactory(new PropertyValueFactory<>("partCost"));
            associatedPartToProductTable.refresh();

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a part to be added to associated list ");
            alert.showAndWait();

        }
    }

    /**
     * Removes part from products associated table
     * @param actionEvent
     * @throws Exception
     */
    @FXML
    private void onRemoveAssociatedPart(ActionEvent actionEvent) throws Exception {

        try {
            Products p = new Products();
            Parts removeAssociatedPartFromProduct = associatedPartToProductTable.getSelectionModel().getSelectedItem();

            if (p.deleteAssociatedPart(removeAssociatedPartFromProduct)) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part? ");
                alert.setContentText(String.valueOf("Are you sure you want to remove this associated part? \n" + "Part ID: " + removeAssociatedPartFromProduct.getPartID() +
                        " Part Name:  " + removeAssociatedPartFromProduct.getPartName() + " Part Inventory Level:  " +
                        removeAssociatedPartFromProduct.getPartStock() + " Part Cost: " + removeAssociatedPartFromProduct.getPartCost()));
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    associatedProductList.remove(removeAssociatedPartFromProduct);
                    associatedPartToProductTable.refresh();
                }
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select an associated part to be removed. ");
            alert.showAndWait();
            System.out.println(e.getLocalizedMessage());
        }
    }

    /**
     * Removes part from products associated table
     * @param actionEvent
     */
    @FXML
    private void onSearchParts(ActionEvent actionEvent) {
        try {

            String partName = searchParts.getText();
            ObservableList<Parts> partNameList = Inventory.lookupPartByName(partName);
            if (partName != null) {
                if (partNameList.size() == 0) {
                    int partID = Integer.parseInt(partName);
                    Parts pId = Inventory.lookupPartById(partID);
                    partNameList.add(pId);
                    partsTableView.refresh();
                    searchParts.clear();
                }
                partsTableView.refresh();
                partsTableView.setItems(partNameList);
                searchParts.clear();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Part not found ");
            alert.showAndWait();
            System.out.println(e.getLocalizedMessage());

        }
    }
}
