package dev.lyphium.suppressor;

import dev.lyphium.suppressor.command.SuppressorCommand;
import dev.lyphium.suppressor.listener.BlockListener;
import dev.lyphium.suppressor.manager.RegionManager;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;
import net.kyori.adventure.util.UTF8ResourceBundleControl;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;
import java.util.Objects;
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
        new SuppressorCommand(regionManager)
                .register(Objects.requireNonNull(getCommand("suppressor")));
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new BlockListener(regionManager), this);
    }

    private void registerLanguages() {
        final TranslationRegistry registry = TranslationRegistry.create(Key.key("suppressor"));
        registry.defaultLocale(Locale.ENGLISH);

        ResourceBundle bundle = ResourceBundle.getBundle("suppressor", Locale.ENGLISH, UTF8ResourceBundleControl.get());
        registry.registerAll(Locale.ENGLISH, bundle, true);
        bundle = ResourceBundle.getBundle("suppressor", Locale.GERMAN, UTF8ResourceBundleControl.get());
        registry.registerAll(Locale.GERMAN, bundle, true);

        GlobalTranslator.translator().addSource(registry);
    }
}
