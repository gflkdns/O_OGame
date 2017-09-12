package com.miqt.o_ogame;

import android.os.Build;

import com.google.gson.Gson;
import com.miqt.miwrenchlib.MNetUtils;
import com.miqt.o_ogame.entity.Data;
import com.miqt.o_ogame.entity.Device;
import com.miqt.o_ogame.net.AUdpReceive;
import com.miqt.o_ogame.net.UdpSend;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UdpTest {
    @Test
    public void addition_isCorrect() throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String name = "test——2";
                    String ip = "10.2.2.3";
                    String port = cfg.INSTANCE.getTcp_port() + "";
                    Device dev = new Device(name, ip, port);
                    Data data = new Data<>(Data.Companion.getTYPE_DEVICE_INFO(), dev);
                    String str = new Gson().toJson(data);
                    new UdpSend().send(str);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        new AUdpReceive() {
            @Override
            public void onRecerver(String message) {
                System.out.println(message);
            }
        }.join();
    }

}
