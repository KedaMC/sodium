package net.caffeinemc.mods.sodium.mixin.workarounds.context_creation;

import com.mojang.blaze3d.shaders.ShaderSource;
import com.mojang.blaze3d.systems.RenderSystem;
import net.caffeinemc.mods.sodium.client.compatibility.environment.GlContextInfo;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderSystem.class)
public class RenderSystemMixin {
    @Shadow
    @Final
    static Logger LOGGER;

    @Inject(method = "initRenderer", at = @At(value = "RETURN"))
    private static void postContextReady(long window, int i, boolean bl, ShaderSource shaderSource, boolean bl2, CallbackInfo ci) {
        GlContextInfo context = GlContextInfo.create();
        LOGGER.info("OpenGL Vendor: {}", context.vendor());
        LOGGER.info("OpenGL Renderer: {}", context.renderer());
        LOGGER.info("OpenGL Version: {}", context.version());
    }
}
