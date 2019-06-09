package me.dretax.SimpleSlots;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleSlots extends JavaPlugin {

    protected SimpleSlotsL SL = new SimpleSlotsL(this);
    protected FileConfiguration config;
    private PluginManager _pm;
    private static ConsoleCommandSender _cs;
    private static final String _prefix = ChatColor.YELLOW + "[SimpleSlots] ";
    protected int SimpleSlots;

    public void onEnable() {
        this._pm = this.getServer().getPluginManager();
        _cs = this.getServer().getConsoleSender();
        this.config = this.getConfig();
        this.config.addDefault("SimpleSlots", 100);
        this.config.options().copyDefaults(true);
        this.saveConfig();
        this.SimpleSlots = this.config.getInt("SimpleSlots");
        this.getServer().getPluginManager().registerEvents(this.SL, this);
        this.getCommand("simples").setExecutor(this);
        this.sendConsoleMessage( ChatColor.GREEN + "Successfully Enabled!");
    }

    public static void sendConsoleMessage(String msg) {
        _cs.sendMessage(_prefix + ChatColor.AQUA + msg);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("simpleslots.reload")) {
                    this.ConfigReload();
                    sender.sendMessage(_prefix + ChatColor.GREEN + "Config Reloaded!");
                } else {
                    sender.sendMessage(_prefix + ChatColor.RED + "You Don't Have Permission to do this!");
                }
            }
            if (args[0].equalsIgnoreCase("editslot")) {
                if (sender.hasPermission("simpleslots.slot")) {
                    if (args.length == 2) {
                        if (!isInteger(args[1])) {
                            sender.sendMessage(_prefix + ChatColor.GREEN + "This isn't a number.");
                            return false;
                        }

                        this.config.set("SimpleSlots", Integer.valueOf(args[1]));
                        this.saveConfig();
                        this.ConfigReload();
                        sender.sendMessage(_prefix + ChatColor.GREEN + "Changed Slot Number To: " + args[1]);
                    }
                } else {
                    sender.sendMessage(_prefix + ChatColor.RED + "You Don't Have Permission to do this!");
                }
            }
        } else {
            sender.sendMessage(_prefix + ChatColor.GREEN + "1.4 " + ChatColor.AQUA + "===Commands:===");
            sender.sendMessage(ChatColor.BLUE + "/simples reload" + ChatColor.GREEN + " - Reloads Config");
            sender.sendMessage(ChatColor.BLUE + "/simples editslot" + ChatColor.GREEN + " - Edits Slot | USE ONLY NUMBERS");
        }
        return false;
    }

    public void ConfigReload() {
        this.reloadConfig();
        this.config = this.getConfig();
        this.SimpleSlots = this.config.getInt("SimpleSlots");
        this.reloadConfig();
    }

    public boolean isInteger(String s) {
        return isInteger(s,10);
    }

    private boolean isInteger(String s, int radix) {
        if(s.isEmpty())  {
            return false;
        }

        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }
}
