package net.trompgames.bungee;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class MainBungee extends Plugin implements Listener{

	
	@Override
	public void onEnable(){
		this.getProxy().registerChannel("spleggChannel");	
        	this.getProxy().getPluginManager().registerListener(this, this);    
        	this.getLogger().info("Splegg Bugee Enabled");
	}
	
	
	@EventHandler
    public void onPluginMessage(PluginMessageEvent event) {
    		
    if (!event.getTag().equals("spleggChannel")) {
        return;
    }

    if (!(event.getSender() instanceof Server)) {
        return;
    }
        
    ServerInfo server = BungeeCord.getInstance().getPlayer(event.getReceiver().toString()).getServer().getInfo();
    ByteArrayInputStream stream = new ByteArrayInputStream(event.getData());
    DataInputStream in = new DataInputStream(stream);
    String command = null;
    try {
		command = in.readUTF();
	} catch (IOException e) {
		e.printStackTrace();
	}
        

    if(command.equalsIgnoreCase("getStats")){
       	String s = "NULL";
		try {
			s = in.readUTF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       	ServerInfo info = getInfo(s);
       	info.sendData("spleggChannel", event.getData());
    	//this.getLogger().info("Passed through data to: " + s);
        }
    }
	
	
	public ServerInfo getInfo(String s){
        Map<String, ServerInfo> servers = BungeeCord.getInstance().getServers();
		for (String key : servers.keySet()) {			
			ServerInfo info = servers.get(key);
			if(key.equals(s)) return info;
		}
		return null;
	}
	
	
}
