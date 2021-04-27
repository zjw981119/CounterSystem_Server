package com.gradproject.server.service;

import com.gradproject.server.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
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
}
