package selectcontract;

import java.io.FileWriter;
import java.util.regex.Pattern;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 *
 * @author shmazool
 */
public class NewContract extends javax.swing.JDialog {

    ContractModel theModel;
    /**
     * Creates new form NewContract
     */
    public NewContract(java.awt.Frame parent, boolean modal,ContractModel theModel) {
        super(parent, modal);
        initComponents();
        this.theModel = theModel;
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        contractIDText = new javax.swing.JTextField();
        originText = new javax.swing.JTextField();
        destinationText = new javax.swing.JTextField();
        orderText = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        saveNewContract = new javax.swing.JButton();
        cancelNewContract = new javax.swing.JButton();
        jButtonClear = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 153, 204));

        jLabel1.setText("Add a new contract");

        contractIDText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contractIDTextActionPerformed(evt);
            }
        });

        destinationText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                destinationTextActionPerformed(evt);
            }
        });

        jLabel2.setText("Contract ID");

        jLabel3.setText("Origin");

        jLabel4.setText("Destination");

        jLabel5.setText("Order");

        saveNewContract.setText("Save");
        saveNewContract.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveNewContractActionPerformed(evt);
            }
        });

        cancelNewContract.setText("Cancel");
        cancelNewContract.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelNewContractActionPerformed(evt);
            }
        });

        jButtonClear.setText("Clear");
        jButtonClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(71, 71, 71)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1)
                            .addComponent(contractIDText)
                            .addComponent(originText)
                            .addComponent(destinationText)
                            .addComponent(orderText, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(saveNewContract)
                        .addGap(28, 28, 28)
                        .addComponent(jButtonClear)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addComponent(cancelNewContract)))
                .addContainerGap(92, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(contractIDText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(originText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(destinationText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(orderText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveNewContract)
                    .addComponent(cancelNewContract)
                    .addComponent(jButtonClear))
                .addGap(43, 43, 43))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveNewContractActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveNewContractActionPerformed
        // TODO add your handling code here:

        try{
            if(!contractIDText.getText().matches("(^[0-9][a-zA-Z]{3}?$)")){
                JFrame frame = new JFrame();
                JOptionPane.showMessageDialog(frame, "Please input a single digit followed by three letters");
            }
            else if(!originText.getText().matches("Victoria|Vancouver|Seattle|Nanaimo|Prince George") || !destinationText.getText().matches("Victoria|Vancouver|Seattle|Nanaimo|Prince George") || destinationText.getText().equals(originText.getText())) {
                JFrame frame = new JFrame();
                JOptionPane.showMessageDialog(frame, "Valid options are Victoria,Vancouver,Seattle,Nanaimo,Prince George. Origin can not match Destination");
            }
            else if(orderText.getText().matches("[,]|^[0-9]+$")) {
                JFrame frame = new JFrame();
                JOptionPane.showMessageDialog(frame, "An order item can not consist only of numbers or contain a comma.");
            }
            else if(contractIDText.getText().equals("")||originText.getText().equals("")||destinationText.getText().equals("")||orderText.getText().equals("")) {
                JFrame frame = new JFrame();
                JOptionPane.showMessageDialog(frame, "Do not leave blank fields.");             
            }
            else if(theModel.contractIDExists(contractIDText.getText())){
                JFrame frame = new JFrame();
                JOptionPane.showMessageDialog(frame, "Contract " + contractIDText.getText() +  " already defined.");
            }
            else{

                
                Contract contract = new Contract(contractIDText.getText(),originText.getText(),destinationText.getText(),orderText.getText());
                try{
                    ContractOracle.saveContract(contract);
                }catch (SQLException s) {
                    System.out.println(s.getMessage());
                }
                
                theModel.addContract(contract);
                
                JFrame frame = new JFrame();
                JOptionPane.showMessageDialog(frame, "New contract has been added"); 
                
                clear();
            }
        }catch(Exception ex){
        }
    }//GEN-LAST:event_saveNewContractActionPerformed

    private void clear(){
        contractIDText.setText("");
        originText.setText("");
        destinationText.setText("");
        orderText.setText("");
        
    }
    private void contractIDTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contractIDTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_contractIDTextActionPerformed

    private void destinationTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_destinationTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_destinationTextActionPerformed

    private void cancelNewContractActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelNewContractActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_cancelNewContractActionPerformed

    private void jButtonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_jButtonClearActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelNewContract;
    private javax.swing.JTextField contractIDText;
    private javax.swing.JTextField destinationText;
    private javax.swing.JButton jButtonClear;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField orderText;
    private javax.swing.JTextField originText;
    private javax.swing.JButton saveNewContract;
    // End of variables declaration//GEN-END:variables
}
