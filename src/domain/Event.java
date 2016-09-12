/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

import java.util.GregorianCalendar;

/**
 *
 * @author T
 */
public class Event {
    
    private String event;
    private String event_time;
    private String timeAsDate;
    private String eventType;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event_number) {
        this.event = event_number;
    }

    public long getEvent_time() {
        return Long.parseLong(event_time);
    }

    public String getTimeAsDate(){
        return this.timeAsDate;
    }
    
    public void setEvent_time(String time){
        this.timeAsDate = time;
        String[] bits = time.split("-");
        int year = Integer.parseInt(bits[0]);
        int month = Integer.parseInt(bits[1]) - 1;
        int day = Integer.parseInt(bits[2]);
        
        GregorianCalendar gc = new GregorianCalendar(year, month, day);
        this.event_time = gc.getTimeInMillis() + "";
        // this.event_time = time;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    
    
}
