package com.example.test;


import java.sql.SQLException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws SQLException{

        Database database = new Database();
        Scanner s = new Scanner(System.in);

        int i = 0;
        do{
            System.out.println("\nWelcome to Airlines Management System");
            System.out.println("01. Add new passenger");
            System.out.println("02. Get passenger id by name");
            System.out.println("03. Print all passengers");
            System.out.println("04. Edit passenger");
            System.out.println("05. Delete passenger");
            System.out.println("06. Add new employee");
            System.out.println("07. Get employee by name");
            System.out.println("08. Print all employee");
            System.out.println("09. Edit employee");
            System.out.println("10. Fire employee");
            System.out.println("11. Add new Plane");
            System.out.println("12. Print all planes");
            System.out.println("13. Edit plane");
            System.out.println("14. Delete plane");
            System.out.println("15. Add new Airport");
            System.out.println("16. Print all Airports");
            System.out.println("17. Edit Airport");
            System.out.println("18. Delete Airport");
            System.out.println("19. Add Flight");
            System.out.println("20. Show all flights");
            System.out.println("21. Delay flight");
            System.out.println("22. Book flight");
            System.out.println("23. Set flight stuff");
            System.out.println("24. Cancel flight");
            System.out.println("25. Show flight stuff");
            System.out.println("26. Show flight passengers");
            System.out.println("27. Quit");

            i = s.nextInt();
            switch (i){
                case 1:
                    PassengersController.AddNewPassenger(database, s);
                    break;

                case 2:
                    PassengersController.findPassengerByName(database, s);
                    break;

                case 3:
                    PassengersController.printAllPassengers(database);
                    break;

                case 4:
                    PassengersController.EditPassenger(database, s);
                    break;

                case 5:
                    PassengersController.DeletePassenger(database, s);
                    break;

                case 6:
                    EmployeesController.AddNewEmployee(database, s);
                    break;

                case 7:
                    EmployeesController.FindEmployeeByName(database, s);
                    break;

                case 8:
                    EmployeesController.PrintAllEmployee(database);
                    break;

                case 9:
                    EmployeesController.EditEmployee(database, s);
                    break;

                case 10:
                    EmployeesController.DeleteEmployee(database, s);
                    break;

                case 11:
                    AirplaneController.AddNewAirplane(database, s);
                    break;

                case 12:
                    AirplaneController.PrintAllPlanes(database);
                    break;

                case 13:
                    AirplaneController.EditAirPlane(database, s);
                    break;

                case 14:
                    AirplaneController.DeleteAirplane(database, s);
                    break;

                case 15:
                    AirportsController.AddNewAirport(database, s);
                    break;

                case 16:
                    AirportsController.PrintAllAirports(database);
                    break;

                case 17:
                    AirportsController.EditAirport(database, s);
                    break;

                case 18:
                    AirportsController.DeleteAirport(database, s);
                    break;

                case 19:
                    FlightController.AddNewFlight(database, s);
                    break;

                case 20:
                    FlightController.showAllFlights(database);
                    break;

                case 21:
                    FlightController.delayFlight( database,  s);
                    break;

                case 22:
                    FlightController.bookFlight(database, s);
                    break;

                case 23:
                    FlightController.setFlightStuff(database, s);
                    break;

                case 24:
                    FlightController.cancelFlight(database, s);
                    break;

                case 25:
                    FlightController.showFlightStuff(database, s);
                    break;

                case 26:
                    FlightController.printFlightPassenger(database, s);
                    break;
            }

        }while (i != 27);
    }



}
