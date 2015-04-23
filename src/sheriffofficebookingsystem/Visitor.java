package sheriffofficebookingsystem;


import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author John
 */
public class Visitor {
    String firstName;
    String lastName;
    Date visitDate = new Date();
    int inmateVisited;
    int ssn;
    
    public Visitor(String first, String last, Date date, int inmate, int social){
        this.firstName = first;
        this.lastName = last;
        this.visitDate = date;
        this.inmateVisited = inmate;
        this.ssn = social;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public int getSSN(){
        return ssn;
    }
    public Date getVisitDate(){
        return visitDate;
    }
    public int getInmateVisited(){
        return inmateVisited;
    }
}
