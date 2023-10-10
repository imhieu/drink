package com.jonahseguin.drink.command;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

@Getter
@Setter
public class DrinkHelpService {

    private final DrinkCommandService commandService;
    private HelpFormatter helpFormatter;

    public DrinkHelpService(DrinkCommandService commandService) {
        this.commandService = commandService;
        this.helpFormatter = (sender, container) -> {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&m------------------------------------------------"));
            for (DrinkCommand c : container.getCommands().values()) {
                TextComponent msg = new TextComponent(ChatColor.translateAlternateColorCodes('&',
                        "&6/" + container.getName() + (c.getName().length() > 0 ? " " + c.getName() : "") + " &f" + c.getMostApplicableUsage() + " &7&o- " + c.getShortDescription()));
                msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(ChatColor.GRAY + "/" + container.getName() + " " + c.getName() + " - " + ChatColor.WHITE + c.getDescription())));
                msg.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/" + container.getName() + " " + c.getName()));
                sender.sendMessage(msg.toLegacyText());
            }
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&m------------------------------------------------"));
        };
    }

    public void sendHelpFor(CommandSender sender, DrinkCommandContainer container) {
        this.helpFormatter.sendHelpFor(sender, container);
    }

    public void sendUsageMessage(CommandSender sender, DrinkCommandContainer container, DrinkCommand command) {
        sender.sendMessage(getUsageMessage(container, command));
    }

    public String getUsageMessage(DrinkCommandContainer container, DrinkCommand command) {
        String usage = ChatColor.RED + "Command Usage: /" + container.getName() + " ";
        if (command.getName().length() > 0) {
            usage += command.getName() + " ";
        }
        if (command.getUsage() != null && command.getUsage().length() > 0) {
            usage += command.getUsage();
        } else {
            usage += command.getGeneratedUsage();
        }
        return usage;
    }

}
