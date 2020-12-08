/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Misc.FileManager;
import static View.GUI_Start.BLUE_COLOR;
import static View.GUI_Start.GREEN_COLOR;
import static View.GUI_Start.RED_COLOR;
import static View.GUI_Start.actualColor;
import Model.Candidate;
import java.awt.CardLayout;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * GUI used by the candidates. 
 * @author rebec
 */
public class GUI_Candidate extends javax.swing.JFrame {

    private CardLayout cards;
    private Candidate admin ; 
    private FileManager f;
    /**
     * Creates new form GUI_Candidate
     * @param admin
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public GUI_Candidate(Candidate admin) throws SQLException, ClassNotFoundException {
        
        this.admin = admin ; 
        admin.setDataController();
        initComponents();
        
        System.out.print(admin.getStatusElection());
        UpdateStatusElection(admin.getStatusElection());
        
        setLocationRelativeTo(null);
        setVisible(true);
        
        cards = (CardLayout)mainPanel.getLayout();
        
        //File Manager to save users preferences
        try {
            f=new FileManager(); 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GUI_Official.class.getName()).log(Level.SEVERE, null, ex);
        }
        changeColor(actualColor);
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
        viewStatisticButton = new javax.swing.JButton();
        profilePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        editProfileFirstName = new javax.swing.JTextField();
        editProfileLastName = new javax.swing.JTextField();
        editCandidateDate = new javax.swing.JFormattedTextField();
        editCandidateEmail = new javax.swing.JTextField();
        editCandidatePassword = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        goBackButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        editCandidateParty = new javax.swing.JTextField();

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
        setMinimumSize(new java.awt.Dimension(1024, 720));

        leftPanel.setBackground(new java.awt.Color(0, 102, 130));
        leftPanel.setMaximumSize(new java.awt.Dimension(180, 0));
        leftPanel.setMinimumSize(new java.awt.Dimension(180, 0));
        leftPanel.setPreferredSize(new java.awt.Dimension(180, 0));

        SetingColorButton.setBackground(new java.awt.Color(255, 255, 255));
        SetingColorButton.setForeground(new java.awt.Color(255, 255, 255));
        SetingColorButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/gearIcon.png"))); // NOI18N
        SetingColorButton.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        SetingColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SetingColorButtonActionPerformed(evt);
            }
        });

        exitButton.setBackground(new java.awt.Color(255, 255, 255));
        exitButton.setForeground(new java.awt.Color(255, 255, 255));
        exitButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/logoutIcon.png"))); // NOI18N
        exitButton.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        profileButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/userIcon.png"))); // NOI18N
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
                .addComponent(exitButton, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(profileButton, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SetingColorButton, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
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
        mainPanel.setPreferredSize(new java.awt.Dimension(844, 686));
        mainPanel.setLayout(new java.awt.CardLayout());

        main_menu.setBackground(new java.awt.Color(255, 255, 255));
        main_menu.setPreferredSize(new java.awt.Dimension(668, 686));

        mainMenuDescription.setBackground(new java.awt.Color(255, 255, 255));
        mainMenuDescription.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        mainMenuDescription.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mainMenuDescription.setText("Candidate Main Menu");
        mainMenuDescription.setMaximumSize(new java.awt.Dimension(289, 47));
        mainMenuDescription.setMinimumSize(new java.awt.Dimension(289, 47));
        mainMenuDescription.setPreferredSize(new java.awt.Dimension(289, 47));

        mainMenuText.setBackground(new java.awt.Color(255, 255, 255));
        mainMenuText.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        mainMenuText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mainMenuText.setText("Welcome " + admin.getFirstName() + " " + admin.getLastName() + "to the candidate center.");

        questionVote.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        questionVote.setText("Statue of the election : ");

        answerVote.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        answerVote.setForeground(new java.awt.Color(0, 153, 51));
        answerVote.setText("Active");

        viewStatisticButton.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        viewStatisticButton.setText("View votes and compare with other canidates");
        viewStatisticButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewStatisticButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout main_menuLayout = new javax.swing.GroupLayout(main_menu);
        main_menu.setLayout(main_menuLayout);
        main_menuLayout.setHorizontalGroup(
            main_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainMenuDescription, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(mainMenuText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(main_menuLayout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(questionVote)
                .addGap(115, 115, 115)
                .addComponent(answerVote)
                .addContainerGap(385, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, main_menuLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(viewStatisticButton)
                .addGap(230, 230, 230))
        );
        main_menuLayout.setVerticalGroup(
            main_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(main_menuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainMenuDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(mainMenuText)
                .addGap(164, 164, 164)
                .addGroup(main_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(questionVote)
                    .addComponent(answerVote))
                .addGap(112, 112, 112)
                .addComponent(viewStatisticButton)
                .addContainerGap(243, Short.MAX_VALUE))
        );

        mainPanel.add(main_menu, "mainMenu");

        profilePanel.setBackground(new java.awt.Color(255, 255, 255));
        profilePanel.setMinimumSize(new java.awt.Dimension(844, 686));
        profilePanel.setPreferredSize(new java.awt.Dimension(844, 686));

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

        editCandidateDate.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Date of birth"));
        editCandidateDate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("yyyy-MM-dd"))));
        editCandidateDate.setText(admin.getDOB());
        editCandidateDate.setEnabled(false);
        editCandidateDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editCandidateDateActionPerformed(evt);
            }
        });

        editCandidateEmail.setText(admin.getEmail());
        editCandidateEmail.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Email"));

        editCandidatePassword.setText(admin.getPassword());
        editCandidatePassword.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Password"));

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

        editCandidateParty.setText(admin.getParty());
        editCandidateParty.setBorder(javax.swing.BorderFactory.createTitledBorder("Party"));
        editCandidateParty.setEnabled(false);

        javax.swing.GroupLayout profilePanelLayout = new javax.swing.GroupLayout(profilePanel);
        profilePanel.setLayout(profilePanelLayout);
        profilePanelLayout.setHorizontalGroup(
            profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 844, Short.MAX_VALUE)
            .addGroup(profilePanelLayout.createSequentialGroup()
                .addGroup(profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, profilePanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(profilePanelLayout.createSequentialGroup()
                                .addComponent(editCandidatePassword)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(editCandidateParty, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(editCandidateEmail)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, profilePanelLayout.createSequentialGroup()
                                .addComponent(editProfileFirstName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(editProfileLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editCandidateDate, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(profilePanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(goBackButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(saveButton)))
                .addContainerGap())
        );
        profilePanelLayout.setVerticalGroup(
            profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profilePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(62, 62, 62)
                .addGroup(profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editProfileFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editProfileLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editCandidateDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(editCandidateEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editCandidatePassword, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editCandidateParty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addComponent(jLabel2)
                .addGap(44, 44, 44)
                .addGroup(profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(goBackButton)
                    .addComponent(saveButton))
                .addContainerGap(135, Short.MAX_VALUE))
        );

        mainPanel.add(profilePanel, "profile");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(leftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(leftPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /** 
     * Update the output of the status of the election. 
     * @param status
     * @return 
     */
    private void UpdateStatusElection(String status){
        switch(status)
        {
            case "Active" :
                answerVote.setForeground(GREEN_COLOR);
                answerVote.setText(status);
                break;
            case "Paused" :
                answerVote.setForeground(BLUE_COLOR);
                answerVote.setText(status);
                break;
            case "Done":
                answerVote.setForeground(RED_COLOR);
                answerVote.setText(status);
                break;
            default :
                answerVote.setText("Done");
                break;
        }
    }
    
