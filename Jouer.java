package player;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import java.io.*;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class Jouer implements Runnable{
    byte[] taille;

    public Jouer(byte[] taille) {
        this.taille=taille;
    }
    public void run() {
        try{
            play();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public  void play() throws Exception{
        DataInputStream in= new DataInputStream(new ByteArrayInputStream(this.taille));
        Player player = new Player(in);
        player.play();
    }
}