package org.wei.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {


    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.bind(new InetSocketAddress(80));
        System.out.println("Server listening on port:" + serverChannel.getLocalAddress());

        // 创建一个Selector
        Selector selector = Selector.open();
        // 到达的客户端连接都注册到该selector，并设定标志为OP_ACCEPT：确认连接，接收就绪
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        RequestHandler requestHandler = new RequestHandler();

        // 轮询
        while (true) {
            int select = selector.select();
            // 判断是否有事件发生
            if (select == 0) {
                continue;
            }
            System.out.println("事件发生");

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                // 发生连接确认
                if (key.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel clientChannel = channel.accept();
                    System.out.println("Connection from " + clientChannel.getRemoteAddress());
                    clientChannel.configureBlocking(false);
                    clientChannel.register(selector, SelectionKey.OP_READ);
                }
                // 发生读就绪
                if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    channel.read(buffer);
                    String request = new String(buffer.array()).trim();
                    if ("q".equals(request)) {
                        channel.close();
                    }
                    buffer.clear();
                    System.out.printf("From %s: %s%n", channel.getRemoteAddress(), request);
                    String response = requestHandler.process(request);
                    channel.write(ByteBuffer.wrap(response.getBytes()));
                }
                // 该事件处理结束
                iterator.remove();
            }
        }
    }
}
