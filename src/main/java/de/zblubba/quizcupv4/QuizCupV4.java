package de.zblubba.quizcupv4;

import de.zblubba.quizcupv4.commands.FlyCommand;
import de.zblubba.quizcupv4.commands.InvisCommand;
import de.zblubba.quizcupv4.listeners.ChatListener;
import de.zblubba.quizcupv4.listeners.GeneralListeners;
import de.zblubba.quizcupv4.listeners.MotdListener;
import de.zblubba.quizcupv4.util.Scoreboard;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public final class QuizCupV4 extends JavaPlugin {

    public static QuizCupV4 instance;

    // Command / Listener functions
    public Scoreboard scoreboard = new de.zblubba.quizcupv4.util.Scoreboard();

    public static ArrayList<String> playerList = new ArrayList<>();


    public static File configFile = new File("plugins/QuizCupRemastered", "config.yml");
    public static FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

    public static File frageFile = new File("plugins/QuizCupRemastered", "fragesystem.yml");
    public static FileConfiguration fragen = YamlConfiguration.loadConfiguration(frageFile);

    public static File mysqlFile = new File("plugins/QuizCupRemastered", "mysql.yml");
    public static FileConfiguration mysqlConfig = YamlConfiguration.loadConfiguration(mysqlFile);

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        createFiles();
        loadConfigFiles();

        registerListeners();
        registerCommands();

        recreatePlayerList();
        checkInvis();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new ChatListener(), this);
        pm.registerEvents(new GeneralListeners(), this);
        pm.registerEvents(new MotdListener(), this);
    }

    public void registerCommands() {
        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("closechat").setExecutor(new FlyCommand());
        getCommand("invis").setExecutor(new InvisCommand());
    }

    public static void createFiles() {
        if(!QuizCupV4.configFile.exists() || !QuizCupV4.frageFile.exists() || !QuizCupV4.mysqlFile.exists()) {
            QuizCupV4.getInstance().getLogger().info("One or more files were not found. Creating...");
            if(!QuizCupV4.configFile.exists()) {
                QuizCupV4.configFile.getParentFile().mkdirs();
                QuizCupV4.getInstance().saveResource("config.yml", false);
            }
            if(!QuizCupV4.frageFile.exists()) {
                QuizCupV4.frageFile.getParentFile().mkdirs();
                QuizCupV4.getInstance().saveResource("fragesystem.yml", false);
            }
            if(!QuizCupV4.mysqlFile.exists()) {
                QuizCupV4.mysqlFile.getParentFile().mkdirs();
                QuizCupV4.getInstance().saveResource("mysql.yml", false);
            }
        }
    }

    public static void loadConfigFiles() {
        QuizCupV4.getInstance().getLogger().info("Loading the config files...");
        try {
            QuizCupV4.config.load(configFile);
            QuizCupV4.fragen.load(frageFile);
            QuizCupV4.mysqlConfig.load(mysqlFile);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        } catch (InvalidConfigurationException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static void saveFile(File file, FileConfiguration configuration) {
        try {
            configuration.save(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void recreatePlayerList() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            if(!p.hasPermission("quizcup.helper") && !p.hasPermission("quizcup.admin")) {
                playerList.add(p.getName());
            }
        }
    }

    public static void checkInvis() {
        // SERVER CLOSED ?

        if(!config.getBoolean("invis_enabled")) return;
        for(Player players : Bukkit.getOnlinePlayers()) {
            for(int i = 0; i < playerList.size(); i++) {
                Player p = Bukkit.getPlayer(playerList.get(i));
                if(!players.hasPermission("quizcup.helper")) {
                    p.hidePlayer(players);
                }
            }
        }
    }

    public static QuizCupV4 getInstance() {return instance;}
}
