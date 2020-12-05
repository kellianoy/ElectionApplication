/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Enum.State;
import static GUI.GUI_Start.BLUE_COLOR;
import static GUI.GUI_Start.GREEN_COLOR;
import static GUI.GUI_Start.RED_COLOR;
import static GUI.GUI_Start.actualColor;
import User.Voter;
import java.awt.CardLayout;
import java.awt.Color;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Calendar;

/**
 *
 * @author rebec
 */
public class GUI_Voter extends javax.swing.JFrame {

    private CardLayout cards;
    private Voter admin ; 
    private String [][] infosCandidate ;
    private int cursorCandidate ; 
    /**
     * Creates new form GUI_Voter
     * @param admin
     */
    public GUI_Voter(Voter admin) throws SQLException, ClassNotFoundException {
        this.admin = admin ; 
        admin.setDataController();
        this.infosCandidate = null ; 
        this.cursorCandidate = 0 ; 
        initComponents();
        
        if(admin.getVotedFor())
        {
            answerVote.setText("You have already voted.");
            answerVote.setForeground(GREEN_COLOR);
        }
        
        setLocationRelativeTo(null);
        setVisible(true);
        
        stateComboBox.addItem("STATE");
        stateComboBox.setSelectedItem("STATE");
        //Setting State values in the combo boxes
        for (State s : State.values())
            stateComboBox.addItem(s.toString());
        
        cards = (CardLayout)mainPanel.getLayout();
        
    }
    
