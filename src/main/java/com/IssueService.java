package com;
import model.Issue;
//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;
@Path("/Issues")
public class IssueService
{
 Issue issueObj = new Issue();
 @GET
 @Path("/")
 @Produces(MediaType.TEXT_HTML)
 public String readIssues()
  {
  return issueObj.readIssues();
 }
 
 
 @POST
 @Path("/")
 @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
 @Produces(MediaType.TEXT_PLAIN)
 public String insertIssue(@FormParam("issueCode") String issueCode,
  @FormParam("empAllocated") String empAllocated,
  @FormParam("systemID") String systemID,
  @FormParam("issueDesc") String issueDesc)
 {
  String output = issueObj.insertIssue(issueCode, empAllocated, systemID, issueDesc);
 return output;
 }

 
 
 @PUT
 @Path("/")
 @Consumes(MediaType.APPLICATION_JSON)
 @Produces(MediaType.TEXT_PLAIN)
 public String updateIssue(String issueData)
 {
 //Convert the input string to a JSON object
  JsonObject issueObject = new JsonParser().parse(issueData).getAsJsonObject();
 //Read the values from the JSON object
  String issueID = issueObject.get("issueID").getAsString();
  String issueCode = issueObject.get("issueCode").getAsString();
  String empAllocated = issueObject.get("empAllocated").getAsString();
  String systemID = issueObject.get("systemID").getAsString();
  String issueDesc = issueObject.get("issueDesc").getAsString();
  String output = issueObj.updateIssue(issueID, issueCode, empAllocated, systemID, issueDesc);
 return output;
 }
 
 
 @DELETE
 @Path("/")
 @Consumes(MediaType.APPLICATION_XML)
 @Produces(MediaType.TEXT_PLAIN)
 public String deleteIssue(String issueData)
 {
 //Convert the input string to an XML document
  Document doc = Jsoup.parse(issueData, "", Parser.xmlParser());

 //Read the value from the element <issueID>
  String issueID = doc.select("issueID").text();
  String output = issueObj.deleteIssue(issueID);
 return output;
 }

 
 
 
}


