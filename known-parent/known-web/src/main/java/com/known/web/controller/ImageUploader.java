package com.known.web.controller;

import com.known.common.config.UrlConfig;
import com.known.common.enums.CodeEnum;
import com.known.common.utils.AbsolutePathUtil;
import com.known.common.vo.UeditorResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("/imageUploader")
public class ImageUploader {

    private static final int MAX_FILE_MAX = 3 * 1024 * 1024;

    @Resource
    private UrlConfig urlConfig;

    @RequestMapping("/imageUpload.action")
    public Map<String, Object> imageUpload(MultipartHttpServletRequest multirequest) {

        String realPath = AbsolutePathUtil.getAbsoluteStaticPath() + urlConfig.getImgUpload_Url();
        Map<String, Object> map = new HashMap<>();
        Iterator<String> itr = multirequest.getFileNames();
        if (itr.hasNext()) {
            MultipartFile multipartFile = multirequest.getFile(itr.next());
            long size = multipartFile.getSize();
            if (size > MAX_FILE_MAX) {
                map.put("code", CodeEnum.BUSSINESSERROR);
                map.put("msg", "文件不能超过3M");
                return map;
            }
            String fileName = multipartFile.getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (!"JPG".equalsIgnoreCase(suffix) && !"BMP".equalsIgnoreCase(suffix) &&
                    !"gif".equalsIgnoreCase(suffix) && !"PNG".equalsIgnoreCase(suffix)) {
                map.put("code", CodeEnum.BUSSINESSERROR);
                map.put("msg", "只能上传图片");
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
                map.put("savePath", urlConfig.getImgUpload_Url()+fileName);
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


    @RequestMapping("/ueditorImageUpload.action")
    public UeditorResponse ueditorImageUpload( MultipartHttpServletRequest multirequest) {
        String realPath = AbsolutePathUtil.getAbsoluteStaticPath() + urlConfig.getImgUpload_Url();
        UeditorResponse ueditorResponse = new UeditorResponse();
        Iterator<String> itr = multirequest.getFileNames();
        if (itr.hasNext()) {
            MultipartFile multipartFile = multirequest.getFile(itr.next());
            long size = multipartFile.getSize();
            if (size > MAX_FILE_MAX) {
                ueditorResponse.setState("图片过大");
                return ueditorResponse;
            }
            String fileName = multipartFile.getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (!"JPG".equalsIgnoreCase(suffix) && !"BMP".equalsIgnoreCase(suffix) &&
                    !"gif".equalsIgnoreCase(suffix) && !"PNG".equalsIgnoreCase(suffix)) {
                ueditorResponse.setState("只能是图片");
                return ueditorResponse;
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
                ueditorResponse.setState("SUCCESS");
                return ueditorResponse;
            } catch (Exception e) {
                ueditorResponse.setState("上传参数出错");
                e.printStackTrace();
                return ueditorResponse;
            }
        }
        return ueditorResponse;
    }
}
