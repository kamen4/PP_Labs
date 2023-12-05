//java -classpath A:\PROJECTS\Idea\PP\PP_LAB_9\out\production\PP_LAB_9 App.Client.ClientMain console3

package App.Client;

import App.Models.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {
        if (args.length != 1 && args.length != 2) {
            System.err.println(	"Invalid number of arguments\nUsage: <Name> [host]" );
            waitKeyToStop();
            return;
        }
        try ( Socket sock = ( args.length == 1 ? new Socket( InetAddress.getLocalHost(), Protocol.PORT ) : new Socket( args[1], Protocol.PORT ) )) {
            System.err.println("initialized");
            session(sock, args[0]);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            System.err.println("bye...");
        }
    }

    static void waitKeyToStop() {
        System.err.println("Press a key to stop...");
        try {
            System.in.read();
        } catch (IOException ignored) {
        }
    }
    static class Session {
        boolean connected = false;
        String userName;
        Session( String name ) {
            userName = name;
        }
    }
    static void session(Socket socket, String name) {
        try (Scanner in = new Scanner(System.in);
             ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream())) {
            Session ses = new Session(name);
            if (openSession( ses, is, os, in )) {
                try {
                    MessageReceiveThread receiver = new MessageReceiveThread(ses, is);
                    receiver.start();
                    while (true) {
                        if(!process( ses, is, os, in ))
                            break;
                    }
                } finally {
                    closeSession(ses, os);
                }
            }
        } catch ( Exception e) {
            System.err.println(e.getMessage());
        }
    }
    static boolean process(Session ses, ObjectInputStream is, ObjectOutputStream os, Scanner in) throws IOException, ClassNotFoundException {
        if (!in.hasNextLine())
            return false;
        String line;
        while((line = in.nextLine()).trim().isEmpty());
        if (line.equals("/q") || line.equals("/quit"))
            return false;
        os.writeObject(new MessageLetter(line, ses.userName));
        return true;
    }
    static boolean openSession(Session ses, ObjectInputStream is, ObjectOutputStream os, Scanner in)
            throws IOException, ClassNotFoundException {
        os.writeObject(new MessageConnect(ses.userName));
        MessageConnectResult msg = (MessageConnectResult) is.readObject();
        if (!msg.Error()) {
            System.out.println("connected");
            System.out.println("type /q or /quit to disconnect");
            ses.connected = true;
            return true;
        }
        System.err.println("Unable to connect: "+ msg.getErrorMessage());
        System.err.println("Press <Enter> to continue...");
        if(in.hasNextLine())
            in.nextLine();
        return false;
    }
    static void closeSession(Session ses, ObjectOutputStream os) throws IOException {
        if ( ses.connected ) {
            ses.connected = false;
            os.writeObject(new MessageDisconnect());
        }
    }
}

class MessageReceiveThread extends Thread {
    final ClientMain.Session ses;
    final ObjectInputStream is;
    public MessageReceiveThread(ClientMain.Session ses, ObjectInputStream is)
    {
        this.ses = ses;
        this.is = is;
    }

    public void run() {
        while (ses.connected)
        {
            try {
                MessageLetter msg = (MessageLetter)is.readObject();
                if (msg != null)
                    System.out.println(msg.senderName + ": " + msg.txt);
            } catch (IOException | ClassNotFoundException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
