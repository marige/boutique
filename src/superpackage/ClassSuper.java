package superpackage;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author OBAM
 */
public class ClassSuper {
     
    public Date convertStringToDate(LocalDate localDate) throws ParseException{
            return  new SimpleDateFormat("yyyy-MM-dd").parse(localDate.toString());    
    }
    //connection a la bdd
     public Connection getConnection() throws SQLException{  
                 String url = "jdbc:mysql://localhost:3306/boutique";   
                 com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver();
                 DriverManager.registerDriver(driver);
                  Connection cnt = DriverManager.getConnection(url,"root","mario");
             return cnt;
    }
}