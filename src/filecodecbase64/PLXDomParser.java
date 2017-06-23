/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filecodecbase64;

/**
 *
 * @author gbege
 */
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class PLXDomParser {
    
    private String insuredId;
    private String response_status;
    private String response_errors;
    private String generate_statement_message;
    private String generate_statement_base64_txt;
    private String generate_statement_response_code;
    private String generate_statement_status;
    private String member_statement_statementPdfData;
    private String member_statement_serviceRequestId;
    private final StringWriter errors = new StringWriter();
    
    public void parseMemberRegistrationResponse(String fileName){
        try {	
         //File inputFile = new File("C:\\Temp\\docs\\input2.txt");
         File inputFile = new File(fileName);
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	 Document doc = dBuilder.parse(inputFile);
         doc.getDocumentElement().normalize();
	 MyLogging.log(Level.INFO,"Root element :" + doc.getDocumentElement().getNodeName());
         NodeList nList = doc.getElementsByTagName("fieldType");
	 MyLogging.log(Level.INFO,"----------------------------");
         for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            MyLogging.log(Level.INFO,"Current Element :" + nNode.getNodeName());
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		Element eElement = (Element) nNode;
                String element_name  = eElement.getElementsByTagName("name").item(0).getTextContent();                
                MyLogging.log(Level.INFO,"name : " + eElement.getElementsByTagName("name").item(0).getTextContent());		
		MyLogging.log(Level.INFO,"value : " + eElement.getElementsByTagName("value").item(0).getTextContent());
                if(element_name.equalsIgnoreCase("insuredId")){
                    insuredId  = eElement.getElementsByTagName("value").item(0).getTextContent();
                }else if(element_name.equalsIgnoreCase("status")){
                    response_status = eElement.getElementsByTagName("value").item(0).getTextContent();    
                }else if(element_name.equalsIgnoreCase("errors")){
                    response_errors = eElement.getElementsByTagName("value").item(0).getTextContent();
                }
            }
        }
      } catch(IOException ex){
            ex.printStackTrace(new PrintWriter(errors));
            MyLogging.log(Level.SEVERE, "IO_EXCEPTION ERROR: " + errors.toString());
      }catch(ParserConfigurationException ex){
            ex.printStackTrace(new PrintWriter(errors));
            MyLogging.log(Level.SEVERE, "PARSER_CONFIGURATION_EXCEPTION ERROR: " + errors.toString());
      }catch (SAXException ex) {
            ex.printStackTrace(new PrintWriter(errors));
            MyLogging.log(Level.SEVERE, "SAX_EXCEPTION ERROR: " + errors.toString());
      } 
    }
    
    public void parseGenerateStatementResponse(String fileName){
        try {	
         //File inputFile = new File("C:\\Temp\\docs\\input2.txt");
         File inputFile = new File(fileName);
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	 Document doc = dBuilder.parse(inputFile);
         doc.getDocumentElement().normalize();
	 MyLogging.log(Level.INFO,"Root element :" + doc.getDocumentElement().getNodeName());
         NodeList nList = doc.getElementsByTagName("fieldType");
	 MyLogging.log(Level.INFO,"----------------------------");
         for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            MyLogging.log(Level.INFO,"Current Element :" + nNode.getNodeName());
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		Element eElement = (Element) nNode;
                String element_name  = eElement.getElementsByTagName("name").item(0).getTextContent();                
                MyLogging.log(Level.INFO,"name : " + eElement.getElementsByTagName("name").item(0).getTextContent());		
		MyLogging.log(Level.INFO,"value : " + eElement.getElementsByTagName("value").item(0).getTextContent());
                if(element_name.equalsIgnoreCase("message")){
                    generate_statement_message  = eElement.getElementsByTagName("value").item(0).getTextContent();
                }else if(element_name.equalsIgnoreCase("status")){
                    generate_statement_status = eElement.getElementsByTagName("value").item(0).getTextContent();    
                }else if(element_name.equalsIgnoreCase("responseCode")){
                    generate_statement_response_code = eElement.getElementsByTagName("value").item(0).getTextContent();
                }else if(element_name.equalsIgnoreCase("pdfRet")){
                    generate_statement_base64_txt = eElement.getElementsByTagName("value").item(0).getTextContent();
                }
            }
        }
      } catch(IOException ex){
            ex.printStackTrace(new PrintWriter(errors));
            MyLogging.log(Level.SEVERE, "IO_EXCEPTION ERROR: " + errors.toString());
      }catch(ParserConfigurationException ex){
            ex.printStackTrace(new PrintWriter(errors));
            MyLogging.log(Level.SEVERE, "PARSER_CONFIGURATION_EXCEPTION ERROR: " + errors.toString());
      }catch (SAXException ex) {
            ex.printStackTrace(new PrintWriter(errors));
            MyLogging.log(Level.SEVERE, "SAX_EXCEPTION ERROR: " + errors.toString());
      } 
    }
    
    public void parseMemberStatementResponse(String fileName){
        try {	
         //File inputFile = new File("C:\\Temp\\docs\\input2.txt");
         File inputFile = new File(fileName);
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	 Document doc = dBuilder.parse(inputFile);
         doc.getDocumentElement().normalize();
	 MyLogging.log(Level.INFO,"Root element :" + doc.getDocumentElement().getNodeName());
         NodeList nList = doc.getElementsByTagName("fieldType");
	 MyLogging.log(Level.INFO,"----------------------------");
         for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            MyLogging.log(Level.INFO,"Current Element :" + nNode.getNodeName());
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		Element eElement = (Element) nNode;
                String element_name  = eElement.getElementsByTagName("name").item(0).getTextContent();                
                MyLogging.log(Level.INFO,"name : " + eElement.getElementsByTagName("name").item(0).getTextContent());		
		MyLogging.log(Level.INFO,"value : " + eElement.getElementsByTagName("value").item(0).getTextContent());
                if(element_name.equalsIgnoreCase("statementPdfData")){
                    member_statement_statementPdfData  = eElement.getElementsByTagName("value").item(0).getTextContent();
                }else if(element_name.equalsIgnoreCase("serviceRequestId")){
                    member_statement_serviceRequestId = eElement.getElementsByTagName("value").item(0).getTextContent();    
                }
            }
        }
      } catch(IOException ex){
            ex.printStackTrace(new PrintWriter(errors));
            MyLogging.log(Level.SEVERE, "IO_EXCEPTION ERROR: " + errors.toString());
      }catch(ParserConfigurationException ex){
            ex.printStackTrace(new PrintWriter(errors));
            MyLogging.log(Level.SEVERE, "PARSER_CONFIGURATION_EXCEPTION ERROR: " + errors.toString());
      }catch (SAXException ex) {
            ex.printStackTrace(new PrintWriter(errors));
            MyLogging.log(Level.SEVERE, "SAX_EXCEPTION ERROR: " + errors.toString());
      } 
    }
    
    public String getGenerateStatementMessage(){
        return generate_statement_message;
    }
    
    public String getGenerateStatementStatus(){
        return generate_statement_status;
    }
    
    public String getGenerateStatementResponseCode(){
        return generate_statement_response_code;
    }
    
    public String getGenerateStatementBase64Text(){
        return generate_statement_base64_txt;
    }
    
        
    public String getMemberRegistrationInsureId(){        
        return insuredId;
    }
    
    public String getMemberRegistrationStatus(){
        return response_status;
    }
    
    public String getMemberRegistrationErrors(){
        return response_errors;
    }
    
    public String getMemberStatementBase64Text(){
        return member_statement_statementPdfData;
    }
    
    /*public static void main(String[] args) {
        PLXDomParser pd = new PLXDomParser();
        //pd.parseMemberRegistrationResponse("C:\\Temp\\docs\\input2.txt");
        pd.parseGenerateStatementResponse("C:\\hdk\\files\\encoded\\NSSF_Statement_6222017_204045.txt");
        MyLogging.log(Level.INFO, "message is: "+pd.getGenerateStatementMessage());
        MyLogging.log(Level.INFO, "status is: "+pd.getGenerateStatementStatus());
        MyLogging.log(Level.INFO, "response code is: "+pd.getGenerateStatementResponseCode());
        MyLogging.log(Level.INFO, "base 64 text is: "+pd.getGenerateStatementBase64Text());
        /*MyLogging.log(Level.INFO, "InsureId is: "+pd.getInsureId());
        MyLogging.log(Level.INFO, "status is: "+pd.getStatus());
        MyLogging.log(Level.INFO, "error is: "+pd.getErrors());
    }*/
}
