package Controller;


import Enum.State;
import Model.Official;
import static View.GUI_Start.actualColor;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.RingPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RectangleInsets;
import org.jfree.util.UnitType;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Keke
 */
public class officialChartDisplay {
    
    
    private Official admin;
     
    public officialChartDisplay(Official admin){
         this.admin=admin;
     }
    
    public DefaultCategoryDataset createBarDataset(){
        
        //Dataset setting
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        ArrayList<ArrayList<String>> votes = admin.getVotes();
        for (int i = 0 ; i<votes.size() ; ++i)
            dataset.setValue(Integer.parseInt(votes.get(i).get(1)), "Votes", votes.get(i).get(0));
        return dataset;
    }
    
      /** 
     * Create a JFreeChart out of a DefaultCategoryDataset for votes
     * @param dataset
     * @return 
     */
     public JFreeChart createBarChart(DefaultCategoryDataset dataset)
    {
        JFreeChart myChart; 
        myChart=ChartFactory.createBarChart("Number of votes per candidate", "Candidates", "Number of votes", createBarDataset(), PlotOrientation.VERTICAL, true, true, false);
        
        StandardChartTheme theme = (StandardChartTheme)StandardChartTheme.createJFreeTheme();
        theme.setBarPainter(new StandardBarPainter());
        theme.setRegularFont( new Font("montserrat" , Font.PLAIN , 11));
        theme.apply(myChart);
        
        CategoryPlot p=myChart.getCategoryPlot();
        p.getRangeAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        p.setBackgroundPaint(Color.white);
        p.setRangeGridlinePaint(Color.black);
        
        BarRenderer r = (BarRenderer) p.getRenderer();
        r.setSeriesPaint(0, actualColor);
        r.setMaximumBarWidth(0.1);
        r.setBaseItemLabelsVisible(true);
        r.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        r.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
        r.setDrawBarOutline(false);
        return myChart;
    }
    
    /** 
     * Create a DefaultPieDataset corresponding to all the votes for each candidates
     * @return 
     */
    public DefaultPieDataset createRingPlotDataset(){
        
        //Dataset setting
        DefaultPieDataset dataset = new DefaultPieDataset();
        ArrayList<ArrayList<String>> votes = admin.getVotes();
        int sum = 0;
        for (int i = 0 ; i<votes.size() ; ++i)
            sum+=Integer.parseInt(votes.get(i).get(1));
        for (int i = 0 ; i<votes.size() ; ++i)
            dataset.setValue(votes.get(i).get(0), Integer.parseInt(votes.get(i).get(1))*100/sum);
        return dataset;
    }
    
    /** 
     * Create a JFreeChart out of a DefaultCategoryDataset for votes
     * @param dataset
     * @return 
     */
     public JFreeChart createRingPlotChart(DefaultPieDataset dataset)
    {
        JFreeChart myChart = ChartFactory.createRingChart("Pourcentage of votes per candidate", dataset, true, true, false);
        StandardChartTheme theme = (StandardChartTheme)StandardChartTheme.createJFreeTheme();
        theme.setBarPainter(new StandardBarPainter());
        theme.setRegularFont( new Font("montserrat" , Font.PLAIN , 11));
        theme.apply(myChart);
        RingPlot p = (RingPlot) myChart.getPlot();
        p.setBackgroundPaint(Color.WHITE);
        p.setOutlineVisible(false);
        p.setShadowPaint(null);
        p.setSimpleLabels(true);
        p.setLabelGenerator(new StandardPieSectionLabelGenerator("{1}"));
        p.setSimpleLabelOffset(new RectangleInsets(UnitType.RELATIVE, 0.09, 0.09, 0.09, 0.09));
        p.setLabelBackgroundPaint(null);
        p.setLabelOutlinePaint(null);
        p.setLabelShadowPaint(null);
        p.setSectionDepth(0.33);
        p.setSectionOutlinesVisible(false);
        p.setSeparatorsVisible(false);
        p.setIgnoreNullValues(false);
        p.setIgnoreZeroValues(false);
        return myChart;
    }
    
     
       /** 
     * Create a DefaultCategoryDataset corresponding to all the votes for each candidates
     * @return 
     */
    public DefaultCategoryDataset createStackedBarDataset(){
        
        //Dataset setting
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        ArrayList<String[]> votes = admin.getVotesByStates();
        for (String[] current : votes)
        {
            int j=1;
            for (State s : State.values())
            {
                if (j<current.length)
                {
                    dataset.setValue(Integer.parseInt(current[j]), s.toString() , current[0] );
                    j++;
                }
            } 
        }

        return dataset;
    }
    
    /**
     * Creates a StackedBarChart showing the number of votes per states for each candidates
     * @param dataset
     * @return 
     */
    public JFreeChart createStackedBarChart(DefaultCategoryDataset dataset)
    {
        JFreeChart myChart; 
        myChart=ChartFactory.createStackedBarChart("Number of votes per candidate & per state", "Candidates", "Number of votes", createStackedBarDataset(), PlotOrientation.VERTICAL, true, false, false);
        
        StandardChartTheme theme = (StandardChartTheme)StandardChartTheme.createJFreeTheme();
        theme.setBarPainter(new StandardBarPainter());
        theme.setRegularFont( new Font("montserrat" , Font.PLAIN , 11));
        theme.apply(myChart);
        
        CategoryPlot p=myChart.getCategoryPlot();
        p.getRangeAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        p.setBackgroundPaint(Color.white);
        p.setRangeGridlinePaint(Color.black);

        StackedBarRenderer r = (StackedBarRenderer) p.getRenderer();
        r.setSeriesPaint(0, actualColor);
        r.setMaximumBarWidth(0.1);
        r.setBaseItemLabelsVisible(true);
        r.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        r.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());

        return myChart;
    }
     
     
     
}
