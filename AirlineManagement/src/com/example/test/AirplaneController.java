package com.example.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class AirplaneController {
    public static void AddNewAirplane(Database database, Scanner s) throws SQLException {
        System.out.println("Enter economy capacity(int): ");
        int EconomyCapacity = s.nextInt();

        System.out.println("Enter business capacity(int): ");
        int BusinessCapacity = s.nextInt();

        System.out.println("Enter model");
        String model = s.next();

        Airplane airplane = new Airplane();
        airplane.setEconomyCapacity(EconomyCapacity);
        airplane.setBusinessCapacity(BusinessCapacity);
        airplane.setModel(model);

        int id;

        ArrayList<Airplane> planes = getAllPlanes(database);
        if (planes.size()!=0){
             id = planes.get(planes.size()-1).getId()+1;
        }else{
            id = 0;
        }
        airplane.setId(id);

        String insert = "INSERT INTO `airplane`(`id`, `EconomyCapacity`, `BusinessCapacity`, `model`) VALUES ('"+ airplane.getId()+"','"+
                airplane.getEconomyCapacity()+"','"+ airplane.getBusinessCapacity()+"','"+ airplane.getModel()+"');";
        database.getStatement().execute(insert);
        System.out.println("Airplane added successfully!");

    }

    public static void PrintAllPlanes(Database database) throws SQLException{
        System.out.println("-----------------------------------------------------------");
        for (Airplane plane : getAllPlanes(database)){
            plane.print();
        }
        System.out.println("-----------------------------------------------------------");
    }

    public static void EditAirPlane(Database database, Scanner s) throws SQLException{
        System.out.println("Enter airplane id(int): \n(-1 to show all planes)");
        int id = s.nextInt();

        if (id == -1){
            PrintAllPlanes(database);
            System.out.println("Enter id (int): ");
            id = s.nextInt();
        }

        Airplane p = getPlaneByID(database, id);

        System.out.println("Enter economy capacity (int): \n(-1 to keep old value)");
        int EconomyCapacity = s.nextInt();
        if (EconomyCapacity == -1) EconomyCapacity = p.getEconomyCapacity();

        System.out.println("Enter business capacity (int): \n(-1 to keep old value)");
        int BusinessCapacity = s.nextInt();
        if (BusinessCapacity == -1) BusinessCapacity = p.getBusinessCapacity();

        System.out.println("Enter Model: \n(-1 to keep old value)");
        String Model = s.next();
        if (Model.equals("-1")) Model = p.getModel();

        p.setEconomyCapacity(EconomyCapacity);
        p.setBusinessCapacity(BusinessCapacity);
        p.setModel(Model);

        String update = "UPDATE `airplane` SET `id`='"+p.getId()+"',`EconomyCapacity`='"+p.getEconomyCapacity()+
                "',`BusinessCapacity`='"+p.getBusinessCapacity()+"',`model`='"+p.getModel()+"' WHERE `id` = "+p.getId()+" ;";
        database.getStatement().execute(update);
        System.out.println("Airplane updated successfully!");

    }

    public static Airplane getPlaneByID(Database database, int id)throws SQLException{
        Airplane a = new Airplane();
        String get = "SELECT `id`, `EconomyCapacity`, `BusinessCapacity`, `model` " +
                "FROM `airplane` WHERE `id` = "+id+" ;";
        ResultSet rs = database.getStatement().executeQuery(get);
        rs.next();
        a.setId(rs.getInt("id"));
        a.setEconomyCapacity(rs.getInt("EconomyCapacity"));
        a.setBusinessCapacity(rs.getInt("BusinessCapacity"));
        a.setModel(rs.getString("model"));
        return a;

    }

    public static void DeleteAirplane(Database database, Scanner s) throws SQLException{
        System.out.println("Enter id(int): \n (-1 to show all planes)");
        int id = s.nextInt();
        if (id == -1){
            PrintAllPlanes(database);
            System.out.println("Enter id(int): ");
            id = s.nextInt();
        }
        String delete = "DELETE FROM `airplane` WHERE `id` = "+id+" ;";
        database.getStatement().execute(delete);
        System.out.println("Plane deleted successfully!!!");
    }

    public static ArrayList<Airplane> getAllPlanes(Database database) throws SQLException{
        ArrayList<Airplane> planes = new ArrayList<>();
        String get = "SELECT * FROM `airplane`";
        ResultSet rs = database.getStatement().executeQuery(get);
        while (rs.next()){
            Airplane a = new Airplane();
            a.setId(rs.getInt("id"));
            a.setEconomyCapacity(rs.getInt("EconomyCapacity"));
            a.setBusinessCapacity(rs.getInt("BusinessCapacity"));
            a.setModel(rs.getString("model"));
            planes.add(a);
        }
        return planes;
    }



}
