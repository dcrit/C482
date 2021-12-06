package Model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import java.text.DecimalFormat;

public class Products  {

    //Products variables
    private int productId, productStock, productMin, productMax;
    private String productName;
    private double productCost;

    //Associated parts list
    private ObservableList<Parts> associatedParts = FXCollections.observableArrayList();

    //Products constructor
    public Products(int productId, String productName, int productStock, double productCost, int productMax, int productMin) throws NoSuchMethodException {
        this.productId = productId;
        this.productStock = productStock;
        this.productName = productName;
        this.productCost = productCost;
        this.productMin = productMin;
        this.productMax = productMax;
    }

    //Getters and setters
    public Products() { }

    public int getProductId() { return productId; }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    public int getProductMin() {
        return productMin;
    }

    public void setProductMin(int productMin) {
        this.productMin = productMin;
    }

    public int getProductMax() {
        return productMax;
    }

    public void setProductMax(int productMax) {
        this.productMax = productMax;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    //This getter transforms product costs to two place
    public double getProductCost() {
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(productCost));
    }

    public void setProductCost(double productCost) { this.productCost = productCost; }

    public void addAssociatedPart(Parts part) { this.associatedParts.add(part); }

    public ObservableList<Parts> getAssociatedParts() { return associatedParts; }

    public void setAssociatedParts(ObservableList<Parts> associatedPart) { this.associatedParts = associatedPart; }

    //This boolean method deletes associated parts from the list
    public boolean deleteAssociatedPart(Parts selectedAssociatePart)  {
        associatedParts.remove(selectedAssociatePart);
       return true;

    }

    //This boolean method checks for valid inputs for part name and for negative numbers
    public boolean checkUserValuesProduct() throws Exception {
        try {

        if(getProductName() == "") {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter a product name " );
            alert.showAndWait();
            throw new Exception(" No product name ");

        }
        else if (getProductStock() < 0 ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter positive Stock ");
            alert.showAndWait();
            throw new Exception(" Not a positive Stock number ");

        }

        else if (getProductCost() < 0.00){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(" Please enter a positive Cost ");
            alert.showAndWait();
            throw new Exception(" Not a positive cost number ");

        }

        else if(getProductMax() < 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter a positive Max number ");
            alert.showAndWait();
            throw new Exception(" Not a positive Max number ");
        }

        else if(getProductMin() < 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter a positive MIN number ");
            alert.showAndWait();
            throw new Exception(" Not a positive Min number ");
        }

        else if(getProductStock() < getProductMin() || getProductStock() > getProductMax()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Incorrect Inventory ");
            alert.showAndWait();
            throw new Exception(" Not a correct Inventory input ");

        }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please Enter A Valid Cost ");
            alert.showAndWait();
            System.out.println(e.getCause());

        }
        return true;
    }
}
