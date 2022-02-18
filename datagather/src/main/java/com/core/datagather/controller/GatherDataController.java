package com.core.datagather.controller;

import com.core.datagather.entity.Statistics;
import com.core.datagather.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/data")
public class GatherDataController {

    @Autowired
    public Environment env;

    /**
     * 获取有效的区域信息
     * @return
     */
    @GetMapping(value = "/test",produces = {"application/json"})
    public Map queryTerritoryList(){

        Map<String,Object> result = new HashMap<>();

        String path = env.getProperty("init.param.fileurl");
        List<File> fileList = FileUtil.getFiles(path);
        if(fileList == null){
            result.put("smg","统计文件路劲错误");
        }else{

            long num = 0;
            long time = 0;
            for(File file : fileList){
                String conten = FileUtil.getContent(file);
                JSONObject entity = JSONObject.fromObject(conten);
                Statistics statistics = (Statistics)JSONObject.toBean(entity, Statistics.class);
                num += statistics.getNum();
                time += statistics.getTime();
            }
            time = time / num;

            result.put("请求次数",num);
            result.put("平均耗时",time);
    }
        return result;
    }

}
