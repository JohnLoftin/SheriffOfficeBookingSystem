/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sheriffofficebookingsystem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Inmate implements Comparable<Inmate>{
    
    String firstName, lastName, ethnicity, status, block;
    Date dateArrested;
    Date dateReleased;
    List<Date> courtDates = new ArrayList<>();
    boolean isBooked, isReleased;
    String height, weight;
    int id;
    
    public Inmate(String first, String last, String inmateEthnicity, String inmateStatus, 
            String cellBlock, String inmateHeight, String inmateWeight, int inmateID){
        this.firstName = first;
        this.lastName = last;
        this.height = inmateHeight;
        this.weight = inmateWeight;
        this.ethnicity = inmateEthnicity;
        this.status = inmateStatus;
        this.block = cellBlock;
        this.id = inmateID;
    }
    public void isBooked(boolean booked){
        this.isBooked = booked;
        this.dateArrested = new Date();
    }
    public void isReleased(boolean released){
        this.isReleased = released;
        this.dateReleased = new Date();
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getEthnicity(){
        return ethnicity;
    }
    public String getStatus(){
        return status;
    }
    public String getBlock(){
        return block;
    }
    public String getHeight(){
        return height;
    }
    public String getWeight(){
        return weight;
    }
    public int getID(){
        return id;
    }
    public List getCourtDates(){
        return courtDates;
    }
    public void setStatus(String inmateStatus){
        this.status = inmateStatus;
    }
    public void setBlock(String cellBlock){
        this.block = cellBlock;
    }
    public void setCourtDates(Date date){
        courtDates.add(date);
    }

    @Override
    public int compareTo(Inmate o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
