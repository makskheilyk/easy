package com.khe.test;

import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import static com.khe.test.Util.downloadUrl;

/**
 * @author Maksym Kheilyk
 */
public class Loader {

    private final static Logger LOG = Logger.getLogger(Loader.class);

    public static void load(int countPage, String url) {

        LinkedHashSet<String> links = Util.searchLinks(url);
        if (links.size() < countPage) {
            LOG.error("Number of links on a site less than requested. Number of links: " + links.size());
            return;
        }
        Iterator<String> iteratorLinks = links.iterator();

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(countPage);

        List<Future<String>> results = new ArrayList<>();

        for (int i = 0; i < countPage; i++)
        {
            Page page = new Loader.Page(iteratorLinks.next());
            Future<String> result = executor.submit(page);
            results.add(result);
        }

        int count = 0;
        for(Future<String> future : results)
        {
            try {
                LOG.info("Link saved as: " + future.get());
                if (future.isDone()) {
                    LOG.info("The current number of downloaded pages: " + ++count);
                }
            } catch (ExecutionException e) {
                LOG.error("Exception thrown when attempting to retrieve the result of a task.");
            } catch (InterruptedException e) {
                LOG.error("Thread is waiting, sleeping, or otherwise occupied.");
            }
        }
        executor.shutdown();
    }

    public static class Page implements Callable<String> {

        private String url;

        public Page(String url) {
            this.url = url;
        }

        @Override
        public String call() throws Exception {

            URL urls = new URL(url);
            String localFilename = "pages/" + UUID.randomUUID() + ".html";
            downloadUrl(urls, localFilename);

            return localFilename;
        }
    }
}