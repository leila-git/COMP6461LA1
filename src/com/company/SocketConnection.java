package com.company;

import java.io.*;
import java.net.Socket;

public class SocketConnection {
    public static void execute(HttpObject httpObject) {
        if (httpObject.typeRequest == "POST") {
            executePost(httpObject);
        } else if (httpObject.typeRequest == "GET") {
            executeGet(httpObject);
        }
    }

    public static void executePost(HttpObject httpObject) {
        try {
            Socket socket = new Socket(httpObject.host, 80);
            PrintWriter wtr = new PrintWriter(socket.getOutputStream());
            wtr.println("");
            wtr.println("POST /post HTTP/1.1");
            wtr.println("Host: " + httpObject.host);
            if (httpObject.headers.size() > 0) {
                for (String temp : httpObject.headers) {
                    wtr.println(temp);
                }
            }


            if (httpObject.isInline) {
                wtr.println("Content-Length: " + httpObject.data.length());
                wtr.println("");
                wtr.print(httpObject.data);
            }
            if (httpObject.readFromFile) {
                String dataFromFile = readFromFile(httpObject.fileName);
                wtr.println("Content-Length: " + dataFromFile.length());
                wtr.println("");
                wtr.println(dataFromFile);
            }
            wtr.flush();


            printAndWriteToScreen(httpObject, socket);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFromFile(String filename) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }

    public static void executeGet(HttpObject httpObject) {
        try {
            Socket socket = new Socket(httpObject.host, 80);
            PrintWriter wtr = new PrintWriter(socket.getOutputStream());
            wtr.println("GET " + httpObject.urlParamaters + " HTTP/1.1");
            wtr.println("Host: " + httpObject.host);
            if (httpObject.headers.size() > 0) {
                for (String temp : httpObject.headers) {
                    wtr.println(temp);
                }
            }
            wtr.println("");
            wtr.flush();

            printAndWriteToScreen(httpObject, socket);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String[] performVerboseOperation(String str) throws Exception {
        int splitat = str.indexOf("{");
        if (splitat == -1) {
            throw new Exception("No body found");
        }
        String verbosePart = str.substring(0, splitat - 1);
        String body = str.substring(splitat);
        return new String[]{verbosePart, body};
    }

    public static void writeToFile(String filename, String data) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename));
            bufferedWriter.write(data);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void printAndWriteToScreen(HttpObject httpObject, Socket socket) throws IOException {
        //----Printing into Screen
        StringBuilder readerOutput = new StringBuilder();
        StringBuilder printToScreen = new StringBuilder();
        String readLine = "";
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        while ((readLine = bufferedReader.readLine()) != null) {
            readerOutput.append(readLine + System.lineSeparator());
        }

        if (!httpObject.isVerbose) {
            try {
                printToScreen.append(performVerboseOperation(readerOutput.toString())[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            printToScreen.append(readerOutput);
        }
        if (httpObject.writeTofile) {
            writeToFile(httpObject.fileName, printToScreen.toString());
        } else {
            System.out.println(printToScreen);
        }
    }

}
