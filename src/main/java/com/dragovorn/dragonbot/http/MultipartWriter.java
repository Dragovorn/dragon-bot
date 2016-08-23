package com.dragovorn.dragonbot.http;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.concurrent.ConcurrentHashMap;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 8:23 PM.
 * as of 8/22/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class MultipartWriter {

    private static final String EOL = "\r\n";

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private OutputStream outputStream;

    private PrintWriter writer;

    private RequestParams params;

    private String boundary;
    private String charset;

    private MultipartWriter(HttpURLConnection connection, RequestParams params) throws IOException {
        this.params = params;
        this.outputStream = connection.getOutputStream();
        this.boundary = "===" + System.currentTimeMillis() + "====";
        this.charset = params.getCharset().name();

        if (!charsetSupported(this.charset)) {
            this.charset = DEFAULT_CHARSET.name();
        }

        connection.setRequestProperty("Content-Type", "multipart/from-data; boundary=" + boundary);

        try {
            writer = new PrintWriter(new OutputStreamWriter(outputStream, charset));
        } catch (UnsupportedEncodingException exception) { /* Will always succeed. */ }
    }

    private static boolean charsetSupported(String name) {
        return Charset.availableCharsets().keySet().contains(name);
    }

    public static void write(HttpURLConnection connection, RequestParams params) throws IOException {
        MultipartWriter writer = new MultipartWriter(connection, params);
        writer.writeParts();
    }

    private void writeParts() throws IOException {
        for (ConcurrentHashMap.Entry<String, String> param : params.stringEntrySet()) {
            add(param.getKey(), param.getValue());
        }

        for (ConcurrentHashMap.Entry<String, File> param : params.fileEntrySet()) {
            add(param.getKey(), param.getValue());
        }

        writer.append(EOL).flush();
        writer.append("--").append(boundary).append("--").append(EOL);
        writer.close();
    }

    private void add(String name, String value) {
        writer.append("--").append(boundary).append(EOL);
        writer.append("Content-Disposition: form-data; name=\"").append(name).append("\";").append(EOL);
        writer.append("Content-Type: text/plain; charset=").append(charset).append(EOL);
        writer.append(EOL);
        writer.append(value).append(EOL);
        writer.flush();
    }

    private void add(String name, File file) throws IOException {
        String fileName = file.getName();

        writer.append("--").append(boundary).append(EOL);
        writer.append("Content-Disposition: form-data; name=\"").append(name).append("\"; filename=\"").append(fileName).append("\"").append(EOL);
        writer.append("Content-Type: ").append(URLConnection.guessContentTypeFromName(fileName)).append(EOL);
        writer.append("Content-Transfer-Encoding: binary").append(EOL);
        writer.append(EOL);
        writer.flush();

        Files.copy(file.toPath(), outputStream);
        outputStream.flush();

        writer.append(EOL);
        writer.flush();
    }
}