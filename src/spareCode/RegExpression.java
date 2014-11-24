/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spareCode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author ozielcarneiro
 */
public class RegExpression {
    
    public static void main(String[] args) {
        Pattern pat;
        pat = Pattern.compile("<thead.+</thead>");
        Matcher match = pat.matcher("noscript><a href=\"http://ad.Doubleclick.net/jump/espn.us.com.nba/nbaboxscore;pgtyp=nbaboxscore;sp=nba;gmid=400488876;sz=884x40,1x1;\" target=\"_blank\"><img src=\"http://ad.Doubleclick.net/ad/espn.us.com.nba/nbaboxscore;pgtyp=nbaboxscore;sp=nba;gmid=400488876;sz=884x40,1x1;\" border=\"0\" alt=\"\"></a></noscript><div class=\"mod-container mod-no-header-footer mod-open mod-open-gamepack mod-box\" id=\"my-players-table\"><div class=\"mod-content\"><table border=\"0\" width=\"100%\" class=\"mod-data\"><thead><tr class=\"team-color-strip\"><th style=\"border-bottom-color: #FA0028; text-align: left; font: 700 14px/25px Helvetica,Arial,sans-serif;\" colspan=\"15\"><div style=\"margin-right: 6px;\" class=\"logo-small logo-nba-small nba-small-lac floatleft\"></div><a name=\"Los Angeles\"></a>Los Angeles Clippers</th></tr><tr align=\"right\"><th style=\"text-align:left\">STARTERS</th><th width=5%>MIN</th><th width=7% nowrap>FGM-A</th><th width=7%>3PM-A</th><th width=7%>FTM-A</th><th width=6%>OREB</th><th width=6%>DREB</th><th width=6%>REB</th><th width=6%>AST</th><th width=6%>STL</th><th width=6%>BLK</th><th width=6%>TO</th><th width=6%>PF</th><th width=6%>+/-</th><th width=6%>PTS</th></tr></thead><tbody><tr align=right valign=middle class=\"odd player-46-3989\"><td style=\"text-align:left\" nowrap><a href=\"http://espn.go.com/nba/player/_/id/3989/blake-griffin\">");
        while(match.find()) {
            System.out.println (match.group());
        }
        
    }
    
    
    
}
