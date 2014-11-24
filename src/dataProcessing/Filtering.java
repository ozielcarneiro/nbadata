/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataProcessing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author ozielcarneiro
 */
public class Filtering {
    public static void filtering(){
        //2013-2014 NBA Season Box Scores in ESPN
        //starting file files/400488874.html
        //ending file files/400490103.html
        //due to postponed the following games didn't happen in their correct
        //time positions gameId=400489148 and gameId=400489550
        //total of 1230 files
        int id = 400488874;
        
        for (int i = 0; i < 1230; i++) {
            String fileName = "files/"+(id+i)+".html";
            
            try{
                FileReader fr = new FileReader(fileName);
                BufferedReader br = new BufferedReader(fr);
                String line = "";
                String full = "";
                while(line!=null){
                    line = br.readLine();
                    if(line==null){
                        break;
                    }
                    full = full+line;
                }
                br.close();
                
                Pattern pat1 = Pattern.compile("<div class=\"game-time-location\">.+201[3-4]</p>");
                Matcher mat1 = pat1.matcher(full);
                Pattern pat = Pattern.compile("<table border.+</table>");
                Matcher mat = pat.matcher(full);
                
                if(mat1.find()&&mat.find()){
                    String newName = "filteredHTML/"+(id+i)+".html";
                    File file = new File(newName);
                    if(!file.exists()){
                        file.createNewFile();
                    }
                    
                    FileWriter fw = new FileWriter(file.getAbsoluteFile());
                    BufferedWriter bw = new BufferedWriter(fw);
                    String toFile = mat1.group()+"</div>"+mat.group();
                    bw.write(toFile);
                    bw.close();
                    
                    System.out.println((id+i));
                    
                }else{
                    System.out.println("not found in "+fileName);
                }
                
            }
            catch(IOException e){
                e.printStackTrace();
            }
            
        }
    }
    
    public static void main(String[] args) {
        filtering();
    }
}
