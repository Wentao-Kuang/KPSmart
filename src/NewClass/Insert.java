package NewClass;

import domain.DiscontinueEvent;
import domain.MailEvent;
import domain.TransportCompany;
import domain.UpdateCustomerPriceEvent;
import domain.UpdateTransportPriceEvent;
import java.util.ArrayList;
import java.util.List;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XQueryService;

public class Insert {

    
    private final static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc";
    private final static String driver = "org.exist.xmldb.DatabaseImpl";

    public void bb(String query) throws Exception {
        System.out.println("reading query: " + query);
        Class cl = Class.forName(driver);
        String[] Value2 = null;
        Database database = (Database) cl.newInstance();
        DatabaseManager.registerDatabase(database);
        Collection col = DatabaseManager.getCollection(URI + "/db/kps");
        XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
        service.setProperty("indent", "yes");
        service.query(query);
    }
    
    public void addmail(MailEvent mailEvent) throws Exception{    
        String xquery= "update insert\n<mail>\n<event>"+mailEvent.getEvent()+"</event>\n<event_time>"+mailEvent.getTimeAsDate()+"</event_time>\n<weight>"+mailEvent.getWeight()+"</weight>\n<volume>"+mailEvent.getVolume()+"</volume>\n<time>"+mailEvent.getDuration()+"</time>\n<priority_id>"+mailEvent.getPriority_id()+"</priority_id>\n<origin>"+mailEvent.getOrigin()+"</origin>\n<destination>"+mailEvent.getDestination()+"</destination>\n<price>"+mailEvent.getPrice()+"</price>\n<cost>"+mailEvent.getCost()+"</cost>\n</mail>\ninto\ndoc(\"Kps_manager.xml\")/Business_events/mail_events";            
        this.bb(xquery);
    }
    
    public void addDiscontinueRoute(DiscontinueEvent discontinueEvent) throws Exception{    
        String xquery= "update insert\n<dicontinue>\n<event>"+discontinueEvent.getEvent()+"</event>\n<event_time>"+discontinueEvent.getTimeAsDate()+"</event_time>\n<priority_id>"+discontinueEvent.getPriority_id()+"</priority_id>\n<origin>"+discontinueEvent.getOrigin()+"</origin>\n<destination>"+discontinueEvent.getDestination()+"</destination>\n<company>"+discontinueEvent.getCompany()+"</company>\n</dicontinue>\ninto\ndoc(\"Kps_manager.xml\")/Business_events/DiscountinueRoute_events";            
        this.bb(xquery);
    }
    
    public void updateCustomerPrice(UpdateCustomerPriceEvent ucpe) throws Exception{    
        String xquery= "update insert\n<price>\n<event>"+ucpe.getEvent()+"</event>\n<event_time>"+ucpe.getTimeAsDate()+"</event_time>\n<priority_id>"+ucpe.getPriority_id()+"</priority_id>\n<origin>"+ucpe.getOrigin()+"</origin>\n<destination>"+ucpe.getDestination()+"</destination>\n<company>"+ucpe.getCompany()+"</company>\n<customer_cost_pergram>"+ucpe.getCustomer_cost_pergram()+"</customer_cost_pergram>\n<customer_cost_percc>"+ucpe.getCustomer_cost_percc()+"</customer_cost_percc>\n</price>\ninto\ndoc(\"Kps_manager.xml\")/Business_events/ChangeCustomerPrice_events";            
        this.bb(xquery);
    }
    
    public void updateTransportPrice(UpdateTransportPriceEvent utpe) throws Exception{    
        String xquery= "update insert\n<price>\n<event>"+utpe.getEvent()+"</event>\n<event_time>"+utpe.getTimeAsDate()+"</event_time>\n<priority_id>"+utpe.getPriority_id()+"</priority_id>\n<origin>"+utpe.getOrigin()+"</origin>\n<destination>"+utpe.getDestination()+"</destination>\n<company>"+utpe.getCompany()+"</company>\n<company_cost_pergram>"+utpe.getCompany_cost_percc()+"</company_cost_pergram>\n<company_cost_percc>"+utpe.getCompany_cost_percc()+"</company_cost_percc>\n<duration>"+utpe.getDuration()+"</duration>\n</price>\ninto\ndoc(\"Kps_manager.xml\")/Business_events/ChangeTransportPrice";            
        this.bb(xquery);
    }    
    
    public void updateCarrier(TransportCompany Tc) throws Exception{    
        String xquery= "update insert\n<company id='"+Tc.getId()+"'>\n<name>"+Tc.getName()+"</name>\n</company>\ninto\ndoc(\"Kps_manager.xml\")/Business_events/companies";            
        this.bb(xquery);
    }    
    
    public void deleteCarrier(TransportCompany Tc) throws Exception{
        String xquery="update delete fn:doc(\"Kps_manager.xml\")/Business_events/companies/company[@id='"+Tc.getId()+"' and name='"+Tc.getName()+"']";
        this.bb(xquery);
    }
    
    public static void main(String[] args) throws Exception {
        Insert i = new Insert();
        TransportCompany d= new TransportCompany();
        d.setId("company0001");
        d.setName("Air NewZealand");
        i.deleteCarrier(d);
                
    }
    
    
    
}
