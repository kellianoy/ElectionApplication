/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import static Application.ElectionApplication.convertSQLtoGregorian;
import Enum.*;
import static GUI.GUI_Start.*;
import User.*;
import java.awt.*;
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
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;



/**
 * GUI used by the officials.
 * @author Keke
 */
public class GUI_Official extends javax.swing.JFrame {

    private Official admin;
    private CardLayout cards;
    private DefaultTableModel voterTableModel;
    private DefaultTableModel candidateTableModel;
    
    private ChartPanel mainMenuChartPanel;
    private JFreeChart mainMenuChart;
    
    private ChartPanel analyzeChartPanel;
    private JFreeChart analyzeChart;
    
    
    public GUI_Official(Official admin) {
        
        this.admin = admin;
        
       
        
        //Chart creation
        mainMenuChart=createVotesBarChart(createBarDataset());
        mainMenuChartPanel= new ChartPanel(mainMenuChart);
        
        analyzeChart=createVotesStackedBarChart(createStackedBarDataset());
        analyzeChartPanel = new ChartPanel(analyzeChart);
        
        
        initComponents();
        leftPanel.setBackground(actualColor);
        setLocationRelativeTo(null);
        setVisible(true);
        
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
        
        //Updating status of vote
        statusText.setText(admin.getLastStatus());
        UpdateStatusButtons(statusText);
        
        
       
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
     * Create a DefaultCategoryDataset corresponding to all the votes for each candidates
     * @return 
     */
    public DefaultCategoryDataset createBarDataset(){
        
        //Dataset setting
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        ArrayList<ArrayList<String>> votes = admin.getVotes();
        for (int i = 0 ; i<votes.size() ; ++i)
            dataset.setValue(Integer.parseInt(votes.get(i).get(1)), "votes", votes.get(i).get(0));
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
        myChart=ChartFactory.createBarChart("Votes", "Candidates", "Number of votes", createBarDataset(), PlotOrientation.VERTICAL, true, true, false);
        
        StandardChartTheme theme = (StandardChartTheme)StandardChartTheme.createJFreeTheme();
        theme.setTitlePaint(actualColor);
        theme.setBarPainter(new StandardBarPainter());
        theme.setRegularFont( new Font("montserrat" , Font.PLAIN , 11));
        theme.apply(myChart);
        
        CategoryPlot p=myChart.getCategoryPlot();
        p.getRangeAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        p.setBackgroundPaint(Color.white);
        p.setRangeGridlinePaint(Color.black);
        
        BarRenderer r=(BarRenderer) p.getRenderer();
        r.setSeriesPaint(0, new Color(50,50,50));
        r.setDrawBarOutline(false);
        r.setMaximumBarWidth(0.1);
       
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
        myChart=ChartFactory.createStackedBarChart("Votes", "Candidates", "Number of votes", createStackedBarDataset(), PlotOrientation.VERTICAL, true, false, false);
        
        StandardChartTheme theme = (StandardChartTheme)StandardChartTheme.createJFreeTheme();
        theme.setTitlePaint(actualColor);
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

        settingsPopUp = new javax.swing.JPopupMenu();
        redOption = new javax.swing.JMenuItem();
        greenOption = new javax.swing.JMenuItem();
        blueOption = new javax.swing.JMenuItem();
        jComboBox5 = new javax.swing.JComboBox<>();
        leftPanel = new javax.swing.JPanel();
        settingsButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        settingsButton1 = new javax.swing.JButton();
        mainPanel = new javax.swing.JPanel();
        mainMenu = new javax.swing.JPanel();
        voteStatusText = new javax.swing.JLabel();
        statusText = new javax.swing.JLabel();
        startButton = new javax.swing.JButton();
        pauseButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        textPanel = new javax.swing.JPanel();
        mainMenuDescription = new javax.swing.JLabel();
        mainMenuText = new javax.swing.JLabel();
        chartPanel = chartPanel=mainMenuChartPanel;
        addVoterPanel = new javax.swing.JPanel();
        addVoterButton = new javax.swing.JButton();
        addVoterFirstName = new javax.swing.JTextField();
        addVoterLastName = new javax.swing.JTextField();
        addVoterEmail = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<String>();
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
        addCandidateEmail = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox<String>();
        addCandidateDate = new javax.swing.JFormattedTextField();
        addCandidatePassword = new javax.swing.JPasswordField();
        addCandidateDescription = new javax.swing.JTextField();
        addCandidateBack = new javax.swing.JButton();
        addCandidateTextPanel = new javax.swing.JPanel();
        addCandidateText = new javax.swing.JLabel();
        addCandidateCaption = new javax.swing.JLabel();
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
        jComboBox3 = new javax.swing.JComboBox<String>();
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
        jComboBox4 = new javax.swing.JComboBox<String>();
        editCandidatesPassword = new javax.swing.JTextField();
        editCandidatesDescriptionPanel = new javax.swing.JPanel();
        editCandidatesDescription = new javax.swing.JTextField();
        analyzeVotesPanel = new javax.swing.JPanel();
        analyzeTextPanel = new javax.swing.JPanel();
        AnalyzeText = new javax.swing.JLabel();
        AnalyzeCaption = new javax.swing.JLabel();
        jPanel1 = jPanel1=analyzeChartPanel;
        analyzeBack = new javax.swing.JButton();
        MenuBar = new javax.swing.JMenuBar();
        newMenu = new javax.swing.JMenu();
        newMenuVoter = new javax.swing.JMenuItem();
        newMenuCandidate = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        editMenuVoter = new javax.swing.JMenuItem();
        editMenuCandidate = new javax.swing.JMenuItem();

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

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Election Simulator");
        setMinimumSize(new java.awt.Dimension(800, 600));
        setResizable(false);

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

        jButton1.setText("Analyze Votes");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        settingsButton1.setBackground(new java.awt.Color(255, 255, 255));
        settingsButton1.setForeground(new java.awt.Color(255, 255, 255));
        settingsButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/userIcon.png"))); // NOI18N
        settingsButton1.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        settingsButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout leftPanelLayout = new javax.swing.GroupLayout(leftPanel);
        leftPanel.setLayout(leftPanelLayout);
        leftPanelLayout.setHorizontalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addComponent(settingsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        leftPanelLayout.setVerticalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, leftPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(exitButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(settingsButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(settingsButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setPreferredSize(new java.awt.Dimension(623, 600));
        mainPanel.setLayout(new java.awt.CardLayout());

        mainMenu.setBackground(new java.awt.Color(255, 255, 255));
        mainMenu.setFocusTraversalPolicyProvider(true);

        voteStatusText.setBackground(new java.awt.Color(255, 255, 255));
        voteStatusText.setFont(new java.awt.Font("Montserrat Medium", 0, 18)); // NOI18N
        voteStatusText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        voteStatusText.setText("Vote Status :");

        statusText.setBackground(new java.awt.Color(255, 255, 255));
        statusText.setFont(new java.awt.Font("Montserrat Medium", 0, 18)); // NOI18N
        statusText.setForeground(new java.awt.Color(0, 185, 16));
        statusText.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
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
            .addComponent(mainMenuText, javax.swing.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE)
            .addComponent(mainMenuDescription, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        textPanelLayout.setVerticalGroup(
            textPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(textPanelLayout.createSequentialGroup()
                .addComponent(mainMenuText)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainMenuDescription)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout chartPanelLayout = new javax.swing.GroupLayout(chartPanel);
        chartPanel.setLayout(chartPanelLayout);
        chartPanelLayout.setHorizontalGroup(
            chartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        chartPanelLayout.setVerticalGroup(
            chartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 353, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout mainMenuLayout = new javax.swing.GroupLayout(mainMenu);
        mainMenu.setLayout(mainMenuLayout);
        mainMenuLayout.setHorizontalGroup(
            mainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(textPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(mainMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(mainMenuLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(mainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(mainMenuLayout.createSequentialGroup()
                                .addComponent(voteStatusText)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(statusText))
                            .addGroup(mainMenuLayout.createSequentialGroup()
                                .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(pauseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(stopButton, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        mainMenuLayout.setVerticalGroup(
            mainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainMenuLayout.createSequentialGroup()
                .addComponent(textPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(mainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(voteStatusText)
                    .addComponent(statusText, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(mainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pauseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stopButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(chartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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
            .addComponent(addVoterCaption, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addContainerGap()
                .addGroup(addVoterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addVoterBack)
                    .addGroup(addVoterPanelLayout.createSequentialGroup()
                        .addGroup(addVoterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addVoterFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addVoterEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(addVoterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addVoterPassword, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addVoterLastName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(addVoterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(addVoterDate)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addVoterPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(addVoterButton)
                .addGap(81, 81, 81))
        );
        addVoterPanelLayout.setVerticalGroup(
            addVoterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addVoterPanelLayout.createSequentialGroup()
                .addComponent(addVoterTextPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(addVoterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addVoterFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addVoterLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addVoterDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(addVoterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addVoterEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addVoterPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addComponent(addVoterButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 273, Short.MAX_VALUE)
                .addComponent(addVoterBack, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        javax.swing.GroupLayout addCandidatePanelLayout = new javax.swing.GroupLayout(addCandidatePanel);
        addCandidatePanel.setLayout(addCandidatePanelLayout);
        addCandidatePanelLayout.setHorizontalGroup(
            addCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addCandidateTextPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(addCandidatePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(addCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, addCandidatePanelLayout.createSequentialGroup()
                        .addComponent(addCandidateBack)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(addCandidatePanelLayout.createSequentialGroup()
                        .addGroup(addCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(addCandidatePanelLayout.createSequentialGroup()
                                .addGroup(addCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(addCandidateFirstName)
                                    .addComponent(addCandidateEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(20, 20, 20)
                                .addGroup(addCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(addCandidatePassword, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(addCandidateLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(addCandidateDescription))
                        .addGroup(addCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(addCandidatePanelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(addCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(addCandidateDate)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(addCandidatePanelLayout.createSequentialGroup()
                                .addGap(86, 86, 86)
                                .addComponent(addCandidateButton)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        addCandidatePanelLayout.setVerticalGroup(
            addCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addCandidatePanelLayout.createSequentialGroup()
                .addComponent(addCandidateTextPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(addCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addCandidatePanelLayout.createSequentialGroup()
                        .addGroup(addCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(addCandidateFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addCandidateLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(addCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(addCandidateEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addCandidatePassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(addCandidateDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addCandidateDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(addCandidatePanelLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(addCandidateButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 250, Short.MAX_VALUE)
                .addComponent(addCandidateBack, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addComponent(editVoterDate, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(editVotersEditOK, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addContainerGap()
                .addGroup(editVotersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(editVotersEditPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(editVotersPanelLayout.createSequentialGroup()
                        .addGroup(editVotersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(editVotersBack)
                            .addGroup(editVotersPanelLayout.createSequentialGroup()
                                .addComponent(editVotersScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(editVotersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(editVotersDelete)
                                    .addComponent(editVotersEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        editVotersPanelLayout.setVerticalGroup(
            editVotersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editVotersPanelLayout.createSequentialGroup()
                .addComponent(editVotersTextPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(editVotersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editVotersPanelLayout.createSequentialGroup()
                        .addComponent(editVotersEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(editVotersDelete))
                    .addComponent(editVotersScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(editVotersEditPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
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
                .addGroup(editCandidatesTextPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(editCandidatesCaption, javax.swing.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE)
                    .addComponent(editCandidatesText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
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

        javax.swing.GroupLayout editCandidatesEditPanelLayout = new javax.swing.GroupLayout(editCandidatesEditPanel);
        editCandidatesEditPanel.setLayout(editCandidatesEditPanelLayout);
        editCandidatesEditPanelLayout.setHorizontalGroup(
            editCandidatesEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editCandidatesEditPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(editCandidatesEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(editCandidatesFirstName, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                    .addComponent(editCandidatesEmail))
                .addGap(26, 26, 26)
                .addGroup(editCandidatesEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(editCandidatesLastName, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                    .addComponent(editCandidatesPassword))
                .addGap(26, 26, 26)
                .addGroup(editCandidatesEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editCandidatesEditPanelLayout.createSequentialGroup()
                        .addComponent(editCandidatesDate, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(editCandidatesEditOK, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(editCandidatesPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        editCandidatesDescriptionPanel.setBackground(new java.awt.Color(255, 255, 255));

        editCandidatesDescription.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Description"));
        editCandidatesDescription.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editCandidatesDescriptionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout editCandidatesDescriptionPanelLayout = new javax.swing.GroupLayout(editCandidatesDescriptionPanel);
        editCandidatesDescriptionPanel.setLayout(editCandidatesDescriptionPanelLayout);
        editCandidatesDescriptionPanelLayout.setHorizontalGroup(
            editCandidatesDescriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editCandidatesDescriptionPanelLayout.createSequentialGroup()
                .addComponent(editCandidatesDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        editCandidatesDescriptionPanelLayout.setVerticalGroup(
            editCandidatesDescriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editCandidatesDescriptionPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(editCandidatesDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout editCandidatePanelLayout = new javax.swing.GroupLayout(editCandidatePanel);
        editCandidatePanel.setLayout(editCandidatePanelLayout);
        editCandidatePanelLayout.setHorizontalGroup(
            editCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(editCandidatesTextPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(editCandidatePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(editCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editCandidatePanelLayout.createSequentialGroup()
                        .addComponent(editCandidatesBack)
                        .addGap(107, 107, 107)
                        .addComponent(editCandidatesDescriptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(editCandidatePanelLayout.createSequentialGroup()
                        .addComponent(editCandidatesScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(editCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(editCandidatesDelete)
                            .addComponent(editCandidatesEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(editCandidatesEditPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        editCandidatePanelLayout.setVerticalGroup(
            editCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editCandidatePanelLayout.createSequentialGroup()
                .addComponent(editCandidatesTextPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(editCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editCandidatePanelLayout.createSequentialGroup()
                        .addComponent(editCandidatesEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(editCandidatesDelete))
                    .addComponent(editCandidatesScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(editCandidatesEditPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(editCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(editCandidatesDescriptionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editCandidatesBack, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            .addGroup(analyzeTextPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(analyzeTextPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(AnalyzeCaption, javax.swing.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE)
                    .addComponent(AnalyzeText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        analyzeTextPanelLayout.setVerticalGroup(
            analyzeTextPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(analyzeTextPanelLayout.createSequentialGroup()
                .addComponent(AnalyzeText)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AnalyzeCaption)
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 403, Short.MAX_VALUE)
        );

        analyzeBack.setText("Go back");
        analyzeBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                analyzeBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout analyzeVotesPanelLayout = new javax.swing.GroupLayout(analyzeVotesPanel);
        analyzeVotesPanel.setLayout(analyzeVotesPanelLayout);
        analyzeVotesPanelLayout.setHorizontalGroup(
            analyzeVotesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(analyzeTextPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(analyzeVotesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(analyzeBack)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        analyzeVotesPanelLayout.setVerticalGroup(
            analyzeVotesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(analyzeVotesPanelLayout.createSequentialGroup()
                .addComponent(analyzeTextPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(analyzeBack)
                .addContainerGap())
        );

        mainPanel.add(analyzeVotesPanel, "analyzePanel");

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
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(leftPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
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
            if (admin.addVoter(new Voter(addVoterEmail.getText(), new String(addVoterPassword.getPassword()), convertSQLtoGregorian(addVoterDate.getText()), addVoterFirstName.getText(), addVoterLastName.getText(), jComboBox1.getItemAt(jComboBox1.getSelectedIndex())  , null)))
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
                    convertSQLtoGregorian(addCandidateDate.getText()), addCandidateFirstName.getText(), addCandidateLastName.getText(), 
                    jComboBox2.getItemAt(jComboBox2.getSelectedIndex()), addCandidateDescription.getText())))
            {
                JOptionPane.showMessageDialog(null, "Added successfully " + addCandidateFirstName.getText() + " " + addCandidateLastName.getText() + " to the database" , this.getTitle(), 1 );
                mainMenuChart=createVotesBarChart(createBarDataset());
                mainMenuChartPanel.setChart(mainMenuChart);
                
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
    }//GEN-LAST:event_redOptionActionPerformed

    private void greenOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_greenOptionActionPerformed
        actualColor=GREEN_COLOR;
        leftPanel.setBackground(actualColor);
    }//GEN-LAST:event_greenOptionActionPerformed

    private void blueOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_blueOptionActionPerformed
        actualColor=BLUE_COLOR;
        leftPanel.setBackground(actualColor);
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
           if (admin.modifyVoter(new Voter(editVoterEmail.getText(), editVoterPassword.getText(), convertSQLtoGregorian(editVoterDate.getText()), editVoterFirstName.getText(), 
                   editVoterLastName.getText(), (String)jComboBox3.getModel().getSelectedItem(), null) , (String)editVotersTable.getValueAt(editVotersTable.getSelectedRow(), 2)))
           {
               JOptionPane.showMessageDialog(null, "Informations successfully edited" , this.getTitle(), 1 );
               updateVoterTable(voterTableModel, editVotersTable);
               editInvisible(editVotersEditPanel, editVotersDelete);
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
                 editInvisible(editCandidatesDescriptionPanel, editCandidatesDelete);
           }
           else
           {
                 editVisible(editCandidatesEditPanel, editCandidatesDelete);
                 editVisible(editCandidatesDescriptionPanel, editCandidatesDelete);
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
                        mainMenuChart=createVotesBarChart(createBarDataset());
                        mainMenuChartPanel.setChart(mainMenuChart);
                        
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
            if (admin.modifyCandidate(new Candidate(editCandidatesEmail.getText(), editCandidatesPassword.getText(), convertSQLtoGregorian(editCandidatesDate.getText()), editCandidatesFirstName.getText(), 
                       editCandidatesLastName.getText(), (String)jComboBox4.getModel().getSelectedItem(), editCandidatesDescription.getText()),
                    (String)editCandidatesTable.getValueAt(editCandidatesTable.getSelectedRow(), 2)))
               {
                   JOptionPane.showMessageDialog(null, "Informations successfully edited" , this.getTitle(), 1 );
                   updateCandidateTable(candidateTableModel, editCandidatesTable);

                   editInvisible(editCandidatesEditPanel, editCandidatesDelete);
                   editInvisible(editCandidatesDescriptionPanel, editCandidatesDelete);

                   mainMenuChart=createVotesBarChart(createBarDataset());
                   mainMenuChartPanel.setChart(mainMenuChart);
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
            }   
        }
    });
        
        if (editCandidatesEditPanel.isVisible()||editCandidatesDescriptionPanel.isVisible())
        {
           editInvisible(editCandidatesEditPanel, editCandidatesDelete);
           editInvisible(editCandidatesDescriptionPanel, editCandidatesDelete);
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

    private void settingsButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_settingsButton1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        cards.show(mainPanel, "analyzePanel");
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void analyzeBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_analyzeBackActionPerformed
        cards.show(mainPanel, "mainMenu");
    }//GEN-LAST:event_analyzeBackActionPerformed
    
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
    private javax.swing.JFormattedTextField addCandidateDate;
    private javax.swing.JTextField addCandidateDescription;
    private javax.swing.JTextField addCandidateEmail;
    private javax.swing.JTextField addCandidateFirstName;
    private javax.swing.JTextField addCandidateLastName;
    private javax.swing.JPanel addCandidatePanel;
    private javax.swing.JPasswordField addCandidatePassword;
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
    private javax.swing.JButton analyzeBack;
    private javax.swing.JPanel analyzeTextPanel;
    private javax.swing.JPanel analyzeVotesPanel;
    private javax.swing.JMenuItem blueOption;
    private javax.swing.JPanel chartPanel;
    private javax.swing.JPanel editCandidatePanel;
    private javax.swing.JButton editCandidatesBack;
    private javax.swing.JLabel editCandidatesCaption;
    private javax.swing.JFormattedTextField editCandidatesDate;
    private javax.swing.JButton editCandidatesDelete;
    private javax.swing.JTextField editCandidatesDescription;
    private javax.swing.JPanel editCandidatesDescriptionPanel;
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
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JPanel mainMenu;
    private javax.swing.JLabel mainMenuDescription;
    private javax.swing.JLabel mainMenuText;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenu newMenu;
    private javax.swing.JMenuItem newMenuCandidate;
    private javax.swing.JMenuItem newMenuVoter;
    private javax.swing.JButton pauseButton;
    private javax.swing.JMenuItem redOption;
    private javax.swing.JButton settingsButton;
    private javax.swing.JButton settingsButton1;
    private javax.swing.JPopupMenu settingsPopUp;
    private javax.swing.JButton startButton;
    private javax.swing.JLabel statusText;
    private javax.swing.JButton stopButton;
    private javax.swing.JPanel textPanel;
    private javax.swing.JLabel voteStatusText;
    // End of variables declaration//GEN-END:variables





}
