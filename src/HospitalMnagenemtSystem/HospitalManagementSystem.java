package HospitalMnagenemtSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

//import com.mysql.cj.xdevapi.PreparableStatement;

public class HospitalManagementSystem {
  private static final   String url="jdbc:mysql://Localhost:3306/hospital";
    private static final      String username="root";
    private static final      String password="root";
    
    public static void main(String[] args) {
     try{
          // Class.forName("com.mysql.jdbc.Diver");
          Class.forName("com.mysql.cj.jdbc.Driver");
          // System.out.println("Driver loaded successfully");
           }   catch(ClassNotFoundException e){
                  System.out.println(e.getMessage());
              }    
              Scanner scanner=new Scanner(System.in);

              try{
               //@SuppressWarnings("unused")
               Connection connection=DriverManager.getConnection(url,username,password);
               //System.out.println("connection estavlished Successfully !!");

               //Connection connection;
               Patient patient=new Patient(connection,scanner);
               Doctor doctor= new Doctor(connection,scanner);

               while (true) {
                System.out.println("*******************HOSPITAL MANAGEMENT SYSTEM***********************");
                System.out.println("1.Add Patient");
                System.out.println("2.View Patients ");
                System.out.println("3.Add New Doctor");
                System.out.println("4.View Doctors ");
                System.out.println("5.Book Appointment ");
                System.out.println("6.Exit ");

                   System.out.println("Enter your choice ");
                   int  choice=scanner.nextInt();
                   switch (choice) {
                    case 1:
                          //addpatients
                          patient.addPatient();
                          System.out.println("");
                          break;
                          case 2:
                          //view Patients
                          patient.viewPatientS();
                          System.out.println(" ");
                          break;
                          case 3:
                          doctor.JoinNewDoctor();
                          System.out.println(" ");    
                          break;
                          case 4:
                          //View Doctors
                          doctor.viewDoctor();
                          System.out.println(" ");
                          break;
                          case 5:
                          //Book Appointment
                          bookAppointment(patient, doctor, connection, scanner);
                          System.out.println(" ");;
                          break;

                          case 6:
                          System.out.println("Thinkyou for using Hospital Management System !!!");
                          return;
                         
                          default :
                          System.out.println("Enter valid choice !!!");

                   }
               }
                 }catch(SQLException e){
                   System.out.println(e.getMessage());
           }
   
    }
    
    public static void bookAppointment(Patient patient,Doctor docter,Connection connection,Scanner scanner){

         System.out.println("Enter Patient ID");
         int patientId=scanner.nextInt();
         System.out.println("Enter Doctor ID:");
         int docterId=scanner.nextInt();
         System.out.println("Enter Appointment date(yyyy-mm-dd)");
         String appointment=scanner.next();
         if(patient.getPatientById(patientId)&& docter.getDoctorById(docterId)){
          if (checkDoctorAvalilability(docterId , appointment, connection)) {
            String appsql="insert into appointments(patient_id,doctor_id,appointment_date)values(?,?,?);";
             try {
              PreparedStatement preparedStatement= connection.prepareStatement(appsql);
              preparedStatement.setInt(1,patientId);
              preparedStatement.setInt(2,docterId);
              preparedStatement.setString(3,appointment);
              int rowsAffected=preparedStatement.executeUpdate();
              if(rowsAffected>0){
                System.out.println("Appointment Booked!");
              }
              else{
                System.out.println("Failed to  book Appointment");
              }

             } catch (SQLException e) {
              
              e.printStackTrace();

             }
          }else{
            System.out.println("Doctor not available on this date!!");
          }
         }else{
          System.out.println("Docter and Patient doesn't exists!!!");
         }
    }

    public static boolean checkDoctorAvalilability(int docterId ,String appointment,Connection connection){

               String sql="Select COUNT(*)from appointments where doctor_id=? and appointment_date=?;";
               try {

                PreparedStatement preparedStatement=connection.prepareStatement(sql);
                preparedStatement.setInt(1,docterId);
                preparedStatement.setString(2,appointment);           
                ResultSet resultSet=preparedStatement.executeQuery();
                   if(resultSet.next()){
                
                    int count=resultSet.getInt(1);
                if(count==0){
                  return true;
                }else{
                  return false;
                }
                  }

               } catch (SQLException e) {

                e.printStackTrace();

              }
              return false;
              
    }
}
