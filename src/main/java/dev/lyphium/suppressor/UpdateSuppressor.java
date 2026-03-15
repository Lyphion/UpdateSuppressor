package dev.lyphium.suppressor;

import dev.lyphium.suppressor.command.SuppressorCommand;
import dev.lyphium.suppressor.listener.BlockListener;
import dev.lyphium.suppressor.listener.PlayerListener;
import dev.lyphium.suppressor.manager.RegionManager;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.minimessage.translation.MiniMessageTranslationStore;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;
import java.util.ResourceBundle;

public final class UpdateSuppressor extends JavaPlugin {

    private RegionManager regionManager;

    @Override
    public void onEnable() {
        regionManager = new RegionManager(this);
        regionManager.loadRegions();

        registerLanguages();
        registerCommands();
        registerListeners();

        getLogger().info("Plugin activated");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin deactivated");
    }

    private void registerCommands() {
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            final Commands registrar = commands.registrar();

            final SuppressorCommand command = new SuppressorCommand(regionManager);
            registrar.register(command.construct(), SuppressorCommand.DESCRIPTION);
        });
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new BlockListener(regionManager), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(regionManager), this);
    }

    private void registerLanguages() {
        final MiniMessageTranslationStore store = MiniMessageTranslationStore.create(Key.key("suppressor"));
        store.defaultLocale(Locale.ENGLISH);

        ResourceBundle bundle = ResourceBundle.getBundle("suppressor", Locale.ENGLISH);
        store.registerAll(Locale.ENGLISH, bundle, true);
        bundle = ResourceBundle.getBundle("suppressor", Locale.GERMAN);
        store.registerAll(Locale.GERMAN, bundle, true);

        GlobalTranslator.translator().addSource(store);
    }
}
