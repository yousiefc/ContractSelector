
package selectcontract;

/**
 *
 * @author C Yousief
 */
public class SelectContract {
    public static void main(String[] args) {
        ContractView theView = new ContractView();
        ContractModel theModel = new ContractModel();
        ContractController theController;
        theController = new ContractController(theView, theModel);
        theView.setVisible(true);
    }
}
