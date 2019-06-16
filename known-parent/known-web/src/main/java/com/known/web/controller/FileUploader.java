package com.known.web.controller;

import com.known.common.config.MailConfig;
import com.known.common.config.UrlConfig;
import com.known.common.enums.DateTimePatternEnum;
import com.known.common.enums.CodeEnum;
import com.known.common.utils.AbsolutePathUtil;
import com.known.common.utils.DateUtil;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("/fileUploader")
public class FileUploader {

    private static final int MAX_FILE_MAX = 10 * 1024 * 1024;


    @Resource
    private UrlConfig urlConfig;



    @RequestMapping("/fileUpload.action")
    public Map<String, Object> fileUpload(MultipartHttpServletRequest multirequest) {


        String realPath = AbsolutePathUtil.getAbsoluteStaticPath() + urlConfig.getFileUpload_Url();
        Map<String, Object> map = new HashMap<>();
        Iterator<String> itr = multirequest.getFileNames();
        if (itr.hasNext()) {
            MultipartFile multipartFile = multirequest.getFile(itr.next());
            long size = multipartFile.getSize();
            if (size > MAX_FILE_MAX) {
                map.put("code", CodeEnum.BUSSINESSERROR);
                map.put("msg", "文件不能超过10M");
                return map;
            }
            String fileName = multipartFile.getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (!"zip".equalsIgnoreCase(suffix) && !"rar".equalsIgnoreCase(suffix)) {
                map.put("code", CodeEnum.BUSSINESSERROR);
                map.put("msg", "只能ZIP或RAR文件");
                return map;
            }
            String current = String.valueOf(System.currentTimeMillis());
            fileName = current + "." + suffix;
            File dir = new File(realPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String filePath = realPath + "/"+fileName;
            File file = new File(filePath);
            try {
                multipartFile.transferTo(file);
                map.put("code", CodeEnum.SUCCESS);
                map.put("savePath", urlConfig.getFileUpload_Url()+"/"+fileName);
                System.out.println(map.get("savePath"));
                return map;
            } catch (Exception e) {
                map.put("code", CodeEnum.SERVERERROR);
                map.put("msg", "服务器异常,上传失败");
                return map;
            }
        }
        return map;
    }

    /**
     * 删除文件
     */
    @RequestMapping("/fileDelete.action")
    public Map<String, Object> fileDelete(String fileName) {
        Map<String, Object> map = new HashMap<>();
        File file = new File(AbsolutePathUtil.getAbsoluteStaticPath()+fileName);
        try {
            if (file.exists()) {
                file.delete();
            }
            map.put("code", CodeEnum.SUCCESS);
            return map;
        } catch (Exception e) {
            map.put("code", CodeEnum.SERVERERROR);
            map.put("msg", "服务器异常,删除失败");
            return map;
        }
    }


}
