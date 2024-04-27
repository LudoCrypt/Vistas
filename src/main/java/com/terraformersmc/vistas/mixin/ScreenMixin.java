package com.terraformersmc.vistas.mixin;

import com.terraformersmc.vistas.title.VistasRotatingCubemapRenderer;
import net.minecraft.client.gui.CubeMapRenderer;
import net.minecraft.client.gui.RotatingCubeMapRenderer;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class ScreenMixin {
    @Shadow
    @Final
    protected static CubeMapRenderer PANORAMA_RENDERER;

    @Shadow
    @Mutable
    @Final
    protected static RotatingCubeMapRenderer ROTATING_PANORAMA_RENDERER;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void vistas$useOurRenderer(CallbackInfo ci) {
        ROTATING_PANORAMA_RENDERER = new VistasRotatingCubemapRenderer(PANORAMA_RENDERER);
    }
}
