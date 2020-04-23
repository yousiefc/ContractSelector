
package selectcontract;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 *
 * @author C Yousief
 */
class ContractController {
    
    private ContractView theView;
    private ContractModel theModel;

    ContractController(ContractView theView, ContractModel theModel) {
        this.theView = theView;
        this.theModel = theModel;
        
        this.theView.setOriginCityList(this.theModel.getOriginCityList());
        this.theView.addcomboBoxListener(new ComboListener());
        this.theView.addPrevListener(new PrevButtonListener());
        this.theView.addBidListener(new BidButtonListener());
        this.theView.addNextListener(new NextButtonListener());
        this.theView.addNewContractListener(new NewContractListener());
        setUpDisplay();
    }
    
    private void setUpDisplay() {
        try{
            if(theModel.getCurrentContractNum() == 0) {
                theView.fadePrev();
            }
            else{
                theView.unFadePrev();
            }
            
            if(theModel.getCurrentContractNum() == theModel.getContractCount()-1){
                theView.fadeNext();
            }
            else{
                theView.unFadeNext();
            }
            
            if(theModel.foundContracts()){
                Contract c = theModel.getTheContract();
                theView.setContractID(c.getContractID().toUpperCase());
                theView.setDestCity(c.getDestCity());
                theView.setOriginCity(c.getOriginCity());
                theView.setOrderItem(c.getOrderItem());
            }else{
                theView.setContractID("N/A");
                theView.setDestCity("N/A");
                theView.setOriginCity("N/A");
                theView.setOrderItem("N/A");
            }
        }catch (Error ex) {
            System.out.println(ex);
            theView.displayErrorMessage("Error: there was a problem setting the contract.\n" + " Contract number: " + theModel.getCurrentContractNum());
        }
        
        theView.updateContractViewPanel(theModel.getCurrentContractNum(),theModel.getContractCount());
    }
    
    class ComboListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            System.out.println(e.getItem().toString());
            if(e.getStateChange() == ItemEvent.SELECTED) {
                String selectedCity = e.getItem().toString();
                System.out.println(selectedCity);
                theModel.updateContractList(selectedCity);
                setUpDisplay();
            }
        }
    }
    
    class PrevButtonListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if(theModel.getCurrentContractNum() == 0){
                return;
            }
            try{
                theModel.prevContract();
                
            }catch (Exception ex) {
                System.out.println(ex);
                theView.displayErrorMessage("Error: There is a problem setting a previous contract.");
            }
            setUpDisplay();
        }
    }
    
    class NextButtonListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if(theModel.getCurrentContractNum() == theModel.getContractCount()-1){
                return;
            }
            try{
                theModel.nextContract();
                
            }catch (Exception ex) {
                System.out.println(ex);
                theView.displayErrorMessage("Error: There is a problem setting a previous contract.");
            }
            setUpDisplay();
        }
    }
    
    class BidButtonListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                ConfirmBid cb;
                cb = new ConfirmBid(theView, true, theModel.getTheContract());
                cb.setLocationRelativeTo(null);
                cb.setVisible(true);
             
            }catch (Exception ex) {
                System.out.println(ex);
                theView.displayErrorMessage("Error: There is a problem setting a previous contract.");
            }
            setUpDisplay();
        }
    }
    
    class NewContractListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                NewContract nc;
                nc = new NewContract(theView,true,theModel);
                nc.setLocationRelativeTo(null);
                nc.setVisible(true);
            }catch (Exception ex) {
                System.out.println(ex);
            }
            setUpDisplay();
        }
    }
    
}
