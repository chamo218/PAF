package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter; 

public class Bill {
		//A common method to connect to the DB
		 private Connection connect() 
		 { 
			 Connection con = null; 
			 try
			 { 
				 Class.forName("com.mysql.jdbc.Driver"); 
				 
				 //Provide the correct details: DBServer/DBName, username, password 
				 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paf", "root", ""); 
			 } 
			 catch (Exception e) 
			 {
				 e.printStackTrace();
			 } 
			 return con; 
		 }
		 
		 public String insertBill(String accountID, String accountTypeID, String units) 
		 { 
			 String output = ""; 
			 
			 try
			 { 
				 Connection con = connect();
				 
				 if (con == null) 
				 {
					 return "Error while connecting to the database for inserting."; 
				 }
				 
				 // create a prepared statement
				 String query = " insert into bills (`billID`,`accountID`,`accountTypeID`,`date`,`units`,`chargeForUnits`,`previousBalance`,`totalAmount`,`paymentStatus`)"
				 + " values (?, ?, ?, ?, ?, ?, ?,?,?)"; 
				 PreparedStatement preparedStmt = con.prepareStatement(query); 
				 
				 LocalDate localDate = LocalDate.now(); 
				 //DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
				 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				 String formatDateTime = localDate.format(formatter);
				 //Date date1=(Date) new SimpleDateFormat("yyyy-MM-dd").parse(formatDateTime);
				 
				 //variable declaration
				 Double chargeForUnits = 0.00;
				 Double previousBalance = 0.00;
				 Double totalAmount = 0.00;
				 String paymentStatus = "unpaid";
				 
				 //bill calculation
				 int id=Integer.parseInt(accountTypeID);
				 int u = Integer.parseInt(units);
				 
				 if(id == 1) {
					 if(u <= 60) {
						 chargeForUnits = 7.85 * u;
						 totalAmount = chargeForUnits + 60.00;
					 }
					 else {
						int a = u-60;
						chargeForUnits = (7.85 * 60) + (10.00 * a);
						totalAmount = chargeForUnits + 60.00;
						 
					 }
				 }
				 else if (id == 2) {
					 chargeForUnits = 10.80*u;
					 totalAmount = chargeForUnits + 600.00;
				 }
				 else if (id == 3) {
					 chargeForUnits = 18.80*u;
					 totalAmount = chargeForUnits + 600.00;
				 }
				 else {
					 output = "Invalid account type"; 
				 }
				 

				 preparedStmt.setInt(1, 0); 
				 preparedStmt.setInt(2, Integer.parseInt(accountID)); 
				 preparedStmt.setInt(3, id); 
				 preparedStmt.setString(4, formatDateTime); 
				 //preparedStmt.setDate(3, date1); 
				 preparedStmt.setInt(5, Integer.parseInt(units)); 
				 preparedStmt.setDouble(6, chargeForUnits);
				 preparedStmt.setDouble(7, previousBalance);
				 preparedStmt.setDouble(8, totalAmount);
				 preparedStmt.setString(9, paymentStatus); 
				 
				 // execute the statement
				 preparedStmt.execute(); 
				 con.close(); 
				 
				 output = "Inserted successfully"; 
			 } 
			 catch (Exception e) 
			 { 
				 output = "Error while inserting the bills."; 
				 System.err.println(e.getMessage()); 
			 } 
			 return output; 
		 } 
		 
