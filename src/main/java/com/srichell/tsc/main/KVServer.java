package com.srichell.tsc.main;

import com.srichell.tsc.lru.LRU;
import com.srichell.tsc.lru.LRUEntry;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by sridhar on 6/8/17.
 */
public class KVServer {
    private static final long DEFAULT_MAX_ENTRIES = 10;
    private static final int DEFAULT_LISTEN_PORT = 7000;

    private int listenPort;
    private long maxEntries;
    ServerSocket serverSocket;
    LRU<LRUEntry, String, String> lru;

    public static long getDefaultMaxEntries() {
        return DEFAULT_MAX_ENTRIES;
    }

    public static int getDefaultListenPort() {
        return DEFAULT_LISTEN_PORT;
    }

    public KVServer setListenPort(int listenPort) {
        this.listenPort = listenPort;
        return this;
    }

    public int getListenPort() {
        return listenPort;
    }

    public long getMaxEntries() {
        return maxEntries;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public LRU<LRUEntry, String, String> getLru() {
        return lru;
    }

    public KVServer setMaxEntries(long maxEntries) {
        this.maxEntries = maxEntries;
        return this;
    }

    private static void printUsage() {
        System.out.println("Usage :");
        System.out.println("\t KVServer [-h | --help] | [-n |--maxEntries] <maxEntries> [-p | --port] <listenPort>");
        System.exit(0);
    }

    public static void main(String[] args) {
        int listenPort = getDefaultListenPort();
        long maxEntries = getDefaultMaxEntries();

        for(int i = 0; i < args.length; i++) {
            if(args[i].equalsIgnoreCase("-h") || args[i].equalsIgnoreCase("--help")) {
                printUsage();
            }

            if(args[i].equalsIgnoreCase("-n") || args[i].equalsIgnoreCase("--maxEntries")) {
                maxEntries = Long.parseLong(args[i+1]);
            }

            if(args[i].equalsIgnoreCase("-p") || args[i].equalsIgnoreCase("--port")) {
                listenPort = Integer.parseInt(args[i+1]);
            }
        }

        try {
            KVServer server = new KVServer().
                    setListenPort(listenPort).
                    setMaxEntries(maxEntries).
                    init();
            server.run();
        } catch (IOException e) {
            System.out.println("Exception Occurred During Initilization" + e); // exceptions occurring in run() are handled there
            System.exit(-1);
        }
    }

    public void run() {
        Socket clientSocket = null;
        while(true) {
            try {
                clientSocket = getServerSocket().accept();
                DataInputStream is = new DataInputStream(clientSocket.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String commandLine = null;
                // One Line, one command
                while ((commandLine = reader.readLine()) != null) {

                    String[] commandSegments = commandLine.split(" ");
                    if(commandSegments[0].equalsIgnoreCase("GET")) {

                    }
                    if(commandSegments[0].equalsIgnoreCase("PUT")) {

                    }
                    System.out.println("Unknown Command. Ignoring");
                }
                is.close();
                reader.close();
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Exception occurred while handling request from client " + e);
            }
        }
    }

    private KVServer init() throws IOException {
        serverSocket = new ServerSocket(getListenPort());
        lru = new LRU<LRUEntry, String, String>(maxEntries);
        return this;
    }
}
