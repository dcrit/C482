package Model;

import java.text.DecimalFormat;

public abstract class Parts {
    //Part variables
    private int partID, partStock, partsMin, partsMax;
    private String partName;
    private double partCost;

    //Part constructor
    public Parts(int partID,  String partName, int partStock, double partCost, int partsMax, int partsMin ) {
        this.partID = partID;
        this.partStock = partStock;
        this.partName = partName;
        this.partCost = partCost;
        this.partsMin = partsMin;
        this.partsMax = partsMax;
    }

    //Getters and setters
    public int getPartID() {
        return partID;
    }

    public void setPartID(int partID) {
        this.partID = partID;
    }

    public int getPartStock() { return partStock; }

    public void setPartStock(int partStock) { this.partStock = partStock; }

    public String getPartName() { return partName; }

    public void setPartName(String partName) { this.partName = partName; }

    public Parts() { }


   //Transforms part cost to two decimal places
    public double getPartCost() {
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(partCost));
    }

    public void setPartCost(double partCost) {
        this.partCost = partCost;
    }

    public int getPartsMin() {
        return partsMin;
    }

    public void setPartsMin(int partsMin) {
        this.partsMin = partsMin;
    }

    public int getPartsMax() {
        return partsMax;
    }

    public void setPartsMax(int partsMax) {
        this.partsMax = partsMax;
    }

}
