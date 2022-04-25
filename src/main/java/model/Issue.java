package model;
import java.sql.*;
public class Issue
{ //A common method to connect to the DB
private Connection connect()
 {
 Connection con = null;
 try
 {
 Class.forName("com.mysql.jdbc.Driver");

 //Provide the correct details: DBServer/DBName, username, password
 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "");
 }
 catch (Exception e)
 {e.printStackTrace();}
 return con;
 }
public String insertIssue(String code, String name, String price, String desc)
 {
 String output = "";
 try
 {
 Connection con = connect();
 if (con == null)
 {return "Error while connecting to the database for inserting."; }
 // create a prepared statement
 String query = " insert into issues (`issueID`,`issueCode`,`empAllocated`,`systemID`,`issueDesc`)"
 + " values (?, ?, ?, ?, ?)";
 PreparedStatement preparedStmt = con.prepareStatement(query);
 // binding values
 preparedStmt.setInt(1, 0);
 preparedStmt.setString(2, code);
 preparedStmt.setString(3, name);
 preparedStmt.setDouble(4, Double.parseDouble(price));
 preparedStmt.setString(5, desc);
 // execute the statement
 preparedStmt.execute();
 con.close();
 output = "Inserted successfully";
 }
 catch (Exception e)
 {
 output = "Error while inserting the issue.";
 System.err.println(e.getMessage());
 }
 return output;
 }
public String readIssues()
 {
 String output = "";
 try
 {
 Connection con = connect();
 if (con == null)
 {return "Error while connecting to the database for reading."; }
 // Prepare the html table to be displayed
 output = "<table border='1'><tr><th>Issue Code</th><th>Issue Name</th>" +
 "<th>Issue Price</th>" +
 "<th>Issue Description</th>" +
 "<th>Update</th><th>Remove</th></tr>";

 String query = "select * from issues";
 Statement stmt = con.createStatement();
 ResultSet rs = stmt.executeQuery(query);
 // iterate through the rows in the result set
 while (rs.next())
 {
 String issueID = Integer.toString(rs.getInt("issueID"));
 String issueCode = rs.getString("issueCode");
 String empAllocated = rs.getString("empAllocated");
 String systemID = Double.toString(rs.getDouble("systemID"));
 String issueDesc = rs.getString("issueDesc");
 // Add into the html table
 output += "<tr><td>" + issueCode + "</td>";
 output += "<td>" + empAllocated + "</td>";
 output += "<td>" + systemID + "</td>";
 output += "<td>" + issueDesc + "</td>";
 // buttons
 output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
 + "<td><form method='post' action='issues.jsp'>"
 + "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
 + "<input name='issueID' type='hidden' value='" + issueID
 + "'>" + "</form></td></tr>";
 }
 con.close();
 // Complete the html table
 output += "</table>";
 }
 catch (Exception e)
 {
 output = "Error while reading the issues.";
 System.err.println(e.getMessage());
 }
 return output;
 }
public String updateIssue(String ID, String code, String name, String price, String desc) 
{
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {return "Error while connecting to the database for updating."; }
	 // create a prepared statement
	 String query = "UPDATE issues SET issueCode=?,empAllocated=?,systemID=?,issueDesc=? WHERE issueID=?";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 // binding values
	 preparedStmt.setString(1, code);
	 preparedStmt.setString(2, name);
	 preparedStmt.setDouble(3, Double.parseDouble(price));
	 preparedStmt.setString(4, desc);
	 preparedStmt.setInt(5, Integer.parseInt(ID));
	 // execute the statement
	 preparedStmt.execute();
	 con.close();
	 output = "Updated successfully";
	 }
	 catch (Exception e)
	 {
	 output = "Error while updating the issue.";
	 System.err.println(e.getMessage());
	 }
	 return output;
	 }
	public String deleteIssue(String issueID)
	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {return "Error while connecting to the database for deleting."; }
	 // create a prepared statement
	 String query = "delete from issues where issueID=?";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 // binding values
	 preparedStmt.setInt(1, Integer.parseInt(issueID));
	 // execute the statement
	 preparedStmt.execute();
	 con.close();
	 output = "Deleted successfully";
	 }
	 catch (Exception e)
	 {
	 output = "Error while deleting the issue.";
	 System.err.println(e.getMessage());
	 }
	 return output;
	 }
	} 