
package com.example.hotbargrid.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerInventory;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class HotbarInputMixin {

    @Inject(method = "handleInputEvents", at = @At("TAIL"))
    private void handleScrollOnly(CallbackInfo ci) {
        MinecraftClient client = (MinecraftClient)(Object)this;

        if (client.player == null) return;

        PlayerInventory inv = client.player.getInventory();

        int slot = inv.selectedSlot;

        double scroll = client.mouse.getVerticalWheel();

        if (scroll > 0) {
            slot = (slot + 8) % 9;
        } else if (scroll < 0) {
            slot = (slot + 1) % 9;
        }

        inv.selectedSlot = slot;
    }
}
