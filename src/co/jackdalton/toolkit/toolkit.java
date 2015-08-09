package co.jackdalton.toolkit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;

public class toolkit extends JavaPlugin {
    String version = "0.0.8";
    String tkPrefix = ChatColor.LIGHT_PURPLE + "[Toolkit] " + ChatColor.WHITE + " : " + ChatColor.DARK_PURPLE;
    public boolean checkOnline(String userName) {
        Player p = Bukkit.getPlayerExact(userName);
        if (p != null) {
            return true;
        }
        return false;
    }
    public String getPos(Player player) {
        Location l = player.getLocation();
        String loc = "(" + l.getBlockX() + ", " + l.getBlockY() + ", " + l.getBlockZ() + ")";
        return loc;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("reallist")) {
            int i = 0;
            for (Player p : Bukkit.getOnlinePlayers()) {
                i++;
                sender.sendMessage(tkPrefix + ChatColor.BLUE + "[" + i + "]" + ChatColor.DARK_PURPLE + " : " + p.getDisplayName());
            }
            if (i == 0) {
                sender.sendMessage(tkPrefix + "No players are online, which means you must be doing this through the console.");
            }
            return true;
        }
        if (command.getName().equalsIgnoreCase("toolkit")) {
            sender.sendMessage(tkPrefix + "Toolkit v" + version + " || By Jack Dalton // Stale_Muffins");
            sender.sendMessage(tkPrefix + "===============================");
            sender.sendMessage(tkPrefix + "/forcetp <player>: Teleport to a player whether or not they are in /v.");
            sender.sendMessage(tkPrefix + "/checkop <player>: Checks whether a player is op.");
            sender.sendMessage(tkPrefix + "/getgamemode <player>: Displays a player's current gamemode.");
            sender.sendMessage(tkPrefix + "/reallist: Displays a list of all online players, regardless of /v.");
            sender.sendMessage(tkPrefix + "/forcemsg <player> <message>: Messages a player regardless of whether they are in /v.");
            sender.sendMessage(tkPrefix + "/getworld <player>: Displays a player's current world.");
            sender.sendMessage(tkPrefix + "/checkperm <player> <permission>: Checks whether a player has a permission.");
            sender.sendMessage(tkPrefix + "/getflyers: Displays a list of players who are currently flying.");
            sender.sendMessage(tkPrefix + "/getcoords <player>: Displays a player's coordinates.");
            sender.sendMessage(tkPrefix + "===============================");
            return true;
        }
        if (command.getName().equalsIgnoreCase("forcemsg")) {
            if (args.length > 1) {
                String recip = args[0];
                String[] altArgs = new String[args.length - 1];
                for (int i = 1; i < args.length; i++) {
                    altArgs[i - 1] = args[i];
                }
                String message = "";
                for (String s : altArgs) {
                    message += s + " ";
                }
                if (checkOnline(recip)) {
                    Player r = Bukkit.getPlayerExact(recip);
                    String output = ChatColor.GOLD + "" + ChatColor.BOLD + "[" + ChatColor.RED + "me" + ChatColor.GOLD + " -> " + ChatColor.GRAY + recip + ChatColor.GOLD + ChatColor.BOLD + "]" + " " + ChatColor.WHITE + message;
                    String outmsg = ChatColor.GOLD + "" + ChatColor.BOLD + "[" + ChatColor.GRAY + sender.getName() + ChatColor.GOLD + " -> " + ChatColor.RED + "me" + ChatColor.GOLD + ChatColor.BOLD + "]" + " " + ChatColor.WHITE + message;
                    sender.sendMessage(output);
                    r.sendMessage(outmsg);
                } else {
                    sender.sendMessage(tkPrefix + "Error: Player is not online.");
                }
            } else {
                sender.sendMessage(tkPrefix + "Error: 2 arguments required, " + args.length + " present.");
            }
            return true;
        }
        if (command.getName().equalsIgnoreCase("forcetp")) {
            if (args.length == 1) {
                if (checkOnline(args[0])) {
                    Player target = Bukkit.getPlayerExact(args[0]);
                    Location l = target.getLocation();
                    Player orig = (Player)sender;
                    orig.teleport(l);
                } else {
                    sender.sendMessage(tkPrefix + "Error: player is offline.");
                }
            } else {
                sender.sendMessage(tkPrefix + "Error: 1 argument required, " + args.length + " present.");
            }
            return true;
        }
        if (command.getName().equalsIgnoreCase("getworld")) {
            if (args.length == 1) {
                if (checkOnline(args[0])) {
                    Player p = Bukkit.getPlayerExact(args[0]);
                    String world = p.getWorld().getName();
                    sender.sendMessage(tkPrefix + "Player \"" + args[0] + "\" is currently in " + world + ".");
                } else {
                    sender.sendMessage(tkPrefix + "Error: player is offline.");
                }
            } else {
                sender.sendMessage(tkPrefix + "Error: 1 argument required, " + args.length + " present.");
            }
            return true;
        }
        if (command.getName().equalsIgnoreCase("checkop")) {
            if (args.length == 1) {
                if (checkOnline(args[0])) {
                    Player p = Bukkit.getPlayerExact(args[0]);
                    String isOpped = "error";
                    if (p.isOp()) {
                        isOpped = "is";
                    } else {
                        isOpped = "is not";
                    }
                    sender.sendMessage(tkPrefix + args[0] + " " + isOpped + " opped.");
                } else {
                    sender.sendMessage(tkPrefix + "Error: player is offline.");
                }
            } else {
                sender.sendMessage(tkPrefix + "Error: 1 argument required, " + args.length + " present.");
            }
            return true;
        }
        if (command.getName().equalsIgnoreCase("checkperm")) {
            if (args.length == 2) {
                String player = args[0];
                String perm = args[1];
                if (checkOnline(player)) {
                    Player p = Bukkit.getPlayerExact(player);
                    sender.sendMessage(tkPrefix + player + " has " + perm + ": " + p.hasPermission(perm));
                } else {
                    sender.sendMessage(tkPrefix + "Error: player is offline.");
                }
            } else {
                sender.sendMessage(tkPrefix + "Error: 2 arguments required, " + args.length + " present.");
            }
            return true;
        }
        if (command.getName().equalsIgnoreCase("getflyers")) {
            int f = 0;
            int i = 0;
            for (Player p : Bukkit.getOnlinePlayers()) {
                i++;
                if (p.isFlying()) {
                    f++;
                    sender.sendMessage(tkPrefix + "[" + f + "] " + p.getDisplayName() + " is flying at " + getPos(p) + ".");
                }
            }
            if (f == 0) {
                sender.sendMessage(tkPrefix + "Nobody is currently flying.");
            }
            return true;
        }
        if (command.getName().equalsIgnoreCase("totalnick")) {
            if (args.length == 1) {
                Player p = Bukkit.getPlayerExact(sender.getName());
                String nick = args[0];
                if (nick == "off") {
                    p.setDisplayName(p.getDisplayName());
                    p.sendMessage("You no longer have a nickname.");
                } else {
                    p.setDisplayName(nick);
                    p.sendMessage("You are now known as " + nick + ".");
                }
            }
            return true;
        }
        if (command.getName().equalsIgnoreCase("getgamemode")) {
            if (args.length == 1) {
                if (checkOnline(args[0])) {
                    Player p = Bukkit.getPlayerExact(args[0]);
                    String gamemode = p.getGameMode().name();
                    sender.sendMessage(tkPrefix + args[0] + " is currently in " + gamemode.toLowerCase() + " mode.");
                } else {
                    sender.sendMessage(tkPrefix + "Error: player is offline.");
                }
            } else {
                sender.sendMessage(tkPrefix + "Error: 1 argument required, " + args.length + " present.");
            }
            return true;
        }
        if (command.getName().equalsIgnoreCase("getcoords")) {
            if (args.length == 1) {
                if (checkOnline(args[0])) {
                    Player target = Bukkit.getPlayerExact(args[0]);
                    String loc = getPos(target);
                    sender.sendMessage(tkPrefix + "Player \"" + args[0] + "\" is at " + loc + ".");
                } else {
                    sender.sendMessage(tkPrefix + "Error: player is offline.");
                }
            } else {
                sender.sendMessage(tkPrefix + "Error: 1 argument required, " + args.length + " present.");
            }
            return true;
        }
        return false;
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onEnable() {

    }
}