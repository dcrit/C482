package Controller;

import Model.Inventory;
import Model.Parts;
import Model.Products;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainForm implements Initializable {


    @FXML
    private Button addProduct;
    @FXML
    private Button modifyProduct;
    @FXML
    private Button deleteProduct;
    @FXML
    private Button exitProgram;
    @FXML
    private Button addPart;
    @FXML
    private Button modifyPart;
    @FXML
    private Button deletePart;

    @FXML
    private TableView<Products> productsTableView;
    @FXML
    private TableColumn<Products, Integer> allProductidCol;
    @FXML
    private TableColumn<Products, String> allProductNameCol;
    @FXML
    private TableColumn<Products, Integer> allProductStockCol;
    @FXML
    private TableColumn<Products, Double> allProductPriceCostPerUnitcol;

    @FXML
    private TableView<Parts> partsTableView;
    @FXML
    private TableColumn<Parts, Integer> allPartidCol;
    @FXML
    private TableColumn<Parts, String> allPartNameCol;
    @FXML
    public TableColumn<Parts, Integer> allPartStockCol;
    @FXML
    private TableColumn<Parts, Double> allPartPriceCostPerUnitCol;

    @FXML
    private TextField searchParts;
    @FXML
    private TextField searchProducts;

    /**
     * Initializer
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partsView();
        productsView();


    }

    /**
     * Method for showing parts in table
     */
    private void partsView(){
        partsTableView.setItems(Inventory.getAllParts());
        allPartidCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        allPartNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
        allPartStockCol.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        allPartPriceCostPerUnitCol.setCellValueFactory(new PropertyValueFactory<>("partCost"));
    }

    /**
     * Method for showing products in table
     */
    private void productsView(){
        productsTableView.setItems(Inventory.getAllProducts());
        allProductidCol.setCellValueFactory((new PropertyValueFactory<>("productId")));
        allProductNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        allProductStockCol.setCellValueFactory(new PropertyValueFactory<>("productStock"));
        allProductPriceCostPerUnitcol.setCellValueFactory(new PropertyValueFactory<>("productCost"));
    }

    /**
     * Takes user to AddParts controller and FXML
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void onAddPart(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/View/AddParts.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1400, 800);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Takes user to ModifyParts controller and FXML
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void onModifyPart(ActionEvent actionEvent) throws IOException {

        Parts modifyPart = partsTableView.getSelectionModel().getSelectedItem();
        ModifyParts.setPassPart(modifyPart);

       try {
           Parent root = FXMLLoader.load(getClass().getResource("/View/ModifyParts.fxml"));
           Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
           Scene scene = new Scene(root, 1400, 800);
           stage.setTitle("Modify Part");
           stage.setScene(scene);
           stage.show();
       } catch (Exception e){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setContentText("Please select a part to be modified. " );
           alert.showAndWait();

           System.out.println(e.getCause());
       }
    }

    /**
     * Deletes Parts
     * @param actionEvent
     */
    @FXML
    private void onDeletePart(ActionEvent actionEvent) {

        try {
            Parts deletePart = partsTableView.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part? ");
            alert.setContentText(String.valueOf("Are you sure you want to delete this part? \n" + "Part ID: " + deletePart.getPartID() +
                    " Part Name:  " + deletePart.getPartName() + " Part Inventory Level:  " +
                    deletePart.getPartStock() + " Part Cost: " + deletePart.getPartCost()));
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(deletePart);
                partsTableView.refresh();

            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a product to be deleted. " );
            alert.showAndWait();

            System.out.println(" LocalizedMessage " + e.getLocalizedMessage());
        }
    }

    /**
     * Takes user to AddProducts controller and FXML
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void onAddProduct(ActionEvent actionEvent) throws IOException{

        Parent root = FXMLLoader.load(getClass().getResource("/View/AddProducts.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1400, 800);
        stage.setTitle("Add Products ");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Deletes Products
     * @param actionEvent
     * @throws Exception
     */
    @FXML
    private void onDeleteProduct(ActionEvent actionEvent) throws Exception {

            try{
                Products deleteProduct = productsTableView.getSelectionModel().getSelectedItem();
                ObservableList<Parts> list = deleteProduct.getAssociatedParts();

            if(list.size() >=1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Product has associated part ");
                alert.showAndWait();
            }  else if (list.size() == 0) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Product? ");
                alert.setContentText("Are you sure you want to delete this Product? \n" + "Product ID: " + deleteProduct.getProductId() +
                        " Product Name:  " + deleteProduct.getProductName() + " Product Stock:  " +
                        deleteProduct.getProductStock() + " Product Cost: " + deleteProduct.getProductCost());
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Inventory.deleteProduct(deleteProduct);
                    productsTableView.refresh();
                }
            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a product to be deleted. " );
            alert.showAndWait();
            System.out.println(" LocalizedMessage " + e.getLocalizedMessage());
        }
    }

    /**
     * Searches part
     * @param actionEvent
     * @throws Exception
     */
    @FXML
    private void onSearchParts(ActionEvent actionEvent) throws Exception {

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
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Part not found " );
            alert.showAndWait();

            System.out.println(e.getLocalizedMessage());

        }

    }

    /**
     * Searches Product
     * @param actionEvent
     */
    @FXML
    private void onSearchProducts(ActionEvent actionEvent) {

        try {

            String productName = searchProducts.getText();
            ObservableList<Products> productNameList = Inventory.lookupProductByName(productName);
            if (productName != null) {
                if (productNameList.size() == 0) {
                    int productID = Integer.parseInt(productName);
                    Products pId = Inventory.lookupProductById(productID);
                    productNameList.add(pId);
                    productsTableView.refresh();
                    searchProducts.clear();
                }
                productsTableView.refresh();
                productsTableView.setItems(productNameList);
                searchProducts.clear();
            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Product not found " );
            alert.showAndWait();

            System.out.println(e.getLocalizedMessage());

        }
    }

    /**
     * Takes the user to ModifyProducts controller and FXML
     * @param actionEvent
     * @throws IOException
     */
    public void onModifyProduct(ActionEvent actionEvent) throws IOException {

        Products modifyProduct = productsTableView.getSelectionModel().getSelectedItem();
        ModifyProducts.setGetPassProduct(modifyProduct);

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/ModifyProducts.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1400, 800);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a product to be modified. " );
            alert.showAndWait();

            System.out.println(e.getLocalizedMessage());
        }
    }

    /**
     * Exits application
     * @param actionEvent
     */
    @FXML
    private void onExitProgram(ActionEvent actionEvent) {
        System.exit(0);
    }
}
