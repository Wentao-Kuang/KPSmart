/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import NewClass.M;
import NewClass.Query;
import com.googlecode.charts4j.*;
import domain.UpdateCustomerPriceEvent;
import domain.UpdateTransportPriceEvent;
import domain.DiscontinueEvent;
import domain.Event;
import domain.MailEvent;
import domain.Route;
import java.awt.BorderLayout;
import java.awt.Panel;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Tony Work
 */
public class ReviewPanel extends javax.swing.JPanel {

    private ArrayList<MailEvent> mailEvents;
    private ArrayList<UpdateCustomerPriceEvent> updateCustomerPriceEvent;
    private ArrayList<Event> events;
    private LineChart revenueChart;
    private LineChart eventsChart;
    private double totalIncome;
    private double totalExpense;
    String months[] = {
      "Jan", "Feb", "Mar", "Apr",
      "May", "Jun", "Jul", "Aug",
      "Sep", "Oct", "Nov", "Dec"};
    
    /**
     * Creates new form ReviewPanel
     */
    JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
    
    public ReviewPanel() throws IOException {
        this.revenueChart = null;
        this.eventsChart = null;
        initComponents();
        buildAllEventData();
        countEvents();
        amountOfMail();
        jSplitPane1.setDividerLocation(0.5);
    }
    
    public void createEventsGraph(){
      
        // double minMoney = Double.MAX_VALUE;
        
        GregorianCalendar minDate = new GregorianCalendar();
        GregorianCalendar maxDate = new GregorianCalendar();
        maxDate.setTimeInMillis(0); // The start of time???
        
        for (int i = 0; i != this.events.size(); ++i){
            Event m = this.events.get(i);    
            GregorianCalendar mDate = new GregorianCalendar();
            mDate.setTimeInMillis(m.getEvent_time());
            if (mDate.compareTo(minDate) < 0) minDate = mDate;
            if (mDate.compareTo(maxDate) > 0) maxDate = mDate;
            
        }
        
        GregorianCalendar[] xDates = buildDateXAxisLabels(minDate, maxDate);
        String[] xLabels = new String[xDates.length];
        for (int i = 0; i != xLabels.length; i++){
            xLabels[i] = this.months[(xDates[i].get(Calendar.MONTH))];
        }
        
        
        
        double[] countValue = buildMonthlyValuesEventGraph(xDates);
        
        double maxCount = Double.MIN_VALUE;
        for (int i = 0; i != countValue.length; ++i){
            double val = countValue[i];
            if (val > maxCount) maxCount = val;
            
        }
        
        String[] yLabels = buildMoneyYAxisLabels(0, maxCount);
        maxCount = Double.parseDouble(yLabels[yLabels.length - 1]);
        countValue = ratioValuesForGraph(countValue, maxCount);
        
        
        double[] line1 = countValue;
        String[] xAxisLabels = xLabels;//buildDateXAxisLabels(minDate, maxDate);
        String[] yAxisLabels = yLabels;// new String[]{"0", "20", "40", "60", "80", "100", "120"};
        String[] plotLabels = new String[]{"Income", "Expenses"};
    
        int numXLabels = xLabels.length;
        int numYLabels = yLabels.length;
        
        LineChart lineChart = ChartFactory.newLineChart("Revenue and Expense", numXLabels, 
                xAxisLabels, numYLabels, yAxisLabels, plotLabels, line1);
        this.eventsChart = lineChart;
        
        setGraphLayout(lineChart, jLabel9);

   
    }   
    
