/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import domain.Employee;
import domain.Manager;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 *
 * @author Tony Work
 */
public class MainWindow extends javax.swing.JFrame {

    private LoginPanel lp;
    private HomePanel hp;
    
    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();
        initialise();
        
    }

    private void initialise(){
        this.setLocationRelativeTo(null);
        setMaximized(true);
        lp = new LoginPanel();
        setLoginPanel();
        lp.setActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("Login")){
                    setHomePanel(lp.getUsernameField(), lp.getPasswordField());
                }
            }
            
        });
    }
    

    /**
     * Method which deals with an issue in newer java releases for maximising windows.
     * @param maximized 
     */
    public void setMaximized(boolean maximized){
    if(maximized){
        DisplayMode mode = this.getGraphicsConfiguration().getDevice().getDisplayMode();
        Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());
        this.setMaximizedBounds(new Rectangle(
                mode.getWidth() - insets.right - insets.left, 
                mode.getHeight() - insets.top - insets.bottom
        ));
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }else{
        this.setExtendedState(JFrame.NORMAL);
    }
}
    
    
    /**
     * this method removes the current panel and replaces it with the loginpanel
     * irrespective.
     */
    private void setLoginPanel(){
        if (this.hp != null)
            this.remove(this.hp);
        this.add(lp);
    }
    
    /**
     * Set home panel.
     */
    private boolean setHomePanel(String username, char[] password){
        // if username and password are correct
        Employee emp = new Manager(0, username, "Charles");
        remove(lp);
        if (hp == null){
            hp = new HomePanel(emp);
        }
        
        add(hp);
        revalidate();
        repaint();
        return true;
    }
    
    /**
     * This method is called from within the constructor to initialize the form. WARNING:
     * Do NOT modify this code. The content of this method is always regenerated by the
     * Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(3);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}