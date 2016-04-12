package edu.ap.jaxrs;

import java.io.*;
import javax.ws.rs.*;
import javax.json.*;

@Path("/quotes")
public class Quotes {
	
	static final String FILE = "/Users/Matthijs/Desktop/Quotes.json";
	// get all quotes	
	@GET
	@Produces({"text/html"})
	public String getProductsHTML() {
		String quote = "<html><body>";
		try {
			JsonReader r = Json.createReader(new StringReader(getQuotesJSONJSON()));
			JsonObject rootObj = r.readObject();
			JsonArray array = rootObj.getJsonArray("quotes");
			for(int i = 0 ; i < array.size(); i++) {
				JsonObject obj = array.getJsonObject(i);
				quote += "<b>qoute : " + obj.getString("quote") +"<b>, geschreven door " + obj.getString("author")+ "</b><br>";
			}
		}
		catch(Exception ex) {
			quote = "<html><body>" + ex.getMessage();
		}
		
		return quote + "</body></html>";
	}
	
	@GET
	@Consumes({"application/json"})
	@Produces({"application/json"})
	public String getQuotesJSON() {
		String jsonString = "";
		try {
			InputStream input = new FileInputStream(FILE);
	        JsonReader r = Json.createReader(input);
	        JsonObject obj = r.readObject();
	       
	        
	        jsonString = obj.toString();
		} 
		catch (Exception ex) {
			jsonString = ex.getMessage();
		}
		
		return jsonString;
	}

	
	@POST
	@Consumes({"application/json"})
	public String addQuote(String quoteJSON) {
		String result = "";
		try {
			// read existing products
			InputStream	input = new FileInputStream(FILE);
	        JsonReader reader1 = Json.createReader();
	        JsonObject jsonObject = reader1.readObject();
	        
	        JsonReader reader2 = Json.createReader(new StringReader(quoteJSON));
	        JsonObject newObject = reader2.readObject();
	        	        
	        JsonArray array = jsonObject.getJsonArray("quotes");
	        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
	        
	        for(int i = 0; i < array.size(); i++){
	     
	       JsonObject obj = array.getJsonObject(i);
	       arrBuilder.add(obj);
	        }
	        
	        arrBuilder.add(newObject);
	        
	        
	        JsonArray newArray = arrBuilder.build();
	        JsonObjectBuilder builder = Json.createObjectBuilder();
	        builder.add("quotes", newArray);
	        JsonObject newJSON = builder.build();

	        // write to file
	        OutputStream os = new FileOutputStream(FILE);
	        JsonWriter writer = Json.createWriter(os);
	        writer.writeObject(newJSON);
	        writer.close();
		} 
	
		
		return result;
	}
}