package Model;

import javafx.scene.control.Alert;

public class InHouse extends Parts {

    /**
     * Variable that will be added to the parts class
     */
    private int machineId;

    /**
     * Constructor that extends from the super class parts
     * @param partID
     * @param partName
     * @param partStock
     * @param partCost
     * @param partsMax
     * @param partsMin
     * @param machineId
     */
    public InHouse(int partID, String partName, int partStock, double partCost, int partsMax, int partsMin, int machineId) {
        super(partID,  partName, partStock, partCost,partsMax, partsMin );
        this.machineId = machineId;
    }

    /**
     * Getters and setters
     * @return
     */
    public int getMachineId() { return machineId; }
    public void setMachineId(int machineId) { this.machineId = machineId; }


    /**
     * Boolean method that checks if the user has entered a part name or negative numbers
     * @return
     * @throws Exception
     */
    public Boolean checkUserValuesID() throws Exception {

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

        else if(getMachineId() < 0 ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter a valid Machine ID  " );
            alert.showAndWait();
            throw new Exception(" Not a positive Machine ID Input");
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
