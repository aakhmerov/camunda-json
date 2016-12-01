package be.ecornely;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class StartProcess {
  
  private static final ObjectMapper om = new ObjectMapper();
  
  public static void main(String[] args) throws Exception {
    ObjectNode entity = om.createObjectNode();
    ObjectNode variables = entity.putObject("variables");
    String demo = "[{\"network\":\"192.168.0.0\",\"mask\":\"24\",\"longmask\":\"255.255.255.0\",\"gateway\":\"192.168.0.1\",\"priority\":100},{\"network\":\"192.168.2.0\",\"mask\":\"24\",\"longmask\":\"255.255.255.0\",\"gateway\":\"192.168.2.1\",\"priority\":100}]";
    ObjectNode demoNode = variables.putObject("demo");
    demoNode.put("type", "Object");
    
    //set the value as a String
    demoNode.put("value", demo);
    
    //set the value as a json array
    //demoNode.set("value", om.readTree(demo));
    
    demoNode.putObject("valueInfo").put("objectTypeName", "com.fasterxml.jackson.databind.node.ArrayNode").put("serializationDataFormat", "application/json");
    
    System.out.printf("The request payload will be:%n%s%n%n", om.writerWithDefaultPrettyPrinter().writeValueAsString(entity));
    
    Response resp = ClientBuilder.newClient().target("http://localhost:8080/engine-rest/process-definition/key/LogDemoWorkflow/start").request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(entity));
    System.out.println(resp.getStatusInfo());
    String response = resp.readEntity(String.class);
    System.out.println(response);
    
  }

}
