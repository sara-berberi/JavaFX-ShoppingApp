module org.shoppingappdemo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; 


    opens org.shoppingappdemo to javafx.fxml;
    exports org.shoppingappdemo;
    exports org.shoppingappdemo.BrowseProducts;
    opens org.shoppingappdemo.BrowseProducts to javafx.fxml;
}