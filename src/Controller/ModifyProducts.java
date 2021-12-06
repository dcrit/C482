package Controller;

import Model.*;
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

public class ModifyProducts implements Initializable {

    @FXML
    private TextField searchParts;
    @FXML
    private TextField productIDField;
    @FXML
    private TextField modifyProductNameField;
    @FXML
    private TextField modifyProductPriceField;
    @FXML
    private TextField modifyProductStockField;
    @FXML
    private TextField modifyProductMaxField;
    @FXML
    private TextField modifyProductMinField;

    @FXML
    private TableView<Parts> partsTableView;
    @FXML
    private TableColumn<Parts, Integer> allPartIdCol;
    @FXML
    private TableColumn<Parts, String> allPartNameCol;
    @FXML
    private TableColumn<Parts, Integer> allPartStockCol;
    @FXML
    private TableColumn<Parts, Double> allPartCostCol;

    @FXML
    private TableView<Parts> modifyAssociatedPartToProductTable;
    @FXML
    private TableColumn<Parts, Integer> associatedPartToProductIdCol;
    @FXML
    private TableColumn<Parts, String> associatedPartToProductPartNameCol;
    @FXML
    private TableColumn<Parts, Integer> associatedPartToProductPartStockCol;
    @FXML
    private TableColumn<Parts, Double> associatedPartToProductPartCostCol;

    @FXML
    private Button modifyAddToAssociatedPartList;
    @FXML
    private Button removeAssociatedPart;
    @FXML
    private Button modifySaveProduct;
    @FXML
    private Button cancelToMain;

    //Observable list for modifying products
    ObservableList<Parts> modifyAssociatedProductList = FXCollections.observableArrayList();

    //Passing the product
    private static Products getPassProduct = null;
    public static void setGetPassProduct(Products getPassProduct) {
        ModifyProducts.getPassProduct = getPassProduct;
    }

    //Initializer
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showParts();
        showPassProduct((getPassProduct));
        showAssociatedParts();
    }

    //Methods to show products
    private void showPassProduct(Products passProduct) {
        productIDField.setText((String.valueOf(passProduct.getProductId())));
        modifyProductNameField.setText(String.valueOf(passProduct.getProductName()));
        modifyProductStockField.setText(String.valueOf(passProduct.getProductStock()));
        modifyProductPriceField.setText(String.valueOf(passProduct.getProductCost()));
        modifyProductMaxField.setText(String.valueOf(passProduct.getProductMax()));
        modifyProductMinField.setText(String.valueOf(passProduct.getProductMin()));
    }

    //Method to show associated parts
    private void showAssociatedParts(){
        modifyAssociatedProductList = getPassProduct.getAssociatedParts();
        modifyAssociatedPartToProductTable.setItems(modifyAssociatedProductList);
        associatedPartToProductIdCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        associatedPartToProductPartNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
        associatedPartToProductPartStockCol.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        associatedPartToProductPartCostCol.setCellValueFactory(new PropertyValueFactory<>("partCost"));
    }

    //Methods to show parts
    private void showParts(){
        partsTableView.setItems(Inventory.getAllParts());
        allPartIdCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        allPartNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
        allPartStockCol.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        allPartCostCol.setCellValueFactory(new PropertyValueFactory<>("partCost"));
    }


    //Adds part to associated list
    public void onAddToAssociatedPartList(ActionEvent actionEvent) {

        Parts addToAssociatedProductTable = partsTableView.getSelectionModel().getSelectedItem();

        if (addToAssociatedProductTable != null) {

            modifyAssociatedProductList.add(addToAssociatedProductTable);
            modifyAssociatedPartToProductTable.setItems(modifyAssociatedProductList);
            associatedPartToProductIdCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
            associatedPartToProductPartNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
            associatedPartToProductPartStockCol.setCellValueFactory(new PropertyValueFactory<>("partStock"));
            associatedPartToProductPartCostCol.setCellValueFactory(new PropertyValueFactory<>("partCost"));
            modifyAssociatedPartToProductTable.refresh();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a part to be added to associated list ");
            alert.showAndWait();
        }
    }

    //Removes part from the associated list
    public void onRemoveAssociatedPart(ActionEvent actionEvent) throws Exception {

        Products products = new Products();

        try {
            Parts removeAssociatedPartFromProduct = modifyAssociatedPartToProductTable.getSelectionModel().getSelectedItem();

            if (products.deleteAssociatedPart(removeAssociatedPartFromProduct)) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part? ");
                alert.setContentText(String.valueOf("Are you sure you want to remove this associated part? \n" + "Part ID: " + removeAssociatedPartFromProduct.getPartID() +
                        " Part Name:  " + removeAssociatedPartFromProduct.getPartName() + " Part Inventory Level:  " +
                        removeAssociatedPartFromProduct.getPartStock() + " Part Cost: " + removeAssociatedPartFromProduct.getPartCost()));
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    modifyAssociatedProductList.remove(removeAssociatedPartFromProduct);
                    modifyAssociatedPartToProductTable.refresh();

                }
            }
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select an associated part to be removed. " );
            alert.showAndWait();
            System.out.println(e.getCause());
        }
    }

    //Updates a modified product
    public void onModifySaveProduct(ActionEvent actionEvent) {

        try {

            DecimalFormat df = new DecimalFormat("#.##");
            int id = getPassProduct.getProductId();
            String productName = modifyProductNameField.getText();
            int productStock = Integer.parseInt(modifyProductStockField.getText());
            double productCost = Double.parseDouble(modifyProductPriceField.getText());
            int productMax = Integer.parseInt(modifyProductMaxField.getText());
            int productMin = Integer.parseInt(modifyProductMinField.getText());
            Products addProduct = new Products(id, productName, productStock, productCost, productMax, productMin);

            //Checks if the user has entered valid inputs
            if (addProduct.checkUserValuesProduct()) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Adding:");
                alert.setContentText(
                        "ID: " + id + "  Part Name: " + productName + "  Stock Level: " + productStock +
                                "  Cost: " + df.format(productCost) + "  Max: " + productMax + "  Min: " + productMin);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Inventory.updateProduct(id, addProduct);
                    for(Parts addToList: modifyAssociatedProductList) addProduct.addAssociatedPart(addToList);
                    addProduct = new Products(id, productName, productStock, productCost, productMax, productMin);
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

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter a valid input " );
            alert.showAndWait();
            System.out.println(" LocalizedMessage " + e.getLocalizedMessage());
        }
    }


    //Search parts
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

    //Returns user to MainForm controller and FXML
    public void onCancelToMain(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1400, 800);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();
    }
}
