package projekt_2022;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.sqlite.SQLiteDataSource;

public class SQL {
	private Connection conn;
	
	//function for numerical round
			private static double round(double value, int places) {
			    if (places < 0) throw new IllegalArgumentException();

			    long factor = (long) Math.pow(10, places);
			    value = value * factor;
			    long tmp = Math.round(value);
			    return (double) tmp / factor;
			}
	
	public boolean connect() { 
	       conn= null; 
	       try {
	              conn = DriverManager.getConnection("jdbc:sqlite:studentsDB.db");                       
	       } 
	      catch (SQLException e) { 
	            System.out.println(e.getMessage());
		    return false;
	      }
	      return true;
	}
	
	public void disconnect() { 
		if (conn != null) {
		       try {     conn.close();  } 
	               catch (SQLException ex) { System.out.println(ex.getMessage() + "\nSQLite not disconnected"); }
		}

	}
	
	public boolean createTable()
	{
	     if (conn==null)
	           return false;
	    String sql = "CREATE TABLE IF NOT EXISTS students (" + "id integer PRIMARY KEY," + "grp varchar(255),"+ "firstname varchar(255), " + "lastname varchar(255), " +"day int," + "month int," + "year int," + "avg float" + ");";
	    try{
	            Statement stmt = conn.createStatement(); 
	            stmt.execute(sql);
	            return true;
	    } 
	    catch (SQLException e) {
	    System.out.println(e.getMessage());
	    }
	    return false;
	}


	
	public void insertRecord(int id, String grp, String firstname, String lastname,int day,int month,int year ,float average ) {
        connect();
        String sql = "INSERT INTO students(id, grp, firstname, lastname, day, month, year, avg) VALUES(?,?,?,?,?,?,?,?)";
        try {
        
            PreparedStatement pstmt = conn.prepareStatement(sql); 
            pstmt.setInt(1, id);
            pstmt.setString(2, grp);
            pstmt.setString(3, firstname);
            pstmt.setString(4, lastname);
            pstmt.setInt(5, day);
            pstmt.setInt(6, month);
            pstmt.setInt(7, year);
            pstmt.setFloat(8, average);
           if(pstmt.executeUpdate()==0) System.err.println("SQL_error: insert not executed");
           else System.out.println("SQL: " + lastname + " with ID: " + id + " successfully inserted"); 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        disconnect();
        
    }
	
	public void update(int id, String grp, String firstname, String lastname,int day,int month,int year ,float average ) {
		connect();
		String sql = "UPDATE students SET id = ?, grp = ?, firstname = ?, lastname = ?, day = ?, month = ?, year = ?, avg = ? WHERE id = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setString(2, grp);
            pstmt.setString(3, firstname);
            pstmt.setString(4, lastname);
            pstmt.setInt(5, day);
            pstmt.setInt(6, month);
            pstmt.setInt(7, year);
            pstmt.setFloat(8, average);
            pstmt.setInt(9, id);
            pstmt.executeUpdate();
            System.out.println("SQL: row: " + id + " status: Query updated!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		disconnect();
        
		
	}
	
	public void selectAll(){
		connect();
        String sql = "SELECT id, grp, firstname, lastname, day, month, year, avg FROM students";
        try {
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql);
            System.out.println("ID\tSpec\tFirstname\tLastname\tBirth date\tAvg mark\n");
             while (rs.next()) {
                	System.out.println(
                rs.getInt("id") +  "\t" +
                rs.getString("grp") + "\t" +
				rs.getString("firstname") + "    \t" +
				rs.getString("lastname") + "    \t" +
				rs.getInt("day") + "." +
				rs.getInt("month") + "." +
				rs.getInt("year") + " \t" +
				rs.getFloat("avg") + "\t");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        disconnect();
        System.out.println("\nNote: t - technician, h - humanist, c - combined");
	}
	
	public void getRecord(int id){
	     String sql = "SELECT id, grp, firstname, lastname, day, month, year, avg FROM students where id=?";
	     try {
	           PreparedStatement pstmt  = conn.prepareStatement(sql);
	           pstmt.setInt(1,id);
	           ResultSet rs  = pstmt.executeQuery();

	          while (rs.next()) {
	               System.out.println(
	            		   		rs.getInt("id") +  "\t" + 
	                            rs.getString("group") + "\t" +
	                            rs.getString("firstname") + "\t" +
	                            rs.getString("lastname") + "\t" +
	                            rs.getInt("day") + "\t" +
	                            rs.getInt("month") + "\t" +
	                            rs.getInt("year") + "\t" +
	                            rs.getFloat("average") + "\t");
	          }
	     } 
	     catch (SQLException e) {
	           System.out.println(e.getMessage());
	     }
	}
	
	public void importSQL() throws IOException { 
		connect();
		String sql = "SELECT id, grp, firstname, lastname, day, month, year, avg FROM students";
	        try {
	             Statement stmt  = conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql);
	             BufferedWriter bw = new BufferedWriter(new FileWriter("root\\data.txt"));
	             
	             while (rs.next()) {
	            	 bw.write(rs.getString("grp") + " " + rs.getInt("ID") + " " + rs.getString("firstname") + " " + rs.getString("lastname") + " " + rs.getInt("day") + " " + rs.getInt("month") + " " + rs.getInt("year") + " " + round(rs.getDouble("avg"),2) + "\n");
	            }
	             bw.close();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	        disconnect();
	}
	
	public void delete(int id) {
		connect();
        String sql = "DELETE FROM students WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            // execute the delete statement
            if(pstmt.executeUpdate()==0) System.err.println("SQL_error : row with ID " + " not found");
            else System.out.println("SQL: student with ID: " + id + " deleted");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        disconnect();
    }
}
