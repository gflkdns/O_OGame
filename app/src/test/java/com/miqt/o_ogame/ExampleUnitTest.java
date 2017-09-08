package com.miqt.o_ogame;

import com.miqt.o_ogame.net.AUdpReceive;
import com.miqt.o_ogame.net.UdpSend;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
       new Thread(new Runnable() {
           @Override
           public void run() {
              while (true){
                  new UdpSend().send("{\"ip\":\"192.168.1.123\",\"name\":\"test-rk3399\",\"port\":\"test-rk3399\"}");
                  try {
                      Thread.sleep(2000);
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
              }
           }
       }).start();
        new AUdpReceive(){
            @Override
            public void onRecerver(String message) {
                System.out.println(message);
            }
        }.join();
    }

}
