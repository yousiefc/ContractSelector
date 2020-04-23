
package selectcontract;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import javax.sql.DataSource;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
/**
 *
 * @author C Yousief
 */
class ContractModel {
    public static final int NUMBER_OF_CONTRACT_ATTRIBUTES = 4;
    public static final int INDEX_OF_CONTRACT_ID = 0;
    public static final int INDEX_OF_ORIGIN_CITY = 1;
    public static final int INDEX_OF_DEST_CITY = 2;
    public static final int INDEX_OF_ORDER_ITEM = 3;
    private ArrayList<Contract> theContracts;
    private ArrayList<Contract> theContractsAll;
    private ArrayList<String> contractIDs;
    private SortedSet<String> originCityList;
    private int contractCounter;
    
    public ContractModel(){
        originCityList = new TreeSet<>();
        contractIDs = new ArrayList<String>();
        this.contractCounter = 0;
        theContracts = new ArrayList<Contract>();

        DataSource ds = null;
        ResultSet rs = null;
        Connection con = null;
        CallableStatement cstmt = null;
        try {
            ds = MyDataSourceFactory.getOracleDataSource();
            con = ds.getConnection();
            cstmt = con.prepareCall("BEGIN CYOUSIEF_20W.PKGREC.CONTRACT_LIST(?); END;");
            cstmt.registerOutParameter(1, OracleTypes.CURSOR);
            cstmt.execute();
            
            rs = ((OracleCallableStatement) cstmt).getCursor(1);
            while(rs.next()){
                String contractID = rs.getString("CONTRACTID");
                String origCity = rs.getString("ORIGCITY");
                contractIDs.add(contractID.toUpperCase());
                originCityList.add(origCity);
                String destCity = rs.getString("DESTCITY");
                String orderItem = rs.getString("ORDERITEM");
                
                Contract dataContract = new Contract(contractID, origCity, destCity, orderItem);
                theContracts.add(dataContract);
            }
            originCityList.add("All");
            theContractsAll = new ArrayList<>(theContracts);
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (cstmt != null) {
                    cstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
    
    public void addContract(Contract contract){
        originCityList.add(contract.getOriginCity());
        theContractsAll.add(contract);
    }
    
    public boolean contractIDExists(String contractID){
        return contractIDs.contains(contractID.toUpperCase());
    }
    
    public String[] getOriginCityList() {
        String[] a;
        a = originCityList.toArray(new String[originCityList.size()]);
        return a;
    }

    public void updateContractList(String city) {
        theContracts = new ArrayList<>(theContractsAll);
        if(!"All".equals(city)) {
            theContracts.removeIf(s -> !s.contains(city));
        }
        contractCounter = 0;
    }
    
    boolean foundContracts(){
        return !theContracts.isEmpty();
    }
    
    public Contract getTheContract() {
        return theContracts.get(contractCounter);
    }
    
    public int getContractCount() {
        return theContracts.size();
    }
    
    public int getCurrentContractNum() {
        return contractCounter;
    }
    
    public void nextContract() {
        if((contractCounter + 1) <= theContracts.size()-1) {
            contractCounter++;
        }
    }
    
    public void prevContract() {
        if((contractCounter - 1) >= 0) {
            contractCounter--;
        }
    }
}
