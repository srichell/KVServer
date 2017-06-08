package com.srichell.tsc.commands;

import com.srichell.tsc.lru.LRU;
import com.srichell.tsc.lru.LRUEntry;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by sridhar on 6/8/17.
 */
public abstract class Command {
    private String commandType;

    private Socket clientSocket;
    private LRU<LRUEntry, String, String> lru;

    private static final int COMMAND_TYPE_INDEX = 0;
    protected static final String END_OF_RESPONSE = "EOR";


    public abstract void handle() throws IOException;
    protected abstract String getKey();
    protected abstract String getValue();
    protected abstract void setKey(String[] commandSegments);
    protected abstract void setValue(String[] commandSegments);

    public Command(String commandLine, Socket clientSocket, LRU<LRUEntry, String, String> lru) {
        // Client Command will always be in the format (space seperated)
        // Command key <optional value>
        String[] commandSegments = commandLine.split(" ");
        setCommandType(commandSegments[COMMAND_TYPE_INDEX]);
        setKey(commandSegments);
        setValue(commandSegments);
        setLru(lru);
        setClientSocket(clientSocket);
    }

    public static String getCommandType(String commandLine) {
        return commandLine.split(" ")[0];
    }

    public void setCommandType(String commandType) {
        this.commandType = commandType;
    }

    public LRU<LRUEntry, String, String> getLru() {
        return lru;
    }

    public void setLru(LRU<LRUEntry, String, String> lru) {
        this.lru = lru;
    }

    public void setClientSocket(Socket clintSocket) {
        this.clientSocket = clintSocket;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
}
