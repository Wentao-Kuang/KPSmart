/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package NewClass;

import domain.MailEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 *
 * @author T
 */
public class M {
    private ArrayList<MailEvent> mailEvents;
    public void generateXML(){
        this.mailEvents = new ArrayList<MailEvent>();
       /* for (int i = 0; i != this.mailEvents.size(); ++i){
            System.out.println(this.mailEvents.get(i).toString());
        }*/
         Random r = new Random(300);
        double rangeMin = 0;
        double rangeMax = 600;
        
        GregorianCalendar minDate = new GregorianCalendar(2015, 0, 1);
        GregorianCalendar maxDate = new GregorianCalendar(2015, 4, 25);
        
        //minDate.
        for (int i = 0; i != 20; ++i){
            MailEvent m = new MailEvent();
            m.setCost("" + (rangeMin + (rangeMax - rangeMin) * r.nextDouble()));
            m.setPrice("" + (rangeMin + (rangeMax - rangeMin) * r.nextDouble()));
            m.setEvent(i + "");
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTimeInMillis(minDate.getTimeInMillis() + (long)((maxDate.getTimeInMillis() - minDate.getTimeInMillis()) * r.nextDouble()));
            
            m.setEvent_time("" + gc.get(Calendar.YEAR) + "-" + (gc.get(Calendar.MONTH) + 1) + "-" + gc.get(Calendar.DATE));
            m.setWeight("" + (r.nextDouble() * 10));
            m.setVolume("" + (r.nextDouble() * 10));
            m.setDuration("" + r.nextInt(10));
            m.setPriority_id("Air");
            m.setOrigin("pp");
            m.setDestination("Wang");
            mailEvents.add(m);
            
            
        }
        
        for (int i = 0; i != mailEvents.size(); ++i){
            MailEvent m = mailEvents.get(i);
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTimeInMillis(m.getEvent_time());
            String date = gc.get(Calendar.YEAR) + "-" + (gc.get(Calendar.MONTH) + 1) + 
                    "-" + gc.get(Calendar.DATE);
            
            
            System.out.println("<mail>");
            
            System.out.println("<event>" + m.getEvent() + "</event>");
            System.out.println("<event_time>" + date + "</event_time>");
            System.out.println("<weight>" + m.getWeight() + "</weight>");
            System.out.println("<volume>" + m.getVolume() + "</volume>");
            System.out.println("<time>" + m.getDuration() + "</time>");
            System.out.println("<priority_id>" + m.getPriority_id() + "</priority_id>");
            System.out.println("<origin>" + m.getOrigin() + "</origin>");
            System.out.println("<destination>" + m.getDestination() + "</destination>");
            System.out.println("<price>" + m.getPrice() + "</price>");
            System.out.println("<cost>" + m.getCost() + "</cost>");
            System.out.println("</mail>");
        }
    }
}
