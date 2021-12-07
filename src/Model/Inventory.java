package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    /**
     * Parts and products lis
     */
    private static ObservableList<Parts> allParts = FXCollections.observableArrayList();
    private static ObservableList<Products> allProducts =FXCollections.observableArrayList();

    /**
     * Auto-gen variables
     */
    private static int partAutoGenID = 0;
    private static int productAutoGenID = 0;

    /**
     * Passing the part
     * @param part
     */
    public static void addParts(Parts part){
        if(part != null) allParts.add(part);
    }

    /**
     * Getters and setter for Observable lists
     * @return
     */
    public static ObservableList<Parts> getAllParts(){ return allParts; }
    public static void addProducts(Products product){ allProducts.add(product); }
    public static ObservableList<Products> getAllProducts(){ return allProducts; }
    public static void setProductAutoGenID(int productAutoGenID) { Inventory.productAutoGenID = productAutoGenID; }
    public static void setPartAutoGenID(int partAutoGenID) { Inventory.partAutoGenID = partAutoGenID; }

    /**
     * Getters and Setters for Auto-gen and resets
     * @return
     */
    public static int getPartAutoGenID() {
        partAutoGenID++;
        return partAutoGenID;
    }
    public static int getProductAutoGenID() {
        productAutoGenID++;
        return productAutoGenID;
    }
    public static int resetPartAutoGenID(){
        partAutoGenID--;
        return partAutoGenID;
    }
    public static int resetProductAutoGenID(){
        productAutoGenID--;
        return productAutoGenID;
    }


    /**
     * Boolean methods for deleting parts and products
     * @param selectedPart
     * @return
     */
    public static boolean deletePart(Parts selectedPart) {

        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        }
        return false;
    }
    public static boolean deleteProduct(Products selectedProduct){

      if(allProducts.contains((selectedProduct))){
            allProducts.remove(selectedProduct);
          return true;
        }
         return false;
    }

    /**
     * Methods for updating parts and products
     * @param id
     * @param part
     */
    public static void updatePart(int id, Parts part) {
        int index = -1;
        for (Parts parts : Inventory.getAllParts()) {
            index++;
            if (parts.getPartID() == id){
                    allParts.set(index, part);
                    return;
            }
        }
    }
    public static void updateProduct(int id, Products product) {
        int index = -1;
        for (Products products : Inventory.getAllProducts()) {
            index++;
            if (products.getProductId() == id){
                allProducts.set(index, product);
                return;
            }
        }
    }

    /**
     * Methods for searching parts and products by name and id
     * @param partID, partName, productName, productID
     * @return
     */
    public static Parts lookupPartById(int partID) {

        ObservableList<Parts> allParts = getAllParts();

        for (int i = 0; i < allParts.size(); i++ ) {
            Parts inv = getAllParts().get(i);
            if(inv.getPartID() == partID){
                return inv;
            }
        }
        return null;
    }
    public static ObservableList<Parts> lookupPartByName(String partName){
        ObservableList<Parts> namedParts = FXCollections.observableArrayList();
        ObservableList<Parts> allParts = Inventory.getAllParts();

        for (Parts parts: allParts){

            if(parts.getPartName().contains(partName)){
                namedParts.add(parts);
            }
        }

        return namedParts;
    }
    public static Products lookupProductById(int productID) {

        ObservableList<Products> allProducts  = getAllProducts();

        for (int i = 0; i < allProducts.size(); i++ ) {
            Products inv = allProducts.get(i);
            if(inv.getProductCost() == productID){
                return inv;
            }
        }
        return null;
    }
    public static ObservableList<Products> lookupProductByName(String productName){
        ObservableList<Products> namedProducts = FXCollections.observableArrayList();
        ObservableList<Products> allProducts = Inventory.getAllProducts();

        for (Products products: allProducts){

            if(products.getProductName().contains(productName)){
                namedProducts.add(products);
            }
        }
        return namedProducts;
    }

}
