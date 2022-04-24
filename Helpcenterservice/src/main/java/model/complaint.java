package model;
import java.sql.*;
public class complaint {
	//A common method to connect to the DB
	private Connection connect()
	 {
	   Connection con = null;
	   try
	   {
	      Class.forName("com.mysql.jdbc.Driver");

	      //Provide the correct details: DBServer/DBName, username, password
	      con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electrogrid", "root", "");
	 }
		 catch (Exception e)
		 {e.printStackTrace();}
		 return con;
	 }
	public String insertItem(String c_id, String i_type, String message, String status)
	 {
		 String output = "";
		 try
	 {
		 Connection con = connect();
		 if (con == null)
		 {return "Error while connecting to the database for inserting."; }
		 // create a prepared statement
		 String query = " insert into complaint (`c_id`,`i_type`,`message`,`status`)"
		                    + " values (?, ?, ?, ?)";
		 PreparedStatement preparedStmt = con.prepareStatement(query);
		 
		 // binding values
		 
		 preparedStmt.setString(1, c_id);
		 preparedStmt.setString(2, i_type);
		 preparedStmt.setString(3, message);
		 preparedStmt.setString(4, status);
		 // execute the statement
	 
		 preparedStmt.execute();
		 con.close();
	     output = "Inserted successfully";
	 }
	 catch (Exception e)
	 {
		 output = "Error while inserting the item.";
		 System.err.println(e.getMessage());
	 }
	     return output;
	 }
	public String readcomplaint()
	 {
		 String output = "";
		 try
	     {
			 Connection con = connect();
			 if (con == null)
			 {return "Error while connecting to the database for reading."; }
			 // Prepare the html table to be displayed
			 output = "<table border='1'><tr><th>Complaint ID</th><th>Inquiry type</th>" +
			 "<th>Message</th>" +
			 "<th>Status <th>" +
			 "<th>Update</th><th>Remove</th></tr>";
		
			 String query = "select * from complaint";
			 Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery(query);
			 // iterate through the rows in the result set
			 while (rs.next())
	         {
				 String CID = rs.getString("c_id");
				 String IType = rs.getString("i_type");
				 String Message = rs.getString("message");
				 String Status = rs.getString("status");
				 
				 // Add into the html table
				 output += "<tr><td>" + CID + "</td>";
				 output += "<td>" + IType + "</td>";
				 output += "<td>" + Message + "</td>";
				 output += "<td>" +Status + "</td>";
				 // buttons
				 output += "</tr>";
	 }
		 con.close();
		 // Complete the html table
		 output += "</table>";
	 }
	 catch (Exception e)
	 {
		 output = "Error while reading the complaints.";
		 System.err.println(e.getMessage());
	 }
	 return output;
	 }
	public String updateItem(String cid, String itype, String message, String status)
	 
	 {
	 String output = "";
	 try
	 {
		 Connection con = connect();
		 if (con == null)
		 {return "Error while connecting to the database for updating."; }
		 // create a prepared statement
		 String query = "UPDATE complaint SET i_type=?,message=?,status=? WHERE c_id=?";
		 
		 PreparedStatement preparedStmt = con.prepareStatement(query);
		 // binding values
		 preparedStmt.setString(1, itype);
		 preparedStmt.setString(2, message);
		 preparedStmt.setString(3, status);
		 preparedStmt.setString(4, cid);
		 
		 // execute the statement
		 preparedStmt.execute();
		 con.close();
		 output = "Updated successfully";
	 }
	 catch (Exception e)
	 {
		 output = "Error while updating the item.";
		 System.err.println(e.getMessage());
	 }
	 	return output;
	 }
		public String deletecomplaint(String c_id)
	 {
			String output = "";
	 try
	 {
		 Connection con = connect();
		 if (con == null)
	 
			 
			 {return "Error while connecting to the database for deleting."; }
	 // create a prepared statement
	 String query = "delete from complaint where c_id=?";
	 
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 
	 // binding values
	 preparedStmt.setString(1, c_id);
	 // execute the statement
	 preparedStmt.execute();
	 con.close();
	 output = "Deleted successfully";
	 }
	 catch (Exception e)
	 {
	 output = "Error while deleting the item.";
	 System.err.println(e.getMessage());
	 }
	 return output;
	 } 

	
}
