package net.momentonetwork.commands;

import net.momentonetwork.MineableSpawners;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class SpawnerCommand implements CommandExecutor {
    // Make a class variable plugin with main class instance
    private MineableSpawners plugin;
    // Class config variable
    private FileConfiguration config;

    // Constructor
    public SpawnerCommand(MineableSpawners plugin) {
        this.plugin = plugin;
        this.config = this.plugin.getConfigInstance();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
//
// ONE LINE CHANGE BY MOCAVEMAN TO CHECK EACH TYPE OF POSSIBLE SPAWNER FOR INDIVIDULE PERMS
//
//   
            if (sender.hasPermission("MSallow.spawn." + EntityType.valueOf(args[0].toUpperCase())) || !config.getBoolean("require_permission.spawner_command")) {
                if (args.length == 1) {
                    Block target = ((Player) sender).getTargetBlock(null, 5);
                    if (target.getState().getBlock().getType() == Material.SPAWNER) {
                        try {
                            EntityType type = EntityType.valueOf(args[0].toUpperCase());
                            CreatureSpawner spawner = (CreatureSpawner) target.getState();
                            spawner.setSpawnedType(type);
                            spawner.update();
                            sender.sendMessage(ChatColor.GREEN + "Successfully changed spawner type to " + type.toString().toLowerCase() + "!");
                        } catch (IllegalArgumentException e) {
                            sender.sendMessage(ChatColor.RED + "Sorry, that's not a valid mob type.");
                        }
                    }

                } else {
                    sender.sendMessage(ChatColor.RED + "Please enter a type. For example: /spawner cow");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Sorry, you do not have permission to do this!");
            }
        } else {
            System.out.println("Only players can run this command!");
        }
        return false;
    }
}
