package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class WikiScraper {

    // Method to validate the Wikipedia link
    public static void validateWikipediaLink(String url) throws IllegalArgumentException {
        try {
            new URL(url).toURI();
            if (!url.contains("wikipedia.org/wiki/")) {
                throw new IllegalArgumentException("Invalid Wikipedia link.");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid URL format.");
        }
    }

    // Method to scrape and store wiki links using Selenium
    public static Set<String> scrapeWikiLinks(String url, int n) {
        System.setProperty("webdriver.chrome.driver", "C:/Users/prash/Downloads/chromedriver-win32/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        Set<String> visitedLinks = new HashSet<>();
        Queue<String> linkQueue = new LinkedList<>();
        Set<String> foundLinks = new LinkedHashSet<>();

        linkQueue.add(url);
        visitedLinks.add(url);

        while (!linkQueue.isEmpty() && n > 0) {
            String currentUrl = linkQueue.poll();
            try {
                driver.get(currentUrl);
                // Get all link elements
                List<WebElement> links = driver.findElements(By.tagName("a"));
                int count = 0;

                for (WebElement link : links) {
                    String absHref = link.getAttribute("href");
                    if (absHref != null && absHref.contains("wikipedia.org/wiki/") && !visitedLinks.contains(absHref)) {
                        foundLinks.add(absHref);
                        linkQueue.add(absHref);
                        visitedLinks.add(absHref);
                        count++;
                        if (count >= 10) break;  // Stop after 10 unique links
                    }
                }
            } catch (Exception e) {
                System.err.println("Error accessing URL: " + currentUrl);
            }
            n--;
        }
        driver.quit();
        return foundLinks;
    }

    // Method to write results to a JSON file
    public static void writeLinksToJson(Set<String> links, String fileName) {
        try (FileWriter file = new FileWriter(fileName)) {
            file.write("{\"total_count\": " + links.size() + ", \"links\": [\n");
            int count = 0;
            for (String link : links) {
                file.write("  \"" + link + "\"");
                count++;
                if (count < links.size()) {
                    file.write(",");
                }
                file.write("\n");
            }
            file.write("]}\n");
        } catch (IOException e) {
            System.err.println("Error writing to JSON file.");
        }
    }

    public static void main(String[] args) {
        // Example usage
        String url = "https://en.wikipedia.org/wiki/Web_scraping";
        int n = 2; // Set to 2 cycles

        try {
            validateWikipediaLink(url);
            Set<String> wikiLinks = scrapeWikiLinks(url, n);
            System.out.println("Total links found: " + wikiLinks.size());
            wikiLinks.forEach(System.out::println);
            writeLinksToJson(wikiLinks, "wikipedia_links.json");
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}
