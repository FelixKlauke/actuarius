/*
 * MIT License
 *
 * Copyright (c) 2017 - 2019 Felix Klauke
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.d3adspace.actuarius.server.agent;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.DatagramChannel;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Felix Klauke <info@felix-klauke.de>
 */
public class ActuariusDatagramAgent implements IActuariusAgent {

    private final ChannelInitializer<DatagramChannel> datagramChannelChannelInitializer;
    private final EventLoopGroup workerGroup;
    private final Class<? extends Channel> channelClass;

    private Channel channel;

    @Inject
    public ActuariusDatagramAgent(ChannelInitializer<DatagramChannel> datagramChannelChannelInitializer, @Named("workerGroup") EventLoopGroup workerGroup, Class<? extends DatagramChannel> channelClass) {
        this.datagramChannelChannelInitializer = datagramChannelChannelInitializer;
        this.workerGroup = workerGroup;
        this.channelClass = channelClass;
    }

    @Override
    public void bootstrap() {
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(workerGroup)
                    .channel(channelClass)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .handler(datagramChannelChannelInitializer);

            channel = bootstrap.bind(53).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isRunning() {
        return channel != null && channel.isOpen();
    }

    @Override
    public void stop() {
        if (channel == null) {
            return;
        }

        channel.close();
    }
}
