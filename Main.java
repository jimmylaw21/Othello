//import javazoom.jl.decoder.JavaLayerException;
import view.LoginFrame;
import view.Music;
import view.clickMusic;
//import view.Music;
//import view.rainMusic;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args)  {
        //音乐线程
//        Thread thread1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(true) {
//                    try {
//                        Thread.sleep(500);
//                        new Music();
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (JavaLayerException e) {
//                        e.printStackTrace();
//                    } catch (InterruptedException e) {
//                        return;
//                    }
//                }
//            }
//        });
//        thread1.start();
        LoginFrame.selectUserView();
        new Thread(new clickMusic("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\music\\Erika.wav")).start();


    }
}
