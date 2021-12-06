package Model;

import javafx.scene.control.Alert;

import java.util.Objects;

public class Outsourced extends Parts {

    //Variable that will extend parts class
    private String companyName;

    //Constructor that extends the super class with companyName
    public Outsourced(int partID, String partName, int partStock,  double partCost, int partsMax, int partsMin,  String companyName) {
        super(partID, partName, partStock,  partCost, partsMax, partsMin );
        this.companyName = companyName;
    }

    //Getters and Setters
    public String getCompanyName() { return companyName; }

    public void setCompanyName(String companyName) { this.companyName = companyName; }

    //Boolean methods that checks if the user has entered a part name or company and no negative numbers.
    public boolean checkUserValuesCN() throws Exception {

        if(getPartName() == "") {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(" Please enter a part name " );
            alert.showAndWait();
            throw new Exception(" No part name" );

        }
        else if (getPartStock() < 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(" Please enter a positive stock number. ");
            alert.showAndWait();
            throw new Exception(" Positive stock number was not entered. ");

        }

        else if (getPartCost() < 0.00){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(" Please enter a positive cost. ");
            alert.showAndWait();
            throw new Exception(" Positive cost was not entered. ");

        }
        else if(getPartsMax() < 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(" Please enter a positive Max number ");
            alert.showAndWait();
            throw new Exception(" Not a positive Max input");
        }

        else if(getPartsMin() < 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter a positive Min number ");
            alert.showAndWait();
            throw new Exception(" Not a positive Min input");
        }

        else if(Objects.equals(getCompanyName(), "")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter a Company Name " );
            alert.showAndWait();
            throw new Exception(" No Company Name ");
        }

        else if(getPartStock() < getPartsMin() || getPartStock() > getPartsMax()){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Incorrect Inventory ");
            alert.showAndWait();
            throw new Exception(" Not a correct Inventory input ");

        }

        return true;
    }
}
