package com;

import model.Bill;

//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 

//For JSON
import com.google.gson.*; 

//For XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document; 

@Path("/Bills") 
public class BillService {
	
	Bill billObj = new Bill(); 
	
	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 
	public String readItems() 
	{ 
		return billObj.readBills(); 
	}
	
	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertBill(@FormParam("accountID") String accountID,
							 @FormParam("accountTypeID") String accountTypeID,
							 @FormParam("units") String units)
	{ 
		String output = billObj.insertBill(accountID,accountTypeID, units); 
		return output; 
	}
	
	@PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateBill(String billData) 
	{ 
		 //Convert the input string to a JSON object 
		 JsonObject billObject = new JsonParser().parse(billData).getAsJsonObject(); 
		 
		 //Read the values from the JSON object
		 String billID  = billObject.get("billID").getAsString(); 
		 String paymentStatus = billObject.get("paymentStatus").getAsString(); 
		  
		 
		 String output = billObj.updateBill(billID, paymentStatus); 
		 return output; 
	}
	
	@DELETE
	@Path("/") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteBills(String BillData) 
	{ 
		 //Convert the input string to an XML document
		 Document doc = Jsoup.parse(BillData, "", Parser.xmlParser()); 
		 
		 //Read the value from the element <billID>
		 String billID = doc.select("billID").text(); 
		 
		 String output = billObj.deleteBills(billID); 
		 return output; 
	}
}