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
public class Html2Xml {

    public static void main(String[] args) {

        int id = 400488874;

        //total games 1230
        for (int i = 0; i < 1230; i++) {
            String fileName = "filteredHTML/" + (id + i) + ".html";

            try {
                FileReader fr = new FileReader(fileName);
                BufferedReader br = new BufferedReader(fr);
                String line = "";
                String full = "";
                while (line != null) {
                    line = br.readLine();
                    if (line == null) {
                        break;
                    }
                    full = full + line;
                }
                br.close();

                Pattern pat1 = Pattern.compile("<div class=\"game-time-location\">.+201[3-4]</p>");
                Matcher mat1 = pat1.matcher(full);
                String xmlDate = "";
                if (mat1.find()) {
                    String htmlDate = mat1.group();
                    //System.out.println(htmlDate);
                    xmlDate = "<date>" + htmlDate.substring(35, htmlDate.length() - 4) + "</date>";
                }
                //System.out.println(xmlDate);
                String xmlTeams = "";
                int end = 0;
                int[] pts = new int[2];
                for (int k = 0; k < 2; k++) {


                    int start = full.indexOf("<tr class=\"team-color-strip\">", end);

                    String away = "";
                    if (start != -1) {
                        end = full.indexOf("</tr>", start);
                        String htmlAway = full.substring(start, end + 5);
                        int st2 = htmlAway.indexOf("</a>") + 4;
                        int end2 = htmlAway.indexOf("</th>");
                        away = htmlAway.substring(st2, end2);
                        //System.out.println(away);
                    }

                    int point = start;
                    int bound = full.indexOf("TOTALS", start);
                    String xmlAway = "<team>\n<name>" + away + "</name>";
                    point = full.indexOf("player", point);
                    point = full.indexOf("<a href", point);
                    point = full.indexOf(">", point) + 1;
                    int bench = full.indexOf("BENCH", start);
                    int count = 0;
                    while (point < bound && point != -1) {
                        count++;

                        int endSeg = full.indexOf("</a>", point);
                        String seg = full.substring(point, endSeg);
                        String xmlPlayer = "\n<player>\n<name>" + seg + "</name>";
                        //System.out.println("player " + count + ": " + seg);
                        point = full.indexOf(",", endSeg);
                        endSeg = full.indexOf("</td>", point);
                        seg = full.substring(point + 2, endSeg);
                        xmlPlayer += "\n<pos>" + seg + "</pos>";
                        String stats = "";
                        if (point < bench) {
                            stats += "\n<start>true</start>";
                        } else {
                            stats += "\n<start>false</start>";
                        }
                        for (int j = 0; j < 15; j++) {
                            if (j < 14) {
                                point = full.indexOf("<td", endSeg);
                                point = full.indexOf(">", point);
                                endSeg = full.indexOf("</td>", point);
                                seg = full.substring(point, endSeg);
                            }
                            if (seg.contains("DNP")) {
                                stats += "\n<min>0</min>\n<fgm>0</fgm>\n<fga>0</fga>"
                                        + "\n<tpm>0</tpm>\n<tpa>0</tpa>\n<ftm>0</ftm>\n<fta>0</fta>"
                                        + "\n<oreb>0</oreb>\n<dreb>0</dreb>\n<reb>0</reb>\n<ast>0</ast>"
                                        + "\n<stl>0</stl>\n<blk>0</blk>\n<to>0</to>\n<pf>0</pf>"
                                        + "\n<pam>0</pam>\n<pts>0</pts>\n<dnp>true</dnp>";
                                break;
                            } else {
                                switch (j) {
                                    case 0:
                                        stats += "\n<min>" + full.substring(point + 1, endSeg) + "</min>";
                                        break;
                                    case 1:
                                        endSeg = full.indexOf("-", point);
                                        stats += "\n<fgm>" + full.substring(point + 1, endSeg) + "</fgm>";
                                        point = endSeg;
                                        endSeg = full.indexOf("</td>", point);
                                        stats += "\n<fga>" + full.substring(point + 1, endSeg) + "</fga>";
                                        break;
                                    case 2:
                                        endSeg = full.indexOf("-", point);
                                        stats += "\n<tpm>" + full.substring(point + 1, endSeg) + "</tpm>";
                                        point = endSeg;
                                        endSeg = full.indexOf("</td>", point);
                                        stats += "\n<tpa>" + full.substring(point + 1, endSeg) + "</tpa>";
                                        break;
                                    case 3:
                                        endSeg = full.indexOf("-", point);
                                        stats += "\n<ftm>" + full.substring(point + 1, endSeg) + "</ftm>";
                                        point = endSeg;
                                        endSeg = full.indexOf("</td>", point);
                                        stats += "\n<fta>" + full.substring(point + 1, endSeg) + "</fta>";
                                        break;
                                    case 4:
                                        stats += "\n<oreb>" + full.substring(point + 1, endSeg) + "</oreb>";
                                        break;
                                    case 5:
                                        stats += "\n<dreb>" + full.substring(point + 1, endSeg) + "</dreb>";
                                        break;
                                    case 6:
                                        stats += "\n<reb>" + full.substring(point + 1, endSeg) + "</reb>";
                                        break;
                                    case 7:
                                        stats += "\n<ast>" + full.substring(point + 1, endSeg) + "</ast>";
                                        break;
                                    case 8:
                                        stats += "\n<stl>" + full.substring(point + 1, endSeg) + "</stl>";
                                        break;
                                    case 9:
                                        stats += "\n<blk>" + full.substring(point + 1, endSeg) + "</blk>";
                                        break;
                                    case 10:
                                        stats += "\n<to>" + full.substring(point + 1, endSeg) + "</to>";
                                        break;
                                    case 11:
                                        stats += "\n<pf>" + full.substring(point + 1, endSeg) + "</pf>";
                                        break;
                                    case 12:
                                        stats += "\n<pam>" + full.substring(point + 1, endSeg) + "</pam>";
                                        break;
                                    case 13:
                                        stats += "\n<pts>" + full.substring(point + 1, endSeg) + "</pts>";
                                        break;
                                    case 14:
                                        stats += "\n<dnp>false</dnp>";
                                        break;
                                    default:
                                        break;
                                }
                            }

                        }

                        point = full.indexOf("player", endSeg);
                        if (point == -1 || point >= bound) {
                            break;
                        }
                        point = full.indexOf("<a href", point);
                        point = full.indexOf(">", point) + 1;
                        xmlAway += xmlPlayer + stats + "\n</player>";
                    }

                    point = full.indexOf("<strong>", bound);
                    bound = full.indexOf("</tr>", point);
                    count = 0;
                    String stats = "\n<home>" + (k == 1) + "</home>\n<win>false</win>";
                    while (point < bound && point != -1) {
                        int endSeg = full.indexOf("</strong>", point);
                        switch (count) {
                            case 0:
                                endSeg = full.indexOf("-", point);
                                stats += "\n<fgm>" + full.substring(point + 8, endSeg) + "</fgm>";
                                point = endSeg;
                                endSeg = full.indexOf("</strong>", point);
                                stats += "\n<fga>" + full.substring(point + 1, endSeg) + "</fga>";
                                break;
                            case 1:
                                endSeg = full.indexOf("-", point);
                                stats += "\n<tpm>" + full.substring(point + 8, endSeg) + "</tpm>";
                                point = endSeg;
                                endSeg = full.indexOf("</strong>", point);
                                stats += "\n<tpa>" + full.substring(point + 1, endSeg) + "</tpa>";
                                break;
                            case 2:
                                endSeg = full.indexOf("-", point);
                                stats += "\n<ftm>" + full.substring(point + 8, endSeg) + "</ftm>";
                                point = endSeg;
                                endSeg = full.indexOf("</strong>", point);
                                stats += "\n<fta>" + full.substring(point + 1, endSeg) + "</fta>";
                                break;
                            case 3:
                                stats += "\n<oreb>" + full.substring(point + 8, endSeg) + "</oreb>";
                                break;
                            case 4:
                                stats += "\n<dreb>" + full.substring(point + 8, endSeg) + "</dreb>";
                                break;
                            case 5:
                                stats += "\n<reb>" + full.substring(point + 8, endSeg) + "</reb>";
                                break;
                            case 6:
                                stats += "\n<ast>" + full.substring(point + 8, endSeg) + "</ast>";
                                break;
                            case 7:
                                stats += "\n<stl>" + full.substring(point + 8, endSeg) + "</stl>";
                                break;
                            case 8:
                                stats += "\n<blk>" + full.substring(point + 8, endSeg) + "</blk>";
                                break;
                            case 9:
                                stats += "\n<to>" + full.substring(point + 8, endSeg) + "</to>";
                                break;
                            case 10:
                                stats += "\n<pf>" + full.substring(point + 8, endSeg) + "</pf>";
                                break;
                            case 11:
                                stats += "\n<pts>" + full.substring(point + 8, endSeg) + "</pts>";
                                pts[k] = Integer.parseInt(full.substring(point + 8, endSeg));
                                break;
                            default:
                                break;
                        }
                        count++;
                        point = full.indexOf("<strong>",endSeg);
                    }

                    xmlAway += stats+"\n</team>";
                    xmlTeams += "\n" + xmlAway;
                }

                if(pts[0]>pts[1]){
                    xmlTeams = xmlTeams.replace("<home>false</home>\n<win>false</win>", "<home>false</home>\n<win>true</win>");
                }else{
                    xmlTeams = xmlTeams.replace("<home>true</home>\n<win>false</win>", "<home>true</home>\n<win>true</win>");
                }

                if (!xmlDate.isEmpty()) {
                    String newName = "xml/" + (id + i) + ".xml";
                    File file = new File(newName);
                    if (!file.exists()) {
                        file.createNewFile();
                    }

                    FileWriter fw = new FileWriter(file.getAbsoluteFile());
                    BufferedWriter bw = new BufferedWriter(fw);
                    String toFile = "<game>\n<id>" + (id+i) + "</id>\n" + xmlDate + xmlTeams + "\n</game>";
                    bw.write(toFile);
                    bw.close();

                    System.out.println((id + i));

                } else {
                    System.out.println("not found in " + fileName);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }



    }
}
