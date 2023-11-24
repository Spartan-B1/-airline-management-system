package com.example.test;

public class Employee {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String tel;
    private double salary;
    private String position;

    public Employee() {

    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setTel(String tel){
        this.tel = tel;
    }

    public String getTel(){
        return tel;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public void setSalary(double salary){
        this.salary = salary;
    }

    public Double getSalary(){
        return salary;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void print(){
        System.out.println("id: " + getId());
        System.out.println("name: " + getFirstName() + " " + getLastName());
        System.out.println("Tel: " + getTel());
        System.out.println("Email: " + getEmail());
        System.out.println("Salary: " + getSalary());
        System.out.println("Position: " + getPosition());
        System.out.println();
    }
}
