package com.core.datagather.utils;


import cn.edu.hfut.dmic.webcollector.example.DemoManualNewsCrawler;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;

import java.util.regex.Pattern;

public class ZhihuCrawler extends BreadthCrawler {

    /**
     * @param crawlPath crawlPath is the path of the directory which maintains
     *                  information of this crawler
     * @param autoParse if autoParse is true,BreadthCrawler will auto extract
     *                  links which match regex rules from pag
     */
    public ZhihuCrawler(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
        // add 5 start pages and set their type to "list"
        //"list" is not a reserved word, you can use other string instead
        this.addSeedAndReturn("https://www.amazon.com/gp/profile/amzn1.account.AFJRHR6CMJ47HJKBIAYX6BTNPT4Q/ref=cm_cr_dp_mb_gw_tr?ie=UTF8").type("list");
        /*for(int pageIndex = 2; pageIndex <= 5; pageIndex++) {
            String seedUrl = String.format("https://blog.github.com/page/%d/", pageIndex);
            this.addSeed(seedUrl, "list");
        }*/

        setThreads(50);
        getConf().setTopN(100);

        //enable resumable mode
        //setResumable(true);
    }

    @Override
    public void visit(Page page, CrawlDatums next) {
        String url = page.url();

        if (page.matchType("list")) {
            /*if type is "list"*/
            /*detect content page by css selector and mark their types as "content"*/
            next.add(page.links("h1.lh-condensed>a")).type("content");
        }else if(page.matchType("content")) {
            /*if type is "content"*/
            /*extract title and content of news by css selector*/
            String title = page.select("h1[class=lh-l]").first().text();
            String content = page.selectText("div.content.markdown-body");

            //read title_prefix and content_length_limit from configuration
            title = getConf().getString("title_prefix") + title;
            content = content.substring(0, getConf().getInteger("content_length_limit"));

            System.out.println("URL:\n" + url);
            System.out.println("title:\n" + title);
            System.out.println("content:\n" + content);
        }

    }

    public static void main(String[] args) throws Exception {
        ZhihuCrawler crawler = new ZhihuCrawler("crawl", false);

        crawler.getConf().setExecuteInterval(5000);
        crawler.getConf().set("title_prefix","PREFIX_");
        crawler.getConf().set("content_length_limit", 20);

        /*start crawl with depth of 4*/
        crawler.start(4);
    }
}