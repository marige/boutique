package superpackage;


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
}