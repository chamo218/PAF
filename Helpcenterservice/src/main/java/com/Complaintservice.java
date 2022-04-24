package com;

import model.complaint;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType; 
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/") 
public class Complaintservice {
	complaint cobj = new complaint();
	
	@GET
	
	@Path("/readcomplaint")
	
	@Produces(MediaType.TEXT_PLAIN)
	public String readcomplaint()
	 {
	 return cobj.readcomplaint();
	 } 
	@POST
	@Path("/insertcomplaint")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN)
	public String insertcomplaint(@FormParam("c_id") String c_id,
			 @FormParam("i_type") String i_type,
			 @FormParam("message") String message,
			 @FormParam("status") String status)
			{
			 String output = cobj.insertItem(c_id, i_type, message, status);
			return output;
			}
	
	@PUT
	@Path("/updatecomp")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updatecomp(String compdata)
	{
	//Convert the input string to a JSON object
	 JsonObject itemObject = new JsonParser().parse(compdata).getAsJsonObject();
	//Read the values from the JSON object
	 String c_id = itemObject.get("c_id").getAsString();
	 String i_type = itemObject.get("i_type").getAsString();
	 String message = itemObject.get("message").getAsString();
	 String status = itemObject.get("status").getAsString();
	 
	 String output = cobj.updateItem(c_id, i_type, message, status);
	return output;
	}

	@DELETE
	@Path("/deletecomp")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletecomp(String itemData)
	{
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(itemData, "", Parser.xmlParser());

	//Read the value from the element <itemID>
	 String c_id = doc.select("c_id").text();
	 String output = cobj.deletecomplaint(c_id);
	return output;
	}
}
