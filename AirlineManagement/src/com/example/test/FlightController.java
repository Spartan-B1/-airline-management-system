package com.example.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class FlightController {

       final private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd::HH:mm:ss");

        public static void AddNewFlight(Database database, Scanner s)throws SQLException {
            System.out.println("Enter plane id(int): \n(-1 to show all planes)");
            int planID = s.nextInt();
            if (planID == -1){
                AirplaneController.PrintAllPlanes(database);
                System.out.println("Enter plane id(int): ");
                planID = s.nextInt();
            }

            Airplane plane = AirplaneController.getPlaneByID(database, planID);

            System.out.println("Enter origin airport id(int): \n(-1 to show all airports)");
            int originID = s.nextInt();
            if (originID == -1){
                AirportsController.PrintAllAirports(database);
                System.out.println("Enter origin airport id(int):");
                originID = s.nextInt();
            }

            Airport origen = AirportsController.GetAirport(database, originID);

            System.out.println("Enter destination airport id(int): \n(-1 to show all airports)");
            int destinationID = s.nextInt();
            if (destinationID == -1){
                AirportsController.PrintAllAirports(database);
                System.out.println("Enter destination airport id(int): ");
                destinationID = s.nextInt();
            }

            Airport destination = AirportsController.GetAirport(database, destinationID);

            System.out.println("Enter departure Time(yyyy-MM-dd::HH:mm:ss): ");
            String dTime = s.next();
            LocalDateTime departureTime = LocalDateTime.parse(dTime, formatter);


            System.out.println("Enter arrivalTime Time(yyyy-MM-dd::HH:mm:ss): ");
            String aTime = s.next();
            LocalDateTime arrivalTime = LocalDateTime.parse(aTime, formatter);


            Flight flight = new Flight();
            flight.setAirplane(plane);
            flight.setOrigen(origen);
            flight.setDestination(destination);
            flight.setDepartureTime(departureTime);
            flight.setArrivalTime(arrivalTime);

            ArrayList<Flight> flights = getAllFlights(database);
            int id = 0;
            if (flights.size()!=0) id = flights.size();

            flight.setId(id);

            String insert = "INSERT INTO `flight`(`id`, `airplane`, `origen`, `destination`, `departureTime`, `arrivalTime`, " +
                    "`isDelayed`, `bookedEconomy`, `bookedBusiness`, `stuff`, `passengers`) " +
                    "VALUES ('"+flight.getId()+"','"+planID+"','"+originID+"','"+destinationID+"','"+dTime+"','"+aTime+"','"+false+
                    "','0','0','<%%/>','<%%/>');";
            database.getStatement().execute(insert);
            System.out.println("Flight added successfully!!!");


        }

        public static ArrayList<Flight> getAllFlights(Database database) throws SQLException{
            ArrayList<Flight> flights = new ArrayList<>();
            String select = "SELECT * FROM `flight`;";
            ResultSet rs = database.getStatement().executeQuery(select);

            ArrayList<Integer> IDs = new ArrayList<>();
            ArrayList<Integer> planeIds = new ArrayList<>();
            ArrayList<Integer> origenIds = new ArrayList<>();
            ArrayList<Integer> destIds = new ArrayList<>();
            ArrayList<String> depTimes = new ArrayList<>();
            ArrayList<String> arrTimes = new ArrayList<>();
            ArrayList<String> dels = new ArrayList<>();
            ArrayList<Integer> bookedEconomySeats = new ArrayList<>();
            ArrayList<Integer> bookedBusinessSeats = new ArrayList<>();
            ArrayList<String> sts = new ArrayList<>();
            ArrayList<String> pass = new ArrayList<>();
            while(rs.next()){
                IDs.add(rs.getInt("id"));
                planeIds.add(rs.getInt("airplane"));
                origenIds.add(rs.getInt("origin"));
                destIds.add(rs.getInt("destination"));
                depTimes.add(rs.getString("departureTime"));
                arrTimes.add(rs.getString("arrivalTime"));
                dels.add(rs.getString("isDelayed"));
                bookedEconomySeats.add(rs.getInt("bookedEconomy"));
                bookedBusinessSeats.add(rs.getInt("bookedBusiness"));
                sts.add(rs.getString("stuff"));
                pass.add(rs.getString("passengers"));

                for (int i = 0; i <IDs.size(); i++){
                    Flight flight = new Flight();
                    flight.setId(IDs.get(i));
                    int planeID = planeIds.get(i);
                    int originID = origenIds.get(i);
                    int destID = destIds.get(i);
                    String depTime = depTimes.get(i);
                    String arrTime = arrTimes.get(i);
                    String del = dels.get(i);
                    boolean delayed = Boolean.parseBoolean(del);
                    flight.setBookedEconomy(bookedEconomySeats.get(i));
                    flight.setBookedBusiness(bookedBusinessSeats.get(i));
                    String st = sts.get(i);
                    String pas = pass.get(i);

                    Airplane plane = AirplaneController.getPlaneByID(database, planeID);
                    flight.setAirplane(plane);
                    flight.setOrigen(AirportsController.GetAirport(database, originID));
                    flight.setDestination(AirportsController.GetAirport(database, destID));
                    LocalDateTime departure = LocalDateTime.parse(depTime, formatter);
                    flight.setDepartureTime(departure);
                    LocalDateTime arrival = LocalDateTime.parse(arrTime, formatter);
                    flight.setArrivalTime(arrival);
                    if (delayed) flight.delay();



                    String[] stuffID = st.split("<%%/>");
                    Employee[] stuff = new Employee[10];
                    for (int j = 0; j<stuffID.length; j++){
                        int id = Integer.parseInt(stuffID[j]);
                        stuff[j] = EmployeesController.getEmployeeByID(database, id);
                    }
                    flight.setStuff(stuff);



                    String[] passengersID = pas.split("<%%/>");
                    int totalCapacity = plane.getEconomyCapacity()+plane.getBusinessCapacity();
                    Passenger[] passengers = new Passenger[totalCapacity];
                    for (int j=0; j< passengersID.length; j++){
                        int id = Integer.parseInt(passengersID[j]);
                        passengers[j] = PassengersController.getPassengerByID(database, id);
                    }
                    flight.setPassengers(passengers);

                    flights.add(flight);
                }

            }
            return flights;
        }



        public static void showAllFlights(Database database) throws SQLException{
            ArrayList<Flight> flights = getAllFlights(database);
            System.out.println("id\tAirplane\tOrigin\tDestination\tDeparture Time\t\tArrival Time\t\tstatus\t\tAvailable Economy \t " +
                    "Available Business\t");
            for(Flight f : flights){
                f.print();
            }

        }

        public static void delayFlight(Database database, Scanner s) throws SQLException{
            System.out.println("Enter flight id(int): \n(-1 to show all flights)");
            int id = s.nextInt();
            if (id==-1){
                showAllFlights(database);
                System.out.println("Enter flight id(int): ");
                id = s.nextInt();
            }

            String update = "UPDATE `flight` SET `isDelayed`='true' WHERE `id` = "+id+" ;";
            database.getStatement().execute(update);
            System.out.println("Flight delayed successfully!!!");

        }

        public static void bookFlight(Database database, Scanner s)throws SQLException{
            System.out.println("Enter flight id(int): \n(-1 to show all flights");
            int id = s.nextInt();
            if (id == -1){
                showAllFlights(database);
                id = s.nextInt();
            }

            Flight flight = getFlight(database, id);

            Passenger passenger;
            System.out.println("Enter passenger id(int): \n(-1 to get passenger by name");
            int passengerID = s.nextInt();
            if (passengerID == -1){
                passenger = PassengersController.getPassengerByName(database,s);
            }else{
                passenger = PassengersController.getPassengerByID(database, passengerID);
            }

            System.out.println("1. Economy seat");
            System.out.println("2. Business seat");
            int n = s.nextInt();

            System.out.println("Enter number of seats (int): ");
            int num = s.nextInt();

            if (n == 1){
                flight.setBookedEconomy(flight.getBookedEconomy()+num);
            }else{
                flight.setBookedBusiness(flight.getBookedBusiness()+num);
            }

            Passenger[] passengers = flight.getPassengers();
            for (int i = 0; i<passengers.length; i++){
                if (passengers[i] == null){
                    passengers[i] = passenger;
                    break;
                }
            }

            StringBuilder sb = new StringBuilder();
            for(Passenger p : flight.getPassengers()){
               if (p!=null) sb.append(p.getId()).append("<%%/>");
            }

            String update = "UPDATE `flight` SET `bookedEconomy`='"+flight.getBookedEconomy()+"',`bookedBusiness`='"+flight.getBookedBusiness()+"'," +
                    "`passengers`='"+sb.toString()+"' WHERE `id` = "+flight.getId()+" ;";
            database.getStatement().execute(update);
            System.out.println("Booked successfully!!!");

        }

        public static Flight getFlight(Database database, int id) throws  SQLException{
            Flight flight = new Flight();
            String select = "SELECT `id`, `airplane`, `origen`, `destination`, `departureTime`, `arrivalTime`, `isDelayed`, " +
                    "`bookedEconomy`, `bookedBusiness`, `stuff`, `passengers` FROM `flight` WHERE `id` = "+id+" ;";
            ResultSet rs = database.getStatement().executeQuery(select);
            rs.next();
            int ID = rs.getInt("id");
            int planeID = rs.getInt("airplane");
            int origenID = rs.getInt("origin");
            int destID = rs.getInt("destination");
            String depTime = rs.getString("departureTime");
            String arrTime = rs.getString("arrivalTime");
            String del = rs.getString("isDelayed");
            int bookedEconomySeats = rs.getInt("bookedEconomy");
            int bookedBusinessSeats = rs.getInt("bookedBusiness");
            String st = rs.getString("stuff");
            String pas = rs.getString("passengers");
            boolean delayed = Boolean.parseBoolean(del);

            flight.setId(ID);
            Airplane plane = AirplaneController.getPlaneByID(database, planeID);
            flight.setAirplane(plane);
            flight.setOrigen(AirportsController.GetAirport(database, origenID));
            flight.setDestination(AirportsController.GetAirport(database, destID));
            LocalDateTime departure = LocalDateTime.parse(depTime, formatter);
            flight.setDepartureTime(departure);
            LocalDateTime arrival = LocalDateTime.parse(arrTime, formatter);
            flight.setArrivalTime(arrival);
            if (delayed) flight.delay();

            flight.setBookedEconomy(bookedEconomySeats);
            flight.setBookedBusiness(bookedBusinessSeats);

            String[] stuffID = st.split("<%%/>");
            Employee[] stuff = new Employee[10];
            for (int j = 0; j<stuffID.length; j++){
                int idst = Integer.parseInt(stuffID[j]);
                stuff[j] = EmployeesController.getEmployeeByID(database, idst);
            }
            flight.setStuff(stuff);



            String[] passengersID = pas.split("<%%/>");
            int totalCapacity = plane.getEconomyCapacity()+plane.getBusinessCapacity();
            Passenger[] passengers = new Passenger[totalCapacity];
            for (int j=0; j< passengersID.length; j++){
                int idpass = Integer.parseInt(passengersID[j]);
                passengers[j] = PassengersController.getPassengerByID(database, idpass);
            }
            flight.setPassengers(passengers);

            return flight;
        }

        public static void setFlightStuff(Database database, Scanner s) throws SQLException{
            System.out.println("Enter flight id(int): \n(-1 to show all flights");
            int id = s.nextInt();
            if (id == -1){
                showAllFlights(database);
                System.out.println("Enter flight id(int)");
                id = s.nextInt();
            }

            Flight flight = getFlight(database, id);
            System.out.println("1. show all employees");
            System.out.println("2. Continue");
            int j = s.nextInt();
            if (j == 1) EmployeesController.PrintAllEmployee(database);
            System.out.println("Enter employees ids: (int)");
            Employee[] employees = new Employee[10];
            for (int i = 0; i<10; i++){
                System.out.println("id " +(i+1)+ "/10");
                int ID = s.nextInt();
                employees[i] = EmployeesController.getEmployeeByID(database,ID);
            }

            flight.setStuff(employees);

            StringBuilder bd = new StringBuilder();
            for (Employee e : flight.getStuff()){
                if (e!=null) bd.append(e.getId()).append("<%%/>");
            }

            String update = "UPDATE `flight` SET `stuff`='"+bd.toString()+"' WHERE `id` = "+flight.getId()+" ;";
            database.getStatement().execute(update);
            System.out.println("Stuff updated successfully!!");

        }

        public static void cancelFlight(Database database, Scanner s) throws SQLException{
            System.out.println("Enter flight id(int): \n(-1 to show all flights");
            int id = s.nextInt();
            if (id == -1){
                showAllFlights(database);
                System.out.println("Enter flight id(int): ");
                id = s.nextInt();
            }

            String delete = "DELETE FROM `flight` WHERE `id` = "+id+" ;";
            database.getStatement().execute(delete);
            System.out.println("Flight cancelled successfully!!!");

        }

        public static void showFlightStuff(Database database, Scanner s) throws SQLException{
            System.out.println("Enter flight id(int): ");
            int id = s.nextInt();
            if (id == -1){
                showAllFlights(database);
                System.out.println("Enter flight id(int): ");
                id = s.nextInt();
            }
            Flight f = getFlight(database, id);

            System.out.println("id\tFirst Name\tLast Name\tEmail\tTel\tPosition");
            for (Employee e : f.getStuff()){
                if (e!= null){
                    System.out.print(e.getId()+"\t\t");
                    System.out.print(e.getFirstName()+"\t");
                    System.out.print(e.getLastName()+"\t");
                    System.out.print(e.getEmail()+"\t");
                    System.out.print(e.getTel()+"\t");
                    System.out.print(e.getPosition());
                    System.out.println();
                }
            }
        }

        public static void printFlightPassenger(Database database, Scanner s) throws SQLException{
            System.out.println("Enter Flight id(int): ");
            int id = s.nextInt();
            if (id == -1){
                showAllFlights(database);
                System.out.println("Enter flight id(int): ");
                id = s.nextInt();
            }
            Flight f = getFlight(database, id);


            System.out.println("id\tFirst Name\tLast Name\tEmail\tTel");
            for (Passenger p : f.getPassengers()){
               if (p!=null){
                   System.out.print(p.getId()+"\t");
                   System.out.print(p.getFirstName()+"\t\t");
                   System.out.print(p.getLastName()+"\t\t");
                   System.out.print(p.getEmail()+"\t");
                   System.out.print(p.getTel()+"\t");
                   System.out.println();
               }
            }

        }

}