    private void SetingColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SetingColorButtonActionPerformed
        settingsPopUp.show(this, SetingColorButton.getX(), SetingColorButton.getY()-settingsPopUp.getHeight()/2-2);
    }//GEN-LAST:event_SetingColorButtonActionPerformed

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

    private void profileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_profileButtonActionPerformed
        cards.show(mainPanel, "profile");
    }//GEN-LAST:event_profileButtonActionPerformed

    private void changeColor(Color c){
        leftPanel.setBackground(c);
        f.saveColor(c);
    }
    private void redOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_redOptionActionPerformed
        actualColor=RED_COLOR;
        changeColor(actualColor);
    }//GEN-LAST:event_redOptionActionPerformed

    private void greenOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_greenOptionActionPerformed
        actualColor=GREEN_COLOR;
        changeColor(actualColor);
    }//GEN-LAST:event_greenOptionActionPerformed

    private void blueOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_blueOptionActionPerformed
        actualColor=BLUE_COLOR;
        changeColor(actualColor);
    }//GEN-LAST:event_blueOptionActionPerformed

    private void viewStatisticButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewStatisticButtonActionPerformed

    }//GEN-LAST:event_viewStatisticButtonActionPerformed

    private void editCandidateDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editCandidateDateActionPerformed

    }//GEN-LAST:event_editCandidateDateActionPerformed

    private void goBackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goBackButtonActionPerformed
        cards.show(mainPanel, "mainMenu");
    }//GEN-LAST:event_goBackButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        String [] infosModif = new String[2] ; 
        infosModif[0] = editCandidateEmail.getText(); 
        infosModif[1] = editCandidatePassword.getText(); 
        if(infosModif[0].equals("") || infosModif[1].equals(""))
            JOptionPane.showMessageDialog(null, "The new email or password enter is empty, please complet it to be able to modify your profil." , this.getTitle(), 1 );
        else if(admin.updateProdile(infosModif))
            JOptionPane.showMessageDialog(null, "Informations successfully edited" , this.getTitle(), 1 );
        else 
            JOptionPane.showMessageDialog(null, "A problem occured. Your modification was not taken into account." , this.getTitle(), 1 );
    }//GEN-LAST:event_saveButtonActionPerformed

    /**
     * @param args the command line arguments
     */
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton SetingColorButton;
    private javax.swing.JLabel answerVote;
    private javax.swing.JMenuItem blueOption;
    private javax.swing.JFormattedTextField editCandidateDate;
    private javax.swing.JTextField editCandidateEmail;
    private javax.swing.JTextField editCandidateParty;
    private javax.swing.JTextField editCandidatePassword;
    private javax.swing.JTextField editProfileFirstName;
    private javax.swing.JTextField editProfileLastName;
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
    private javax.swing.JButton profileButton;
    private javax.swing.JPanel profilePanel;
    private javax.swing.JLabel questionVote;
    private javax.swing.JMenuItem redOption;
    private javax.swing.JButton saveButton;
    private javax.swing.JPopupMenu settingsPopUp;
    private javax.swing.JButton viewStatisticButton;
    // End of variables declaration//GEN-END:variables
}