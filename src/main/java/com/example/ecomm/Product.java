package com.example.ecomm;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Product {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleDoubleProperty price;

    public Product(int id, String name, double price){
        this.id=new SimpleIntegerProperty(id);
        this.name=new SimpleStringProperty(name);
        this.price=new SimpleDoubleProperty(price);
    }
    public static ObservableList<Product>getAllProducts(){
        String query="SELECT * FROM product";
        return getProducts(query);
    }

    public static ObservableList<Product>getProductsByName(String productName){
        String query="SELECT * FROM product WHERE name LIKE lower('%"+productName.toLowerCase()+"%') ";
        return getProducts(query);
    }


    public static ObservableList<Product> getProducts(String query){
        ObservableList<Product>data= FXCollections.observableArrayList();
        DbConnection connection=new DbConnection();
        try{
            ResultSet rs = connection.getQueryTable(query);
            while(rs.next()) {
                Product product = new Product(rs.getInt("id"), rs.getString("name"), rs.getDouble("price"));
                data.add(product);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return data;
    }
    public int getId() {
        return id.get();
    }

    public String getName(){
        return name.get();
    }

    public double getPrice(){
        return price.get();
    }
}
