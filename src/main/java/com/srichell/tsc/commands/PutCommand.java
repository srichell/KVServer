package com.srichell.tsc.commands;

import com.srichell.tsc.lru.LRU;
import com.srichell.tsc.lru.LRUEntry;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by sridhar on 6/8/17.
 */
public class PutCommand extends Command {
    String key;
    String value;
    private static final int KEY_INDEX = 1;
    private static final int VALUE_INDEX = 2;

    @Override
    public void handle() throws IOException {
        PrintStream os = new PrintStream(getClientSocket().getOutputStream());
        getLru().push(new LRUEntry(getKey(), getValue()));
        String outputString = String.format("SUCCESS PUT (%s, %s)\n", getKey(), getValue());

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
    protected String getValue() { return value; }

    @Override
    protected void setKey(String[] commandSegments) { this.key = commandSegments[KEY_INDEX]; }

    @Override
    protected void setValue(String[] commandSegments) { this.value = commandSegments[VALUE_INDEX]; }

    public PutCommand(String commandLine, Socket clientSocket, LRU lru) {
        super(commandLine, clientSocket, lru);
    }
}
