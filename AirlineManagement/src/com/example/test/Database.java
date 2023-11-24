package com.example.test;

import java.sql.*;


public class Database {
    final private  String url = "jbdc:mysql://localhost/airline management system";
    final private  String user = "user";
    final private  String pass = "#1#2#3%1%2%3";
    final private Statement statement;


    public Database() throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, pass);
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                               ResultSet.CONCUR_READ_ONLY);
    }

    public Statement getStatement(){
        return statement;
    }




}
