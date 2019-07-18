package com.dragovorn.dragonbot.updater;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.swing.JProgressBar;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Downloader {

    private final String url;

    public Downloader(String url) {
        this.url = url;
    }

    public void download(File file, JProgressBar bar) throws IOException {
        if (file.delete()) {
            if (!file.createNewFile()) {
                return;
            }
        }

        HttpClient client = HttpClientBuilder.create().build();

        HttpGet request = new HttpGet(this.url);
        HttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            long size = entity.getContentLength();

            BufferedInputStream bufferedInputStream = new BufferedInputStream(entity.getContent());
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));

            int inByte;
            int sum = 0;

            while ((inByte = bufferedInputStream.read()) != -1) {
                bufferedOutputStream.write(inByte);

                sum += inByte;

                bar.setValue(sum / (int) size * 100);
            }

            bufferedInputStream.close();
            bufferedOutputStream.close();
        } else {
            throw new NullPointerException("Failed to retrieve entity data!");
        }
    }
}
