package view;//package view;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Music {
    public Music() throws FileNotFoundException, JavaLayerException {
        Player player;
        String str = System.getProperty("user.dir")+"/music/music.wav";
        BufferedInputStream name = new BufferedInputStream(new FileInputStream("C:\\Users\\jimmylaw21\\Desktop\\Othello 6\\src\\music\\music.wav"));
        player = new Player(name);
        player.play();
    }
}
