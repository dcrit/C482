package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    /**
     *
     * Future Feature:
     * A future feature I will be adding to this application is the ability to save data into MySQL Workbench. Adding
     * this feature will help keep data in a centralized location and gives the user the ability to use MySQL SQL’s
     * queries to pull data information and the ability to load the data in excels sheets. I plan on also making this
     * application executable and ready for deployment.
     *
     * Error Resolution:
     * The error I had the most issues with was my Auto-Gen ID’s system. At first, I was trying to reset ID in the
     * onSave action event for the parts and products. I’ve tried a lot of techniques but was having no luck. I then
     * realized that the TextFields were being invoked every time an error occurred or an action of a save or cancel
     * button, which would increment the ID. I then made decrement methods in the Inventory class then called them in
     * the cancel button option in the Alert box and in the catch clause for errors. So far it’s been working properly.
     *
     * @author Dan Crites
     *
     */


    /**
     * Main Form
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
        stage.setTitle("Main Form");
        stage.setScene(new Scene(root, 1400, 675));
        stage.show();

    }

    /**
     * Main Initializer
     * @param args
     */
    public static void main(String[] args){

        launch(args);

    }
}
