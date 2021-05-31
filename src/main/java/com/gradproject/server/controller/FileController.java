package com.gradproject.server.controller;

import com.gradproject.server.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/file")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileService fileService;

    @GetMapping("/showImage/{id}")
    public void showImage(@PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response){
        logger.info("查看id：{}的图片.", id);
        try {
            fileService.showImage(id, request, response);
        } catch (Exception e){
            logger.error("图片显示异常，异常id:【{}】", id, e);
        }
    }
}
