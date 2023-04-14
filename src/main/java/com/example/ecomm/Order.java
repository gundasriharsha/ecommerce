package com.example.ecomm;

import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Order {
    public static int placeSingleOrder(Product product, Customer customer){
        String getMaxGroupOrderId="SELECT MAX(group_order_id) + 1 as id FROM orders";
        DbConnection dbConnection=new DbConnection();
        try{
            ResultSet rs=dbConnection.getQueryTable(getMaxGroupOrderId);
            if(rs.next()){
                String orderQuery = "INSERT INTO orders(group_order_id, customer_id, product_id, status) VALUES("+rs.getInt("id")+"," + customer.getId() + ", " + product.getId() + ", 'ORDERED')";
                return dbConnection.updateInsertDB(orderQuery);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    public static int placeMultipleOrders(ObservableList<Product>products, Customer customer){
        String getMaxGroupOrderId="SELECT MAX(group_order_id) + 1 as id FROM orders";
        DbConnection dbConnection=new DbConnection();
        try{
            int groupOrderId=0;
            ResultSet rs=dbConnection.getQueryTable(getMaxGroupOrderId);
            if(rs.next()) {
                groupOrderId = rs.getInt("id");
            }
            int count=0;
            for(Product product: products){
                String orderQuery = "INSERT INTO orders(group_order_id, customer_id, product_id, status) VALUES("+groupOrderId+"," + customer.getId() + ", " + product.getId() + ", 'ORDERED')";
                count+=dbConnection.updateInsertDB(orderQuery);
            }
            return count;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
