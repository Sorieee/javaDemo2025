package top.sorie.netty.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class EchoServer {
	public static final int PORT = 8089;
	public static void main(String[] args) throws InterruptedException {
		ServerBootstrap bootstrap = new ServerBootstrap();
		NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup(4);
		try {
			bootstrap.group(eventLoopGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<Channel>() {
						@Override
						protected void initChannel(Channel nioServerSocketChannel) throws Exception {
							nioServerSocketChannel
									.pipeline().addLast(new StringDecoder(),
											new StringEncoder(),
											new ChannelInboundHandlerAdapter() {
												@Override
												public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
													System.out.println(msg.toString());
													ctx.channel().writeAndFlush(msg);
												}
											}

									);
						}
					});
			ChannelFuture f = bootstrap.bind(PORT).sync();
			f.channel().closeFuture().sync();
		} catch (Exception e) {

		} finally {
			eventLoopGroup.shutdownGracefully();
		}



	}
}
