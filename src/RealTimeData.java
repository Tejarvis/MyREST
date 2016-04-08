import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
public class RealTimeData {
    long delay = 2*1000; // delay in milliseconds
    LoopTask task = new LoopTask();
    Timer timer = new Timer("TaskName");
    
    	public void start() {
    							timer.cancel();
							    timer = new Timer("TaskName");
							    Date executionDate = new Date(); // no params = now
							    timer.scheduleAtFixedRate(task, executionDate, delay);
    						}

    private class LoopTask extends TimerTask  {
    HttpResponse response;
    String line=""; 
    FileWriter fw;
    BufferedReader rd;
    public void run() {
        //System.out.println("This message will print every 2 seconds.");

	HttpClient client = new DefaultHttpClient();
    HttpGet request = new HttpGet(" http://www.odaa.dk/api/action/datastore_search?resource_id=b3eeb0ff-c8a8-4824-99d6-e0a3747c8b0d");
    request.addHeader("accept", "application/json");
		try {
			response = client.execute(request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   try {
			rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
		} catch (UnsupportedOperationException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   //File file = new File("F:/File.txt");
		   File file = new File("/home/tony/RealTimeData");
		   if (!file.exists()) {
				   try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		   
		   
		   
		try {
			fw = new FileWriter(file.getAbsoluteFile(),true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   BufferedWriter bw = new BufferedWriter(fw);
		   try {
	  	   	   while ((line = rd.readLine()) != null) {
	  		   bw.write(line);	
	  		   
	  	   }
	  	   	bw.close();
	  		System.out.println("Done");
		  	  }
			   catch (IOException e) {
				   e.printStackTrace();
			   }
    	
    }
    }

    public static void main(String[] args) {
    RealTimeData executingTask = new RealTimeData();
    executingTask.start();
    }


}

