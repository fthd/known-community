package com.known.common.utils;


import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import org.springframework.beans.factory.annotation.Value;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * @Author: 
 * @Description:
 * @Date: Create in 2018-05-26 19:48
 */
public class AliyunOSSClientUtil {

    //阿里云API的内或外网域名
    @Value("${ENDPOINT}")
    private static String ENDPOINT;
    //阿里云API的密钥Access Key ID
    @Value("${ACCESS_KEY_ID}")
    private static String ACCESS_KEY_ID;
    //阿里云API的密钥Access Key Secret
    @Value("${ACCESS_KEY_SECRET}")
    private static String ACCESS_KEY_SECRET;
    //阿里云API的bucket名称
    @Value("${BACKET_NAME}")
    private static String BACKET_NAME;
    //阿里云API的文件夹名称
    @Value("${FOLDER}")
    private static String FOLDER;


    /**
     * 获取阿里云OSS客户端对象
     * @return ossClient
     */
    public static OSSClient getOSSClient(){
        return new OSSClient(ENDPOINT,ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    }

    /**
     * 创建存储空间
     * @param ossClient      OSS连接
     * @param bucketName 存储空间
     * @return
     */
    public  static String createBucketName(OSSClient ossClient,String bucketName){
        //存储空间
        final String bucketNames=bucketName;
        if(!ossClient.doesBucketExist(bucketName)){
            //创建存储空间
            Bucket bucket=ossClient.createBucket(bucketName);
            return bucket.getName();
        }
        return bucketNames;
    }

    /**
     * 删除存储空间buckName
     * @param ossClient  oss对象
     * @param bucketName  存储空间
     */
    public static  void deleteBucket(OSSClient ossClient, String bucketName){
        ossClient.deleteBucket(bucketName);
    }

    /**
     * 创建模拟文件夹
     * @param ossClient oss连接
     * @param bucketName 存储空间
     * @param folder   模拟文件夹名如"qj_nanjing/"
     * @return  文件夹名
     */
    public  static String createFolder(OSSClient ossClient,String bucketName,String folder){
        //文件夹名
        final String keySuffixWithSlash =folder;
        //判断文件夹是否存在，不存在则创建
        if(!ossClient.doesObjectExist(bucketName, keySuffixWithSlash)){
            //创建文件夹
            ossClient.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
            //得到文件夹名
            OSSObject object = ossClient.getObject(bucketName, keySuffixWithSlash);
            String fileDir=object.getKey();
            return fileDir;
        }
        return keySuffixWithSlash;
    }

    /**
     * 根据key删除OSS服务器上的文件
     * @param ossClient  oss连接
     * @param folder  模拟文件夹名 如"qj_nanjing/"
     * @param key Bucket下的文件的路径名+文件名 如："upload/cake.jpg"
     */
    public static void deleteFile(OSSClient ossClient, String folder, String key){
        ossClient.deleteObject(BACKET_NAME, folder + key);
    }

    /**
     * 上传图片至OSS
     * @param ossClient  oss连接
     * @param file 上传文件（文件全路径如：D:\\image\\cake.jpg）
     * @param folder 模拟文件夹名 如"qj_nanjing/"
     * @return String 返回的唯一MD5数字签名
     * */
    public static  String uploadObject2OSS(OSSClient ossClient, InputStream is,String fileName,Long fileSize, String folder) {
        String resultStr = null;
        try {
            //以输入流的形式上传文件
           // InputStream is = new FileInputStream(fileInputStream);
            //文件名
          //  String fileName = file.getName();
            //文件大小
           // Long fileSize = file.length();
            //创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            //上传的文件的长度
            metadata.setContentLength(is.available());
            //指定该Object被下载时的网页的缓存行为
            metadata.setCacheControl("no-cache");
            //指定该Object下设置Header
            metadata.setHeader("Pragma", "no-cache");
            //指定该Object被下载时的内容编码格式
            metadata.setContentEncoding("utf-8");
            //文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
            //如果没有扩展名则填默认值application/octet-stream
            metadata.setContentType(getContentType(fileName));
            //指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
            //上传文件   (上传文件流的形式)
            PutObjectResult putResult = ossClient.putObject(BACKET_NAME, FOLDER+folder + fileName, is, metadata);
            //解析结果
            resultStr = putResult.getETag();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultStr;
    }
    public static  String uploadObject2OSS(OSSClient ossClient, File file,String folder) {
        String resultStr = null;
        try {
           // 以输入流的形式上传文件
            InputStream is = new FileInputStream(file);
         //   文件名
            String fileName = file.getName();
          //  文件大小
            Long fileSize = file.length();
            //创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            //上传的文件的长度
            metadata.setContentLength(is.available());
            //指定该Object被下载时的网页的缓存行为
            metadata.setCacheControl("no-cache");
            //指定该Object下设置Header
            metadata.setHeader("Pragma", "no-cache");
            //指定该Object被下载时的内容编码格式
            metadata.setContentEncoding("utf-8");
            //文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
            //如果没有扩展名则填默认值application/octet-stream
            metadata.setContentType(getContentType(fileName));
            //指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
            //上传文件   (上传文件流的形式)
            PutObjectResult putResult = ossClient.putObject(BACKET_NAME, FOLDER+folder + fileName, is, metadata);
            //解析结果
            resultStr = putResult.getETag();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultStr;
    }
    public static boolean doesObjectExist(OSSClient ossClient, String key){
        return ossClient.doesObjectExist(BACKET_NAME,FOLDER+key);
    }
    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     * @param fileName 文件名
     * @return 文件的contentType
     */
    public static  String getContentType(String fileName){
        //文件的后缀名
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        if(".bmp".equalsIgnoreCase(fileExtension)) {
            return "image/bmp";
        }
        if(".gif".equalsIgnoreCase(fileExtension)) {
            return "image/gif";
        }
        if(".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension)  || ".png".equalsIgnoreCase(fileExtension) ) {
            return "image/jpeg";
        }
        if(".html".equalsIgnoreCase(fileExtension)) {
            return "text/html";
        }
        if(".txt".equalsIgnoreCase(fileExtension)) {
            return "text/plain";
        }
        if(".vsd".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.visio";
        }
        if(".ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if(".doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {
            return "application/msword";
        }
        if(".xml".equalsIgnoreCase(fileExtension)) {
            return "text/xml";
        }

        if(".zip".equalsIgnoreCase(fileExtension)) {
            return "application/zip";
        }
        if(".rar".equalsIgnoreCase(fileExtension)) {
            return "application/rar";
        }
        if(".rmvb".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.rn-realmedia";
        }
        //默认返回类型
        return "image/jpeg";
    }


    /**
     * 获得url链接
     *
     * @param key
     * @return
     */
    public static String getUrl(OSSClient ossClient,String key) {
        // 设置URL过期时间为10年  3600l* 1000*24*365*10
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 20);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(BACKET_NAME, FOLDER+key, expiration);
        if (url != null) {
            return url.toString();
        }
        return null;
    }
    /**
     * 根据key获取OSS服务器上的文件输入流
     * @param client OSS客户端
     * @param diskName 文件路径
     * @param key Bucket下的文件的路径名+文件名
     */
    public static final InputStream getOSS2InputStream(OSSClient client,String path){
        OSSObject ossObj = client.getObject(BACKET_NAME, FOLDER + path);
        return ossObj.getObjectContent();
    }
    //测试
    public static void main(String[] args) {
        //初始化OSSClient
        OSSClient ossClient=AliyunOSSClientUtil.getOSSClient();

        //上传文件
        String files="C:\\Users\\Jerry\\Desktop\\wKhTlVkaMOOEWiKhAAAAACPwB8c044.jpg";
        String[] file=files.split(",");
       /* for(String filename:file){
            //System.out.println("filename:"+filename);
            File filess=new File(filename);
            String md5key = AliyunOSSClientUtil.uploadObject2OSS(ossClient, filess, BACKET_NAME, FOLDER+"test/");
            //上传后的文件MD5数字唯一签名:40F4131427068E08451D37F02021473A
            System.out.println(md5key);
            System.out.println(AliyunOSSClientUtil.getUrl(ossClient,"test/wKhTlVkaMOOEWiKhAAAAACPwB8c044.jpg"));
        }*/
       /* File filess=new File(file[0]);
        String md5key = AliyunOSSClientUtil.uploadObject2OSS(ossClient, filess, FOLDER+"test/");
        //上传后的文件MD5数字唯一签名:40F4131427068E08451D37F02021473A
        System.out.println(md5key);*/
        OSSObject t = ossClient.getObject(BACKET_NAME,FOLDER+"wKhTlVkaMOOEWiKhAAAAACPwB8c044.jpg");
       // t.getObjectContent();


        String m = AliyunOSSClientUtil.uploadObject2OSS(ossClient,t.getObjectContent(),"wKhTlVkaMOOEWiKhAAAAACPwB8c044.jpg",1000L,FOLDER+"user/");
        System.out.println(m);
       /* // 图片处理样式
        String style = "image/resize,m_fixed,w_100,h_100/rotate,90";
        // 过期时间10分钟
        Date expiration = new Date(new Date().getTime() + 1000 * 60 * 10 );

        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(BACKET_NAME, FOLDER+"user/wKhTlVkaMOOEWiKhAAAAACPwB8c044.jpg", HttpMethod.GET);
        req.setProcess(style);
        req.setExpiration(expiration);
        URL signedUrl = ossClient.generatePresignedUrl(req);
        System.out.println(signedUrl);*/
      //  System.out.println(AliyunOSSClientUtil.doesObjectExist(ossClient,"test/wKhTlVkaMOOEWiKhAAAAACPwB8c044.jpg"));
    }
}
