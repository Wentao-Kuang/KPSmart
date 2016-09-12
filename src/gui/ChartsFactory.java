package gui;

import com.googlecode.charts4j.AxisLabels;
import com.googlecode.charts4j.AxisLabelsFactory;
import com.googlecode.charts4j.AxisStyle;
import com.googlecode.charts4j.AxisTextAlignment;
import com.googlecode.charts4j.BarChart;
import com.googlecode.charts4j.BarChartPlot;
import com.googlecode.charts4j.Color;
import com.googlecode.charts4j.Data;
import com.googlecode.charts4j.DataUtil;
import com.googlecode.charts4j.Fills;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.LineChart;
import com.googlecode.charts4j.LineStyle;
import com.googlecode.charts4j.LinearGradientFill;
import com.googlecode.charts4j.Plot;
import com.googlecode.charts4j.Plots;
import com.googlecode.charts4j.Line;
import com.googlecode.charts4j.Shape;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
//import javax.sound.sampled.Line;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import static com.googlecode.charts4j.Color.*;
import com.googlecode.charts4j.GChart;
import com.googlecode.charts4j.PieChart;
import com.googlecode.charts4j.Slice;
import static com.googlecode.charts4j.UrlUtil.normalize;
import java.util.Arrays;
import java.util.logging.Logger;



/**
 *
 * @author Lite
 */
public class ChartsFactory {
    
    
   
	
	public static GChart example1(){
		BarChartPlot ep1 = Plots.newBarChartPlot(Data.newData(60,60,60,60), BLUEVIOLET, "Minimal");
		BarChartPlot ep3 = Plots.newBarChartPlot(Data.newData(25, 43, 12, 30), LIMEGREEN, "Consumed");
		BarChartPlot savedPerShutting = Plots.newBarChartPlot(Data.newData(1, 4, 5, 6), GOLD, "Saved Energy per shutting");
		
		BarChart chart = GCharts.newBarChart(ep1,ep3,savedPerShutting);
		
		AxisStyle axisStyle = AxisStyle.newAxisStyle(BLACK, 13, AxisTextAlignment.CENTER);
		AxisLabels energy = AxisLabelsFactory.newAxisLabels("KWH", 50.0);
		energy.setAxisStyle(axisStyle);
		AxisLabels policies = AxisLabelsFactory.newAxisLabels("Policies");
		policies.setAxisStyle(axisStyle);
		AxisLabels savedPerShuttingLabel = AxisLabelsFactory.newAxisLabels("savedPerShutting", 50.0);
		savedPerShuttingLabel.setAxisStyle(axisStyle);
		
		return chart;
		
	}
	
	public static GChart LineChart(){
		final double[] revenues = new double[30];
		final double[] expenses = new double[30];
		for(int i = 0;i< revenues.length;i++){
			revenues[i]=2*i*Math.sqrt(i*i/20);
			expenses[i]=3*i;
		}
			
	Line line1 = Plots.newLine(Data.newData(revenues), SKYBLUE /*Color.newColor("CA3D05")*/, "Revenue");
        line1.setLineStyle(LineStyle.newLineStyle(3, 1, 0));
        line1.addShapeMarkers(Shape.DIAMOND, BLUE, 12);
        line1.addShapeMarkers(Shape.DIAMOND, Color.WHITE, 8);
        Line line2 = Plots.newLine(Data.newData(expenses), LIGHTPINK, "Expenses");
        line2.setLineStyle(LineStyle.newLineStyle(3, 1, 0));
        line2.addShapeMarkers(Shape.DIAMOND, RED, 12);
        line2.addShapeMarkers(Shape.DIAMOND, Color.WHITE, 8);
		
	LineChart chart = GCharts.newLineChart(line1, line2);
        chart.setSize(700, 200);
        chart.setTitle("Values", BLACK, 14);
        //chart.setTransparency(70);
        chart.setBackgroundFill(Fills.newSolidFill(Color.newColor("F0F0F0")));
        //chart.addHorizontalRangeMarker(40, 60, Color.newColor(RED, 30));
        //chart.addVerticalRangeMarker(70, 90, Color.newColor(GREEN, 30));
        chart.setGrid(25, 25, 3, 2);
		       
        return chart;
	}
        
    public static GChart PieChart() {
        // EXAMPLE CODE START
        Slice s1 = Slice.newSlice(90, LIGHTGREEN, "National");
        Slice s2 = Slice.newSlice(10, LIGHTCYAN, "International");

        PieChart chart = GCharts.newPieChart(s1, s2);
        chart.setTitle("", BLACK, 16);
        chart.setSize(500, 200);
        String url = chart.toURLString();
        chart.setBackgroundFill(Fills.newSolidFill(Color.newColor("F0F0F0")));
        // EXAMPLE CODE END. Use this url string in your web or
        // Internet application.
        //Logger.global.info(url);
        return chart;
   }

    
}
