/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dice;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 *
 * @author Zsolt
 */
public class Utility {

    public static String excutePost(String targetURL, String urlParameters, String apikey)
    {
        URL url;
        HttpURLConnection connection = null;  
        try {
          //Create connection
          url = new URL(targetURL);
          connection = (HttpURLConnection)url.openConnection();
          connection.setRequestMethod("POST");
          connection.setRequestProperty("Content-Type", 
               "application/json");
          connection.setRequestProperty("x-access-token", apikey);

          //connection.setRequestProperty("Content-Length", "" + 
                   //Integer.toString(urlParameters.getBytes().length));
          //connection.setRequestProperty("Content-Language", "en-US");  

          connection.setUseCaches (false);
          connection.setDoInput(true);
          connection.setDoOutput(true);


          //Send request
          DataOutputStream wr = new DataOutputStream (
                      connection.getOutputStream ());
          wr.writeBytes (urlParameters);
          wr.flush ();
          wr.close ();

          //Get Response    
          InputStream is = connection.getInputStream();
          BufferedReader rd = new BufferedReader(new InputStreamReader(is));
          String line;
          StringBuffer response = new StringBuffer(); 
          while((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
          }
          rd.close();
          return response.toString();

        } catch (Exception e) {

          e.printStackTrace();
          return null;

        } finally {

          if(connection != null) {
            connection.disconnect(); 
          }
        }
    }
 }
