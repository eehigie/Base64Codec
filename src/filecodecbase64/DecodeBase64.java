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
//import java.io.PrintWriter;
//import java.io.StringWriter;
import java.util.Properties;
import org.apache.logging.log4j.Logger;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
    
    @Override
    public void doInvokeMethod(String MethodName, SiebelPropertySet input, SiebelPropertySet output)
            throws SiebelBusinessServiceException{
         
         try{
            ip = InetAddress.getLocalHost();
            hIP = ip.getHostAddress();   
            LOG.info("IP is:"+hIP);
            //get filepath properties
            LOG.info("loading properties");
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
            
            //get filename from input object
            LOG.info("get file properties");
            base64_filename = input.getProperty("filename");
            base64_file = input.getProperty("file");
            LOG.info("encoded file is "+encoded_filepath+base64_file);
            LOG.info("decoded file is "+decoded_filepath+base64_file);
            FileCodecBase64 decoder = new FileCodecBase64();
            isDecoded = decoder.decode(base64_filename, decoded_filepath+base64_file);
            
            if(isDecoded){
                output.setProperty("Decoded_File", decoded_filepath+base64_file);
                output.setProperty("ErrorMessage", "");
            }
            
            
         }catch(UnknownHostException uhe){
             LOG.error("Error in doInvoke", uhe);
             output.setProperty("ErrorMessage", uhe.getMessage());
             throw new SiebelBusinessServiceException("UNKNOWN_HOST","Host not found");
         }catch(FileNotFoundException fnfe){
             LOG.error("File not found", fnfe);
             output.setProperty("ErrorMessage", fnfe.getMessage());
             throw new SiebelBusinessServiceException("FILE_NOT_FOUND","File not found");
         }catch(IOException ioe){
             LOG.error("Error", ioe);
             output.setProperty("ErrorMessage", ioe.getMessage());
             throw new SiebelBusinessServiceException("ERROR","error");
         }catch(Exception e){
             LOG.error("Error", e);
             output.setProperty("ErrorMessage", e.getMessage());
             throw new SiebelBusinessServiceException("ERROR","error");
         }                    
        
         
         
         
     }     
    
}
