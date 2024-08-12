/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mypack;

import static java.lang.Character.UnicodeBlock.forName;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ejb.Stateful;

/**
 *
 * @author Admin
 */
@Stateful
public class RRBean {
       public RRBean(){}
       public String roombooking(String cn,String cm,String rt){
           String message="";
           try{
              Class.forName("com.mysql.jdbc.Driver");
              Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","root");
              String query= "select * from room_book where roomtype=? and statusroom='not booked'";
              PreparedStatement pst= con.prepareStatement(query);
              pst.setString(1,rt);
              ResultSet rs= pst.executeQuery();
              if(rs.next()){
                  String rno= rs.getString(1);
                  PreparedStatement psmt1= con.prepareStatement("update room_book set custr=? where roomid=?");
                  psmt1.setString(1,cn);
                   psmt1.setString(2,cm);
                   psmt1.executeUpdate();
                  psmt1= con.prepareStatement("update room_book set mob=? where roomid=?");
                  message= "room"+rno+"booked and charges="+rs.getString(3);
              }
              else{
                  message="Room"+rt+"currently not available";
              }
           }
           catch(ClassNotFoundException | SQLException e)
           {
               message=""+e;
                
           }
           return message;
           }  
       }