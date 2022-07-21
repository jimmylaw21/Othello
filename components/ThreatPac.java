package components;

import javazoom.jl.decoder.JavaLayerException;

import java.io.FileNotFoundException;

public class ThreatPac implements Runnable{

    Thread thread = new Thread(Thread.currentThread());


    public ThreatPac(){
        thread.start();
    }


    @Override
    public void run() {
//        while (true) {
//            if (ChessGridComponent.isClick){
//                try {
//                    new clickMusic();
//                    System.out.println("有音效");
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (JavaLayerException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }
}
