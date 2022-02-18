package com.core.datagather.job;

import com.core.datagather.service.GatherDataService;
import com.core.datagather.utils.HttpRequest;
import com.sleepycat.utilint.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;


@Slf4j
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class BaseTask {

    @Autowired
    private GatherDataService gatherDataService;

    /**
     * 测试
     */
    @Scheduled(cron = "0/1 * * * * ? ")
    public void tickerSvm() {

        //for(int i=0 ; i < 6 ; i++){
            gatherDataService.gatherUserData();
        //}

    }

}
