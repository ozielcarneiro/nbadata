/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataStoring;

import entity.Boxscore;
import entity.BoxscorePK;
import entity.Game;
import entity.Player;
import entity.Playerboxscore;
import entity.PlayerboxscorePK;
import entity.Team;
import entityControllers.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author ozielcarneiro
 */
public class Xml2DB {
    
    public static void readAndPersist(String filename, EntityManagerFactory emf){
        
        BoxscoreJpaController bsControl = new BoxscoreJpaController(emf);
        GameJpaController gameControl = new GameJpaController(emf);
        PlayerJpaController playControl = new PlayerJpaController(emf);
        PlayerboxscoreJpaController playBSControl = new PlayerboxscoreJpaController(emf);
        TeamJpaController teamControl = new TeamJpaController(emf);
        
        try {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            InputStream in = new FileInputStream(filename);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            Game gam = null;
            Team home = null;
            Team away = null;
            Team auxTeam = null;
            Boxscore boxscore = null;
            Boxscore homeBS = null;
            Boxscore awayBS = null;
            Player play = null;
            Playerboxscore playBS = null;
            ArrayList<Player> listPlay = new ArrayList<Player>();
            ArrayList<Playerboxscore> listPlayBS = new ArrayList<Playerboxscore>();
            int count = 0;
            boolean openGame = false;
            boolean openTeam = false;
            boolean openPlayer = false;
            while(eventReader.hasNext()){
                XMLEvent event = eventReader.nextEvent();
                count++;
                if(event.isStartElement()){
                    StartElement startElem = event.asStartElement();
                    String tag = startElem.getName().getLocalPart();
                    if(tag.equals("id")){
                        event = eventReader.nextEvent();
                        String data = event.asCharacters().getData();
                        gam = gameControl.findGame(Integer.parseInt(data));
                        if(gam==null){
                            gam = new Game(Integer.parseInt(data));
                        }
                        openGame = true;
                    }else if(tag.equals("date")){
                        event = eventReader.nextEvent();
                        String data = event.asCharacters().getData();
                        data = data.replaceFirst(",", "");
                        data = data.substring(data.indexOf("T")+2);
                        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
                        gam.setGameDate(df.parse(data));
                    }else if(tag.equals("team")){
                        openTeam = true;
                    }else if(tag.equals("name")){
                        event = eventReader.nextEvent();
                        String data = event.asCharacters().getData();
                        if(openPlayer){
                            play.setName(data);
                        }else if(openTeam){
                            auxTeam = teamControl.findTeam(data);
                            if(auxTeam == null){
                                auxTeam = new Team();
                                auxTeam.setName(data);
                            }
                            boxscore = bsControl.findBoxscore(new BoxscorePK(gam.getIdgame(), auxTeam.getName()));
                            if(boxscore == null){
                                boxscore = new Boxscore();
                                boxscore.setGame(gam);
                                boxscore.setTeam(auxTeam);
                                boxscore.setBoxscorePK(new BoxscorePK(gam.getIdgame(), auxTeam.getName()));
                            }
                        }
                    }else if(tag.equals("player")){
                        play = new Player();
                        playBS = new Playerboxscore();
                        openPlayer = true;
                        playBS.setPlayer(play);
                        playBS.setGame(gam);
                    }else if(tag.equals("pos")){
                        event = eventReader.nextEvent();
                        String data = event.asCharacters().getData();
                        play.setPosition(data);
                    }else if(tag.equals("start")){
                        event = eventReader.nextEvent();
                        String data = event.asCharacters().getData();
                        playBS.setStarter(Boolean.valueOf(data));
                    }else if(tag.equals("min")){
                        event = eventReader.nextEvent();
                        String data = event.asCharacters().getData();
                        playBS.setMinutes(Integer.parseInt(data));
                    }else if(tag.equals("fgm")){
                        event = eventReader.nextEvent();
                        String data = event.asCharacters().getData();
                        if(openPlayer){
                            playBS.setFgm(Integer.parseInt(data));
                        }else if(openTeam){
                            boxscore.setFgm(Integer.parseInt(data));
                        }
                    }else if(tag.equals("fga")){
                        event = eventReader.nextEvent();
                        String data = event.asCharacters().getData();
                        if(openPlayer){
                            playBS.setFga(Integer.parseInt(data));
                        }else if(openTeam){
                            boxscore.setFga(Integer.parseInt(data));
                        }
                    }else if(tag.equals("tpm")){
                        event = eventReader.nextEvent();
                        String data = event.asCharacters().getData();
                        if(openPlayer){
                            playBS.setTpm(Integer.parseInt(data));
                        }else if(openTeam){
                            boxscore.setTpm(Integer.parseInt(data));
                        }
                    }else if(tag.equals("tpa")){
                        event = eventReader.nextEvent();
                        String data = event.asCharacters().getData();
                        if(openPlayer){
                            playBS.setTpa(Integer.parseInt(data));
                        }else if(openTeam){
                            boxscore.setTpa(Integer.parseInt(data));
                        }
                    }else if(tag.equals("ftm")){
                        event = eventReader.nextEvent();
                        String data = event.asCharacters().getData();
                        if(openPlayer){
                            playBS.setFtm(Integer.parseInt(data));
                        }else if(openTeam){
                            boxscore.setFtm(Integer.parseInt(data));
                        }
                    }else if(tag.equals("fta")){
                        event = eventReader.nextEvent();
                        String data = event.asCharacters().getData();
                        if(openPlayer){
                            playBS.setFta(Integer.parseInt(data));
                        }else if(openTeam){
                            boxscore.setFtm(Integer.parseInt(data));
                        }
                    }else if(tag.equals("oreb")){
                        event = eventReader.nextEvent();
                        String data = event.asCharacters().getData();
                        if(openPlayer){
                            playBS.setOreb(Integer.parseInt(data));
                        }else if(openTeam){
                            boxscore.setOreb(Integer.parseInt(data));
                        }
                    }else if(tag.equals("dreb")){
                        event = eventReader.nextEvent();
                        String data = event.asCharacters().getData();
                        if(openPlayer){
                            playBS.setDreb(Integer.parseInt(data));
                        }else if(openTeam){
                            boxscore.setDreb(Integer.parseInt(data));
                        }
                    }else if(tag.equals("reb")){
                        event = eventReader.nextEvent();
                        String data = event.asCharacters().getData();
                        if(openPlayer){
                            playBS.setReb(Integer.parseInt(data));
                        }else if(openTeam){
                            boxscore.setReb(Integer.parseInt(data));
                        }
                    }else if(tag.equals("ast")){
                        event = eventReader.nextEvent();
                        String data = event.asCharacters().getData();
                        if(openPlayer){
                            playBS.setAst(Integer.parseInt(data));
                        }else if(openTeam){
                            boxscore.setAst(Integer.parseInt(data));
                        }
                    }else if(tag.equals("stl")){
                        event = eventReader.nextEvent();
                        String data = event.asCharacters().getData();
                        if(openPlayer){
                            playBS.setStl(Integer.parseInt(data));
                        }else if(openTeam){
                            boxscore.setStl(Integer.parseInt(data));
                        }
                    }else if(tag.equals("blk")){
                        event = eventReader.nextEvent();
                        String data = event.asCharacters().getData();
                        if(openPlayer){
                            playBS.setBlk(Integer.parseInt(data));
                        }else if(openTeam){
                            boxscore.setBlk(Integer.parseInt(data));
                        }
                    }else if(tag.equals("to")){
                        event = eventReader.nextEvent();
                        String data = event.asCharacters().getData();
                        if(openPlayer){
                            playBS.setTov(Integer.parseInt(data));
                        }else if(openTeam){
                            boxscore.setTov(Integer.parseInt(data));
                        }
                    }else if(tag.equals("pf")){
                        event = eventReader.nextEvent();
                        String data = event.asCharacters().getData();
                        if(openPlayer){
                            playBS.setPf(Integer.parseInt(data));
                        }else if(openTeam){
                            boxscore.setPf(Integer.parseInt(data));
                        }
                    }else if(tag.equals("pam")){
                        event = eventReader.nextEvent();
                        String data = event.asCharacters().getData();
                        playBS.setPam(Integer.parseInt(data));
                    }else if(tag.equals("pts")){
                        event = eventReader.nextEvent();
                        String data = event.asCharacters().getData();
                        if(openPlayer){
                            playBS.setPts(Integer.parseInt(data));
                        }else if(openTeam){
                            boxscore.setPts(Integer.parseInt(data));
                        }
                    }else if(tag.equals("dnp")){
                        event = eventReader.nextEvent();
                        String data = event.asCharacters().getData();
                        playBS.setDnp(Boolean.valueOf(data));
                    }else if(tag.equals("home")){
                        event = eventReader.nextEvent();
                        String data = event.asCharacters().getData();
                        boxscore.setHome(Boolean.valueOf(data));
                    }else if(tag.equals("win")){
                        event = eventReader.nextEvent();
                        String data = event.asCharacters().getData();
                        boxscore.setWin(Boolean.valueOf(data));
                    }else{
                        //System.out.println(tag);
                    }
                    
                }else if(event.isEndElement()){
                    EndElement endElem = event.asEndElement();
                    String tag = endElem.getName().getLocalPart();
                    if(tag.equals("player")){
                        if(openPlayer){
                            if(play!=null){
                                listPlay.add(play);
                                //System.out.println(play.toString());
                            }
                            if(playBS!=null){
                                listPlayBS.add(playBS);
                                //System.out.println(playBS.toCsv());
                            }
                            openPlayer = false;
                        }
                    }else if(tag.equals("team")){
                        if(openTeam){
                            if(auxTeam!=null&&boxscore!=null){
                                if(boxscore.getHome()){
                                    home = auxTeam;
                                    homeBS = boxscore;
                                    gam.setHomeTeam(home);
                                    //System.out.println(home.toString());
                                    //System.out.println(boxscore.getBoxscorePK().toString());
                                    
                                }else{
                                    away = auxTeam;
                                    awayBS = boxscore;
                                    gam.setAwayTeam(away);
                                    //System.out.println(boxscore.getBoxscorePK().toString());
                                }
                                openTeam = false;
                            }
                        }
                    }else if(tag.equals("game")){
                        if(openGame){
                            System.out.println(gam.toString());
                            openGame = false;
                            break;
                        }
                    }
                }
                
            }
            
            //PERSIST
            int ite = 0;
            Team checkTeam = teamControl.findTeam(away.getName());
            if(checkTeam==null){
                teamControl.create(away);
            }else{
                away = checkTeam;
            }
            checkTeam = teamControl.findTeam(home.getName());
            if(checkTeam==null){
                teamControl.create(home);
            }else{
                home = checkTeam;
            }
            gam.setAwayTeam(away);
            gam.setHomeTeam(home);
            Game checkGam = gameControl.findGame(gam.getIdgame());
            if(checkGam==null){
                gameControl.create(gam);
            }else{
                gam = checkGam;
            }
            awayBS.setGame(gam);
            awayBS.setTeam(away);
            awayBS.setBoxscorePK(new BoxscorePK(gam.getIdgame(), away.getName()));
            Boxscore checkBS = bsControl.findBoxscore(awayBS.getBoxscorePK());
            if(checkBS==null){
                bsControl.create(awayBS);
            }else{
                awayBS = checkBS;
            }
            homeBS.setGame(gam);
            homeBS.setTeam(home);
            homeBS.setBoxscorePK(new BoxscorePK(gam.getIdgame(), home.getName()));
            checkBS = bsControl.findBoxscore(homeBS.getBoxscorePK());
            if(checkBS==null){
                bsControl.create(homeBS);
            }else{
                homeBS = checkBS;
            }
            while(ite<listPlay.size()){
                play = listPlay.get(ite);
                Player checkPlay = playControl.findPlayer(play);
                if(checkPlay==null){
                    playControl.create(play);
                }else{
                    play = checkPlay;
                }
                playBS = listPlayBS.get(ite);
                playBS.setGame(gam);
                playBS.setPlayer(play);
                playBS.setPlayerboxscorePK(new PlayerboxscorePK(gam.getIdgame(), play.getIdplayer()));
                Playerboxscore checkPBS = playBSControl.findPlayerboxscore(playBS.getPlayerboxscorePK());
                if(checkPBS==null){
                    playBSControl.create(playBS);
                }else{
                    playBS = checkPBS;
                }
                ite++;
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Xml2DB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(Xml2DB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Xml2DB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Xml2DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NBADataPU");
        int fileid = 400488874;
        for (int i = 0; i < 1230; i++) {
            readAndPersist("xml/"+(fileid+i)+".xml", emf);
        }
        emf.close();
    }
    
}
