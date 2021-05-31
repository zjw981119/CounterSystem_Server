package com.gradproject.server.service;

import com.gradproject.server.entity.SelfCounter;
import com.gradproject.server.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Base64;
import java.util.Date;

@Service
public class FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    @Autowired
    private SelfCounterService counterService;

    private String filePath = "D:\\Logs\\Counter\\";

    /**
     * 将传入的数据写入到本地磁盘中
     *
     * @param picString
     * @param carNo
     * @return 本地地址
     */
    public String savePicture(String picString, String carNo) {
        String writerAddr = filePath + DateUtils.getFormatDate(new Date(), DateUtils.FORMAT_SHORT) + "\\" +
                carNo + DateUtils.getFormatDate(new Date(), DateUtils.FORMAT_FULLY) + ".txt";
        logger.info("图片保存地址为：【{}】", writerAddr);
        File writerFile = new File(writerAddr);
        //判断文件是否存在，如果存在则不创建，不存在则创建文件，保证文件一定存在
        // 如果路径不存在，创建路径
        if (!writerFile.getParentFile().exists()) {
            writerFile.getParentFile().mkdirs();
        }
        //创建文件
        try {
            writerFile.createNewFile();
        } catch (IOException e) {
            logger.error("文件创建失败!");
        }
        //加true，表示再原来的基础上追加内容，默认false即每次读入就把之前的内容清空再读入。
        //UTF-8是编码方式
        try (BufferedWriter br = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(writerFile, false), "UTF-8"))) {
            //获取每行的读入内容
            br.write(picString);
            br.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return writerAddr;
    }

    /**
     * 根据路径返回图片的base64编码
     *
     * @param id
     */
    public void showImage(Integer id, HttpServletRequest request, HttpServletResponse response){

        //获取数据库中对应id的记录中的picture列的数据，为保存图片base64编码文本的地址
        String picPath = counterService.findCounterPicById(id);
        try (OutputStream outputStream = response.getOutputStream()){
            Path path = Paths.get(picPath);
            //文件不存在，则结束
            if(!Files.exists(path)){
                return;
            }
            //将txt文件读取为字节数组
            byte[] data = Files.readAllBytes(path);
            //将base64转为String
            String base64Str = new String(data);
            //替换base64格式头部
            base64Str = base64Str.replace("data:image/jpg;base64,", "");
            //解码，将base64转为图像
            data = Base64.getDecoder().decode(base64Str);
            //将图像输出到客户端
            ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();
            byteArrayInputStream.write(data);
            byteArrayInputStream.writeTo(outputStream);
            //outputStream.write(data);


        } catch (IOException e) {
            logger.error("图片显示失败，异常信息为：【{}】", e.getMessage(), e);
        }
    }
}
