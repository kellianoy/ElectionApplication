/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Enum.*;
import static GUI.GUI_Start.*;
import User.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.*;
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



/**
 * GUI used by the officials.
 * @author Keke
 */
public class GUI_Official extends javax.swing.JFrame {

    private Official admin;
    private CardLayout cards;
    private CardLayout cardsChart;
    
    private DefaultTableModel voterTableModel;
    private DefaultTableModel candidateTableModel;
    
    private ChartPanel mainMenuChartPanel;
    private JFreeChart mainMenuChart;
    private ChartPanel analyzeChartPanel;
    private JFreeChart analyzeChart;
    private JFreeChart ringChart;
    private ChartPanel ringChartPanel;
    
    private FileManager f;
    
    public GUI_Official(Official admin) {
        
        this.admin = admin;
        
        //Chart creation
        updateCharts();
        
        initComponents();
        setLocationRelativeTo(null);
        setVisible(true);
        
        //Set the color of the background to the actual color
        leftPanel.setBackground(actualColor);
       
        
        //Initialization of combo boxes
       
       //Setting State values in the combo boxes
        jComboBox1.addItem("STATE");
        jComboBox1.setSelectedItem("STATE");
        for (State s : State.values())
        {
            jComboBox1.addItem(s.toString());
            jComboBox3.addItem(s.toString());
        }
        
        //Setting Party values in the combo boxes
        jComboBox2.addItem("PARTY");
        jComboBox2.setSelectedItem("PARTY");
        for (Party s : Party.values())
        {
            jComboBox2.addItem(s.toString());
            jComboBox4.addItem(s.toString());      
        }
        
        //Getting layout
        cards = (CardLayout)mainPanel.getLayout();
        cardsChart = (CardLayout)chartMainPanel.getLayout();
        
        //Updating status of vote
        statusText.setText(admin.getLastStatus());
        UpdateStatusButtons(statusText);
        
        //File Manager to save users preferences
        try {
            f=new FileManager(); 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GUI_Official.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
    /**
     * Updating all data into the charts
     */
    public void updateCharts(){
        mainMenuChart=createVotesBarChart(createBarDataset());
        mainMenuChartPanel= new ChartPanel(mainMenuChart);
        
        analyzeChart=createVotesStackedBarChart(createStackedBarDataset());
        analyzeChartPanel = new ChartPanel(analyzeChart);
        
        ringChart=createRingPlotChart(createRingPlotDataset());
        ringChartPanel = new ChartPanel(ringChart);
    }
    
     /**
     * Main after construction of JFrame object
     */
    public void embeddedMain() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI_Start.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        UpdateStatusButtons(statusText);

        
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
     public JFreeChart createVotesBarChart(DefaultCategoryDataset dataset)
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
    public JFreeChart createVotesStackedBarChart(DefaultCategoryDataset dataset)
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
        r.setSeriesPaint(0, new Color(40,40,40));
        r.setMaximumBarWidth(0.1);
        r.setBaseItemLabelsVisible(true);
        r.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        r.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());

        return myChart;
    }
     
     
     
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        imageChooser = new javax.swing.JFileChooser();
        settingsPopUp = new javax.swing.JPopupMenu();
        redOption = new javax.swing.JMenuItem();
        greenOption = new javax.swing.JMenuItem();
        blueOption = new javax.swing.JMenuItem();
        leftPanel = new javax.swing.JPanel();
        settingsButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        dataAnalysisButton = new javax.swing.JButton();
        profileButton = new javax.swing.JButton();
        mainMenuButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        voteStatusText = new javax.swing.JLabel();
        statusText = new javax.swing.JLabel();
        startButton = new javax.swing.JButton();
        pauseButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        mainPanel = new javax.swing.JPanel();
        mainMenu = new javax.swing.JPanel();
        textPanel = new javax.swing.JPanel();
        mainMenuDescription = new javax.swing.JLabel();
        mainMenuText = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextArea3 = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jTextArea4 = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextArea5 = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        jTextArea6 = new javax.swing.JTextArea();
        jTextArea7 = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTextArea8 = new javax.swing.JTextArea();
        addVoterPanel = new javax.swing.JPanel();
        addVoterButton = new javax.swing.JButton();
        addVoterFirstName = new javax.swing.JTextField();
        addVoterLastName = new javax.swing.JTextField();
        addVoterEmail = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        addVoterDate = new javax.swing.JFormattedTextField();
        addVoterPassword = new javax.swing.JPasswordField();
        addVoterBack = new javax.swing.JButton();
        addVoterTextPanel = new javax.swing.JPanel();
        addVoterCaption = new javax.swing.JLabel();
        addVoterText = new javax.swing.JLabel();
        addCandidatePanel = new javax.swing.JPanel();
        addCandidateButton = new javax.swing.JButton();
        addCandidateFirstName = new javax.swing.JTextField();
        addCandidateLastName = new javax.swing.JTextField();
        imageRetrieved = new javax.swing.JLabel();
        dataPath = new javax.swing.JLabel();
        addCandidateEmail = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox<>();
        addCandidateDate = new javax.swing.JFormattedTextField();
        addCandidatePassword = new javax.swing.JPasswordField();
        addCandidateDescription = new javax.swing.JTextField();
        addCandidateBack = new javax.swing.JButton();
        addCandidateTextPanel = new javax.swing.JPanel();
        addCandidateText = new javax.swing.JLabel();
        addCandidateCaption = new javax.swing.JLabel();
        addCandidateSearchImage = new javax.swing.JButton();
        addCandidateClearImage = new javax.swing.JButton();
        editVotersPanel = new javax.swing.JPanel();
        editVotersBack = new javax.swing.JButton();
        editVotersScrollPanel = new javax.swing.JScrollPane();
        editVotersTable = new javax.swing.JTable();
        editVotersEdit = new javax.swing.JButton();
        editVotersDelete = new javax.swing.JButton();
        editVotersTextPanel = new javax.swing.JPanel();
        editVotersText = new javax.swing.JLabel();
        editVotersCaption = new javax.swing.JLabel();
        editVotersEditPanel = new javax.swing.JPanel();
        editVotersEditOK = new javax.swing.JButton();
        editVoterFirstName = new javax.swing.JTextField();
        editVoterLastName = new javax.swing.JTextField();
        editVoterEmail = new javax.swing.JTextField();
        editVoterDate = new javax.swing.JFormattedTextField();
        jComboBox3 = new javax.swing.JComboBox<>();
        editVoterPassword = new javax.swing.JTextField();
        editCandidatePanel = new javax.swing.JPanel();
        editCandidatesBack = new javax.swing.JButton();
        editCandidatesScrollPanel = new javax.swing.JScrollPane();
        editCandidatesTable = new javax.swing.JTable();
        editCandidatesEdit = new javax.swing.JButton();
        editCandidatesDelete = new javax.swing.JButton();
        editCandidatesTextPanel = new javax.swing.JPanel();
        editCandidatesText = new javax.swing.JLabel();
        editCandidatesCaption = new javax.swing.JLabel();
        editCandidatesEditPanel = new javax.swing.JPanel();
        editCandidatesEditOK = new javax.swing.JButton();
        editCandidatesFirstName = new javax.swing.JTextField();
        editCandidatesLastName = new javax.swing.JTextField();
        editCandidatesEmail = new javax.swing.JTextField();
        editCandidatesDate = new javax.swing.JFormattedTextField();
        jComboBox4 = new javax.swing.JComboBox<>();
        editCandidatesPassword = new javax.swing.JTextField();
        editCandidatesDescription = new javax.swing.JTextField();
        imageLabel = new javax.swing.JLabel();
        editCandidatesChangeImage = new javax.swing.JButton();
        analyzeVotesPanel = new javax.swing.JPanel();
        analyzeTextPanel = new javax.swing.JPanel();
        AnalyzeText = new javax.swing.JLabel();
        AnalyzeCaption = new javax.swing.JLabel();
        chartMainPanel = new javax.swing.JPanel();
        idlePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextArea1 = new javax.swing.JTextArea();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jTextArea9 = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        ringPlotPanel = ringChartPanel;
        stackedBarPanel = analyzeChartPanel;
        barPanel = mainMenuChartPanel;
        stackedBarChartButton = new javax.swing.JButton();
        ringChartButton = new javax.swing.JButton();
        barChartButton = new javax.swing.JButton();
        profilePanel = new javax.swing.JPanel();
        profileFirstName = new javax.swing.JTextField();
        profileLastName = new javax.swing.JTextField();
        profileDate = new javax.swing.JFormattedTextField();
        profileEmail = new javax.swing.JTextField();
        profilePassword = new javax.swing.JTextField();
        profileText = new javax.swing.JLabel();
        profileSaveButton = new javax.swing.JButton();
        profileBackButton = new javax.swing.JButton();
        profileCaptionText = new javax.swing.JLabel();
        MenuBar = new javax.swing.JMenuBar();
        newMenu = new javax.swing.JMenu();
        newMenuVoter = new javax.swing.JMenuItem();
        newMenuCandidate = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        editMenuVoter = new javax.swing.JMenuItem();
        editMenuCandidate = new javax.swing.JMenuItem();

        imageChooser.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        imageChooser.setApproveButtonText("");
        imageChooser.setApproveButtonToolTipText("");
        imageChooser.setBackground(java.awt.Color.darkGray);
        imageChooser.setCurrentDirectory(new java.io.File("C:\\Users\\Keke\\Images"));
        imageChooser.setFileFilter(new ImageFilter());

        settingsPopUp.setPreferredSize(new java.awt.Dimension(200, 100));
        settingsPopUp.setRequestFocusEnabled(false);

