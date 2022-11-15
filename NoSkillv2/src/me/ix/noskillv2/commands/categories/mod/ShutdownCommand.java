package me.ix.noskillv2.commands.categories.mod;

import java.util.ArrayList;
import java.util.List;

import me.ix.noskillv2.commands.CommandCategory;
import me.ix.noskillv2.commands.CommandContext;
import me.ix.noskillv2.commands.ICommand;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class ShutdownCommand implements ICommand {

	@Override
	public void execute(CommandContext ctx, ArrayList<String> arguments) {
		
	}

	@Override
	public String getName() {
		return "shutdown";
	}

	@Override
	public String getHelp() {
		return "Shut the bot down.";
	}

	@Override
	public CommandCategory getCategory() {
		return CommandCategory.MOD;
	}

	@Override
	public List<OptionData> getArguments() {
		return null;
	}

	

}
