package model;
import java.sql.*;

public class User {
	
	private Connection connect()
	{
		 Connection con = null;
	
		 try
		 {
			 Class.forName("com.mysql.jdbc.Driver");
			 con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/users", "root", "");
			 
			 //For testing
			 System.out.print("Successfully connected");
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
	
		 return con;
	}
	
	public String insertUser(String name, String phone, String address, String email, String password)
	{
		 String output = "";
		 try
		 {
			 Connection con = connect();
			 if (con == null)
			 {
				 return "Error while connecting to the database";
			 }
			 
			 // create a prepared statement
			 String query = " insert into users (`userID`,`name`,`phone`,`address`,`email`, `password`)"
					 + " values (?, ?, ?, ?, ?, ?)";
			 	PreparedStatement preparedStmt = con.prepareStatement(query);
			 	// binding values
				preparedStmt.setInt(1, 0);
				preparedStmt.setString(2, name);
				preparedStmt.setString(3, phone);
				preparedStmt.setString(4, address);
				preparedStmt.setString(5, email);
				preparedStmt.setString(6, password);

				//execute the statement
				preparedStmt.execute();
				con.close();
				
				output = "Account Successfully Created";	
		 }
		 catch (Exception e)
		 {
				 output = "Error while inserting";
				 System.err.println(e.getMessage());
		 }
		 return output;
	}
	
	
	public String readUsers()
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
				 output = "<table border='1'><tr>"
						 +"<th>name</th><th>phone</th>"
						 + "<th>address</th><th>email</th><th>password</th>"
						 + "<th>Update</th><th>Remove</th></tr>";
				 
				 String query = "select * from users";
				 Statement stmt = con.createStatement();
				 ResultSet rs = stmt.executeQuery(query);
				 
				 // iterate through the rows in the result set
				 while (rs.next())
				 {
					 String userID = Integer.toString(rs.getInt("userID"));
					 String name = rs.getString("name");
					 String phone = rs.getString("phone");
					 String address = rs.getString("address");
					 String email = rs.getString("email");
					 String password = rs.getString("password");
					 
					 // Add a row into the html table
					 output += "<tr><td>" + name + "</td>";
					 output += "<td>" + phone + "</td>";
					output += "<td>" + address + "</td>";
					output += "<td>" + email + "</td>";
					output += "<td>" + password + "</td>";
	
					 // buttons
					 output += "<td><input name='btnUpdate' type='button' value='Update' action = 'update.jsp'></td>"
					 + "<td><form method='post' action='UserDetails.jsp'>"
					 + "<input name='btnRemove' "
					 + " type='submit' value='Remove'>"
					 + "<input name='userID' type='hidden' "
					 + " value='" + userID + "'>" + "</form></td></tr>";
				 }
				 con.close();
				 // Complete the html table
				 output += "</table>";
			 }
			 catch (Exception e)
			 {
			 output = "Error while reading the Users.";
			 System.err.println(e.getMessage());
			 }
			return output;
	}
	
	public String updateUser(String userID, String name, String phone, String address, String email, String password)
	{
		 String output = "";
		 
		 try
		 {
			 Connection con = connect();
			 
			 if (con == null)
			 {return "Error while connecting to the database for updating."; }
			 
			 // create a prepared statement
			 String query = "UPDATE users SET name=?,phone=?,address=?, email=?, password=?  WHERE userID=?";
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 // binding values
			 
			 preparedStmt.setString(1, name);
			 preparedStmt.setString(2, phone);
			 preparedStmt.setString(3, address);
			 preparedStmt.setString(4, email);
			 preparedStmt.setString(5, password);

			 preparedStmt.setInt(6, Integer.parseInt(userID));
			 // execute the statement
			 preparedStmt.execute();
			 con.close();
			 output = "Account Updated successfully";
		 }
		 
		 catch (Exception e)
		 {
			 output = "Error while updating the user";
			 System.err.println(e.getMessage());
		 }
		 
		 	return output;
	}
	
	
	public String deleteUser(String userID)
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
					 
					 String query = "delete from users where userID=?";
					 PreparedStatement preparedStmt = con.prepareStatement(query);
					 
					 // binding values
					 preparedStmt.setInt(1, Integer.parseInt(userID));
				
					 // execute the statement
					 preparedStmt.execute();
					 con.close();
					 output = "Account Deleted successfully";
			}
			catch (Exception e)
			{
					output = "Error while deleting the user.";
					System.err.println(e.getMessage());
			}
			
			return output;
	}
	
}