        redOption.setText("Set Color to Red");
        redOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redOptionActionPerformed(evt);
            }
        });
        settingsPopUp.add(redOption);

        greenOption.setText("Set Color to Green");
        greenOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                greenOptionActionPerformed(evt);
            }
        });
        settingsPopUp.add(greenOption);

        blueOption.setText("Set Color to Blue");
        blueOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blueOptionActionPerformed(evt);
            }
        });
        settingsPopUp.add(blueOption);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Election Simulator");
        setMinimumSize(new java.awt.Dimension(800, 600));
        setResizable(false);
        setSize(new java.awt.Dimension(800, 600));

        leftPanel.setBackground(new java.awt.Color(0, 102, 130));
        leftPanel.setMaximumSize(new java.awt.Dimension(180, 0));
        leftPanel.setMinimumSize(new java.awt.Dimension(180, 0));
        leftPanel.setPreferredSize(new java.awt.Dimension(180, 0));

        settingsButton.setBackground(new java.awt.Color(255, 255, 255));
        settingsButton.setForeground(new java.awt.Color(255, 255, 255));
        settingsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/gearIcon.png"))); // NOI18N
        settingsButton.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        settingsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsButtonActionPerformed(evt);
            }
        });

        exitButton.setBackground(new java.awt.Color(255, 255, 255));
        exitButton.setForeground(new java.awt.Color(255, 255, 255));
        exitButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/logoutIcon.png"))); // NOI18N
        exitButton.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        dataAnalysisButton.setText("Data Analysis");
        dataAnalysisButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dataAnalysisButtonActionPerformed(evt);
            }
        });

        profileButton.setBackground(new java.awt.Color(255, 255, 255));
        profileButton.setForeground(new java.awt.Color(255, 255, 255));
        profileButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/userIcon.png"))); // NOI18N
        profileButton.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        profileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                profileButtonActionPerformed(evt);
            }
        });

        mainMenuButton.setText("Main Menu");
        mainMenuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainMenuButtonActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        voteStatusText.setBackground(new java.awt.Color(255, 255, 255));
        voteStatusText.setFont(new java.awt.Font("Montserrat Medium", 0, 18)); // NOI18N
        voteStatusText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        voteStatusText.setText("Vote Status");

        statusText.setBackground(new java.awt.Color(255, 255, 255));
        statusText.setFont(new java.awt.Font("Montserrat Medium", 0, 18)); // NOI18N
        statusText.setForeground(new java.awt.Color(0, 185, 16));
        statusText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        statusText.setText("Active");

        startButton.setText("Start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        pauseButton.setText("Pause");
        pauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseButtonActionPerformed(evt);
            }
        });

        stopButton.setText("Stop");
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(stopButton, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pauseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(statusText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(voteStatusText, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(voteStatusText)
                .addGap(1, 1, 1)
                .addComponent(statusText)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pauseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stopButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout leftPanelLayout = new javax.swing.GroupLayout(leftPanel);
        leftPanel.setLayout(leftPanelLayout);
        leftPanelLayout.setHorizontalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(leftPanelLayout.createSequentialGroup()
                        .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                        .addComponent(profileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                        .addComponent(settingsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(leftPanelLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(mainMenuButton, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dataAnalysisButton, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        leftPanelLayout.setVerticalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, leftPanelLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(dataAnalysisButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(mainMenuButton)
                .addGap(154, 154, 154)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(exitButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(settingsButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(profileButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setPreferredSize(new java.awt.Dimension(620, 580));
        mainPanel.setLayout(new java.awt.CardLayout());

        mainMenu.setBackground(new java.awt.Color(255, 255, 255));
        mainMenu.setFocusTraversalPolicyProvider(true);

        textPanel.setBackground(new java.awt.Color(255, 255, 255));

        mainMenuDescription.setBackground(new java.awt.Color(255, 255, 255));
        mainMenuDescription.setFont(new java.awt.Font("Montserrat Medium", 0, 18)); // NOI18N
        mainMenuDescription.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mainMenuDescription.setText("Welcome to the admin center " +  admin.getFirstName() + " " + admin.getLastName() + " !");

        mainMenuText.setBackground(new java.awt.Color(255, 255, 255));
        mainMenuText.setFont(new java.awt.Font("Montserrat Medium", 0, 36)); // NOI18N
        mainMenuText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mainMenuText.setText("Official Main Menu");

        javax.swing.GroupLayout textPanelLayout = new javax.swing.GroupLayout(textPanel);
        textPanel.setLayout(textPanelLayout);
        textPanelLayout.setHorizontalGroup(
            textPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainMenuDescription, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(mainMenuText, javax.swing.GroupLayout.DEFAULT_SIZE, 832, Short.MAX_VALUE)
        );
        textPanelLayout.setVerticalGroup(
            textPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(textPanelLayout.createSequentialGroup()
                .addComponent(mainMenuText)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainMenuDescription)
                .addGap(30, 30, 30))
        );

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(50, 50, 50));
        jLabel13.setText("What can you do ?");

        jTextArea3.setEditable(false);
        jTextArea3.setBackground(new java.awt.Color(255, 255, 255));
        jTextArea3.setColumns(20);
        jTextArea3.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTextArea3.setForeground(new java.awt.Color(70, 70, 70));
        jTextArea3.setLineWrap(true);
        jTextArea3.setRows(5);
        jTextArea3.setText("Modify or delete voters and candidates informations that are already in the system.");
        jTextArea3.setWrapStyleWord(true);
        jTextArea3.setBorder(javax.swing.BorderFactory.createCompoundBorder());

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(50, 50, 50));
        jLabel4.setText("Edit");

        jTextArea4.setEditable(false);
        jTextArea4.setBackground(new java.awt.Color(255, 255, 255));
        jTextArea4.setColumns(20);
        jTextArea4.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTextArea4.setForeground(new java.awt.Color(70, 70, 70));
        jTextArea4.setLineWrap(true);
        jTextArea4.setRows(5);
        jTextArea4.setText("Create a new voter or a new candidate to participate in the election.");
        jTextArea4.setWrapStyleWord(true);
        jTextArea4.setBorder(javax.swing.BorderFactory.createCompoundBorder());

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(50, 50, 50));
        jLabel5.setText("New");

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 102));
        jLabel7.setText("Bar options");

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 102));
        jLabel8.setText("Status options");

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(50, 50, 50));
        jLabel9.setText("Start");

        jTextArea5.setEditable(false);
        jTextArea5.setBackground(new java.awt.Color(255, 255, 255));
        jTextArea5.setColumns(20);
        jTextArea5.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTextArea5.setForeground(new java.awt.Color(70, 70, 70));
        jTextArea5.setLineWrap(true);
        jTextArea5.setRows(5);
        jTextArea5.setText("Allow the election to begin by erasing all current votes.");
        jTextArea5.setWrapStyleWord(true);
        jTextArea5.setBorder(javax.swing.BorderFactory.createCompoundBorder());

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(50, 50, 50));
        jLabel10.setText("Pause");

        jTextArea6.setEditable(false);
        jTextArea6.setBackground(new java.awt.Color(255, 255, 255));
        jTextArea6.setColumns(20);
        jTextArea6.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTextArea6.setForeground(new java.awt.Color(70, 70, 70));
        jTextArea6.setLineWrap(true);
        jTextArea6.setRows(5);
        jTextArea6.setText("Pause the election. If the election is paused, voters won't be able to vote.");
        jTextArea6.setWrapStyleWord(true);
        jTextArea6.setBorder(javax.swing.BorderFactory.createCompoundBorder());

        jTextArea7.setEditable(false);
        jTextArea7.setBackground(new java.awt.Color(255, 255, 255));
        jTextArea7.setColumns(20);
        jTextArea7.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTextArea7.setForeground(new java.awt.Color(70, 70, 70));
        jTextArea7.setLineWrap(true);
        jTextArea7.setRows(5);
        jTextArea7.setText("Close the election and annouce the winner.");
        jTextArea7.setWrapStyleWord(true);
        jTextArea7.setBorder(javax.swing.BorderFactory.createCompoundBorder());

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(50, 50, 50));
        jLabel11.setText("Stop");

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 51, 102));
        jLabel12.setText("Data Analysis");

        jTextArea8.setEditable(false);
        jTextArea8.setBackground(new java.awt.Color(255, 255, 255));
        jTextArea8.setColumns(20);
        jTextArea8.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTextArea8.setForeground(new java.awt.Color(70, 70, 70));
        jTextArea8.setLineWrap(true);
        jTextArea8.setRows(5);
        jTextArea8.setText("Review votes using charts");
        jTextArea8.setWrapStyleWord(true);
        jTextArea8.setBorder(javax.swing.BorderFactory.createCompoundBorder());

        javax.swing.GroupLayout mainMenuLayout = new javax.swing.GroupLayout(mainMenu);
        mainMenu.setLayout(mainMenuLayout);
        mainMenuLayout.setHorizontalGroup(
            mainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(textPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(mainMenuLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(mainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextArea5)
                    .addComponent(jTextArea6)
                    .addGroup(mainMenuLayout.createSequentialGroup()
                        .addGroup(mainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextArea7)
                            .addComponent(jTextArea4)
                            .addComponent(jTextArea3)
                            .addGroup(mainMenuLayout.createSequentialGroup()
                                .addGroup(mainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel4))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addComponent(jTextArea8)
                    .addGroup(mainMenuLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addContainerGap())
                    .addGroup(mainMenuLayout.createSequentialGroup()
                        .addGroup(mainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8)
                            .addComponent(jLabel10))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        mainMenuLayout.setVerticalGroup(
            mainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainMenuLayout.createSequentialGroup()
                .addComponent(textPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextArea4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextArea3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextArea5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextArea6, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextArea7, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextArea8, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(144, Short.MAX_VALUE))
        );

        mainPanel.add(mainMenu, "mainMenu");

        addVoterPanel.setBackground(new java.awt.Color(255, 255, 255));

        addVoterButton.setText("Add");
        addVoterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addVoterButtonActionPerformed(evt);
            }
        });

        addVoterFirstName.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "First Name"));
        addVoterFirstName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addVoterFirstNameActionPerformed(evt);
            }
        });

        addVoterLastName.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Last Name"));

        addVoterEmail.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Email"));

        jComboBox1.setBorder(null);
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        addVoterDate.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Date of birth"));
        addVoterDate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("yyyy-MM-dd"))));
        addVoterDate.setText("yyyy-mm-dd");
        addVoterDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addVoterDateActionPerformed(evt);
            }
        });

        addVoterPassword.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Password"));

        addVoterBack.setText("Go back");
        addVoterBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addVoterBackActionPerformed(evt);
            }
        });

        addVoterTextPanel.setBackground(new java.awt.Color(255, 255, 255));

        addVoterCaption.setBackground(new java.awt.Color(255, 255, 255));
        addVoterCaption.setFont(new java.awt.Font("Montserrat Medium", 0, 18)); // NOI18N
        addVoterCaption.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        addVoterCaption.setText("Add a new Voter to the Database");

        addVoterText.setBackground(new java.awt.Color(255, 255, 255));
        addVoterText.setFont(new java.awt.Font("Montserrat Medium", 0, 36)); // NOI18N
        addVoterText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        addVoterText.setText("New Voter");

        javax.swing.GroupLayout addVoterTextPanelLayout = new javax.swing.GroupLayout(addVoterTextPanel);
        addVoterTextPanel.setLayout(addVoterTextPanelLayout);
        addVoterTextPanelLayout.setHorizontalGroup(
            addVoterTextPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addVoterCaption, javax.swing.GroupLayout.DEFAULT_SIZE, 844, Short.MAX_VALUE)
            .addComponent(addVoterText, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        addVoterTextPanelLayout.setVerticalGroup(
            addVoterTextPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addVoterTextPanelLayout.createSequentialGroup()
                .addComponent(addVoterText)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addVoterCaption)
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout addVoterPanelLayout = new javax.swing.GroupLayout(addVoterPanel);
        addVoterPanel.setLayout(addVoterPanelLayout);
        addVoterPanelLayout.setHorizontalGroup(
            addVoterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addVoterTextPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(addVoterPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(addVoterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addVoterPanelLayout.createSequentialGroup()
                        .addGroup(addVoterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(addVoterFirstName, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                            .addComponent(addVoterEmail))
                        .addGap(72, 72, 72)
                        .addGroup(addVoterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(addVoterLastName, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                            .addComponent(addVoterPassword))
                        .addGap(72, 72, 72)
                        .addGroup(addVoterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(addVoterDate, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(addVoterPanelLayout.createSequentialGroup()
                        .addComponent(addVoterBack)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addVoterButton, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );
        addVoterPanelLayout.setVerticalGroup(
            addVoterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addVoterPanelLayout.createSequentialGroup()
                .addComponent(addVoterTextPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(addVoterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addVoterFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addVoterLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addVoterDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(addVoterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addVoterEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addVoterPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 448, Short.MAX_VALUE)
                .addGroup(addVoterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addVoterBack, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addVoterButton))
                .addContainerGap())
        );

        mainPanel.add(addVoterPanel, "addVoter");

        addCandidatePanel.setBackground(new java.awt.Color(255, 255, 255));

        addCandidateButton.setText("Add");
        addCandidateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCandidateButtonActionPerformed(evt);
            }
        });

        addCandidateFirstName.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "First Name"));
        addCandidateFirstName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCandidateFirstNameActionPerformed(evt);
            }
        });

        addCandidateLastName.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Last Name"));

        imageRetrieved.setBackground(new java.awt.Color(255, 255, 255));
        imageRetrieved.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        imageRetrieved.setForeground(new java.awt.Color(50, 50, 50));
        imageRetrieved.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imageRetrieved.setText("Select an image");

        dataPath.setBackground(new java.awt.Color(255, 255, 255));
        dataPath.setForeground(new java.awt.Color(50, 50, 50));
        dataPath.setText("Data Path");

        addCandidateEmail.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Email"));

        jComboBox2.setBorder(null);
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        addCandidateDate.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Date of birth"));
        addCandidateDate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("yyyy-MM-dd"))));
        addCandidateDate.setText("yyyy-mm-dd");
        addCandidateDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCandidateDateActionPerformed(evt);
            }
        });

        addCandidatePassword.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Password"));

        addCandidateDescription.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Description"));

        addCandidateBack.setText("Go back");
        addCandidateBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCandidateBackActionPerformed(evt);
            }
        });

        addCandidateTextPanel.setBackground(new java.awt.Color(255, 255, 255));

        addCandidateText.setBackground(new java.awt.Color(255, 255, 255));
        addCandidateText.setFont(new java.awt.Font("Montserrat Medium", 0, 36)); // NOI18N
        addCandidateText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        addCandidateText.setText("New Candidate");

        addCandidateCaption.setBackground(new java.awt.Color(255, 255, 255));
        addCandidateCaption.setFont(new java.awt.Font("Montserrat Medium", 0, 18)); // NOI18N
        addCandidateCaption.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        addCandidateCaption.setText("Add a new Candidate to the Database");

        javax.swing.GroupLayout addCandidateTextPanelLayout = new javax.swing.GroupLayout(addCandidateTextPanel);
        addCandidateTextPanel.setLayout(addCandidateTextPanelLayout);
        addCandidateTextPanelLayout.setHorizontalGroup(
            addCandidateTextPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addCandidateCaption, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(addCandidateText, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        addCandidateTextPanelLayout.setVerticalGroup(
            addCandidateTextPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addCandidateTextPanelLayout.createSequentialGroup()
                .addComponent(addCandidateText)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addCandidateCaption)
                .addGap(30, 30, 30))
        );

        addCandidateSearchImage.setText("Search Image");
        addCandidateSearchImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCandidateSearchImageActionPerformed(evt);
            }
        });

        addCandidateClearImage.setText("Clear Image");
        addCandidateClearImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCandidateClearImageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout addCandidatePanelLayout = new javax.swing.GroupLayout(addCandidatePanel);
        addCandidatePanel.setLayout(addCandidatePanelLayout);
        addCandidatePanelLayout.setHorizontalGroup(
            addCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addCandidateTextPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(addCandidatePanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(addCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addCandidateBack)
                    .addGroup(addCandidatePanelLayout.createSequentialGroup()
                        .addGroup(addCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, addCandidatePanelLayout.createSequentialGroup()
                                .addComponent(addCandidateFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(72, 72, 72)
                                .addComponent(addCandidateLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(addCandidateDescription, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addCandidateEmail)
                            .addComponent(dataPath, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(imageRetrieved, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(72, 72, 72)
                        .addGroup(addCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(addCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(addCandidateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(addCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(addCandidatePassword)
                                    .addComponent(addCandidateDate, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                                    .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(addCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(addCandidateClearImage, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(addCandidateSearchImage, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(20, 20, 20))
        );
        addCandidatePanelLayout.setVerticalGroup(
            addCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addCandidatePanelLayout.createSequentialGroup()
                .addComponent(addCandidateTextPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(addCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addCandidateFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addCandidateLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addCandidateDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(addCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addCandidateEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addCandidatePassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(addCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addCandidateDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(addCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addCandidatePanelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(addCandidateSearchImage)
                        .addGap(18, 18, 18)
                        .addComponent(addCandidateClearImage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(addCandidatePanelLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(imageRetrieved, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dataPath, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                        .addGap(30, 30, 30)))
                .addGroup(addCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addCandidateBack, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addCandidateButton))
                .addContainerGap())
        );

        mainPanel.add(addCandidatePanel, "addCandidate");

        editVotersPanel.setBackground(new java.awt.Color(255, 255, 255));

        editVotersBack.setText("Go back");
        editVotersBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editVotersBackActionPerformed(evt);
            }
        });

        editVotersTable.setAutoCreateRowSorter(true);
        editVotersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        editVotersScrollPanel.setViewportView(editVotersTable);

        editVotersEdit.setText("Edit");
        editVotersEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editVotersEditActionPerformed(evt);
            }
        });

        editVotersDelete.setText("Delete");
        editVotersDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editVotersDeleteActionPerformed(evt);
            }
        });

        editVotersTextPanel.setBackground(new java.awt.Color(255, 255, 255));

        editVotersText.setBackground(new java.awt.Color(255, 255, 255));
        editVotersText.setFont(new java.awt.Font("Montserrat Medium", 0, 36)); // NOI18N
        editVotersText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        editVotersText.setText("Edit Voters");

        editVotersCaption.setBackground(new java.awt.Color(255, 255, 255));
        editVotersCaption.setFont(new java.awt.Font("Montserrat Medium", 0, 18)); // NOI18N
        editVotersCaption.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        editVotersCaption.setText("Edit or delete voters by selecting one and using an option");

        javax.swing.GroupLayout editVotersTextPanelLayout = new javax.swing.GroupLayout(editVotersTextPanel);
        editVotersTextPanel.setLayout(editVotersTextPanelLayout);
        editVotersTextPanelLayout.setHorizontalGroup(
            editVotersTextPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editVotersTextPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(editVotersTextPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(editVotersText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editVotersCaption, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        editVotersTextPanelLayout.setVerticalGroup(
            editVotersTextPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editVotersTextPanelLayout.createSequentialGroup()
                .addComponent(editVotersText)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editVotersCaption)
                .addGap(30, 30, 30))
        );

        editVotersEditPanel.setBackground(new java.awt.Color(255, 255, 255));

        editVotersEditOK.setText("OK");
        editVotersEditOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editVotersEditOKActionPerformed(evt);
            }
        });

        editVoterFirstName.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "First Name"));
        editVoterFirstName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editVoterFirstNameActionPerformed(evt);
            }
        });

        editVoterLastName.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Last Name"));

        editVoterEmail.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Email"));

        editVoterDate.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Date of birth"));
        editVoterDate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("yyyy-MM-dd"))));
        editVoterDate.setText("yyyy-mm-dd");
        editVoterDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editVoterDateActionPerformed(evt);
            }
        });

        jComboBox3.setBorder(null);
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        editVoterPassword.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Password"));

        javax.swing.GroupLayout editVotersEditPanelLayout = new javax.swing.GroupLayout(editVotersEditPanel);
        editVotersEditPanel.setLayout(editVotersEditPanelLayout);
        editVotersEditPanelLayout.setHorizontalGroup(
            editVotersEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editVotersEditPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(editVotersEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(editVoterFirstName, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                    .addComponent(editVoterEmail))
                .addGap(26, 26, 26)
                .addGroup(editVotersEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(editVoterLastName, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                    .addComponent(editVoterPassword))
                .addGap(26, 26, 26)
                .addGroup(editVotersEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editVotersEditPanelLayout.createSequentialGroup()
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(editVotersEditPanelLayout.createSequentialGroup()
                        .addComponent(editVoterDate, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(editVotersEditOK, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))))
        );
        editVotersEditPanelLayout.setVerticalGroup(
            editVotersEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editVotersEditPanelLayout.createSequentialGroup()
                .addGroup(editVotersEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editVoterDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editVoterLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editVoterFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editVotersEditOK))
                .addGap(18, 18, 18)
                .addGroup(editVotersEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editVoterEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editVoterPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout editVotersPanelLayout = new javax.swing.GroupLayout(editVotersPanel);
        editVotersPanel.setLayout(editVotersPanelLayout);
        editVotersPanelLayout.setHorizontalGroup(
            editVotersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(editVotersTextPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(editVotersPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(editVotersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(editVotersEditPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(editVotersPanelLayout.createSequentialGroup()
                        .addGroup(editVotersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(editVotersBack)
                            .addGroup(editVotersPanelLayout.createSequentialGroup()
                                .addComponent(editVotersScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 721, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(editVotersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(editVotersDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(editVotersEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addContainerGap())))
        );
        editVotersPanelLayout.setVerticalGroup(
            editVotersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editVotersPanelLayout.createSequentialGroup()
                .addComponent(editVotersTextPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(editVotersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editVotersPanelLayout.createSequentialGroup()
                        .addComponent(editVotersEdit)
                        .addGap(18, 18, 18)
                        .addComponent(editVotersDelete)
                        .addGap(0, 340, Short.MAX_VALUE))
                    .addComponent(editVotersScrollPanel))
                .addGap(18, 18, 18)
                .addComponent(editVotersEditPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(editVotersBack, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        mainPanel.add(editVotersPanel, "editVoter");

        editCandidatePanel.setBackground(new java.awt.Color(255, 255, 255));

        editCandidatesBack.setText("Go back");
        editCandidatesBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editCandidatesBackActionPerformed(evt);
            }
        });

        editCandidatesTable.setAutoCreateRowSorter(true);
        editCandidatesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        editCandidatesScrollPanel.setViewportView(editCandidatesTable);

        editCandidatesEdit.setText("Edit");
        editCandidatesEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editCandidatesEditActionPerformed(evt);
            }
        });

        editCandidatesDelete.setText("Delete");
        editCandidatesDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editCandidatesDeleteActionPerformed(evt);
            }
        });

        editCandidatesTextPanel.setBackground(new java.awt.Color(255, 255, 255));

        editCandidatesText.setBackground(new java.awt.Color(255, 255, 255));
        editCandidatesText.setFont(new java.awt.Font("Montserrat Medium", 0, 36)); // NOI18N
        editCandidatesText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        editCandidatesText.setText("Edit Candidates");

        editCandidatesCaption.setBackground(new java.awt.Color(255, 255, 255));
        editCandidatesCaption.setFont(new java.awt.Font("Montserrat Medium", 0, 18)); // NOI18N
        editCandidatesCaption.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        editCandidatesCaption.setText("Edit or delete candidates by selecting one and using an option");

        javax.swing.GroupLayout editCandidatesTextPanelLayout = new javax.swing.GroupLayout(editCandidatesTextPanel);
        editCandidatesTextPanel.setLayout(editCandidatesTextPanelLayout);
        editCandidatesTextPanelLayout.setHorizontalGroup(
            editCandidatesTextPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editCandidatesTextPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(editCandidatesTextPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(editCandidatesCaption, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editCandidatesText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        editCandidatesTextPanelLayout.setVerticalGroup(
            editCandidatesTextPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editCandidatesTextPanelLayout.createSequentialGroup()
                .addComponent(editCandidatesText)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editCandidatesCaption)
                .addGap(30, 30, 30))
        );

        editCandidatesEditPanel.setBackground(new java.awt.Color(255, 255, 255));

        editCandidatesEditOK.setText("OK");
        editCandidatesEditOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editCandidatesEditOKActionPerformed(evt);
            }
        });

        editCandidatesFirstName.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "First Name"));
        editCandidatesFirstName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editCandidatesFirstNameActionPerformed(evt);
            }
        });

        editCandidatesLastName.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Last Name"));

        editCandidatesEmail.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Email"));

        editCandidatesDate.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Date of birth"));
        editCandidatesDate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("yyyy-MM-dd"))));
        editCandidatesDate.setText("yyyy-mm-dd");
        editCandidatesDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editCandidatesDateActionPerformed(evt);
            }
        });

        jComboBox4.setBorder(null);
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        editCandidatesPassword.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Password"));
        editCandidatesPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editCandidatesPasswordActionPerformed(evt);
            }
        });

        editCandidatesDescription.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Description"));
        editCandidatesDescription.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editCandidatesDescriptionActionPerformed(evt);
            }
        });

        imageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imageLabel.setText("No image");

        editCandidatesChangeImage.setText("Change Image");
        editCandidatesChangeImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editCandidatesChangeImageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout editCandidatesEditPanelLayout = new javax.swing.GroupLayout(editCandidatesEditPanel);
        editCandidatesEditPanel.setLayout(editCandidatesEditPanelLayout);
        editCandidatesEditPanelLayout.setHorizontalGroup(
            editCandidatesEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editCandidatesEditPanelLayout.createSequentialGroup()
                .addGroup(editCandidatesEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(editCandidatesEditPanelLayout.createSequentialGroup()
                        .addGroup(editCandidatesEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(editCandidatesFirstName, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                            .addComponent(editCandidatesEmail))
                        .addGap(26, 26, 26)
                        .addGroup(editCandidatesEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(editCandidatesLastName, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                            .addComponent(editCandidatesPassword))
                        .addGap(26, 26, 26)
                        .addGroup(editCandidatesEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(editCandidatesDate, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(editCandidatesEditPanelLayout.createSequentialGroup()
                        .addComponent(editCandidatesDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(editCandidatesChangeImage, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(imageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(editCandidatesEditOK, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        editCandidatesEditPanelLayout.setVerticalGroup(
            editCandidatesEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editCandidatesEditPanelLayout.createSequentialGroup()
                .addGroup(editCandidatesEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editCandidatesDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editCandidatesLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editCandidatesFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editCandidatesEditOK))
                .addGap(18, 18, 18)
                .addGroup(editCandidatesEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editCandidatesEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editCandidatesPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(editCandidatesEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editCandidatesDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editCandidatesChangeImage))
                .addContainerGap(12, Short.MAX_VALUE))
            .addComponent(imageLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout editCandidatePanelLayout = new javax.swing.GroupLayout(editCandidatePanel);
        editCandidatePanel.setLayout(editCandidatePanelLayout);
        editCandidatePanelLayout.setHorizontalGroup(
            editCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(editCandidatesTextPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(editCandidatePanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(editCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editCandidatePanelLayout.createSequentialGroup()
                        .addComponent(editCandidatesBack)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(editCandidatePanelLayout.createSequentialGroup()
                        .addComponent(editCandidatesScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 721, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(editCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(editCandidatesDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(editCandidatesEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(25, 25, 25))
                    .addComponent(editCandidatesEditPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        editCandidatePanelLayout.setVerticalGroup(
            editCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editCandidatePanelLayout.createSequentialGroup()
                .addComponent(editCandidatesTextPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(editCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editCandidatePanelLayout.createSequentialGroup()
                        .addComponent(editCandidatesEdit)
                        .addGap(18, 18, 18)
                        .addComponent(editCandidatesDelete)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(editCandidatesScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(editCandidatesEditPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(editCandidatesBack, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        mainPanel.add(editCandidatePanel, "editCandidate");

        analyzeVotesPanel.setBackground(new java.awt.Color(255, 255, 255));
        analyzeVotesPanel.setForeground(new java.awt.Color(255, 255, 255));

        analyzeTextPanel.setBackground(new java.awt.Color(255, 255, 255));

        AnalyzeText.setBackground(new java.awt.Color(255, 255, 255));
        AnalyzeText.setFont(new java.awt.Font("Montserrat Medium", 0, 36)); // NOI18N
        AnalyzeText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AnalyzeText.setText("Analysis");

        AnalyzeCaption.setBackground(new java.awt.Color(255, 255, 255));
        AnalyzeCaption.setFont(new java.awt.Font("Montserrat Medium", 0, 18)); // NOI18N
        AnalyzeCaption.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AnalyzeCaption.setText("Review votes under diverse circumstances");

        javax.swing.GroupLayout analyzeTextPanelLayout = new javax.swing.GroupLayout(analyzeTextPanel);
        analyzeTextPanel.setLayout(analyzeTextPanelLayout);
        analyzeTextPanelLayout.setHorizontalGroup(
            analyzeTextPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, analyzeTextPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(analyzeTextPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(AnalyzeCaption, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(AnalyzeText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        analyzeTextPanelLayout.setVerticalGroup(
            analyzeTextPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(analyzeTextPanelLayout.createSequentialGroup()
                .addComponent(AnalyzeText)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AnalyzeCaption)
                .addGap(30, 30, 30))
        );

        chartMainPanel.setBackground(new java.awt.Color(255, 255, 255));
        chartMainPanel.setForeground(new java.awt.Color(255, 255, 255));
        chartMainPanel.setLayout(new java.awt.CardLayout());

        idlePanel.setBackground(new java.awt.Color(255, 255, 255));
        idlePanel.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(50, 50, 50));
        jLabel1.setText("Choose a chart to display");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(50, 50, 50));
        jLabel2.setText("Stacked Bar Chart");

        jTextArea1.setEditable(false);
        jTextArea1.setBackground(new java.awt.Color(255, 255, 255));
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTextArea1.setForeground(new java.awt.Color(70, 70, 70));
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("Displays a stacked bar chart of the votes for each candidate with the number of votes for each state.");
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setBorder(javax.swing.BorderFactory.createCompoundBorder());

        jTextArea2.setEditable(false);
        jTextArea2.setBackground(new java.awt.Color(255, 255, 255));
        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTextArea2.setForeground(new java.awt.Color(70, 70, 70));
        jTextArea2.setLineWrap(true);
        jTextArea2.setRows(5);
        jTextArea2.setText("Displays a ring chart of the pourcentage of votes for each candidate. ");
        jTextArea2.setWrapStyleWord(true);
        jTextArea2.setBorder(javax.swing.BorderFactory.createCompoundBorder());

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(50, 50, 50));
        jLabel3.setText("Ring Chart");

        jTextArea9.setEditable(false);
        jTextArea9.setBackground(new java.awt.Color(255, 255, 255));
        jTextArea9.setColumns(20);
        jTextArea9.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTextArea9.setForeground(new java.awt.Color(70, 70, 70));
        jTextArea9.setLineWrap(true);
        jTextArea9.setRows(5);
        jTextArea9.setText("Displays a bar chart of the votes for each candidate.");
        jTextArea9.setWrapStyleWord(true);
        jTextArea9.setBorder(javax.swing.BorderFactory.createCompoundBorder());

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(50, 50, 50));
        jLabel6.setText("Bar Chart");

        javax.swing.GroupLayout idlePanelLayout = new javax.swing.GroupLayout(idlePanel);
        idlePanel.setLayout(idlePanelLayout);
        idlePanelLayout.setHorizontalGroup(
            idlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(idlePanelLayout.createSequentialGroup()
                .addGroup(idlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextArea2)
                    .addComponent(jTextArea1)
                    .addGroup(idlePanelLayout.createSequentialGroup()
                        .addGroup(idlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1)
                            .addComponent(jLabel6)
                            .addComponent(jTextArea9, javax.swing.GroupLayout.PREFERRED_SIZE, 655, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        idlePanelLayout.setVerticalGroup(
            idlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(idlePanelLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextArea9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextArea1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextArea2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(258, Short.MAX_VALUE))
        );

        chartMainPanel.add(idlePanel, "idle");

        javax.swing.GroupLayout ringPlotPanelLayout = new javax.swing.GroupLayout(ringPlotPanel);
        ringPlotPanel.setLayout(ringPlotPanelLayout);
        ringPlotPanelLayout.setHorizontalGroup(
            ringPlotPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        ringPlotPanelLayout.setVerticalGroup(
            ringPlotPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 467, Short.MAX_VALUE)
        );

        chartMainPanel.add(ringPlotPanel, "ringPlot");

        stackedBarPanel.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout stackedBarPanelLayout = new javax.swing.GroupLayout(stackedBarPanel);
        stackedBarPanel.setLayout(stackedBarPanelLayout);
        stackedBarPanelLayout.setHorizontalGroup(
            stackedBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 810, Short.MAX_VALUE)
        );
        stackedBarPanelLayout.setVerticalGroup(
            stackedBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 467, Short.MAX_VALUE)
        );

        chartMainPanel.add(stackedBarPanel, "stackedBar");

        javax.swing.GroupLayout barPanelLayout = new javax.swing.GroupLayout(barPanel);
        barPanel.setLayout(barPanelLayout);
        barPanelLayout.setHorizontalGroup(
            barPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        barPanelLayout.setVerticalGroup(
            barPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 467, Short.MAX_VALUE)
        );

        chartMainPanel.add(barPanel, "bar");

        stackedBarChartButton.setText("Stacked Bar Chart");
        stackedBarChartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stackedBarChartButtonActionPerformed(evt);
            }
        });

        ringChartButton.setText("Ring chart");
        ringChartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ringChartButtonActionPerformed(evt);
            }
        });

        barChartButton.setText("Bar Chart");
        barChartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                barChartButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout analyzeVotesPanelLayout = new javax.swing.GroupLayout(analyzeVotesPanel);
        analyzeVotesPanel.setLayout(analyzeVotesPanelLayout);
        analyzeVotesPanelLayout.setHorizontalGroup(
            analyzeVotesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(analyzeTextPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(analyzeVotesPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(analyzeVotesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chartMainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(analyzeVotesPanelLayout.createSequentialGroup()
                        .addComponent(barChartButton)
                        .addGap(20, 20, 20)
                        .addComponent(stackedBarChartButton)
                        .addGap(20, 20, 20)
                        .addComponent(ringChartButton)))
                .addGap(20, 20, 20))
        );
        analyzeVotesPanelLayout.setVerticalGroup(
            analyzeVotesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(analyzeVotesPanelLayout.createSequentialGroup()
                .addComponent(analyzeTextPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chartMainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                .addGroup(analyzeVotesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(barChartButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stackedBarChartButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ringChartButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        mainPanel.add(analyzeVotesPanel, "analyzePanel");

        profilePanel.setBackground(new java.awt.Color(255, 255, 255));
        profilePanel.setForeground(new java.awt.Color(255, 255, 255));

        profileFirstName.setText(admin.getFirstName());
        profileFirstName.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "First Name"));
        profileFirstName.setMinimumSize(new java.awt.Dimension(12, 74));
        profileFirstName.setPreferredSize(new java.awt.Dimension(12, 74));

        profileLastName.setText(admin.getLastName());
        profileLastName.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Last Name"));
        profileLastName.setMinimumSize(new java.awt.Dimension(12, 74));
        profileLastName.setPreferredSize(new java.awt.Dimension(12, 74));

        profileDate.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Date of birth"));
        profileDate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("yyyy-MM-dd"))));
        profileDate.setText(admin.getDOB());
        profileDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                profileDateActionPerformed(evt);
            }
        });

        profileEmail.setText(admin.getEmail());
        profileEmail.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Email"));
        profileEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                profileEmailActionPerformed(evt);
            }
        });

        profilePassword.setText(admin.getPassword());
        profilePassword.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Password"));
        profilePassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                profilePasswordActionPerformed(evt);
            }
        });

        profileText.setBackground(new java.awt.Color(255, 255, 255));
        profileText.setFont(new java.awt.Font("Montserrat Medium", 0, 36)); // NOI18N
        profileText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        profileText.setText("Profile");

        profileSaveButton.setText("Save Modifications");
        profileSaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                profileSaveButtonActionPerformed(evt);
            }
        });

        profileBackButton.setText("Go Back");
        profileBackButton.setMaximumSize(new java.awt.Dimension(76, 32));
        profileBackButton.setMinimumSize(new java.awt.Dimension(76, 32));
        profileBackButton.setPreferredSize(new java.awt.Dimension(76, 32));
        profileBackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                profileBackButtonActionPerformed(evt);
            }
        });

        profileCaptionText.setBackground(new java.awt.Color(255, 255, 255));
        profileCaptionText.setFont(new java.awt.Font("Montserrat Medium", 0, 18)); // NOI18N
        profileCaptionText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        profileCaptionText.setText("Change your informations");

        javax.swing.GroupLayout profilePanelLayout = new javax.swing.GroupLayout(profilePanel);
        profilePanel.setLayout(profilePanelLayout);
        profilePanelLayout.setHorizontalGroup(
            profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(profileText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(profileCaptionText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, profilePanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(profilePanelLayout.createSequentialGroup()
                        .addComponent(profileBackButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(profileSaveButton))
                    .addGroup(profilePanelLayout.createSequentialGroup()
                        .addGroup(profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(profilePassword, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(profileEmail)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, profilePanelLayout.createSequentialGroup()
                                .addComponent(profileFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(profileLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(profileDate, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 271, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );
        profilePanelLayout.setVerticalGroup(
            profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, profilePanelLayout.createSequentialGroup()
                .addComponent(profileText)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(profileCaptionText)
                .addGap(58, 58, 58)
                .addGroup(profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(profileLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(profileDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(profileFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(profileEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(profilePassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 375, Short.MAX_VALUE)
                .addGroup(profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(profileBackButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(profileSaveButton))
                .addContainerGap())
        );

        mainPanel.add(profilePanel, "profile");

        MenuBar.setBorder(javax.swing.BorderFactory.createCompoundBorder());

        newMenu.setText("New");

        newMenuVoter.setText("Voter");
        newMenuVoter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newMenuVoterActionPerformed(evt);
            }
        });
        newMenu.add(newMenuVoter);

        newMenuCandidate.setText("Candidate");
        newMenuCandidate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newMenuCandidateActionPerformed(evt);
            }
        });
        newMenu.add(newMenuCandidate);

        MenuBar.add(newMenu);

        editMenu.setText("Edit");

        editMenuVoter.setText("Voter");
        editMenuVoter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editMenuVoterActionPerformed(evt);
            }
        });
        editMenu.add(editMenuVoter);

        editMenuCandidate.setText("Candidate");
        editMenuCandidate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editMenuCandidateActionPerformed(evt);
            }
        });
        editMenu.add(editMenuCandidate);

        MenuBar.add(editMenu);

        setJMenuBar(MenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(leftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 844, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(leftPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /** 
     * Change the enabled buttons and the color when status change 
     * @param status
     */
    private void UpdateStatusButtons(JLabel status){
        switch(status.getText())
        {
            case "Active" :
                status.setForeground(GREEN_COLOR);
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                pauseButton.setEnabled(true);
                break;
            case "Paused" :
                status.setForeground(BLUE_COLOR);
                startButton.setEnabled(false);
                stopButton.setEnabled(false);
                pauseButton.setEnabled(true);
                break;
            case "Done":
                status.setForeground(RED_COLOR);
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                pauseButton.setEnabled(false);
                break;
            default :
                status.setText("Done");
                UpdateStatusButtons(status);
                break;
        }
    }
    
    private void newMenuVoterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newMenuVoterActionPerformed
        cards.show(mainPanel, "addVoter");
    }//GEN-LAST:event_newMenuVoterActionPerformed

    private void addVoterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addVoterButtonActionPerformed
        if (addVoterFirstName.getText().equals(""))
            JOptionPane.showMessageDialog(null, "Please enter a first name." , this.getTitle(), 1 );
        else if (addVoterLastName.getText().equals(""))
            JOptionPane.showMessageDialog(null, "Please enter a last name." , this.getTitle(), 1 );
        else if (addVoterEmail.getText().equals(""))
            JOptionPane.showMessageDialog(null, "Please enter an email adress." , this.getTitle(), 1 );
        else if (new String(addVoterPassword.getPassword()).equals(""))
            JOptionPane.showMessageDialog(null, "Please enter a password." , this.getTitle(), 1 );
        else if (addVoterDate.getText().equals("")||Integer.parseInt(addVoterDate.getText().substring(0, 4))<1900||Integer.parseInt(addVoterDate.getText().substring(0, 4))>2002)
            JOptionPane.showMessageDialog(null, "Please enter a correct date of birth." ,this.getTitle(), 1 );
        else if (jComboBox1.getItemAt(jComboBox1.getSelectedIndex())==null||jComboBox1.getItemAt(jComboBox1.getSelectedIndex())=="STATE")
            JOptionPane.showMessageDialog(null, "Please enter a state" , this.getTitle(), 1 );
        else
        {   
            if (admin.addVoter(new Voter(addVoterEmail.getText(), new String(addVoterPassword.getPassword()), addVoterDate.getText(), addVoterFirstName.getText(), addVoterLastName.getText(), jComboBox1.getItemAt(jComboBox1.getSelectedIndex())  , null)))
                JOptionPane.showMessageDialog(null, "Added successfully " + addVoterFirstName.getText() + " " + addVoterLastName.getText() + " to the database" , this.getTitle(), 1 );
            else
                JOptionPane.showMessageDialog(null, "An error occured. Email adress is already in use." , this.getTitle(), 1 );
        }
        
            
        
    }//GEN-LAST:event_addVoterButtonActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void addVoterFirstNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addVoterFirstNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addVoterFirstNameActionPerformed

    private void addVoterDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addVoterDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addVoterDateActionPerformed

    private void addCandidateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCandidateButtonActionPerformed

        if (addCandidateFirstName.getText().equals(""))
            JOptionPane.showMessageDialog(null, "Please enter a first name." , this.getTitle(), 1 );
        else if (addCandidateLastName.getText().equals(""))
            JOptionPane.showMessageDialog(null, "Please enter a last name." , this.getTitle(), 1 );
        else if (addCandidateEmail.getText().equals(""))
            JOptionPane.showMessageDialog(null, "Please enter an email adress." , this.getTitle(), 1 );
        else if (new String(addCandidatePassword.getPassword()).equals(""))
            JOptionPane.showMessageDialog(null, "Please enter a password." , this.getTitle(), 1 );
        else if (addCandidateDate.getText().equals("")||Integer.parseInt(addCandidateDate.getText().substring(0, 4))<1900||Integer.parseInt(addCandidateDate.getText().substring(0, 4))>2002)
            JOptionPane.showMessageDialog(null, "Please enter a correct date of birth." , this.getTitle(), 1 );
        else if (jComboBox2.getItemAt(jComboBox2.getSelectedIndex())==null||jComboBox2.getItemAt(jComboBox2.getSelectedIndex())=="PARTY")
            JOptionPane.showMessageDialog(null, "Please enter a state" , this.getTitle(), 1 );
        else
        {
            if (admin.addCandidate(new Candidate(addCandidateEmail.getText(), new String(addCandidatePassword.getPassword()), 
                    addCandidateDate.getText(), addCandidateFirstName.getText(), addCandidateLastName.getText(), 
                    jComboBox2.getItemAt(jComboBox2.getSelectedIndex()), addCandidateDescription.getText())))
            {
                
                try {
                    if (imageRetrieved.getIcon()!=null) //If there's an image
                        if (admin.uploadImage(addCandidateEmail.getText(), imageChooser.getSelectedFile())) //Then try to upload it
                            JOptionPane.showMessageDialog(null, "Added successfully along with it's picture " + addCandidateFirstName.getText() + " " + addCandidateLastName.getText() + " to the database" , this.getTitle(), 1 );
                        else
                            JOptionPane.showMessageDialog(null, "Added successfully " + addCandidateFirstName.getText() + " " + addCandidateLastName.getText() + " to the database" , this.getTitle(), 1 );
                   //We update the charts because we introduced a new candidate 
                    updateCharts();
                    
                    
                } catch (IOException ex) {
                    Logger.getLogger(GUI_Official.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
            }
            else
                JOptionPane.showMessageDialog(null, "An error occured. Email adress is already in use." , this.getTitle(), 1 );
        }
            
    }//GEN-LAST:event_addCandidateButtonActionPerformed

    private void addCandidateFirstNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCandidateFirstNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addCandidateFirstNameActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void addCandidateDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCandidateDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addCandidateDateActionPerformed

    private void newMenuCandidateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newMenuCandidateActionPerformed
        cards.show(mainPanel, "addCandidate");
    }//GEN-LAST:event_newMenuCandidateActionPerformed

    private void redOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_redOptionActionPerformed
        actualColor=RED_COLOR;
        leftPanel.setBackground(actualColor);
        f.saveColor(actualColor);
    }//GEN-LAST:event_redOptionActionPerformed

    private void greenOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_greenOptionActionPerformed
        actualColor=GREEN_COLOR;
        leftPanel.setBackground(actualColor);
        f.saveColor(actualColor);
    }//GEN-LAST:event_greenOptionActionPerformed

    private void blueOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_blueOptionActionPerformed
        actualColor=BLUE_COLOR;
        leftPanel.setBackground(actualColor);
        f.saveColor(actualColor);
    }//GEN-LAST:event_blueOptionActionPerformed

    private void settingsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsButtonActionPerformed
        settingsPopUp.show(this, settingsButton.getX(), settingsButton.getY()-settingsPopUp.getHeight()/2-2);
    }//GEN-LAST:event_settingsButtonActionPerformed

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        dispose();
        GUI_Start main_menu;
        try {
            main_menu = new GUI_Start();
            main_menu.embeddedMain();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(GUI_Official.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_exitButtonActionPerformed

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
       int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to conclude the election and announce the winner ?", this.getTitle(), JOptionPane.YES_NO_OPTION);
       if (reply == JOptionPane.YES_OPTION) {
            statusText.setText("Done");
            UpdateStatusButtons(statusText);
        if (!admin.addElectionEntry(statusText.getText()))
            JOptionPane.showMessageDialog(null, "Something happened with the entry in the status Database." , this.getTitle(), 1 );
       }
    }//GEN-LAST:event_stopButtonActionPerformed

    private void addVoterBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addVoterBackActionPerformed
        cards.show(mainPanel, "mainMenu");
    }//GEN-LAST:event_addVoterBackActionPerformed

    private void addCandidateBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCandidateBackActionPerformed
        cards.show(mainPanel, "mainMenu");
        mainMenuChart=createVotesBarChart(createBarDataset());
    }//GEN-LAST:event_addCandidateBackActionPerformed

    private void editVotersBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editVotersBackActionPerformed
        cards.show(mainPanel, "mainMenu");
    }//GEN-LAST:event_editVotersBackActionPerformed

    private void editMenuVoterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editMenuVoterActionPerformed
        cards.show(mainPanel, "editVoter");
        
        String[] names = {"First name", "Last name", "Email", "Password", "Date of Birth", "State"};
        
        voterTableModel = new DefaultTableModel(admin.getAllVoters(), names){
        
        /** Making the rows uneditable so that we can't modify the information */ 
        @Override
        public boolean isCellEditable(int row, int column){  
          return false;  
        }
        };

        editVotersTable.setModel(voterTableModel);
        editVotersTable.getTableHeader().setReorderingAllowed(false);
        editVotersTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
        @Override
        public void valueChanged(ListSelectionEvent event) {
            int row=editVotersTable.getSelectedRow();
            if (row!=-1){
                editVoterFirstName.setText((String)editVotersTable.getValueAt(row, 0));
                editVoterLastName.setText((String)editVotersTable.getValueAt(row, 1));
                editVoterEmail.setText((String)editVotersTable.getValueAt(row, 2));
                editVoterPassword.setText((String)editVotersTable.getValueAt(row, 3));
                editVoterDate.setText((String)editVotersTable.getValueAt(row, 4));
                jComboBox3.getModel().setSelectedItem((String)editVotersTable.getValueAt(row, 5));
            }   
        }
    });
        
        if (editVotersEditPanel.isVisible())
           editInvisible(editVotersEditPanel, editVotersDelete);

    }//GEN-LAST:event_editMenuVoterActionPerformed

    /** updates the voter table after an operation */
    private void updateVoterTable(DefaultTableModel tModel, JTable table){
        String[] names = {"First name", "Last name", "Email", "Password", "Date of Birth", "State"};
        tModel.setDataVector(admin.getAllVoters(), names);
        table.updateUI();
    }
    /** updates the candidates table after an operation */
     private void updateCandidateTable(DefaultTableModel tModel, JTable table){
        String[] names = {"First name", "Last name", "Email", "Password", "Date of Birth", "Party", "Description"};
        tModel.setDataVector(admin.getAllCandidates(), names);
        table.updateUI();
    }
    
    private void editVoterFirstNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editVoterFirstNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editVoterFirstNameActionPerformed

    private void editVoterDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editVoterDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editVoterDateActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void editVotersEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editVotersEditActionPerformed
       
       if (editVotersTable.getSelectedRow()!=-1)
           if (editVotersEditPanel.isVisible())
                 editInvisible(editVotersEditPanel, editVotersDelete);
           else
                 editVisible(editVotersEditPanel, editVotersDelete);
       else 
           JOptionPane.showMessageDialog(null, "You have to select a row before editing" , this.getTitle(), 1 );
    }//GEN-LAST:event_editVotersEditActionPerformed

    private void editVotersDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editVotersDeleteActionPerformed
        int row=editVotersTable.getSelectedRow();
        if (row!=-1)
        {
            String firstName = (String) editVotersTable.getValueAt(row, 0);
                String lastName = (String) editVotersTable.getValueAt(row, 1);
                int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + firstName + " " + lastName + " from the database ?", this.getTitle(), JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    if (admin.removeUser((String) editVotersTable.getValueAt(row, 2)))
                    {
                        JOptionPane.showMessageDialog(null, "You have successfully removed " + firstName + " " + lastName + " from the database" , this.getTitle(), 1 );
                        updateVoterTable(voterTableModel, editVotersTable);
                        updateCharts();
                    }
                    else
                        JOptionPane.showMessageDialog(null, firstName + " " + lastName + " has not been deleted from the database due to an error" , this.getTitle(), 1 );
                } 
        }
        else 
           JOptionPane.showMessageDialog(null, "You have to select a row before deleting" , this.getTitle(), 1 );
    }//GEN-LAST:event_editVotersDeleteActionPerformed

    private void editVotersEditOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editVotersEditOKActionPerformed
        if (Integer.parseInt(editVoterDate.getText().substring(0, 4))>1900 && Integer.parseInt(editVoterDate.getText().substring(0, 4))<2002)
           if (admin.modifyVoter(new Voter(editVoterEmail.getText(), editVoterPassword.getText(), editVoterDate.getText(), editVoterFirstName.getText(), 
                   editVoterLastName.getText(), (String)jComboBox3.getModel().getSelectedItem(), null) , (String)editVotersTable.getValueAt(editVotersTable.getSelectedRow(), 2)))
           {
               JOptionPane.showMessageDialog(null, "Informations successfully edited" , this.getTitle(), 1 );
               updateVoterTable(voterTableModel, editVotersTable);
               editInvisible(editVotersEditPanel, editVotersDelete);
               updateCharts();
           }
           else
               JOptionPane.showMessageDialog(null, "A problem occured. Your modification was not taken into account." , this.getTitle(), 1 );
        else
            JOptionPane.showMessageDialog(null, "Date has to be between 1900 and 2020." , this.getTitle(), 1 );
    }//GEN-LAST:event_editVotersEditOKActionPerformed

    private void editCandidatesBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editCandidatesBackActionPerformed
        cards.show(mainPanel, "mainMenu");
    }//GEN-LAST:event_editCandidatesBackActionPerformed

    private void editCandidatesEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editCandidatesEditActionPerformed
        if (editCandidatesTable.getSelectedRow()!=-1)
           if (editCandidatesEditPanel.isVisible())
           {
                 editInvisible(editCandidatesEditPanel, editCandidatesDelete);
           }
           else
           {
                 editVisible(editCandidatesEditPanel, editCandidatesDelete);
           }
       else 
           JOptionPane.showMessageDialog(null, "You have to select a row before editing" , this.getTitle(), 1 );
    }//GEN-LAST:event_editCandidatesEditActionPerformed

    private void editCandidatesDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editCandidatesDeleteActionPerformed
        int row=editCandidatesTable.getSelectedRow();
        if (row!=-1)
        {
            String firstName = (String) editCandidatesTable.getValueAt(row, 0);
                String lastName = (String) editCandidatesTable.getValueAt(row, 1);
                int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + firstName + " " + lastName + " from the database ?", this.getTitle(), JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    if (admin.removeUser((String) editCandidatesTable.getValueAt(row, 2)))
                    {
                        JOptionPane.showMessageDialog(null, "You have successfully removed " + firstName + " " + lastName + " from the database" , this.getTitle(), 1 );
                        updateCandidateTable(candidateTableModel, editCandidatesTable);
                        updateCharts();
                        
                    }
                    else
                        JOptionPane.showMessageDialog(null, firstName + " " + lastName + " has not been deleted from the database due to an error" , this.getTitle(), 1 );
                } 
        }
        else 
           JOptionPane.showMessageDialog(null, "You have to select a row before deleting" , this.getTitle(), 1 );
    }//GEN-LAST:event_editCandidatesDeleteActionPerformed

    private void editCandidatesEditOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editCandidatesEditOKActionPerformed
      
        if (Integer.parseInt(editCandidatesDate.getText().substring(0, 4))>1900 && Integer.parseInt(editCandidatesDate.getText().substring(0, 4))<2002)
            if (admin.modifyCandidate(new Candidate(editCandidatesEmail.getText(), editCandidatesPassword.getText(), editCandidatesDate.getText(), editCandidatesFirstName.getText(), 
                       editCandidatesLastName.getText(), (String)jComboBox4.getModel().getSelectedItem(), editCandidatesDescription.getText()),
                    (String)editCandidatesTable.getValueAt(editCandidatesTable.getSelectedRow(), 2)))
               {
                   try {
                    if (imageLabel.getIcon()!=null) //If there's an image
                        if (!admin.uploadImage(editCandidatesEmail.getText(), imageChooser.getSelectedFile())) //Then try to upload it
                            JOptionPane.showMessageDialog(null, "Problem uploading the image" , this.getTitle(), 1 );       
                } catch (IOException ex) {
                    Logger.getLogger(GUI_Official.class.getName()).log(Level.SEVERE, null, ex);
                }
                   
                   JOptionPane.showMessageDialog(null, "Informations successfully edited" , this.getTitle(), 1 );
                   updateCandidateTable(candidateTableModel, editCandidatesTable);

                   editInvisible(editCandidatesEditPanel, editCandidatesDelete);
                   updateCharts();
               }
               else
                   JOptionPane.showMessageDialog(null, "A problem occured. Your modification was not taken into account." , this.getTitle(), 1 );
        else{
            JOptionPane.showMessageDialog(null, "Date has to be between 1900 and 2002." , this.getTitle(), 1 );
        }
    }//GEN-LAST:event_editCandidatesEditOKActionPerformed

    private void editCandidatesFirstNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editCandidatesFirstNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editCandidatesFirstNameActionPerformed

    private void editCandidatesDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editCandidatesDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editCandidatesDateActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void editCandidatesPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editCandidatesPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editCandidatesPasswordActionPerformed

    private void editCandidatesDescriptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editCandidatesDescriptionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editCandidatesDescriptionActionPerformed

    private void editMenuCandidateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editMenuCandidateActionPerformed
        cards.show(mainPanel, "editCandidate");
        String[] names = {"First name", "Last name", "Email", "Password", "Date of Birth", "Party", "Description"};
        
        candidateTableModel = new DefaultTableModel(admin.getAllCandidates(), names){
        
        /** Making the rows uneditable so that we can't modify the information */ 
        @Override
        public boolean isCellEditable(int row, int column){  
          return false;  
        }
        };

        editCandidatesTable.setModel(candidateTableModel);
        editCandidatesTable.getTableHeader().setReorderingAllowed(false);
        editCandidatesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
        @Override
        public void valueChanged(ListSelectionEvent event) {
            int row=editCandidatesTable.getSelectedRow();
            if (row!=-1){
                editCandidatesFirstName.setText((String)editCandidatesTable.getValueAt(row, 0));
                editCandidatesLastName.setText((String)editCandidatesTable.getValueAt(row, 1));
                editCandidatesEmail.setText((String)editCandidatesTable.getValueAt(row, 2));
                editCandidatesPassword.setText((String)editCandidatesTable.getValueAt(row, 3));
                editCandidatesDate.setText((String)editCandidatesTable.getValueAt(row, 4));
                jComboBox4.getModel().setSelectedItem((String)editCandidatesTable.getValueAt(row, 5));
                editCandidatesDescription.setText((String)editCandidatesTable.getValueAt(row, 6));
                byte[] myImage = admin.getPicture((String)editCandidatesTable.getValueAt(row, 2));
                if (myImage!=null)
                {
                    ImageIcon image = new ImageIcon(myImage);
                    ImageIcon scaledImage = new ImageIcon(image.getImage().getScaledInstance(imageLabel.getWidth(),imageLabel.getHeight() , java.awt.Image.SCALE_SMOOTH));
                    imageLabel.setText("");
                    imageLabel.setIcon(scaledImage);
                    
                }
                else
                {
                    imageLabel.setText("There is no picture available.");
                    imageLabel.setIcon(null);
                }
                
            }   
        }
    });
        
        if (editCandidatesEditPanel.isVisible())
        {
           editInvisible(editCandidatesEditPanel, editCandidatesDelete);
        }
    }//GEN-LAST:event_editMenuCandidateActionPerformed

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to start the election and erase all votes ?", this.getTitle(), JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            statusText.setText("Active");
            UpdateStatusButtons(statusText);
            if(admin.resetVotes())
                JOptionPane.showMessageDialog(null, "Votes were successfully cleared. Election will now start." , this.getTitle(), 1 );
            else
                JOptionPane.showMessageDialog(null, "Something happened. Votes were not deleted." , this.getTitle(), 1 );
            if (!admin.addElectionEntry(statusText.getText()))
                JOptionPane.showMessageDialog(null, "Something happened with the entry in the status Database." , this.getTitle(), 1 );

        }
        
    }//GEN-LAST:event_startButtonActionPerformed

    private void pauseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pauseButtonActionPerformed
        String lastEntry= admin.getLastStatus();
        if (lastEntry.equals("Paused"))
            statusText.setText("Active");
        else
            statusText.setText("Paused");
        
        UpdateStatusButtons(statusText);
        if (!admin.addElectionEntry(statusText.getText()))
                JOptionPane.showMessageDialog(null, "Something happened with the entry in the status Database." , this.getTitle(), 1 );
        
    }//GEN-LAST:event_pauseButtonActionPerformed

    private void profileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_profileButtonActionPerformed
        cards.show(mainPanel, "profile");
    }//GEN-LAST:event_profileButtonActionPerformed

    private void dataAnalysisButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dataAnalysisButtonActionPerformed
        cards.show(mainPanel, "analyzePanel");
        cardsChart.show(chartMainPanel, "idle");
        
    }//GEN-LAST:event_dataAnalysisButtonActionPerformed

    private void profileDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_profileDateActionPerformed

    }//GEN-LAST:event_profileDateActionPerformed

    private void profileSaveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_profileSaveButtonActionPerformed
        
        if (profileEmail.getText().equals(""))
            JOptionPane.showMessageDialog(null, "Please enter a valid email" , this.getTitle(), 1 );
        else if (profilePassword.getText().equals(""))
            JOptionPane.showMessageDialog(null, "Please enter a valid password" , this.getTitle(), 1 );
        else if (profileFirstName.getText().equals(""))
            JOptionPane.showMessageDialog(null, "Please enter a valid first name" , this.getTitle(), 1 );
        else if (profileLastName.getText().equals(""))
            JOptionPane.showMessageDialog(null, "Please enter a valid last name", this.getTitle(), 1 );
        else if (profileDate.getText().equals("")||Integer.parseInt(profileDate.getText().substring(0, 4))<1900 || Integer.parseInt(profileDate.getText().substring(0, 4))>2002)
            JOptionPane.showMessageDialog(null, "Please enter a valid date" , this.getTitle(), 1 );
        else{
            String[] info = new String[]{ profileEmail.getText(), profilePassword.getText(), profileFirstName.getText(), profileLastName.getText(), profileDate.getText()};
            if(admin.modifySelf(info))
            {
                JOptionPane.showMessageDialog(null, "Informations successfully edited" , this.getTitle(), 1 );
                mainMenuDescription.setText("Welcome to the admin center " +  profileFirstName.getText() + " " + profileLastName.getText() + " ! ");
            }
            else
            JOptionPane.showMessageDialog(null, "A problem occured. Your modification was not taken into account." , this.getTitle(), 1 );
        }
    }//GEN-LAST:event_profileSaveButtonActionPerformed

    private void profileBackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_profileBackButtonActionPerformed
        cards.show(mainPanel, "mainMenu");
    }//GEN-LAST:event_profileBackButtonActionPerformed

    private void profilePasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_profilePasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_profilePasswordActionPerformed

    private void profileEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_profileEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_profileEmailActionPerformed

    private void stackedBarChartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stackedBarChartButtonActionPerformed
        cardsChart.show(chartMainPanel, "stackedBar");
    }//GEN-LAST:event_stackedBarChartButtonActionPerformed

    private void ringChartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ringChartButtonActionPerformed
       cardsChart.show(chartMainPanel, "ringPlot");
    }//GEN-LAST:event_ringChartButtonActionPerformed

    private void barChartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_barChartButtonActionPerformed
        cardsChart.show(chartMainPanel, "bar");
    }//GEN-LAST:event_barChartButtonActionPerformed

    private void mainMenuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainMenuButtonActionPerformed
        cards.show(mainPanel, "mainMenu");
    }//GEN-LAST:event_mainMenuButtonActionPerformed

    private void addCandidateSearchImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCandidateSearchImageActionPerformed
        int returnVal = imageChooser.showOpenDialog(this);
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File f = imageChooser.getSelectedFile();
            ImageIcon myImage = new ImageIcon(f.toString());
            ImageIcon scaledImage = new ImageIcon(myImage.getImage().getScaledInstance(imageRetrieved.getWidth(),imageRetrieved.getHeight() , java.awt.Image.SCALE_SMOOTH));
            imageRetrieved.setText("");
            imageRetrieved.setIcon(scaledImage);
            dataPath.setText(f.getAbsolutePath());
        } 
        else 
            JOptionPane.showMessageDialog(null, "Couldn't access the file." , this.getTitle(), 1 );

    }//GEN-LAST:event_addCandidateSearchImageActionPerformed

    private void addCandidateClearImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCandidateClearImageActionPerformed
        imageRetrieved.setText("Select an image");
        imageRetrieved.setIcon(null);
        dataPath.setText("");
    }//GEN-LAST:event_addCandidateClearImageActionPerformed

    private void editCandidatesChangeImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editCandidatesChangeImageActionPerformed
        int returnVal = imageChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File f = imageChooser.getSelectedFile();
            ImageIcon myImage = new ImageIcon(f.toString());
            ImageIcon scaledImage = new ImageIcon(myImage.getImage().getScaledInstance(imageRetrieved.getWidth(),imageRetrieved.getHeight() , java.awt.Image.SCALE_SMOOTH));
            imageLabel.setText("");
            imageLabel.setIcon(scaledImage);
        } 
        else 
            JOptionPane.showMessageDialog(null, "Couldn't access the file." , this.getTitle(), 1 );
    }//GEN-LAST:event_editCandidatesChangeImageActionPerformed
    
    /** Set a panel to visible and enabled, and put a button to disabled
     * @param edit
     * @param delete */
    public void editVisible(JPanel edit, JButton delete){
        edit.setVisible(true);
        edit.setEnabled(true);
        delete.setEnabled(false);
    }
    
    /** Set a panel to invisible and disabled, and put a button to enabled
     * @param edit
     * @param delete */
    public void editInvisible(JPanel edit, JButton delete){
        edit.setVisible(false);
        edit.setEnabled(false);
        delete.setEnabled(true);
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AnalyzeCaption;
    private javax.swing.JLabel AnalyzeText;
    private javax.swing.JMenuBar MenuBar;
    private javax.swing.JButton addCandidateBack;
    private javax.swing.JButton addCandidateButton;
    private javax.swing.JLabel addCandidateCaption;
    private javax.swing.JButton addCandidateClearImage;
    private javax.swing.JFormattedTextField addCandidateDate;
    private javax.swing.JTextField addCandidateDescription;
    private javax.swing.JTextField addCandidateEmail;
    private javax.swing.JTextField addCandidateFirstName;
    private javax.swing.JTextField addCandidateLastName;
    private javax.swing.JPanel addCandidatePanel;
    private javax.swing.JPasswordField addCandidatePassword;
    private javax.swing.JButton addCandidateSearchImage;
    private javax.swing.JLabel addCandidateText;
    private javax.swing.JPanel addCandidateTextPanel;
    private javax.swing.JButton addVoterBack;
    private javax.swing.JButton addVoterButton;
    private javax.swing.JLabel addVoterCaption;
    private javax.swing.JFormattedTextField addVoterDate;
    private javax.swing.JTextField addVoterEmail;
    private javax.swing.JTextField addVoterFirstName;
    private javax.swing.JTextField addVoterLastName;
    private javax.swing.JPanel addVoterPanel;
    private javax.swing.JPasswordField addVoterPassword;
    private javax.swing.JLabel addVoterText;
    private javax.swing.JPanel addVoterTextPanel;
    private javax.swing.JPanel analyzeTextPanel;
    private javax.swing.JPanel analyzeVotesPanel;
    private javax.swing.JButton barChartButton;
    private javax.swing.JPanel barPanel;
    private javax.swing.JMenuItem blueOption;
    private javax.swing.JPanel chartMainPanel;
    private javax.swing.JButton dataAnalysisButton;
    private javax.swing.JLabel dataPath;
    private javax.swing.JPanel editCandidatePanel;
    private javax.swing.JButton editCandidatesBack;
    private javax.swing.JLabel editCandidatesCaption;
    private javax.swing.JButton editCandidatesChangeImage;
    private javax.swing.JFormattedTextField editCandidatesDate;
    private javax.swing.JButton editCandidatesDelete;
    private javax.swing.JTextField editCandidatesDescription;
    private javax.swing.JButton editCandidatesEdit;
    private javax.swing.JButton editCandidatesEditOK;
    private javax.swing.JPanel editCandidatesEditPanel;
    private javax.swing.JTextField editCandidatesEmail;
    private javax.swing.JTextField editCandidatesFirstName;
    private javax.swing.JTextField editCandidatesLastName;
    private javax.swing.JTextField editCandidatesPassword;
    private javax.swing.JScrollPane editCandidatesScrollPanel;
    private javax.swing.JTable editCandidatesTable;
    private javax.swing.JLabel editCandidatesText;
    private javax.swing.JPanel editCandidatesTextPanel;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem editMenuCandidate;
    private javax.swing.JMenuItem editMenuVoter;
    private javax.swing.JFormattedTextField editVoterDate;
    private javax.swing.JTextField editVoterEmail;
    private javax.swing.JTextField editVoterFirstName;
    private javax.swing.JTextField editVoterLastName;
    private javax.swing.JTextField editVoterPassword;
    private javax.swing.JButton editVotersBack;
    private javax.swing.JLabel editVotersCaption;
    private javax.swing.JButton editVotersDelete;
    private javax.swing.JButton editVotersEdit;
    private javax.swing.JButton editVotersEditOK;
    private javax.swing.JPanel editVotersEditPanel;
    private javax.swing.JPanel editVotersPanel;
    private javax.swing.JScrollPane editVotersScrollPanel;
    private javax.swing.JTable editVotersTable;
    private javax.swing.JLabel editVotersText;
    private javax.swing.JPanel editVotersTextPanel;
    private javax.swing.JButton exitButton;
    private javax.swing.JMenuItem greenOption;
    private javax.swing.JPanel idlePanel;
    private javax.swing.JFileChooser imageChooser;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JLabel imageRetrieved;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextArea jTextArea4;
    private javax.swing.JTextArea jTextArea5;
    private javax.swing.JTextArea jTextArea6;
    private javax.swing.JTextArea jTextArea7;
    private javax.swing.JTextArea jTextArea8;
    private javax.swing.JTextArea jTextArea9;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JPanel mainMenu;
    private javax.swing.JButton mainMenuButton;
    private javax.swing.JLabel mainMenuDescription;
    private javax.swing.JLabel mainMenuText;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenu newMenu;
    private javax.swing.JMenuItem newMenuCandidate;
    private javax.swing.JMenuItem newMenuVoter;
    private javax.swing.JButton pauseButton;
    private javax.swing.JButton profileBackButton;
    private javax.swing.JButton profileButton;
    private javax.swing.JLabel profileCaptionText;
    private javax.swing.JFormattedTextField profileDate;
    private javax.swing.JTextField profileEmail;
    private javax.swing.JTextField profileFirstName;
    private javax.swing.JTextField profileLastName;
    private javax.swing.JPanel profilePanel;
    private javax.swing.JTextField profilePassword;
    private javax.swing.JButton profileSaveButton;
    private javax.swing.JLabel profileText;
    private javax.swing.JMenuItem redOption;
    private javax.swing.JButton ringChartButton;
    private javax.swing.JPanel ringPlotPanel;
    private javax.swing.JButton settingsButton;
    private javax.swing.JPopupMenu settingsPopUp;
    private javax.swing.JButton stackedBarChartButton;
    private javax.swing.JPanel stackedBarPanel;
    private javax.swing.JButton startButton;
    private javax.swing.JLabel statusText;
    private javax.swing.JButton stopButton;
    private javax.swing.JPanel textPanel;
    private javax.swing.JLabel voteStatusText;
    // End of variables declaration//GEN-END:variables





}
