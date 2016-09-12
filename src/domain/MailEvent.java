/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author Kane
 */
public class MailEvent extends Event{

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public double getWeight() {
        return Double.parseDouble(weight);
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public double getVolume() {
        return Double.parseDouble(volume);
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }


    public String getPriority_id() {
        return priority_id;
    }

    public void setPriority_id(String priority_id) {
        this.priority_id = priority_id;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getPrice() {
        return Double.parseDouble(price);
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public double getCost() {
        return Double.parseDouble(cost);
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public void setDuration(String time){
        this.time = time;
    }
    
    public int getDuration(){
        return Integer.parseInt(this.time);
    }
    
    @Override
    public String toString(){
        String s = this.event + ", " + origin + " : " + destination + " : " + priority_id + 
                "\tCost: " + cost + "\tprice: " + price;
        return s;
    }
    
private String event;
private String weight;
private String volume;
private String time;
private String priority_id;
private String origin;
private String destination;
private String price;
private String cost;

}
