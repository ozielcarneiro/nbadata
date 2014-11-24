/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataAcquisition;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Data Acquisition Class
 * Code modified from www.mykong.com/java/how-to-get-url-content-in-java/ to
 * acquire nba data from ESPN boxscore pages
 * @author ozielcarneiro
 */
public class Acquisiton {
    
    public static void acquireData() {
        URL url;
        
        //2013-2014 NBA Season Box Scores in ESPN
        //starting URL http://espn.go.com/nba/boxscore?gameId=400488874
        //ending URL http://scores.espn.go.com/nba/recap?gameId=400490103
        //total of 1230 games per season
        //extra games due to postponed games not in the same id rule
        //gameId=400528655 in place of gameId=400489148
        //gameId=400532894 in place of gameId=400489550
        int id = 400488874;
        
        try{
            for (int i = 0; i < 1230; i++) {
                int current_id = id+i;
                url = new URL("http://espn.go.com/nba/boxscore?gameId="+current_id);
                URLConnection conn = url.openConnection();
                
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                
                String inputLine;
                String fileName = "files/"+current_id+".html";
                File file = new File(fileName);
                
                if(!file.exists()){
                    file.createNewFile();
                }
                
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                
                while((inputLine = br.readLine()) != null){
                    bw.write(inputLine);
                }
                
                bw.close();
                br.close();
                
                System.out.println(current_id);
            }
            System.out.println("Done!");
        }
        catch(MalformedURLException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
    
    
    public static void main(String[] args) {
        acquireData();
    }
    
}
