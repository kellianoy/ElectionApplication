/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Enum.State;
import Model.Candidate;
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
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author rebec
 */
public class candidateChartDisplay {
    
    private Candidate admin ; 
    
    public candidateChartDisplay(Candidate admin)
    {
        this.admin = admin ; 
    }
    
    /** 
     * Create a DefaultCategoryDataset corresponding to all the votes for each candidates
     * @param nameCompare
     * @param emailCompare
     * @return 
     */
    public DefaultCategoryDataset createStackedBarDataset(String nameCompare, String emailCompare){
        //Dataset setting
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String[][] votes = admin.getVotesByStates(emailCompare);
        for (int i = 0 ; i <2 ; i++)
        {
            String name = new String(); 
            if(i == 0 )
                name = admin.getFirstName() + " " + admin.getLastName() ; 
            else if(i == 1)
                name = nameCompare ; 
            int j=0;
            for (State s : State.values())
            {
                if (j<votes[i].length)
                {
                    dataset.setValue(Integer.parseInt(votes[i][j]), s.toString() , name );
                    j++;
                }
            } 
        }
        return dataset;
    }
    
     /**
     * Creates a StackedBarChart showing the number of votes per states for each candidates
     * @param dataset
     * @param nameCompare
     * @param emailCompare
     * @return 
     */
    public JFreeChart createVotesStackedBarChart(DefaultCategoryDataset dataset, String nameCompare, String emailCompare)
    {
        JFreeChart myChart; 
        myChart=ChartFactory.createStackedBarChart("Number of votes per candidate & per state", "Candidates", "Number of votes", createStackedBarDataset(nameCompare, emailCompare), PlotOrientation.VERTICAL, true, false, false);
        
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
    
       /** 
     * Create a DefaultCategoryDataset corresponding to all the votes for each candidates
     * @return 
     */
    public DefaultCategoryDataset createAllStackedBarDataset(){
        
        //Dataset setting
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        ArrayList<String[]> votes = admin.getAllVotesByStates();
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
    public JFreeChart createAllStackedBarChart(DefaultCategoryDataset dataset)
    {
        JFreeChart myChart; 
        myChart=ChartFactory.createStackedBarChart("Number of votes per candidate & per state", "Candidates", "Number of votes", createAllStackedBarDataset(), PlotOrientation.VERTICAL, true, false, false);
        
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
