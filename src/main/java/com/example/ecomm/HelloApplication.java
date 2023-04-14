package com.example.ecomm;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private VBox bodyPane;

    private VBox product;

    HBox footerBar;

    HBox headerBar;

    Customer loggedInCustomer;

    Button signinButton;

    Label welcomeLabel;

    ObservableList<Product>itemsInCart= FXCollections.observableArrayList();

    ProductList products=new ProductList();

    private GridPane loginPage(){
        Text userNameText=new Text("User Name");
        Text passwordText = new Text("Password");
        TextField userName=new TextField();
        PasswordField passwordField=new PasswordField();
        Button loginButton=new Button("sign in");
        Label loginMessage=new Label();
        GridPane gridPane=new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.add(userNameText,0,0);
        gridPane.add(userName,1,0);
        gridPane.add(passwordText,0,1);
        gridPane.add(passwordField, 1,1);
        gridPane.add(loginButton,0,2);
        gridPane.add(loginMessage,1,2);
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
//                Code functionality here
                String username=userName.getText();
                String password=passwordField.getText();
                loggedInCustomer=Login.customerLogin(username,password);
                if(loggedInCustomer!=null){
                    loginMessage.setText("LOGIN SUCCESSFUL!");
                    bodyPane.getChildren().clear();
                    product=products.getAllProducts();
                    bodyPane.getChildren().add(product);
//                    Put a welcome message
                    welcomeLabel.setText("Welcome,"+" "+loggedInCustomer.getName());
                    headerBar.getChildren().add(welcomeLabel);
                    headerBar.setVisible(true);
                    footerBar.setVisible(true);

                }
                else{
                    loginMessage.setText("LOGIN FAILED, PLEASE TRY AGAIN!");
                }
            }
        });
        return gridPane;
    }

    private HBox  headerBar(){
        TextField searchText=new TextField();
        Button searchButton=new Button("Search");
        searchText.setPrefWidth(160);

        Button loginButton=new Button("signout");
        welcomeLabel=new Label("");
        Button cartButton=new Button("View Cart");

         headerBar = new HBox(20);
        headerBar.setPadding(new Insets(5) );
        headerBar.setAlignment(Pos.CENTER);
        headerBar.getChildren().addAll(searchText, searchButton, loginButton, cartButton);

        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String search=searchText.getText();
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(products.getProductsByName(search));
            }
        });


        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(loginPage());
                headerBar.getChildren().remove(loginButton);
            }
        });

        cartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(loggedInCustomer==null){
                    showDialog("Please login first to see cart");
                    return;
                }
                bodyPane.getChildren().add(products.showProductsInCart(itemsInCart));
                headerBar.getChildren().remove(loginButton);
            }
        });


        return headerBar;
    }

    private HBox  footerBar(){
        TextField searchText=new TextField();
        Button buyNow=new Button("Buy Now");
        Button addToCart=new Button("Add to cart");
        Button placeOrder=new Button("Place order");

        HBox footerBar = new HBox(20);
        footerBar.setPadding(new Insets(5) );
        footerBar.setAlignment(Pos.CENTER);
        footerBar.getChildren().addAll(buyNow, addToCart, placeOrder);
        buyNow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
//                Product + customer
                Product product1=products.getSelectedProduct();
                if(loggedInCustomer==null){
                    showDialog("Please login first!!");
                    return;
                }
                if(product1==null){
                    showDialog("Please select a product first!!");
                    return;
                }
//                if(product1!=null && loggedInCustomer!=null)
                int result=Order.placeSingleOrder(product1, loggedInCustomer );


                if(result>=1){
                    showDialog("Order placed Successfully!");
                }
                else{
                    showDialog("Order failed!!");
                }
            }
        });

        addToCart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(loggedInCustomer==null){
                    showDialog("Please login to add to an item to cart first!!");
                    return;
                }
                Product product=products.getSelectedProduct();
                if(product==null){
                    showDialog("Add an item to cart first!!");
                    return;
                }
                itemsInCart.add(product);
                showDialog("Product added to cart successfully to the cart!!");
            }
        });

        placeOrder.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //place the actual order
                if(loggedInCustomer==null){
                    showDialog("Please login to add to an item to cart first!!");
                    return;
                }
                Product product=products.getSelectedProduct();
                if(itemsInCart.size()==0){
                    showDialog("Add an item to cart first!!");
                    return;
                }
                int count=Order.placeMultipleOrders(itemsInCart, loggedInCustomer);
                showDialog("Order placed for "+count+" no of products");
                itemsInCart.clear();
            }
        });

        return footerBar;
    }

    private void showDialog(String message){
        Alert dialog=new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle("message");
        dialog.setContentText(message);
        dialog.setHeaderText(null);
        dialog.showAndWait();
    }

    private VBox productPageDemo(){
        Text text=new Text("Iam Product page");
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,50));
        VBox product = new VBox();
        product.getChildren().addAll(text);
        return product;
    }

    private BorderPane createContent(){
        BorderPane root=new BorderPane();
        root.setPrefSize(600,500);

        HBox headerBar=headerBar();
        root.setTop(headerBar);
        BorderPane.setAlignment(headerBar,Pos.CENTER);

        GridPane loginPage=loginPage();

        bodyPane=new VBox(20);
        bodyPane.setPadding(new Insets(5));

        bodyPane.setAlignment(Pos.CENTER);

        bodyPane.getChildren().add(loginPage);

        root.setCenter(bodyPane);
//        Footer bar
         footerBar =footerBar();
         root.setBottom(footerBar);
         footerBar.setVisible(false);
         headerBar.setVisible(false);
         return root;
    }

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(createContent());
        stage.setTitle("Ecommerce website!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}