    public void embeddedMain() {
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
        leftPanel = new javax.swing.JPanel();
        SetingColorButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        profileButton = new javax.swing.JButton();
        mainPanel = new javax.swing.JPanel();
        main_menu = new javax.swing.JPanel();
        mainMenuDescription = new javax.swing.JLabel();
        mainMenuText = new javax.swing.JLabel();
        questionVote = new javax.swing.JLabel();
        answerVote = new javax.swing.JLabel();
        viewCandidateButton = new javax.swing.JButton();
        profilePanel = new javax.swing.JPanel();
        profileEditPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        goBackButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        editProfileFirstName = new javax.swing.JTextField();
        editProfileLastName = new javax.swing.JTextField();
        editVoterDate = new javax.swing.JFormattedTextField();
        editVoterEmail = new javax.swing.JTextField();
        editVoterPassword = new javax.swing.JTextField();
        stateComboBox = new javax.swing.JComboBox<String>();
        viewCandidatePanel = new javax.swing.JPanel();
        nameCandidateLabel = new javax.swing.JLabel();
        partyCandidateText = new javax.swing.JLabel();
        descriptionCandidateText = new javax.swing.JLabel();
        voteForButton = new javax.swing.JButton();
        preCandidateButton = new javax.swing.JButton();
        nextCandidateButton = new javax.swing.JButton();

        settingsPopUp.setMaximumSize(new java.awt.Dimension(32773, 98307));
        settingsPopUp.setPreferredSize(new java.awt.Dimension(200, 100));
        settingsPopUp.setRequestFocusEnabled(false);

        redOption.setText("Set color to red");
        redOption.setPreferredSize(new java.awt.Dimension(313, 46));
        redOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redOptionActionPerformed(evt);
            }
        });
        settingsPopUp.add(redOption);

        greenOption.setText("Set color to green");
        greenOption.setPreferredSize(new java.awt.Dimension(313, 46));
        greenOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                greenOptionActionPerformed(evt);
            }
        });
        settingsPopUp.add(greenOption);

        blueOption.setText("Set color to blue");
        blueOption.setPreferredSize(new java.awt.Dimension(313, 46));
        blueOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blueOptionActionPerformed(evt);
            }
        });
        settingsPopUp.add(blueOption);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Election Simulator");
        setMinimumSize(new java.awt.Dimension(800, 600));

        leftPanel.setBackground(new java.awt.Color(0, 102, 130));
        leftPanel.setMaximumSize(new java.awt.Dimension(180, 0));
        leftPanel.setMinimumSize(new java.awt.Dimension(180, 0));
        leftPanel.setPreferredSize(new java.awt.Dimension(180, 0));

        SetingColorButton.setBackground(new java.awt.Color(255, 255, 255));
        SetingColorButton.setForeground(new java.awt.Color(255, 255, 255));
        SetingColorButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/gearIcon.png"))); // NOI18N
        SetingColorButton.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        SetingColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SetingColorButtonActionPerformed(evt);
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

        profileButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/userIcon.png"))); // NOI18N
        profileButton.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        profileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                profileButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout leftPanelLayout = new javax.swing.GroupLayout(leftPanel);
        leftPanel.setLayout(leftPanelLayout);
        leftPanelLayout.setHorizontalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, leftPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(exitButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(profileButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SetingColorButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );
        leftPanelLayout.setVerticalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, leftPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(profileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SetingColorButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        mainPanel.setMinimumSize(new java.awt.Dimension(0, 0));
        mainPanel.setPreferredSize(new java.awt.Dimension(623, 600));
        mainPanel.setLayout(new java.awt.CardLayout());

        main_menu.setBackground(new java.awt.Color(255, 255, 255));

        mainMenuDescription.setBackground(new java.awt.Color(255, 255, 255));
        mainMenuDescription.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        mainMenuDescription.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mainMenuDescription.setText("Official Main Menu");
        mainMenuDescription.setMaximumSize(new java.awt.Dimension(289, 47));
        mainMenuDescription.setMinimumSize(new java.awt.Dimension(289, 47));
        mainMenuDescription.setPreferredSize(new java.awt.Dimension(289, 47));

        mainMenuText.setBackground(new java.awt.Color(255, 255, 255));
        mainMenuText.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        mainMenuText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mainMenuText.setText("Welcome " + admin.getFirstName() + " " + admin.getLastName() + "to the voter center.");

        questionVote.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        questionVote.setText("Have you voted ? ");

        answerVote.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        answerVote.setForeground(new java.awt.Color(170, 40, 50));
        answerVote.setText("Not yet ! ");

        viewCandidateButton.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        viewCandidateButton.setText("View candidates and vote");
        viewCandidateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewCandidateButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout main_menuLayout = new javax.swing.GroupLayout(main_menu);
        main_menu.setLayout(main_menuLayout);
        main_menuLayout.setHorizontalGroup(
            main_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainMenuDescription, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(mainMenuText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(main_menuLayout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addGroup(main_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(viewCandidateButton)
                    .addGroup(main_menuLayout.createSequentialGroup()
                        .addComponent(questionVote)
                        .addGap(114, 114, 114)
                        .addComponent(answerVote)))
                .addContainerGap(244, Short.MAX_VALUE))
        );
        main_menuLayout.setVerticalGroup(
            main_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(main_menuLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(mainMenuDescription, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(mainMenuText)
                .addGap(102, 102, 102)
                .addGroup(main_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(questionVote)
                    .addComponent(answerVote))
                .addGap(145, 145, 145)
                .addComponent(viewCandidateButton)
                .addGap(101, 101, 101))
        );

        mainPanel.add(main_menu, "mainMenu");

        profilePanel.setBackground(new java.awt.Color(255, 255, 255));

        profileEditPanel.setBackground(new java.awt.Color(255, 255, 255));
        profileEditPanel.setForeground(new java.awt.Color(25, 25, 25));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("If you want to modify your First Name, Last Name and date of birth, please contact an official.");

        goBackButton.setText("Go Back");
        goBackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goBackButtonActionPerformed(evt);
            }
        });

        saveButton.setText("Save Modifications");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout profileEditPanelLayout = new javax.swing.GroupLayout(profileEditPanel);
        profileEditPanel.setLayout(profileEditPanelLayout);
        profileEditPanelLayout.setHorizontalGroup(
            profileEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 668, Short.MAX_VALUE)
            .addGroup(profileEditPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(goBackButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(saveButton)
                .addContainerGap())
        );
        profileEditPanelLayout.setVerticalGroup(
            profileEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profileEditPanelLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jLabel2)
                .addGap(42, 42, 42)
                .addGroup(profileEditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(goBackButton))
                .addContainerGap(188, Short.MAX_VALUE))
        );

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Edit Profile");

        editProfileFirstName.setText(admin.getFirstName());
        editProfileFirstName.setBorder(javax.swing.BorderFactory.createTitledBorder("First Name"));
        editProfileFirstName.setEnabled(false);
        editProfileFirstName.setMinimumSize(new java.awt.Dimension(12, 74));
        editProfileFirstName.setPreferredSize(new java.awt.Dimension(12, 74));

        editProfileLastName.setText(admin.getLastName());
        editProfileLastName.setBorder(javax.swing.BorderFactory.createTitledBorder("Last Name"));
        editProfileLastName.setEnabled(false);
        editProfileLastName.setMinimumSize(new java.awt.Dimension(12, 74));
        editProfileLastName.setPreferredSize(new java.awt.Dimension(12, 74));

        editVoterDate.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Date of birth"));
        editVoterDate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("yyyy-MM-dd"))));
        editVoterDate.setText(admin.getDOB().get(Calendar.YEAR) + "-" + admin.getDOB().get(Calendar.MONTH) + "-" + admin.getDOB().get(Calendar.DATE));
        editVoterDate.setEnabled(false);
        editVoterDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editVoterDateActionPerformed(evt);
            }
        });

        editVoterEmail.setText(admin.getEmail());
        editVoterEmail.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Email"));

        editVoterPassword.setText(admin.getPassword());
        editVoterPassword.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Password"));

        stateComboBox.setBorder(null);
        stateComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stateComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout profilePanelLayout = new javax.swing.GroupLayout(profilePanel);
        profilePanel.setLayout(profilePanelLayout);
        profilePanelLayout.setHorizontalGroup(
            profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(profileEditPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(profilePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(profilePanelLayout.createSequentialGroup()
                        .addComponent(editProfileFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(editProfileLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(editVoterDate, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
                    .addGroup(profilePanelLayout.createSequentialGroup()
                        .addGroup(profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(profilePanelLayout.createSequentialGroup()
                                .addComponent(editVoterPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(stateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(editVoterEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        profilePanelLayout.setVerticalGroup(
            profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, profilePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(62, 62, 62)
                .addGroup(profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editProfileFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editProfileLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editVoterDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(editVoterEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editVoterPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(profileEditPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97))
        );

        mainPanel.add(profilePanel, "profile");

        viewCandidatePanel.setBackground(new java.awt.Color(255, 255, 255));

        nameCandidateLabel.setBackground(new java.awt.Color(255, 255, 255));
        nameCandidateLabel.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        nameCandidateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nameCandidateLabel.setText("Name");

        partyCandidateText.setBackground(new java.awt.Color(255, 255, 255));
        partyCandidateText.setFont(new java.awt.Font("Tahoma", 2, 30)); // NOI18N
        partyCandidateText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        partyCandidateText.setText("Party");

        descriptionCandidateText.setText("Description");
        descriptionCandidateText.setBorder(javax.swing.BorderFactory.createTitledBorder("Description"));

        voteForButton.setBackground(new java.awt.Color(255, 255, 255));
        voteForButton.setText("Vote for this candidate");
        voteForButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                voteForButtonActionPerformed(evt);
            }
        });

        preCandidateButton.setText("<-");
        preCandidateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                preCandidateButtonActionPerformed(evt);
            }
        });

        nextCandidateButton.setText("->");
        nextCandidateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextCandidateButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout viewCandidatePanelLayout = new javax.swing.GroupLayout(viewCandidatePanel);
        viewCandidatePanel.setLayout(viewCandidatePanelLayout);
        viewCandidatePanelLayout.setHorizontalGroup(
            viewCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(nameCandidateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(partyCandidateText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(viewCandidatePanelLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(preCandidateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(77, 77, 77)
                .addComponent(voteForButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(95, 95, 95)
                .addComponent(nextCandidateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewCandidatePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(descriptionCandidateText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(26, 26, 26))
        );
        viewCandidatePanelLayout.setVerticalGroup(
            viewCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewCandidatePanelLayout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(nameCandidateLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(partyCandidateText)
                .addGap(50, 50, 50)
                .addComponent(descriptionCandidateText, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                .addGap(76, 76, 76)
                .addGroup(viewCandidatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(voteForButton, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(preCandidateButton, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(nextCandidateButton, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(56, 56, 56))
        );

        mainPanel.add(viewCandidatePanel, "viewCandidate");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(leftPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(1, 1, 1)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 668, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(leftPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        dispose();
        GUI_Start mainMenu;
        try {
            mainMenu = new GUI_Start();
            mainMenu.embeddedMain();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(GUI_Official.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_exitButtonActionPerformed

    private void SetingColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SetingColorButtonActionPerformed
        settingsPopUp.show(this, SetingColorButton.getX(), SetingColorButton.getY()-settingsPopUp.getHeight()/2-2);
    }//GEN-LAST:event_SetingColorButtonActionPerformed

    private void redOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_redOptionActionPerformed
        actualColor=RED_COLOR;
        leftPanel.setBackground(new Color(170,40,50));
    }//GEN-LAST:event_redOptionActionPerformed

    private void greenOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_greenOptionActionPerformed
        actualColor=GREEN_COLOR;
        leftPanel.setBackground(new Color(100,210,50));
    }//GEN-LAST:event_greenOptionActionPerformed

    private void blueOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_blueOptionActionPerformed
        actualColor=BLUE_COLOR;
        leftPanel.setBackground(new Color(0,102,130));
    }//GEN-LAST:event_blueOptionActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        String [] infosModif = new String[3] ; 
        infosModif[0] = (String)stateComboBox.getModel().getSelectedItem();
        infosModif[1] = editVoterEmail.getText(); 
        infosModif[2] = editVoterPassword.getText(); 
        admin.updateProdile(infosModif);
    }//GEN-LAST:event_saveButtonActionPerformed

    private void stateComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stateComboBoxActionPerformed
        
    }//GEN-LAST:event_stateComboBoxActionPerformed

    private void editVoterDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editVoterDateActionPerformed
        
    }//GEN-LAST:event_editVoterDateActionPerformed

    private void viewCardsCandidate()
    {
        if(cursorCandidate == 0 )
            preCandidateButton.setEnabled(false);
        else 
            preCandidateButton.setEnabled(true);
        
        if(cursorCandidate == infosCandidate.length - 1 )
            nextCandidateButton.setEnabled(false);
        else 
            nextCandidateButton.setEnabled(true);
        
        if(admin.getVotedFor())
            voteForButton.setEnabled(false);
        else 
            voteForButton.setEnabled(true);
        
        nameCandidateLabel.setText(infosCandidate[cursorCandidate][1] + " " + infosCandidate[cursorCandidate][2]); 
        partyCandidateText.setText("Party : " + infosCandidate[cursorCandidate][3]); 
        descriptionCandidateText.setText("Party : " + infosCandidate[cursorCandidate][3]); 
        
        cards.show(mainPanel, "viewCandidate");
    }
    
    private void viewCandidateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewCandidateButtonActionPerformed
        infosCandidate = admin.getAllCandidate(); 
        if(infosCandidate != null)
            viewCardsCandidate() ;
        else return ; 
    }//GEN-LAST:event_viewCandidateButtonActionPerformed

    
    private void goBackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goBackButtonActionPerformed
        cards.show(mainPanel, "mainMenu");
    }//GEN-LAST:event_goBackButtonActionPerformed

    private void profileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_profileButtonActionPerformed
        cards.show(mainPanel, "profile");
    }//GEN-LAST:event_profileButtonActionPerformed

    private void nextCandidateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextCandidateButtonActionPerformed
        cursorCandidate ++ ; 
        viewCandidateButtonActionPerformed(evt); 
    }//GEN-LAST:event_nextCandidateButtonActionPerformed

    private void voteForButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_voteForButtonActionPerformed
        admin.vote(infosCandidate[cursorCandidate][0]); 
        if(admin.getVotedFor())
        {
            answerVote.setText("You have already voted.");
            answerVote.setForeground(GREEN_COLOR);
        }
    }//GEN-LAST:event_voteForButtonActionPerformed

    private void preCandidateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_preCandidateButtonActionPerformed
        cursorCandidate -- ; 
        viewCandidateButtonActionPerformed(evt); 
    }//GEN-LAST:event_preCandidateButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton SetingColorButton;
    private javax.swing.JLabel answerVote;
    private javax.swing.JMenuItem blueOption;
    private javax.swing.JLabel descriptionCandidateText;
    private javax.swing.JTextField editProfileFirstName;
    private javax.swing.JTextField editProfileLastName;
    private javax.swing.JFormattedTextField editVoterDate;
    private javax.swing.JTextField editVoterEmail;
    private javax.swing.JTextField editVoterPassword;
    private javax.swing.JButton exitButton;
    private javax.swing.JButton goBackButton;
    private javax.swing.JMenuItem greenOption;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JLabel mainMenuDescription;
    private javax.swing.JLabel mainMenuText;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel main_menu;
    private javax.swing.JLabel nameCandidateLabel;
    private javax.swing.JButton nextCandidateButton;
    private javax.swing.JLabel partyCandidateText;
    private javax.swing.JButton preCandidateButton;
    private javax.swing.JButton profileButton;
    private javax.swing.JPanel profileEditPanel;
    private javax.swing.JPanel profilePanel;
    private javax.swing.JLabel questionVote;
    private javax.swing.JMenuItem redOption;
    private javax.swing.JButton saveButton;
    private javax.swing.JPopupMenu settingsPopUp;
    private javax.swing.JComboBox<String> stateComboBox;
    private javax.swing.JButton viewCandidateButton;
    private javax.swing.JPanel viewCandidatePanel;
    private javax.swing.JButton voteForButton;
    // End of variables declaration//GEN-END:variables
}