    public void createIncomeExpenseChart(){
        
        calculateTotals();
        // double minMoney = Double.MAX_VALUE;
        
        GregorianCalendar minDate = new GregorianCalendar();
        GregorianCalendar maxDate = new GregorianCalendar();
        maxDate.setTimeInMillis(0); // The start of time???
        
        for (int i = 0; i != this.mailEvents.size(); ++i){
            MailEvent m = this.mailEvents.get(i);    
            GregorianCalendar mDate = new GregorianCalendar();
            mDate.setTimeInMillis(m.getEvent_time());
            if (mDate.compareTo(minDate) < 0) minDate = mDate;
            if (mDate.compareTo(maxDate) > 0) maxDate = mDate;
            
        }
        
        GregorianCalendar[] xDates = buildDateXAxisLabels(minDate, maxDate);
        String[] xLabels = new String[xDates.length];
        for (int i = 0; i != xLabels.length; i++){
            xLabels[i] = this.months[(xDates[i].get(Calendar.MONTH))];
        }
        
        
        
        double[] incomeValues = buildMonthlyValues(xDates, true);
        double[] expenseValues = buildMonthlyValues(xDates, false);
        double maxMoney = Double.MIN_VALUE;
        for (int i = 0; i != incomeValues.length; ++i){
            double val = incomeValues[i];
            if (val > maxMoney) maxMoney = val;
            val = expenseValues[i];
            if (val > maxMoney) maxMoney = val;
            
        }
        String[] yLabels = buildMoneyYAxisLabels(0, maxMoney);
        maxMoney = Double.parseDouble(yLabels[yLabels.length - 1]);
        incomeValues = ratioValuesForGraph(incomeValues, maxMoney);
        expenseValues = ratioValuesForGraph(expenseValues, maxMoney);
        
        
        double[] line1 = incomeValues;
        double[] line2 = expenseValues;
        String[] xAxisLabels = xLabels;//buildDateXAxisLabels(minDate, maxDate);
        String[] yAxisLabels = yLabels;// new String[]{"0", "20", "40", "60", "80", "100", "120"};
        String[] plotLabels = new String[]{"Income", "Expenses"};
    
        int numXLabels = xLabels.length;
        int numYLabels = yLabels.length;
        
        LineChart lineChart = ChartFactory.newLineChart("Revenue and Expense", numXLabels, 
                xAxisLabels, numYLabels, yAxisLabels, plotLabels, line1, line2);
        this.revenueChart = lineChart;
        
        setGraphLayout(lineChart, jLabel7);

   
    }
    
    /**
     * This method assumes that there will be a maximum of 6 months visible.
     * 
     * @param startDate
     * @param numMonths
     * @param graphMinMoney
     * @param graphMaxMoney
     * @return 
     */
    private double[] buildMonthlyValues(GregorianCalendar[] dates, boolean isIncome){

        double[] vals = new double[dates.length];
        for (int i = 0; i != this.mailEvents.size(); ++i){
            MailEvent m = this.mailEvents.get(i);
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTimeInMillis(m.getEvent_time());
            for (int j = 0; j != dates.length; ++j){
                if (gc.get(Calendar.YEAR) == dates[j].get(Calendar.YEAR)){
                    if (gc.get(Calendar.MONTH) == dates[j].get(Calendar.MONTH)){
                        vals[j] = isIncome ? vals[j] + m.getPrice() : vals[j] + m.getCost();
                    }
                }
            }
        }
        System.out.println("Values");
        for (int i = 0; i != vals.length; i++){
            System.out.println(vals[i]);
        }
        
        return vals;
    }
    
      /**
     * This method assumes that there will be a maximum of 6 months visible.
     * 
     * @param startDate
     * @param numMonths
     * @param graphMinMoney
     * @param graphMaxMoney
     * @return 
     */
    private double[] buildMonthlyValuesEventGraph(GregorianCalendar[] dates){

        double[] vals = new double[dates.length];
        for (int i = 0; i != this.mailEvents.size(); ++i){
            MailEvent m = this.mailEvents.get(i);
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTimeInMillis(m.getEvent_time());
            for (int j = 0; j != dates.length; ++j){
                if (gc.get(Calendar.YEAR) == dates[j].get(Calendar.YEAR)){
                    if (gc.get(Calendar.MONTH) == dates[j].get(Calendar.MONTH)){
                        vals[j] = vals[j] + 1;
                    }
                }
            }
        }
        System.out.println("Values");
        for (int i = 0; i != vals.length; i++){
            System.out.println(vals[i]);
        }
        
        return vals;
    }
    
    
    
    
    private double[] ratioValuesForGraph(double[] values, double maxMoney){
        double[] vals = new double[values.length];
        for (int i = 0; i != values.length; ++i){
            vals[i] = (values[i] / maxMoney) * 100;
            if (vals[i] > 100) vals[i] = 100;
            
        }
        return vals;
    }
    
