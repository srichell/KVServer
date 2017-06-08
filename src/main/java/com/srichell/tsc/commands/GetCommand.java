package com.srichell.tsc.commands;

import com.srichell.tsc.lru.LRU;
import com.srichell.tsc.lru.LRUEntry;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by sridhar on 6/8/17.
 */
public class GetCommand extends Command {
    private String key;
    private static final int KEY_INDEX = 1;

    @Override
    public void handle() throws IOException {
        PrintStream os = new PrintStream(getClientSocket().getOutputStream());
        String value = getLru().lookup(getKey());
        String outputString = (value == null) ?
                String.format("FAILED. Key (%s) not found\n", getKey()) :
                String.format("SUCCESS GET (%s) = %s\n", getKey(), value);

        os.println(outputString);
        os.flush();

        //Add Sentinel
        os.println(END_OF_RESPONSE);
        os.flush();
    }

    @Override
    protected String getKey() {
        return key;
    }

    @Override
    protected String getValue() { return null; }

    @Override
    protected void setKey(String[] commandSegments) { this.key = commandSegments[KEY_INDEX]; }

    @Override
    protected void setValue(String[] commandSegments) { /* Should not get here*/ }

    public GetCommand(String commandLine, Socket clientSocket, LRU lru) {
        super(commandLine, clientSocket, lru);
    }
}
