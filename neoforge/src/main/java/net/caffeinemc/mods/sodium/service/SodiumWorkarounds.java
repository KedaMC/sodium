package net.caffeinemc.mods.sodium.service;

import net.caffeinemc.mods.sodium.client.compatibility.environment.AndroidPlatform;
import net.caffeinemc.mods.sodium.client.compatibility.workarounds.Workarounds;
import net.neoforged.neoforgespi.earlywindow.GraphicsBootstrapper;

public class SodiumWorkarounds implements GraphicsBootstrapper {
    @Override
    public String name() {
        return "sodium";
    }

    @Override
    public void bootstrap(String[] arguments) {
        AndroidPlatform.requireSupported();
        Workarounds.init();
    }
}
