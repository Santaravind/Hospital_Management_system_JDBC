package HospitalMnagenemtSystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Doctor {
     //private static final String Specialization = null;
     private Connection connection ;
     private Scanner scanner;

     public Doctor( Connection connection ,Scanner scanner ){
          this.connection=connection;
          this.scanner= scanner;

               }

     //    public void JoinNewDocter(){
     //      String sql="insert into doctors(name,specialization )values(?,?);";
          
     //      System.out.println("Enter the Doctor Name");

     //     String name=scanner.nextLine();
     //    //6 scanner.next();
     //    System.out.println("Enter Doctor Specialiazalion:");
     //    String spe=scanner.nextLine();


     //      try {
     //           PreparedStatement preparedStatement=connection.prepareStatement(sql);
     //           preparedStatement.setString(1, name);
     //           preparedStatement.setString(2, spe);
     //         int rowsAffected=  preparedStatement.executeUpdate();   
     //        if(rowsAffected>0){
     //           System.out.println("Welcome a New Doctor .");
     //        }
     //        else{
     //           System.out.println("No doctor was added. Please check your input."); // More informative error message

     //        }
     //      } catch (SQLException e) {
     //           e.printStackTrace();
     //      }
     //    }
       
     public void JoinNewDoctor() {
          System.out.println("Enter the Doctor Name:");
          String name = scanner.nextLine();
      
          System.out.println("Enter Doctor Specialization:");
          String specialization = scanner.nextLine();
                       
      
          try {
               String sql = "INSERT INTO doctors (name, specialization) VALUES (?, ?);";
                      
              PreparedStatement preparedStatement = connection.prepareStatement(sql);
              
              preparedStatement.setString(1, name);
              preparedStatement.setString(2, specialization);
              int rowsAffected = preparedStatement.executeUpdate();
      
              if (rowsAffected > 0) {
                  System.out.println("Welcome a New Doctor.");
              } else {
                  System.out.println("No doctor was added. Please check your input.");
              }
          } catch (SQLException e) {
              e.printStackTrace();
          }
      }
      
        //View the doctors 

         public void viewDoctor(){

          String sql="select * from doctors;";

          try{

                PreparedStatement preparedStatement=connection.prepareStatement(sql);
                ResultSet resultSet =preparedStatement.executeQuery();
                System.out.println(" doctors");
                System.out.println("+-----------+--------------+--------------------+");
                System.out.println("| Doctor ID | Name         |Specialization      |");
                System.out.println("+-----------+--------------+--------------------+");
                
                while(resultSet.next()){
                    int id=resultSet.getInt("id");
                    String name=resultSet.getString("name");
                    String specialString=resultSet.getString("specialization");
                    //String Gender=resultSet.getString("gender");
                    System.out.println();
                    System.out.printf("|%-12s |%-15s |%-15s\n",id,name,specialString);
                    System.out.println("+-----------+--------------+---------------------+");
                
                }


          }catch(SQLException e){
               e.printStackTrace();

          }
     }
          public boolean getDoctorById(int id){
               String sql="select * from doctors where id=?;";

               try{
                    PreparedStatement preparedStatement=connection.prepareStatement(sql);
                    preparedStatement.setInt(1,id);
                    ResultSet resultSet=preparedStatement.executeQuery();
                    if(resultSet.next()){
                         return true;
                    }else{
                         return false;
                    }

               }catch(SQLException e){
                    e.printStackTrace();
               }
          return false;

         }
}
