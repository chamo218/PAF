package com;
import model.Payment;
//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;
@Path("/Items")
public class PaymentService
{
 Payment paymentObj = new Payment();
 @GET
 @Path("/")
 @Produces(MediaType.TEXT_HTML)
 public String readPayments()
  {
  return paymentObj.readPayments();
 }
 
 
 @POST
 @Path("/")
 @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
 @Produces(MediaType.TEXT_PLAIN)
 public String insertItem(@FormParam("billNo") String billNo,
  @FormParam("cardNo") String cardNo,
  @FormParam("securityNo") String securityNo,
  @FormParam("expiryDate") String expiryDate)
 {
  String output = paymentObj.insertPayment(billNo, cardNo, securityNo, expiryDate);
 return output;
 }

 
 
 
 
 
 
 
 
 
}


