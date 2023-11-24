package com.example.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class AirportsController {

    public static void AddNewAirport(Database database, Scanner s) throws SQLException{
        System.out.println("Enter city");
        String city = s.next();

        int id;

        ArrayList<Airport> airports = getAllAirports(database);
        if (airports.size()!=0){
            id = airports.get(airports.size()-1).getId()+1;
        }else {
            id = 0;
        }


        Airport airport = new Airport();
        airport.setId(id);
        airport.setCity(city);

        String insert = "INSERT INTO `airports`(`id`, `city`) VALUES ('"+airport.getId()+"','"+airport.getCity()+"'); ";
        database.getStatement().execute(insert);
        System.out.println("Airport added successfully!!!");

    }

    public static void PrintAllAirports(Database database) throws SQLException{
        System.out.println("----------------------------------------");
        System.out.println("id\tcity");
        for (Airport a : getAllAirports(database)){
            a.print();
        }
        System.out.println("----------------------------------------");

    }

    public static void EditAirport(Database database, Scanner s) throws SQLException{
        System.out.println("Enter airport id(int): \n(-1 to show all airports)");
        int id = s.nextInt();

        if (id == -1){
            PrintAllAirports(database);
            System.out.println("Enter airport id(int): ");
            id = s.nextInt();
        }

        Airport airport = GetAirport(database, id);
        System.out.println("Enter city: ");
        String city = s.next();
        airport.setCity(city);
        String update = "UPDATE `airports` SET `id`='"+airport.getId()+"',`city`='"+airport.getCity()+"' WHERE `id` = "+airport.getId()+" ;" ;

        if (database.getStatement().execute(update)){
            System.out.println("Airport edited successfully!!!");
        }else {
            System.out.println("Operation failed\n try again\n");
        }


    }

    public static void DeleteAirport(Database database, Scanner s)throws SQLException{
        System.out.println("Enter airport id(int): \n(-1 to print all airports");
        int id = s.nextInt();
        if (id == -1){
            PrintAllAirports(database);
            System.out.println("Enter airport id(int): ");
            id = s.nextInt();
        }

        String delete = "DELETE FROM `airports` WHERE `id` = "+id+" ;";
        database.getStatement().execute(delete);
        System.out.println("Airport deleted successfully!!!");
    }


    public static Airport GetAirport(Database database, int id) throws SQLException{
        Airport airport = new Airport();
        String select = "SELECT `id`, `city` FROM `airports` WHERE `id` = "+id+" ;";
        ResultSet rs = database.getStatement().executeQuery(select);
        rs.next();
        airport.setId(rs.getInt("id"));
        airport.setCity(rs.getString("city"));
        return airport;
    }


    public static ArrayList<Airport> getAllAirports(Database database) throws SQLException{
        ArrayList<Airport> airports = new ArrayList<>();
        String select = "SELECT * FROM `airports` ";
        ResultSet rs = database.getStatement().executeQuery(select);
        while(rs.next()){
            Airport a = new Airport();
            a.setId(rs.getInt("id"));
            a.setCity(rs.getString("city"));
            airports.add(a);
        }

        return airports;
    }

}
