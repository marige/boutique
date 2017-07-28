package report;
import java.sql.Connection;
import java.util.HashMap;
import javax.swing.JDialog;
import javax.swing.JFrame;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import superpackage.ClassSuper;


public class REPORT extends ClassSuper {
  
    public static String reportPath=System.getProperty("user.dir")+"\\src\\report\\";
  //  public static String reportPath=System.getProperty("user.dir")+"\\";
    
    public void editionReport(String reportName,String query, HashMap hm) throws Exception{
            Connection conn=getConnection();
            String reportSource=reportPath+reportName+".jrxml";
            JasperDesign jd=JRXmlLoader.load(reportSource);   
            JRDesignQuery qd=new JRDesignQuery();
            qd.setText(query);
            jd.setQuery(qd);
            JasperReport jsp=JasperCompileManager.compileReport(jd);
            JasperPrint jp= JasperFillManager.fillReport(jsp, hm,conn);
            JasperViewer jv=new JasperViewer(jp,false);
            JDialog dialog= new JDialog(new JFrame(),true);
            dialog.setContentPane(jv.getContentPane());
            dialog.setSize(jv.getSize());
            dialog.setVisible(true);    
            conn.close();
    }
    
   /* public HashMap getHashMap(String tableName) throws SQLException{
         HashMap hm= new HashMap();
            ResultSet rs=getResultSet("select * from "+tableName);
            if(rs.next()){
            int nbcolumn=rs.getMetaData().getColumnCount();
            for(int i=1;i<=nbcolumn;i++){
                hm.put(rs.getMetaData().getColumnName(i), rs.getString(i));
            }
            }
            rs.close();
            return hm;
    }*/

}
