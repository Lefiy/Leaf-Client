package com.leafclient.mixins.network;

import java.net.InetAddress;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.MessageDeserializer;
import net.minecraft.util.MessageDeserializer2;
import net.minecraft.util.MessageSerializer;
import net.minecraft.util.MessageSerializer2;

@Mixin(NetworkManager.class)
public class MixinNetworkManager {
	
	/**
     * Overwrite Lan Client
     * @author Lefiy
     * @reason Change Lan Option
     */
	@SuppressWarnings("rawtypes")
	@Overwrite
	public static NetworkManager provideLanClient(InetAddress p_150726_0_, int p_150726_1_)
    {
        final NetworkManager networkmanager = new NetworkManager(true);
        ((Bootstrap)((Bootstrap)((Bootstrap)(new Bootstrap()).group(NetworkManager.eventLoops)).handler(new ChannelInitializer()
        {
            protected void initChannel(Channel p_initChannel_1_)
            {
                try
                {
                    p_initChannel_1_.config().setOption(ChannelOption.IP_TOS, Integer.valueOf(24));
                }
                catch (ChannelException channelexception1)
                {
                    ;
                }

                try
                {
                    p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(true));
                }
                catch (ChannelException channelexception)
                {
                    ;
                }

                p_initChannel_1_.pipeline().addLast("timeout", new ReadTimeoutHandler(20)).addLast("splitter", new MessageDeserializer2()).addLast("decoder", new MessageDeserializer(NetworkManager.STATISTICS)).addLast("prepender", new MessageSerializer2()).addLast("encoder", new MessageSerializer(NetworkManager.STATISTICS)).addLast("packet_handler", networkmanager);
            }
        })).channel(NioSocketChannel.class)).connect(p_150726_0_, p_150726_1_).syncUninterruptibly();
        return networkmanager;
    }
}