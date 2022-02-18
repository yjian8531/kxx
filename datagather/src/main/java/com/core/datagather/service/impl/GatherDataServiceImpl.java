package com.core.datagather.service.impl;

import com.core.datagather.entity.Statistics;
import com.core.datagather.service.GatherDataService;
import com.core.datagather.utils.FileUtil;
import com.core.datagather.utils.HttpRequest;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.tomcat.util.buf.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Service
public class GatherDataServiceImpl implements GatherDataService {

    @Autowired
    public Environment env;

    @Async
    public void gatherUserData(){
        Long date1 = new Date().getTime();
        //String url = "https://www.amazon.com/dethinton-Compatible-Braided-Charging-Transfer/dp/B08Y8MS9HS/?_encoding=UTF8&pf_rd_p=6fec76d7-8705-4663-9a39-957d22e4b035&pd_rd_wg=74GD4&pf_rd_r=540PN96RPS8QQE6091TF&pd_rd_w=PlpRe&pd_rd_r=b02e5535-fc2f-480b-b411-a5efcf1409e8&ref_=ci_mcx_mr_hp_atf_m";
        String url  = "https://www.amazon.com/-/zh/gp/profile/amzn1.account.AH2RHCVGJLZDSW6S6ZCUZRHRDLEA/ref=cm_cr_dp_d_gw_tr?ie=UTF8";
        String str = getHtml(url);
        if(str != null && str.length() > 0){
            Long gap = new Date().getTime() - date1;

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter.format(new Date());
            String fileUrl = env.getProperty("init.param.fileurl")+dateString;
            String conten = FileUtil.readFileByBytes(fileUrl);
            Statistics statistics;
            if(conten == null || conten.length() < 1){
                statistics = new Statistics();
                statistics.setNum(1L);
                statistics.setTime(gap);
            }else{
                JSONObject entity = JSONObject.fromObject(conten);
                statistics = (Statistics)JSONObject.toBean(entity, Statistics.class);
                statistics.setNum(statistics.getNum()+1L);
                statistics.setTime(statistics.getTime()+gap);
            }
            FileUtil.writeFile(fileUrl,JSONObject.fromObject(statistics).toString());
            log.info("[{}]单次请求访问耗时:{}毫秒",Thread.currentThread().getName(),gap);
        }
    }

    public synchronized String getHtml(String url){
        String htmlStr = "";
        try{
            htmlStr = Jsoup.connect(url).execute().body();
        }catch (Exception e){
            e.printStackTrace();
            try{
                Thread.sleep(1000);
            }catch (Exception ex){
                ex.printStackTrace();
            }

        }
        return htmlStr;
    }


    public static void main(String[] args)throws Exception{
        String url = "https://www.amazon.com/gp/profile/amzn1.account.AF34VCAOWKHXXVE6IEY3OHMVOGYA/ref=cm_cr_dp_d_gw_tr?ie=UTF8";
        String str = Jsoup.connect(url)
                .header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.82 Safari/537.36")
                .execute().body();//HttpRequest.sendGet(url)
        //Document document = Jsoup.connect(url).timeout(5000).get();
        //log.info(document.text());
        ///Elements elements = document.getElementsByClass("a-color-base");
        log.info(str);
    }



}
