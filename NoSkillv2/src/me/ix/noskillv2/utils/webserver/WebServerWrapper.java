package me.ix.noskillv2.utils.webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import me.ix.noskillv2.NoSkillv2;

public class WebServerWrapper {
    private WebServer server;

    private String title = "";
    private String logo = "";
    private String time = "";

    private ArrayList<String> commandRows;
    private ArrayList<String> infoRows;

    public WebServerWrapper(int port, int delay) throws IOException {
        server = new WebServer(port, delay);
        server.setCustomCSS("body { font-family: 'Roboto', sans-serif; margin: 0; padding:0; background-color: #292929; }");

        this.title = " NoSkillv2 Web Server | " + java.net.InetAddress.getLocalHost().getHostAddress() + " -> " + System.getProperty("user.name");
        this.logo = getBase64Logo();

        this.commandRows = new ArrayList<String>();
        this.infoRows = new ArrayList<String>();
        
        server.setContent(getContent());
        
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            clearInfoRows();
        	this.setTime(getCurSystemTime());
            addInfoRow(commandRows.size() + " Commands Executed");
            refresh();
        }, 0, 1, TimeUnit.SECONDS);
    }

    public String getCurSystemTime() {
    	return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
    }
    
    public void refresh() {
        server.setContent(getContent());
    }

    public String getCommandRowContent() {
        ArrayList<String> reversedRows = new ArrayList<>(commandRows);
        Collections.reverse(reversedRows);
        String output = "";
        for(String row : reversedRows) {
            output += "<h3 style=\"color: white;\"><span style=\"color: orange;\">" + row + "</span><br /><span id=\"row1\"></span></h3>";
        }
        return output;
    }

    public void clearCommandRows() {
    	commandRows.clear();
    }

    public void addCommandRow(String user, String commandName, String arguments, String guild) {
    	String date = getCurSystemTime();
    	
    	commandRows.add(
    			"<span style=\"color: red; font-weight: bold; margin-right: 5px; \">[" + date + "] </span>" +
				"<span style=\"color: green; font-weight: bold; margin-right: 5px; \">[" + guild + "] </span>" +
    			"<span style=\"color: orange; font-weight: bold; margin-right: 5px; \">[" + user + "] </span><br />" +
				"<span style=\"color: white;\">" + commandName + " " + arguments + "</span><br />"
				);
    	this.refresh();
    }
    
    public String getInfoRowContent() {
        ArrayList<String> reversedRows = new ArrayList<>(infoRows);
        String output = "";
        for(String row : reversedRows) {
            output += "<h3 style=\"color: white;\"><span style=\"color: orange;\">" + row + "</span><br /><span id=\"row1\"></span></h3>";
        }
        return output;
    }

    public void clearInfoRows() {
    	infoRows.clear();
    }

    public void addInfoRow(String content) {
    	infoRows.add("<span style=\"color: white;\">" + content + "</span>");
    	this.refresh();
    }
    
    private String getContent() {
        String logo = getBase64Logo();
        String title = "NoSkill v2 WebServer";

        String formatted = getHTML()
                .replace("{logosrc}", this.logo)
                .replace("{title}", this.title)
                .replace("{time}", this.time)
                .replace("{commandsRows}", getCommandRowContent())
                .replace("{infoRows}", getInfoRowContent());

        return formatted;
    }

    public String getBase64Logo() {
        try {
            byte[] byteData = getClass().getResourceAsStream("/me/ix/noskillv2/utils/webserver/resources/logo.png").readAllBytes();
            String base64String = Base64.getEncoder().encodeToString(byteData);

            return "data:image/png;base64," + base64String;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public String getHTML() {
        try {
            String text = new BufferedReader(
                    new InputStreamReader(getClass().getResourceAsStream("/me/ix/noskillv2/utils/webserver/resources/content.html"), StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
            return text;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
}
