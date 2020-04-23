package selectcontract;
import java.sql.*;
import javax.sql.DataSource;
import oracle.jdbc.OracleTypes;
/**
 *
 * @author C Yousief
 */
public class ContractOracle {
    
    public static void saveContract(Contract c) throws SQLException {
        DataSource ds = MyDataSourceFactory.getOracleDataSource();
        Connection con = null;
        CallableStatement cstmt = null;
        
        try{
            con = ds.getConnection();
            cstmt = con.prepareCall("BEGIN CYOUSIEF_20W.PKGREC.CONTRACT_ADD(?,?,?,?,?); END;");
            
            cstmt.registerOutParameter(5, OracleTypes.NUMBER);
            cstmt.setString(1,c.getContractID());
            cstmt.setString(2,c.getOriginCity());
            cstmt.setString(3,c.getDestCity());
            cstmt.setString(4,c.getOrderItem());
            
            cstmt.executeUpdate();
            int retVal = cstmt.getInt(5);
        }catch (SQLException s) {
            System.out.println(s.getMessage());
        }finally {
            try {
                if (cstmt != null) {
                cstmt.close();
                }
                if (con != null) {
                con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
} 
        }
    }
}
