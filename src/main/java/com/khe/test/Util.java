package com.khe.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedHashSet;

import static com.khe.test.Validation.validUrl;

/**
 * @author Maksym Kheilyk
 */
public final class Util {

    private Util() {
    }

    /**
     * This method loads a page specified in the url and selects from it all links
     *
     * @param searchUrl url to the page on which to search for links
     * @return a list of unique links
     */
    public static LinkedHashSet<String> searchLinks(String searchUrl) {
        LinkedHashSet<String> uniqueLinks = new LinkedHashSet();
        Document doc;
        try {
            doc = Jsoup.connect(searchUrl).get();
            Elements links = doc.select("a");
            for(Element url : links){
                if (validUrl(url.attr("href"))) {
                    uniqueLinks.add(url.attr("href"));
                }
            }
        } catch (IOException e) {
        }
        return uniqueLinks;
    }

    /**
     * This method loads a page specified in the url and stores it on disk
     *
     * @param url the address of the page you want to download
     * @param localFilename path specifies where the page will be saved
     * @throws IOException
     */
    public static void downloadUrl(URL url, String localFilename) throws IOException {
        InputStream is = null;
        FileOutputStream fos = null;

        try {
            URLConnection urlConnection = url.openConnection();

            is = urlConnection.getInputStream();
            fos = new FileOutputStream(localFilename);

            byte[] buffer = new byte[4096];
            int len;

            while ((len = is.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } finally {
                if (fos != null) {
                    fos.close();
                }
            }
        }
    }
}