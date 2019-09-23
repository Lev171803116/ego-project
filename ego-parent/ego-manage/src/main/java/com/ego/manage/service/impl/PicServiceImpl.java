package com.ego.manage.service.impl;

import com.ego.commons.utils.FtpUtil;
import com.ego.manage.service.PicService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@PropertySource(value = "classpath:commons.properties")
public class PicServiceImpl implements PicService {

    @Value("${ftpclient.host}")
    private String host;

    @Value("${ftpclient.port}")
    private int  port;

    @Value("${ftpclient.username}")
    private String userName;

    @Value("${ftpclient.password}")
    private String passWord;

    @Value("${ftpclient.basePath}")
    private String basePath;

    @Value("${ftpclient.filePath}")
    private String filePath;
    @Override
    public Map<String, Object> upload(MultipartFile file) {
        Map<String,Object> map =new HashMap<>();
        String oldName = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString()+oldName.substring(oldName.lastIndexOf("."));
        boolean result = false;
        try {
            result = FtpUtil.uploadFile(host,port,userName,passWord,basePath,filePath,fileName,file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(result){
                map.put("error",0);
                map.put("url","http://"+host+filePath+fileName);
            }else{
                map.put("error",1);
                map.put("msg","图片上传失败");
            }
        }
        return map;
    }
}
