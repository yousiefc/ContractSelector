
package selectcontract;

/**
 *
 * @author C Yousief
 */
public class Contract {
    private String contractID;
    private String originCity;
    private String destCity;
    private String orderItem;
    
    public Contract(String contractID, String originCity, String destCity, String orderItem) {
        this.contractID = contractID;
        this.originCity = originCity;
        this.destCity = destCity;
        this.orderItem = orderItem;
    }
    
    public String getContractID(){
        return this.contractID;
    }
    
    public String getOriginCity(){
        return this.originCity;
    }
    
    public String getDestCity(){
        return this.destCity;
    }
    
    public String getOrderItem(){
        return this.orderItem;
    }

    boolean contains(String city) {
        return city.equals(originCity);
    }
    
}
