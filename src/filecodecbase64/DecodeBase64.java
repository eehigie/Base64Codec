/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filecodecbase64;
import com.siebel.data.SiebelPropertySet;
import com.siebel.eai.SiebelBusinessServiceException;
//import java.io.BufferedWriter;
//import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
//import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
//import java.io.PrintWriter;
//import java.io.StringWriter;
import java.util.Properties;
import org.apache.logging.log4j.Logger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
//import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
/**
 *
 * @author gbege
 */
public class DecodeBase64 extends com.siebel.eai.SiebelBusinessService{
    
    private static final Logger LOG = LogManager.getLogger("DecodeBase64");
    private static InetAddress ip = null;
    private static String hIP = "";
    private static final String OS = System.getProperty("os.name").toLowerCase();    
    private static String base64_file = "";
    private static String base64_filename = "";
    private static boolean isDecoded = false;
    private String encoded_filepath = "";
    private String decoded_filepath = "";
    private String dev_ip = "";
    private String uat_ip = "";
    private String prod_ip = "";
    private String local_ip = "";
    private static String prop_file_path = null;
    private final StringWriter errors = new StringWriter();
    
    @Override
    public void doInvokeMethod(String MethodName, SiebelPropertySet input, SiebelPropertySet output)
            throws SiebelBusinessServiceException{
        
        if(MethodName.equalsIgnoreCase("DecodeFile")){
            MyLogging.log(Level.INFO, "=========IN DecodeFile===============");
            try{
            ip = InetAddress.getLocalHost();
            hIP = ip.getHostAddress();   
            MyLogging.log(Level.INFO,"IP is:"+hIP);
            //get filepath properties
            MyLogging.log(Level.INFO,"loading properties");
            Properties prop = new Properties();        
            InputStream input_prop = null;
                        
            uat_ip = prop.getProperty("uat_ip");            
            local_ip = prop.getProperty("local_ip");
            if(OS.contains("nix")|| OS.contains("nux")){
                if(hIP.equalsIgnoreCase(prod_ip)){                                        
                    prop_file_path = "/home/siebprod/hdk/service.properties";
                    input_prop = new FileInputStream(prop_file_path);
                    prop.load(input_prop);
                    prod_ip = prop.getProperty("prod_ip");
                    encoded_filepath = prop.getProperty("prod_nix_encoded_filepath");
                    decoded_filepath = prop.getProperty("prod_nix_decoded_filepath");
                }else if(hIP.equalsIgnoreCase(uat_ip)){
                    prop_file_path = "/home/siebtest/hdk/service.properties";
                    input_prop = new FileInputStream(prop_file_path);
                    prop.load(input_prop);
                    prod_ip = prop.getProperty("uat_ip");                    
                    encoded_filepath = prop.getProperty("uat_nix_encoded_filepath");
                    decoded_filepath = prop.getProperty("uat_nix_decoded_filepath");
                }else if(hIP.equalsIgnoreCase(dev_ip)){ 
                    prop_file_path = "/home/siebdev/hdk/service.properties";
                    input_prop = new FileInputStream(prop_file_path);
                    prop.load(input_prop);
                    dev_ip = prop.getProperty("dev_ip");                    
                    encoded_filepath = prop.getProperty("dev_nix_encoded_filepath");
                    decoded_filepath = prop.getProperty("dev_nix_decoded_filepath");
                }else if(hIP.equalsIgnoreCase(local_ip)){ 
                    prop_file_path = "/home/gbege/lib/hdk/service.properties";
                    input_prop = new FileInputStream(prop_file_path);
                    prop.load(input_prop);
                    dev_ip = prop.getProperty("local_ip");                    
                    encoded_filepath = prop.getProperty("local_nix_encoded_filepath");
                    decoded_filepath = prop.getProperty("local_nix_decoded_filepath");
                }
            }else if(OS.contains("win")){
                prop_file_path = "D:\\hdk\\service.properties";
                input_prop = new FileInputStream(prop_file_path);
                prop.load(input_prop);
                encoded_filepath = prop.getProperty("win_encoded_filepath");
                decoded_filepath = prop.getProperty("win_decoded_filepath"); 
            }
            String base64Txt = "";
            //get filename from input object
            MyLogging.log(Level.INFO,"get file properties");
            base64_filename = input.getProperty("filename");
            base64_file = input.getProperty("file");
            String fileType = input.getProperty("filetype");
            MyLogging.log(Level.INFO,"fileType is "+fileType);
            MyLogging.log(Level.INFO,"encoded file is "+base64_file);
            MyLogging.log(Level.INFO,"decoded file is "+decoded_filepath+base64_filename);
            FileCodecBase64 decoder = new FileCodecBase64();
            PLXDomParser pd = new PLXDomParser();
            MyLogging.log(Level.INFO,"Calling Parsing Method to Parse XML File .......");
            
            if(fileType.equalsIgnoreCase("GenerateStatement")){
                pd.parseGenerateStatementResponse(base64_file);
                MyLogging.log(Level.INFO,"Parse XML File DONE.......");            
                base64Txt = pd.getGenerateStatementBase64Text();
            }else if(fileType.equalsIgnoreCase("MemberStatement")){                
                pd.parseMemberStatementResponse(base64_file);
                MyLogging.log(Level.INFO,"Parse XML File DONE.......");            
                base64Txt = pd.getMemberStatementBase64Text();
            }
                        
            MyLogging.log(Level.INFO,"Base 64 text is "+base64Txt);
            MyLogging.log(Level.INFO,"Calling Method to decode base 64 string and write to PDF File .......");                              
            isDecoded = decoder.decodeString(base64Txt, decoded_filepath+base64_filename);
            MyLogging.log(Level.INFO,"Decode base 64 string and write to PDF File DONE.......");
            
            if(isDecoded){
                output.setProperty("Decoded_File", decoded_filepath+base64_filename);
                output.setProperty("ErrorMessage", "");
            }                        
         }catch(UnknownHostException ex){             
             ex.printStackTrace(new PrintWriter(errors));
             MyLogging.log(Level.SEVERE, "UNKNOWN_HOST ERROR: " + errors.toString());
             output.setProperty("ErrorMessage", errors.toString());
             throw new SiebelBusinessServiceException("UNKNOWN_HOST","Host not found");
         }catch(FileNotFoundException ex){
             ex.printStackTrace(new PrintWriter(errors));
             MyLogging.log(Level.SEVERE, "FILE_NOT_FOUND ERROR: " + errors.toString());
             output.setProperty("ErrorMessage", errors.toString());
             throw new SiebelBusinessServiceException("FILE_NOT_FOUND","File not found");
         }catch(IOException ex){
             ex.printStackTrace(new PrintWriter(errors));
             MyLogging.log(Level.SEVERE, "IO_EXCEPTION ERROR: " + errors.toString());
             output.setProperty("ErrorMessage", errors.toString());
             throw new SiebelBusinessServiceException("IO_EXCEPTION","error");
         }catch(Exception ex){
             ex.printStackTrace(new PrintWriter(errors));
             MyLogging.log(Level.SEVERE, "ERROR: " + errors.toString());
             output.setProperty("ErrorMessage", errors.toString());
             throw new SiebelBusinessServiceException("ERROR","error");
         }                    
        
                                
        }
        
        if(MethodName.equalsIgnoreCase("GetNSSFId")){
            MyLogging.log(Level.INFO, "=========IN GetNSSFId===============");
            String file_name = input.getProperty("FileName");
            MyLogging.log(Level.INFO,"File name is : "+file_name);
            PLXDomParser pd = new PLXDomParser();
            MyLogging.log(Level.INFO, "Calling parse method ===============");
            pd.parseMemberRegistrationResponse(file_name);
            MyLogging.log(Level.INFO, "Parse method completed===============");
            MyLogging.log(Level.INFO, "Getting values ===============");
            String resp_status = pd.getMemberRegistrationStatus();
            if(resp_status == null){
                resp_status = "";
            }
            MyLogging.log(Level.INFO, "response status is : "+resp_status);
            String nssf_id = pd.getMemberRegistrationInsureId();
            if(nssf_id == null){
                nssf_id = "";
            }
            MyLogging.log(Level.INFO, "nssf id is : "+nssf_id);
            String response_errors = pd.getMemberRegistrationErrors();
            if(response_errors == null){
                response_errors = "";
            }
            MyLogging.log(Level.INFO, "response errors is : "+response_errors);
            output.setProperty("response_status",resp_status );
            output.setProperty("nssf_id",nssf_id );
            output.setProperty("response_errors",response_errors );
        }
             
    }
}
