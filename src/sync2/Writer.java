/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sync2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class Writer implements Runnable {

    private Thread thread;
    boolean run = true;
    boolean suspendFlag;
    String currentRow;
    Reader reader;
    String m_filename;
    int m_numLine = 0;
    Writer m_otherWriter;

    public Writer(Reader _reader, String a_filename, Writer a_otherWriter) {
        super();
        m_filename = a_filename;
        reader = _reader;
        thread = new Thread(this);
        suspendFlag = false;
        m_otherWriter = a_otherWriter;

    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    public void resumeThread() {
        suspendFlag = false;
    }

    public void suspendThread() {
        suspendFlag = true;
    }

    public int GetNumLines() {
        return m_numLine;
    }

    @Override
    public void run() {
        File file = new File(m_filename);
        String lastString = null;
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (reader.isRun() || reader.list.size() != 0) {

            try {
                if (bw != null) {
                    String currentString = (String) reader.list.peek();
                    if (currentString != null && currentString != lastString) {
                        bw.write(currentString);
                        lastString = currentString;
                        bw.flush();
                        bw.write("\n");
                        bw.flush();
                        m_numLine++;

                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (m_otherWriter != null && m_otherWriter.GetNumLines() == m_numLine) {
                reader.list.poll();
            }

        }

    }

}
