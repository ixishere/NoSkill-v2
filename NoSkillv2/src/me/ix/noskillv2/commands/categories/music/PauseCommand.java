package me.ix.noskillv2.commands.categories.music;

import java.util.ArrayList;
import java.util.List;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import me.ix.noskillv2.commands.CommandCategory;
import me.ix.noskillv2.commands.CommandContext;
import me.ix.noskillv2.commands.ICommand;
import me.ix.noskillv2.utils.Utils;
import me.ix.noskillv2.utils.lavaplayer.GuildMusicManager;
import me.ix.noskillv2.utils.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class PauseCommand implements ICommand {

	@Override
	public void execute(CommandContext ctx, ArrayList<String> arguments) {
        final Member self = ctx.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();
        final Member member = ctx.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();
        
        EmbedBuilder eb = Utils.getDefaultEmbed(this, false);
        
        if (!selfVoiceState.inAudioChannel()) {
        	eb.addField("Pause Info", "I need to be in a voice channel for this to work", false);
        	ctx.sendMessage("", eb.build());
            return;
        }

        if (!memberVoiceState.inAudioChannel()) {
        	eb.addField("Pause Info", "You need to be in a voice channel for this command to work", false);
        	ctx.sendMessage("", eb.build());
            return;
        }

        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
        	eb.addField("Pause Info", "You need to be in the same voice channel as me for this to work", false);
        	ctx.sendMessage("", eb.build());
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final AudioPlayer audioPlayer = musicManager.audioPlayer;
        final AudioTrack track = audioPlayer.getPlayingTrack();

        if (track == null) {
        	eb.addField("Pause Info", "There is no track playing currently", false);
        	ctx.sendMessage("", eb.build());
            return;
        }

        final AudioTrackInfo info = track.getInfo();

        audioPlayer.setPaused(true);
        
    	eb.addField("Pause Info", "", false);
    	ctx.sendMessage("", eb.build());
        ctx.sendMessage("Paused `" + info.title + "`");
	}

	@Override
	public String getName() {
		return "pause";
	}

	@Override
	public String getHelp() {
		return "Pause the current song.";
	}

	@Override
	public CommandCategory getCategory() {
		return CommandCategory.MUSIC;
	}

	@Override
	public List<OptionData> getArguments() {
		return null;
	}
	
}
