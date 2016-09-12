/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamanagement;




import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;

import org.xmldb.api.modules.XQueryService;


/**
 *
 * @author kuangwentao
 */
public class company {
    private static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc";
    public static void main(String[] args) throws Exception {
    final String driver = "org.exist.xmldb.DatabaseImpl";

        Class cl = Class.forName(driver);
        Database database = (Database) cl.newInstance();
//        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);
        Collection col=DatabaseManager.getCollection(URI+"/db/kps");
        
    String xquery= "update insert\n<mail>\n<event>21</event>\n<event_time>2015-07-02</event_time>\n<weight>1</weight>\n<volume>1</volume>\n<time>2</time>\n<priority_id>air</priority_id>\n<origin>Wellington</origin>\n<destination>Auckland</destination>\n<price>5</price>\n<cost>3</cost>\n</mail>\ninto\ndoc(\"Kps_manager.xml\")/Business_events/mail_events";    
    XQueryService service = (XQueryService) col.getService("XQueryService","1.0");
    service.setProperty("indent","yes");
    service.query(xquery);

//    }    
     
    }    

}
