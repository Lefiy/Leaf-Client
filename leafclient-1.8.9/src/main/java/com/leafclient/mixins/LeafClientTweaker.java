package com.leafclient.mixins;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;

public class LeafClientTweaker implements ITweaker {
	
    private List<String> launchArgs = new ArrayList<>();

    @Override
    public final void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
    	
    	this.launchArgs.addAll(args);
    	
        if (!args.contains("--version") && profile != null) {
        	this.launchArgs.add("--version");
        	this.launchArgs.add(profile);
        }
        if (!args.contains("--assetsDir") && assetsDir != null) {
        	this.launchArgs.add("--assetsDir");
        	this.launchArgs.add(assetsDir.getAbsolutePath());
        }
        if (!args.contains("--gameDir") && gameDir != null) {
        	this.launchArgs.add("--gameDir");
        	this.launchArgs.add(gameDir.getAbsolutePath());
        }
    }

    @Override
    public final void injectIntoClassLoader(LaunchClassLoader classLoader) {
    	
    	MixinBootstrap.init();
		Mixins.addConfiguration("mixins.leafclient.json");
		MixinEnvironment.getDefaultEnvironment().setObfuscationContext("notch");
		
    }

    @Override
    public String getLaunchTarget() {
        return "net.minecraft.client.main.Main";
    }

    @Override
    public String[] getLaunchArguments() {
        return this.launchArgs.toArray(new String[0]);
    }
}