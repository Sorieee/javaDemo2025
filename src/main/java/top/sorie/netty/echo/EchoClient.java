package top.sorie.netty.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

public class EchoClient {
	public static void main(String[] args) {
		EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(eventLoopGroup)
					.channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<NioSocketChannel>() {

						@Override
						protected void initChannel(NioSocketChannel channel) throws Exception {
							channel.pipeline().addLast(
									new StringDecoder(),
									new StringEncoder(),
									new ChannelInboundHandlerAdapter() {
										@Override
										public void channelActive(ChannelHandlerContext ctx) {
											// 当连接建立时，向服务器发送消息
											ctx.writeAndFlush("Hello, Netty Server!");
										}

										@Override
										public void channelRead(ChannelHandlerContext ctx, Object msg) {
											// 打印接收到的服务器消息
											System.out.println("Client received: " + msg);
											ctx.close();  // 读取完毕后关闭连接
										}

										@Override
										public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
											cause.printStackTrace();
											ctx.close();  // 出现异常时关闭连接
										}
									}
							);
						}
					});
			Channel channel = bootstrap.connect(new InetSocketAddress(8089)).sync().channel();
			channel.writeAndFlush("Hi.");
			// 等待连接关闭
			channel.closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			eventLoopGroup.shutdownGracefully();
		}
	}
}
