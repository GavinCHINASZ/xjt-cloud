package com.xjt.cloud.task.web.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.task.core.service.service.ExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ExecutorController
 * @Author dwt
 * @Date 2019-10-12 16:08
 * @Version 1.0
 */
@RestController
@RequestMapping("/executor/")
public class ExecutorController extends AbstractController {

    @Autowired
    private ExecutorService executorService;

    @RequestMapping(value = "findExecutorList/{projectId}")
    public Data findExecutorList(String json){
        System.out.println("!!!!!!!!!!!!!!!!");
        return executorService.findExecutorList(json);
    }


}