		 public String readBills() 
		 { 
			 String output = ""; 
			 
			 try
			 { 
				 Connection con = connect(); 
				 
				 if (con == null) 
				 {
					 return "Error while connecting to the database for reading."; 
				 }
				 
				 // Prepare the html table to be displayed
				 output = "<table border='1'><tr><th>Bill ID</th><th>Account ID</th>" +
				 "<th>Account Type ID</th>" + 
				 "<th>Date</th>" + 
				 "<th>Units</th>" +
				 "<th>Charge for units</th>" +
				 "<th>Previous Balance</th>" +
				 "<th>Total Amount</th>" +
				 "<th>Payment Status</th>" +
				 "<th>Update</th><th>Remove</th>"+
				 "</tr>"; 
				 
				 String query = "select * from bills"; 
				 Statement stmt = con.createStatement(); 
				 ResultSet rs = stmt.executeQuery(query); 
				 //DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
				 
				 
				 // iterate through the rows in the result set
				 while (rs.next()) 
				 { 
					 String billID = Integer.toString(rs.getInt("billID")); 
					 String accountID = Integer.toString(rs.getInt("accountID")); 
					 String accountTypeID = Integer.toString(rs.getInt("accountTypeID")); 
					 //String date = dateFormat.format(rs.getDate("date")); 
					 String date = rs.getString("date");
					 String units = Integer.toString(rs.getInt("units")); 
					 String chargeForUnits = Double.toString(rs.getDouble("chargeForUnits")); 
					 String previousBalance = Double.toString(rs.getDouble("previousBalance"));
					 String totalAmount = Double.toString(rs.getDouble("totalAmount"));
					 String paymentStatus = rs.getString("paymentStatus"); 
					 
					 
					 // Add into the html table
					 output += "<tr><td>" + billID + "</td>"; 
					 output += "<td>" + accountID + "</td>"; 
					 output += "<td>" + accountTypeID + "</td>"; 
					 output += "<td>" + date + "</td>"; 
					 output += "<td>" + units + "</td>"; 
					 output += "<td>" + chargeForUnits + "</td>";
					 output += "<td>" + previousBalance + "</td>";
					 output += "<td>" + totalAmount + "</td>";
					 output += "<td>" + paymentStatus + "</td>";
					 
					 // buttons
					 output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
					 + "<td><form method='post' action='items.jsp'>"
					 + "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
					 + "<input name='billID' type='hidden' value='" + billID 
					 + "'>" + "</form></td></tr>"; 
				 } 
				 
				 con.close();  
				 
				 // Complete the html table
				 output += "</table>"; 
			 } 
			 catch (Exception e) 
			 { 
				 output = "Error while reading the bills."; 
				 System.err.println(e.getMessage()); 
			 } 
			 return output; 
		 } 
		 
		 public String updateBill(String ID, String paymentStatus) 
		 { 
			 String output = ""; 
			 
			 try
			 { 
				 Connection con = connect(); 
				 
				 if (con == null) 
				 {
					 return "Error while connecting to the database for updating."; 
				 }
				 
				 // create a prepared statement
				 String query = "UPDATE bills SET paymentStatus=? WHERE billID=?"; 
				 PreparedStatement preparedStmt = con.prepareStatement(query); 
				 
				 // binding values 
				 preparedStmt.setString(1, paymentStatus); 
				 preparedStmt.setInt(2, Integer.parseInt(ID)); 
				 
				 // execute the statement
				 preparedStmt.execute(); 
				 con.close(); 
				 
				 output = "Updated successfully"; 
			 } 
			 catch (Exception e) 
			 { 
				 output = "Error while updating the bills."; 
				 System.err.println(e.getMessage()); 
			 } 
			 return output; 
		 } 
		 
		 public String deleteBills(String billID) 
		 { 
			 String output = ""; 
			 
			 try
			 { 
				 Connection con = connect(); 
				 
				 if (con == null) 
				 {
					 return "Error while connecting to the database for deleting."; 
				 }
				 
				 // create a prepared statement
				 String query = "delete from bills where billID=?"; 
				 PreparedStatement preparedStmt = con.prepareStatement(query); 
				 
				 // binding values
				 preparedStmt.setInt(1, Integer.parseInt(billID)); 
				 
				 // execute the statement
				 preparedStmt.execute(); 
				 con.close(); 
				 
				 output = "Deleted successfully"; 
			 } 
			 catch (Exception e) 
			 { 
				 output = "Error while deleting the bills."; 
				 System.err.println(e.getMessage()); 
			 } 
			 return output; 
		 } 
}