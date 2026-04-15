
package com.example.hotbargrid.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class HotbarMixin {

    @Inject(method = "renderHotbar", at = @At("HEAD"), cancellable = true)
    private void renderHotbar(float tickDelta, DrawContext context, CallbackInfo ci) {
        ci.cancel();

        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;

        if (player == null) return;

        PlayerInventory inv = player.getInventory();

        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        int slotSize = 20;
        int gridWidth = slotSize * 3;
        int gridHeight = slotSize * 3;

        int startX = (screenWidth / 2) - (gridWidth / 2);
        int startY = screenHeight - gridHeight - 10;

        int selectedSlot = inv.selectedSlot;

        for (int i = 0; i < 9; i++) {
            int row = i / 3;
            int col = i % 3;

            int x = startX + col * slotSize;
            int y = startY + row * slotSize;

            ItemStack stack = inv.getStack(i);

            context.fill(x - 1, y - 1, x + 17, y + 17, 0x90000000);

            if (i == selectedSlot) {
                context.fill(x - 2, y - 2, x + 18, y + 18, 0xFFFFFF00);
            }

            context.drawItem(stack, x, y);
            context.drawItemInSlot(client.textRenderer, stack, x, y);
        }
    }
}
