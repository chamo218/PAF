package model;

import java.sql.*; 

public class AccountType {
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
		 
		 public String insertAccountType(String type, String fixedCharge, String chargeForBlock1, String chargeForBlock2, String chargeForBlock3) 
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
				 String query = " insert into accounttypes (`accountTypeID`,`type`,`fixedCharge`,`chargeForBlock1`,`chargeForBlock2`,`chargeForBlock3`)"
				 + " values (?, ?, ?, ?, ?, ?)"; 
				 PreparedStatement preparedStmt = con.prepareStatement(query); 
				 
				 // binding values
				 preparedStmt.setInt(1, 0); 
				 preparedStmt.setString(2, type); 
				 preparedStmt.setDouble(3, Double.parseDouble(fixedCharge)); 
				 preparedStmt.setDouble(4, Double.parseDouble(chargeForBlock1)); 
				 preparedStmt.setDouble(5, Double.parseDouble(chargeForBlock2)); 
				 preparedStmt.setDouble(6, Double.parseDouble(chargeForBlock3)); 
				 
				 // execute the statement
				 preparedStmt.execute(); 
				 con.close(); 
				 
				 output = "Inserted successfully"; 
			 } 
			 catch (Exception e) 
			 { 
				 output = "Error while inserting the accountTypes."; 
				 System.err.println(e.getMessage()); 
			 } 
			 return output; 
		 } 
		 
		 public String readIAccountTypes() 
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
				 output = "<table border='1'><tr><th>Type</th>" +
				 "<th>Fixed Charge</th>" + 
				 "<th>Charge For Block1</th>" +
				 "<th>Charge For Block2</th>" +
				 "<th>Charge For Block3</th>" +
				 "<th>Update</th><th>Remove</th></tr>"; 
				 
				 String query = "select * from accounttypes"; 
				 Statement stmt = con.createStatement(); 
				 ResultSet rs = stmt.executeQuery(query); 
				 
				 // iterate through the rows in the result set
				 while (rs.next()) 
				 { 
					 String accountTypeID = Integer.toString(rs.getInt("accountTypeID")); 
					 String type = rs.getString("type"); 
					 String fixedCharge = Double.toString(rs.getDouble("fixedCharge")); 
					 String chargeForBlock1 = Double.toString(rs.getDouble("chargeForBlock1")); 
					 String chargeForBlock2 = Double.toString(rs.getDouble("chargeForBlock2")); 
					 String chargeForBlock3 = Double.toString(rs.getDouble("chargeForBlock3")); 
					 
					 // Add into the html table
					 output += "<tr><td>" + type + "</td>"; 
					 output += "<td>" + fixedCharge + "</td>"; 
					 output += "<td>" + chargeForBlock1 + "</td>"; 
					 output += "<td>" + chargeForBlock2 + "</td>"; 
					 output += "<td>" + chargeForBlock3 + "</td>"; 
					 
					 // buttons
					 output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
					 + "<td><form method='post' action='items.jsp'>"
					 + "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
					 + "<input name='accountTypeID' type='hidden' value='" + accountTypeID 
					 + "'>" + "</form></td></tr>"; 
				 } 
				 
				 con.close(); 
				 
				 // Complete the html table
				 output += "</table>"; 
			 } 
			 catch (Exception e) 
			 { 
				 output = "Error while reading the account types."; 
				 System.err.println(e.getMessage()); 
			 } 
			 return output; 
		 } 
		 
		 public String updateAccountType(String ID, String type, String fixedCharge, String chargeForBlock1, String chargeForBlock2, String chargeForBlock3) 
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
				 String query = "UPDATE accounttypes SET type=?,fixedCharge=?,chargeForBlock1=?,chargeForBlock2=?,chargeForBlock3=? WHERE accountTypeID=?"; 
				 PreparedStatement preparedStmt = con.prepareStatement(query); 
				 
				 // binding values 
				 preparedStmt.setString(1, type); 
				 preparedStmt.setString(2, fixedCharge); 
				 preparedStmt.setDouble(3, Double.parseDouble(chargeForBlock1)); 
				 preparedStmt.setDouble(4, Double.parseDouble(chargeForBlock2)); 
				 preparedStmt.setDouble(5, Double.parseDouble(chargeForBlock3)); 
				 preparedStmt.setInt(6, Integer.parseInt(ID)); 
				 
				 // execute the statement
				 preparedStmt.execute(); 
				 con.close(); 
				 
				 output = "Updated successfully"; 
			 } 
			 catch (Exception e) 
			 { 
				 output = "Error while updating the accountTypes."; 
				 System.err.println(e.getMessage()); 
			 } 
			 return output; 
		 } 
		 
		 public String deleteAccountTypes(String accountTypeID) 
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
				 String query = "delete from accounttypes where accountTypeID=?"; 
				 PreparedStatement preparedStmt = con.prepareStatement(query); 
				 
				 // binding values
				 preparedStmt.setInt(1, Integer.parseInt(accountTypeID)); 
				 
				 // execute the statement
				 preparedStmt.execute(); 
				 con.close(); 
				 
				 output = "Deleted successfully"; 
			 } 
			 catch (Exception e) 
			 { 
				 output = "Error while deleting the accountTypes."; 
				 System.err.println(e.getMessage()); 
			 } 
			 return output; 
		 } 
}
