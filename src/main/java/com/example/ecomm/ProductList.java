package com.example.ecomm;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class ProductList {
    private TableView<Product>productTableView;

    private VBox createAndBindTable(ObservableList<Product>data){
//        Creating columns
        TableColumn id=new TableColumn("ID-Ecomm");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn name=new TableColumn("NAME-Ecomm");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn price=new TableColumn("PRICE-Ecomm");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));


        productTableView=new TableView<>();
        productTableView.setItems(data);
        productTableView.getColumns().addAll(id, name, price);

        VBox vBox=new VBox();
        vBox.setPadding(new Insets(5));
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(productTableView);
        return vBox;
    }

    public VBox getAllProducts(){

        ObservableList<Product>data = Product.getAllProducts();

        return createAndBindTable(data);
    }

    public  VBox getProductsByName(String searchText){
        ObservableList<Product>data = Product.getProductsByName(searchText);
        return createAndBindTable(data);
    }

    public VBox showProductsInCart(ObservableList<Product>productsInCart){
        return createAndBindTable(productsInCart);
    }

    public Product getSelectedProduct(){
        return productTableView.getSelectionModel().getSelectedItem();
    }
}
