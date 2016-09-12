/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import com.googlecode.charts4j.*;
import static com.googlecode.charts4j.Color.BLACK;
import com.googlecode.charts4j.Plots;

/**
 * This class is only intended as a factory of a factory. It stores styling information, 
 * and contains only methods to generate specific charts with specific parameters, to
 * maintain the given style.
 * 
 * @author Tony Work
 */
public class ChartFactory {

    private static final Color[] pieColours = new Color[]{Color.DARKCYAN, Color.CRIMSON, 
        Color.YELLOW, Color.PURPLE, Color.BROWN, Color.GREEN};
    
    
    public static LineChart newLineChart(String title, int numXLabels, String[] xAxisLabels, int numYLabels, 
            String[] yAxisLabels, String[] plotLabells, double[] ... values){
        
        Line[] lines = new Line[values.length];
        
        for (int i = 0; i != values.length; ++i){
            Line line = Plots.newLine(Data.newData(values[i]), ChartFactory.pieColours[i], plotLabells[i]);
           
            line.setLineStyle(LineStyle.newLineStyle(3, 1, 0));
            line.addShapeMarkers(Shape.DIAMOND, ChartFactory.pieColours[i], 12);
            line.addShapeMarkers(Shape.DIAMOND, Color.WHITE, 10);
            lines[i] = line;
        }
        
        // Defining chart.
        LineChart chart = GCharts.newLineChart(lines);
        
        chart.setTitle(title, Color.WHITE, 14);
        chart.addHorizontalRangeMarker(40, 60, Color.newColor(Color.RED, 30));
        chart.addVerticalRangeMarker(70, 90, Color.newColor(Color.GREEN, 30));
        chart.setGrid(25, 25, 3, 2);

        // Defining axis info and styles
        AxisStyle axisStyle = AxisStyle.newAxisStyle(Color.WHITE, 12, AxisTextAlignment.CENTER);
        AxisLabels xAxis = AxisLabelsFactory.newAxisLabels(xAxisLabels);
        xAxis.setAxisStyle(axisStyle);
        
        AxisLabels yAxis = AxisLabelsFactory.newAxisLabels(yAxisLabels);

        // Adding axis info to chart.
        chart.addXAxisLabels(xAxis);
        chart.addYAxisLabels(yAxis);

        // Defining background and chart fills.
        chart.setBackgroundFill(Fills.newSolidFill(Color.newColor("1F1D1D")));
        LinearGradientFill fill = Fills.newLinearGradientFill(0, Color.newColor("363433"), 100);
        fill.addColorAndOffset(Color.newColor("2E2B2A"), 0);
        chart.setAreaFill(fill);
        
        return chart;
    }
    
    public static PieChart newPieChart(int[] values, String[] label, int numValues, String title){
        if (numValues < 0 || numValues > values.length) return null;
        
        int total = 0;
        
        for (int i = 0; i != numValues; ++i)
            total += values[i];
        
        Slice[] slices = new Slice[numValues];
        
        for (int i = 0; i < numValues; i++){
            double v = values[i];
            v = (v * 100) / total;
            slices[i] = Slice.newSlice((int)v, ChartFactory.pieColours[i], label[i]);
        }
        
        PieChart chart = GCharts.newPieChart(slices);
        chart.setBackgroundFill(Fills.newSolidFill(Color.LIGHTGREY));
        chart.setTitle(title, BLACK, 16);
        chart.setThreeD(true);
        
        
        
        return chart;
    }
    
}
