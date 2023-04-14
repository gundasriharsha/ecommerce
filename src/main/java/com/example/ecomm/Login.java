package com.example.ecomm;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {

    public static Customer customerLogin(String userName, String password){
//        Create a query
        String query= "SELECT * FROM customer WHERE email = '"+userName+"' AND password='"+password+"'";
        DbConnection dbConnection=new DbConnection();
        ResultSet rs=dbConnection.getQueryTable(query);
        try {
            if (rs != null && rs.next()) {
                return new Customer(rs.getInt("idcustomer"), rs.getString("name"), rs.getString("email"),0, null);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Login login=new Login();
        System.out.println(login.customerLogin("abcde@gmail.com","pqrstuv"));
    }
}
