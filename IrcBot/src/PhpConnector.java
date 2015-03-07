import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;


/**
 * 
 *@author Alexander
 *@deprecated
 */
public class PhpConnector
{
	/**
	 * 
	 * Sends text to php script.
	 */
    public static void doConnection(String text){
       URL targetURL = null;
	try {
		targetURL = new URL("http://ircsmibot.byethost18.com/writeLogToText.php");
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       HttpURLConnection conn = null;
	try {
		conn = (HttpURLConnection)targetURL.openConnection();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       //data send via POST request
       String data = "test="+text;
       conn.setDoOutput(true);
       conn.setInstanceFollowRedirects(true);
       try {
		conn.setRequestMethod("POST");
	} catch (ProtocolException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       //conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
       ((HttpURLConnection) conn).setFixedLengthStreamingMode(data.length());
       conn.setRequestProperty("charset", "UTF-8");
       conn.setRequestProperty("Content-Length", Integer.toString(
        data.length()));

       conn.setUseCaches(false);
       for(int i=0;i<5;i++){
    	   
       try(OutputStreamWriter out = new OutputStreamWriter(
                                conn.getOutputStream()))
       {
            out.write(data);
            break;
       } catch (IOException e) {
		//e.printStackTrace();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
       } 
       }

       //reading code after POST request (here I want to have value from "test" field
     /*  try(BufferedReader in = new BufferedReader(new InputStreamReader(        
        conn.getInputStream())))
       {
           String currentLine;
           while((currentLine = in.readLine()) != null)
           {
               System.out.println(currentLine);
           }
       }
       ( */


 }
 }