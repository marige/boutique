package report;
import boncommande.BonCommande;
import facture.Facture;
import java.sql.Connection;
import java.util.HashMap;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import societe.Societe;
import societe.SocieteJpaController;
import superpackage.SuperClass;


public class REPORT extends SuperClass {
    public REPORT(){
        sc=new SocieteJpaController().findSociete(1);
    }
    public static String reportPath=System.getProperty("user.dir")+"\\src\\report\\";
  //  public static String reportPath=System.getProperty("user.dir")+"\\";
    Societe sc=null;
    public void editionReport(String reportName,String query, HashMap hm) throws Exception{
            Connection conn=getConnection();
            String reportSource=reportPath+reportName+".jrxml";
            JasperDesign jd=JRXmlLoader.load(reportSource);   
            JRDesignQuery qd=new JRDesignQuery();
            qd.setText(query);
            jd.setQuery(qd);
            JasperReport jsp=JasperCompileManager.compileReport(jd);
            JasperPrint jp= JasperFillManager.fillReport(jsp, hm,conn);
            JasperViewer.viewReport(jp,false);
            conn.close();
    }
    public void etatBonCommande(BonCommande bonDeCommande) throws Exception{
            HashMap parameter= new HashMap();
            parameter.put("idboncommande",bonDeCommande.getIdBonCommande()); 
            parameter.put("datebon",getDateFormatAffichage(bonDeCommande.getDateBonCommande()));
            parameter.put("fournisseur",bonDeCommande.getFournisseur().getLibFournisseur());   
            parameter.put("nomsociete",sc.getLibelle());
            parameter.put("entete",sc.getEntetedocument());
            parameter.put("pieds",sc.getPiedsdocument());
            editionReport("boncommande","select article.libarticle,detailboncommande.quantitedetailboncommande," +
                    "detailboncommande.puachat from detailboncommande,article " +
                    "where article.idarticle=detailboncommande.idarticle and idboncommande="+bonDeCommande.getIdBonCommande(), parameter);//
    }
    
    public void etatBordereau(BonCommande bonDeCommande) throws Exception{
            HashMap parameter= new HashMap();
            parameter.put("idboncommande",bonDeCommande.getIdBonCommande()); 
            parameter.put("datebon",getDateFormatAffichage(bonDeCommande.getDateBonCommande()));
            parameter.put("fournisseur",bonDeCommande.getFournisseur().getLibFournisseur());   
            parameter.put("nomsociete",sc.getLibelle());
            parameter.put("entete",sc.getEntetedocument());
            parameter.put("pieds",sc.getPiedsdocument());
            parameter.put("datereception",getDateFormatAffichage(bonDeCommande.getDatereception()));
            
            editionReport("bordereau_reception","select article.libarticle,detailboncommande.quantitedetailboncommande," +
                    "detailboncommande.puachat from detailboncommande,article " +
                    "where article.idarticle=detailboncommande.idarticle and idboncommande="+bonDeCommande.getIdBonCommande(), parameter);//
    }
    
    
    public void etatFacture(Facture f) throws Exception{
         HashMap parameter= new HashMap();
           parameter.put("idfacture",f.getIdFacture());
           parameter.put("client",f.getClient()); 
           parameter.put("nomsociete",sc.getLibelle());
           parameter.put("entete",sc.getEntetedocument());
           parameter.put("pieds",sc.getPiedsdocument());
            editionReport("facture","select article.libarticle,vente.qte,vente.pu,facture.client from vente,article,facture where vente.idarticle=article.idarticle and vente.idfacture=facture.idfacture and vente.idfacture="+f.getIdFacture(), parameter);//         
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
