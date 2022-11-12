package me.ix.noskillv2;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

public class NoSkillv2 {

	private String TOKEN = "MTA0MTA5NjcwNDM1Njc4NjIzNg.GdRL41.ZmuazX7laErkSsmz0dyKBIDbdmkZjWdUgSS6IU";
	private final ShardManager shardManager;
	
	public NoSkillv2() throws LoginException {
		DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(TOKEN);
		builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
		builder.setActivity(Activity.watching("in development."));
		builder.build();
		
		shardManager = builder.build();
	}
	
	public ShardManager getShardManager() {
		return shardManager;
	}
	
	public static void main(String[] args) {
		try {
			NoSkillv2 bot = new NoSkillv2();
		} catch (LoginException e) {
			System.out.println("ERROR: Invalid Token!");
		}
	}
	
}
