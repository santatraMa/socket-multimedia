package serveur;

import java.io.*;
import java.net.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.BorderLayout;
import java.awt.*;
import java.awt.Image.*;
import java.awt.Image;
import javax.swing.*;

public class Serveur {
    private static Socket s = null;

    public Serveur() throws Exception{
        ServerSocket serverSocket = new ServerSocket(6000);
            s = serverSocket.accept();
            System.out.println("Client connecte");

            ObjectInputStream objinp = new ObjectInputStream(s.getInputStream());
            String input = (String) objinp.readObject();
            System.out.println(input);

            if(input.equals("image")) {
                ObjectOutputStream objout = new ObjectOutputStream(s.getOutputStream());
                objout.writeObject("image");
                this.getServeur(s);
            }

            if(input.equals("music")){
                ObjectOutputStream objout=new ObjectOutputStream(s.getOutputStream());
                objout.writeObject("music");
                this.songServeur(s);
            }

    }

    public void getServeur(Socket s) throws IOException,ClassNotFoundException,InterruptedException {
        OutputStream outputStream = s.getOutputStream();
        ImageIcon image = new ImageIcon("D:/mp3 socket/image/IMG_6443.jpg");
        BufferedOutputStream bops = new BufferedOutputStream(outputStream);
        Image img = image.getImage();
        BufferedImage bi = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bi.createGraphics();
        graphics.drawImage(img,0,0,null);
        graphics.dispose();

        ImageIO.write(bi,"jpg",bops);
        bops.close();
    }

    public void songServeur(Socket s) throws IOException,ClassNotFoundException,InterruptedException{
        DataOutputStream out = new DataOutputStream(s.getOutputStream());
        File fichierMp3 = new File("../../fichier-mp3/Amir_-_On_dirait_(Clip_officiel)(128k).mp3");
        out.writeUTF(fichierMp3.getName().toLowerCase());
        while(true) {
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            String demande =(String) ois.readObject();
            if(demande.contains(".mp3")) {
                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                oos.writeObject(demande);
                //Envoiyer le music
                FileInputStream inputStream = new FileInputStream(fichierMp3);
                byte[] mybytearray = inputStream.readAllBytes();
                while (true) {
                    out.writeUTF(fichierMp3.getName().toLowerCase());
                    out.write(mybytearray);
                }
            }
        }
    }
    
}