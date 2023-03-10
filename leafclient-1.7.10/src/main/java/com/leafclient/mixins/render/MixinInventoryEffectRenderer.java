package com.leafclient.mixins.render;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.leafclient.Client;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.inventory.Container;

@Mixin(InventoryEffectRenderer.class)
public abstract class MixinInventoryEffectRenderer extends GuiContainer {

	public MixinInventoryEffectRenderer(Container p_i1072_1_) {
		super(p_i1072_1_);
	}

	@Shadow
    private boolean hasActivePotionEffects;

    /**
     * Overwrite PotionEffect Render in Inventory
     * @author Lefiy
     * @reason Change Inventory Render
     */
    @Overwrite
    public void initGui() {
    	super.initGui();
        this.hasActivePotionEffects = !Client.getInstance().modmanager.potion.isEnable();
    }
}