    private GregorianCalendar[] buildDateXAxisLabels(GregorianCalendar startDate, GregorianCalendar lastDate){
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), 1);
        
        int numMonths = 0;
        while (gc.compareTo(lastDate) <= 0) {
            ++numMonths;
            gc.add(Calendar.MONTH, 1);
        }

        gc.set(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), 1);
        GregorianCalendar[] xLabels = new GregorianCalendar[numMonths];
        for (int i = 0; i != numMonths; ++i) {
            xLabels[i] = new GregorianCalendar(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DATE));
            gc.add(Calendar.MONTH, 1);
        }
        
        return xLabels;
    }
    
    private String[] buildMoneyYAxisLabels(double minMoney, double maxMoney){
        double diff = maxMoney - minMoney;
        double increments = diff / 10.0;
        
        double currentTick = 0;
        
        String[] yLabels = new String[12];
        double val = minMoney - increments;
        //yLabels[0] = "" + (int)(val);
        
        while (currentTick < 11){
            val = minMoney + (increments * currentTick);
            yLabels[(int)currentTick] = "" + (int)(val);
            ++currentTick;
        }
        val = minMoney + (increments * currentTick);
        yLabels[(int)currentTick] = "" + (int)(val);
        return yLabels;
    }
    
    private void buildMailEventData(){
        this.mailEvents = new ArrayList<MailEvent>();
        Query q = new Query();
        this.mailEvents = q.maillist();
        
        
        
    }
    private void buildUpdateCustomerEventData(){
        this.updateCustomerPriceEvent = new ArrayList<UpdateCustomerPriceEvent>();
        Query q =new Query();
        this.updateCustomerPriceEvent = q.updateCustomerPriceEventList();
        
        
    }
    
    private void buildAllEventData(){
        this.events = new ArrayList<Event>();
        
        this.buildMailEventData();
        calculateTotals();
        if (this.revenueChart == null)
            createIncomeExpenseChart(); 
       
    }
    
    
    private void countEvents(){
        int total = 0;
        int m = 0;
        int route = 0;
        int dcr = 0;
        int pc = 0;
        
        for (int i = 0; i != this.events.size(); ++i){
            if (this.events.get(i) instanceof MailEvent) ++m;
            if (this.events.get(i) instanceof UpdateTransportPriceEvent) ++route;
            if (this.events.get(i) instanceof DiscontinueEvent) ++dcr;
            if (this.events.get(i) instanceof UpdateCustomerPriceEvent) ++pc;
        }
        System.out.println("Mail: " + m + "\tRoute: " + route + "\nDiscontinedRouteEvent: " 
                + dcr + "\tCustomerPriceChangeEvent: " + pc + "\nTotal: " + total);
    }
    
    
    private void calculateTotals(){
        for (int i = 0; i != this.mailEvents.size(); ++i){
            MailEvent m = this.mailEvents.get(i);
            this.totalIncome = m.getPrice();
            jLabel22.setText("$" + this.totalIncome);
            this.totalExpense = m.getCost();
            jLabel23.setText("$" + this.totalExpense);
            jLabel24.setText("$" + (this.totalIncome - this.totalExpense));
        }        
    }
    
    private void amountOfMail(){
        ArrayList<MailEvent> mails= this.mailEvents;
        DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
        for(int i=0; i!=mails.size();++i){
            double totalvolume =0;
            double totalweight=0;
            int number=0;
            MailEvent m=new MailEvent();
            for(int j=0;j<=mails.size();j++){
                if(m.getOrigin().equals(mails.get(i).getOrigin())
                        &&m.getDestination().equals(mails.get(i).getDestination())){
                    totalvolume=totalvolume + (Double)mails.get(i).getVolume();
                    totalweight=totalweight + (Double)mails.get(i).getWeight();
                    number++; 
                }
            Object[] data = new Object[5];
            data[0] = m.getOrigin();
            data[1] = m.getDestination();
            data[2] = totalvolume;
            data[3] = totalweight;
            data[4] = number;
            model.addRow(data); 
                
            }
        
    }
        
        
    }

    
    private void setGraphLayout(GChart graph, JLabel jLabel){
        double width = jLabel.getWidth();
        double height = jLabel.getHeight();
        
        
        if (width <= 0 || height <= 0) return;
        
        
        //height *= 0.75;
        if (width > 1000) width = 1000;
        if (height > 1000) height = 1000;
        System.out.println("width: " + width + "height: " + height);
        if (width * height > 300000){
            height = 300000 / width;
        }
        graph.setSize((int)width, (int)height);
        
        try{
            ImageIcon im = new ImageIcon(ImageIO.read(new URL(graph.toURLString())));
            jLabel.setIcon(im);
        } catch(Exception e){}
        /*
        double space = jLabel.getWidth() - width;
        double marginLeft = space / 2;
        
        space = jLabel.getHeight() - height;
        double marginTop = space / 2;
        
        graph.setMargins((int)marginLeft, 0, (int)marginTop, 0);
        LineChart lc = (LineChart)graph;*/
        
        /*
        Map<String, String> map = lc.getParameters();
        String[] lines = map.get("chdl").split("\\|");
        for (int i = 0; i != lines.length; ++i){
            System.out.println(lines[i]);
        }*/
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form. WARNING:
     * Do NOT modify this code. The content of this method is always regenerated by the
     * Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel9 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton8 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel10.setText("Route Statistics");

        jLabel6.setText("Most used route");

        jLabel8.setText("Least used Route");
        jLabel8.setToolTipText("");

        jLabel3.setText("Number of routes:");

        jLabel4.setText("Discontinued Routes:");

        jLabel2.setText("Number of critical Routes");

        jLabel11.setText("Most Changed Route:");

        jLabel12.setText("From : ");

        jLabel13.setText("From: Eketahuna");

        jLabel14.setText("From: Eketahuna");

        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel25.setText("Critical Routes");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Origin", "Destination", "Carrier", "Cost"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton8.setText("Discontinue");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGap(0, 220, Short.MAX_VALUE)
                        .addComponent(jButton8)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8)
                .addContainerGap())
        );

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel14))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13))
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel10)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel14))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Number of events:");

        jLabel5.setText("Number of price changes:");

        jLabel15.setText("The Latest Event was a Customer Price Change");

        jLabel16.setText("Number of cost changes");

        jLabel17.setText("Most used carrier:");

        jLabel18.setText("Most common priority:");

        jLabel19.setText("Total revenue:");

        jLabel20.setText("Total Expense:");

        jLabel21.setText("Profit:");

        jLabel22.setText("jLabel22");

        jLabel23.setText("jLabel23");

        jLabel24.setText("jLabel24");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, Short.MAX_VALUE)
                                .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel24))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18)
                .addGap(85, 85, 85))
        );

        jSplitPane1.setDividerLocation(200);
        jSplitPane1.setResizeWeight(0.5);
        jSplitPane1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jSplitPane1ComponentResized(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel7.setBackground(new java.awt.Color(51, 0, 255));
        jLabel7.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jLabel7ComponentResized(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane1.setLeftComponent(jPanel2);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "orgin", "destination", "total volume", "total weight", "numbers of mails"
            }
        ));
        jScrollPane3.setViewportView(jTable3);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 578, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(74, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
        );

        jSplitPane1.setRightComponent(jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jSplitPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSplitPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel7ComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jLabel7ComponentResized
        
    }//GEN-LAST:event_jLabel7ComponentResized

    private void jSplitPane1ComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jSplitPane1ComponentResized

        jSplitPane1.setDividerLocation(0.5);
        jSplitPane1.setResizeWeight(0.5);
        if (this.revenueChart != null) this.setGraphLayout(this.revenueChart, jLabel7);
        if (this.eventsChart != null) this.setGraphLayout(this.eventsChart, jLabel9);
    }//GEN-LAST:event_jSplitPane1ComponentResized


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    // End of variables declaration//GEN-END:variables
}
