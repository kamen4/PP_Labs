package App.Server;

import App.Models.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.TreeMap;

public class ServerMain {

    private static final int MAX_USERS = 100;

    public static void main(String[] args) {

        try ( ServerSocket serv = new ServerSocket( Protocol.PORT  )) {
            System.err.println("initialized");
            ServerStopThread tester = new ServerStopThread();
            tester.start();
            do {
                Socket socket = accept(serv);
                if (socket != null) {
                    if (ServerMain.getNumUsers() < ServerMain.MAX_USERS) {
                        System.err.println(socket.getInetAddress().getHostName() + " connected");
                        ServerThread server = new ServerThread(socket);
                        server.start();
                    } else {
                        System.err.println(socket.getInetAddress().getHostName() + " connection rejected");
                        socket.close();
                    }
                }
            } while (!ServerMain.getStopFlag());

        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            stopAllUsers();
            System.err.println("stopped");
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }

    public static Socket accept( ServerSocket serv ) {
        assert( serv != null );
        try {
            serv.setSoTimeout( 1000 );
            return serv.accept();
        } catch (IOException ignored) {
        }
        return null;
    }

    private static void stopAllUsers() {
        String[] nic = getUsers();
        for (String user : nic ) {
            ServerThread ut = getUser( user );
            if ( ut != null ) {
                ut.disconnect();
            }
        }
    }

    private static final Object syncFlags = new Object();
    private static boolean stopFlag = false;
    public static boolean getStopFlag() {
        synchronized ( ServerMain.syncFlags ) {
            return stopFlag;
        }
    }
    public static void setStopFlag( boolean value ) {
        synchronized ( ServerMain.syncFlags ) {
            stopFlag = value;
        }
    }

    private static final Object syncUsers = new Object();
    private static final TreeMap<String, ServerThread> users = new TreeMap<>();
    public static ServerThread getUser( String userName ) {
        synchronized (ServerMain.syncUsers) {
            return ServerMain.users.get( userName );
        }
    }
    public static ServerThread registerUser( String userName, ServerThread user ) {
        synchronized (ServerMain.syncUsers) {
            return ServerMain.users.putIfAbsent(userName, user);
        }
    }
    public static void setUser(String userName, ServerThread user ) {
        synchronized (ServerMain.syncUsers) {
            ServerMain.users.put(userName, user);
            if ( user == null ) {
                ServerMain.users.remove(userName);
            }
        }
    }
    public static String[] getUsers() {
        synchronized (ServerMain.syncUsers) {
            return ServerMain.users.keySet().toArray( new String[0] );
        }
    }

    public static int getNumUsers() {
        synchronized (ServerMain.syncUsers) {
            return ServerMain.users.keySet().size();
        }
    }
}


class ServerStopThread extends Thread {

    Scanner fin;

    public ServerStopThread() {
        fin = new Scanner( System.in );
        ServerMain.setStopFlag( false );
    }
    public void run() {
        while (true) {
            try {
                Thread.sleep( 1000 );
            } catch (InterruptedException e) {
                break;
            }
            if (!fin.hasNextLine())
                continue;
            String str = fin.nextLine();
            if (str.equals("q") || str.equals("quit")) {
                ServerMain.setStopFlag( true );
                break;
            }
        }
    }
}

class ServerThread extends Thread {

    private final Socket              sock;
    private final ObjectOutputStream 	os;
    private final ObjectInputStream 	is;
    private final InetAddress 		  addr;

    private String userName;

    public ServerThread(Socket s) throws IOException {
        sock = s;
        s.setSoTimeout(500);
        os = new ObjectOutputStream( s.getOutputStream() );
        is = new ObjectInputStream( s.getInputStream());
        addr = s.getInetAddress();
        this.setDaemon(true);
    }

    public void run() {
        try {
            while ( true ) {
                Message msg = null;
                try {
                    msg = ( Message ) is.readObject();
                } catch (IOException | ClassNotFoundException ignored) {
                }
                if (msg != null)
                    switch ( msg.getID() ) {
                    case Protocol.CMD_CONNECT:
                        if ( !connect( (MessageConnect) msg ))
                            return;
                        break;
                    case Protocol.CMD_DISCONNECT:
                        return;
                    case Protocol.CMD_LETTER:
                        letter((MessageLetter) msg );
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("Disconnect...");
        } finally {
            disconnect();
        }
    }

    boolean connect( MessageConnect msg ) throws IOException {
        ServerThread old = register( msg.userName );
        if ( old == null )
        {
            os.writeObject(new MessageConnectResult());
            letter(new MessageLetter(msg.userName + " connected", "server"));
            return true;
        } else {
            os.writeObject(new MessageConnectResult("User " + old.userName + " already connected" ));
            return false;
        }
    }

    void letter( MessageLetter msg ) throws IOException {
        for (String name : ServerMain.getUsers())
        {
            if (msg.senderName.equals(name))
                continue;
            ServerMain.getUser(name).os.writeObject(new MessageLetter(msg.txt, msg.senderName));
            System.out.println("Sended to ("+ name +"): " + msg.senderName + ": " + msg.txt);
        }
    }

    private boolean disconnected = false;
    public void disconnect() {
        if ( !disconnected )
            try {
                if (userName != null)
                    letter(new MessageLetter(userName + " disconnected", "server"));
                System.err.println( addr.getHostName() + " disconnected" );
                unregister();
                os.close();
                is.close();
                sock.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                this.interrupt();
                disconnected = true;
            }
    }

    private void unregister() {
        if ( userName != null ) {
            ServerMain.setUser( userName, null );
            userName = null;
        }
    }

    private ServerThread register( String name ) {
        ServerThread old = ServerMain.registerUser( name, this );
        if ( old == null ) {
            if ( name != null ) {
                userName = name;
                System.err.println("User '" + name + "' registered");
            }
        }
        return old;
    }
}

