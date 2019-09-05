package com.integration.networktechdemo.socket;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Wongerfeng on 2019/8/30.
 */
@SuppressLint("HandlerLeak")
public class MessageTransmit implements Runnable{
    private static final String TAG = "MessageTransmit";
    // 以下为Socket服务器的IP和端口，根据实际情况修改
    private static final String SOCKET_IP = "192.168.0.232";
    private static final int SOCKET_PORT = 51000;
    //声明一个缓存读取器
    private BufferedReader mBufferedReader;
    //声明一个输出流对象
    private OutputStream mOutputStream;


    @Override
    public void run() {
        Log.i(TAG, "run:MessageTransmit ");
        try {
            //创建套接字对象,设置指定服务地址的指定端口的套接字
//            Socket socket = new Socket(SOCKET_IP, SOCKET_PORT);
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(SOCKET_IP, SOCKET_PORT), 3000);
            //获取套接字的输入流，构建缓冲器
            mBufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //获取套接字的输出流
            mOutputStream = socket.getOutputStream();
            //开启接收服务端消息的
            new ReceiverThread().start();
            //准备接收来自UI线程的消息，初始化Loop函数
            Looper.prepare();
            //启动loop函数
            Looper.loop();

        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 定义一个发送处理器，向APP后端发送消息
     */
    public Handler mSendHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //服务网络的子线程
            //获取msg携带的消息
            final String message_send = msg.obj.toString();
            Log.i(TAG, "handleMessage: mSendHandler message_send = "+message_send);
            //
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mOutputStream.write(message_send.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    };

    /**
     * 定义子线程接收来自服务端的消息
     */
    private class ReceiverThread extends Thread{

        String content = "";

        @Override
        public void run() {
            super.run();
            Log.i(TAG, "run: ReceiverThread");
            //读取来自服务器的输出流
            try {
                while ((content = mBufferedReader.readLine()) != null){
                    // 将 读取的内容添加到消息队列
                    Message message = Message.obtain();
                    message.obj = content;
                    //发送消息到UI线程
                    SocketActivity.mHandler.sendMessage(message);
                }
            } catch (IOException e) {

                e.printStackTrace();

            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
