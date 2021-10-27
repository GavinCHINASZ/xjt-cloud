package com.xjt.cloud.task.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.message.manage.service.service.MessageService;
import com.xjt.cloud.task.core.dao.sys.UserDao;
import com.xjt.cloud.task.core.entity.task.Task;
import com.xjt.cloud.task.core.service.service.TaskSendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName TaskSendMessageServiceImpl
 * @Author dwt
 * @Date 2019-12-17 11:07
 * @Version 1.0
 */
@Service
public class TaskSendMessageServiceImpl extends AbstractService implements TaskSendMessageService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private MessageService messageService;

    /**
     * @Author: dwt
     * @Date: 2019-12-16 16:55
     * @Param: Task, int
     * @Return: void
     * @Description: 任务消息推送
     */
    @Override
    public void sendTaskMessage(Task task, int type) {
        //执行者类型 0：执行者，1：审核者
        List<Long> userIdList;
        Long projectId = task.getProjectId();
        //String projectName = taskDao.findProjectNameById(task.getProjectId());
        String projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId, "projectName");
        String content = "";
        String title = "";
        String url = "";
        Integer msgType = 2;
        int executorType = 0;
        //1:任务新增提醒，2:任务过期提醒，3:任务过期时间提醒，4:任务待审核提醒，5:任务驳回提醒，6:任务审核通过提醒
        switch (type) {
            case 1:
                content = "【" + projectName + "】您有新的工单【" + task.getTaskName() + "】，详情请查看您的任务。";
                title = "工作工单";
                break;
            case 2:
                content = "【" + projectName + "】 您好，" + projectName + "中，" + task.getTaskTypeDesc() +
                        "工单【" + task.getTaskName() + "】已过期。";
                title = "工单过期提醒";
                break;
            case 3:
                String dateStr;
                if (task.getRemindTime() != null && task.getRemindTime() >= 60) {
                    dateStr = DateUtils.getDayHourMin(task.getRemindTime());
                } else {
                    dateStr = task.getRemindTime() + "分钟";
                }
                if (StringUtils.isNotEmpty(dateStr)) {
                    content = "【" + projectName + "】 您好，" + projectName + "中，" + task.getTaskTypeDesc() +
                            "工单【" + task.getTaskName() + "】还有" + dateStr + "超时，请您及时处理。";
                    title = "过期时间提醒";
                }
                break;
            case 4:
                content = "【" + projectName + "】您有新的待审核工单【" + task.getTaskName() + "】，" +
                        "详情请查看您的待审核任务。";
                title = "任务审核";
                executorType = 1;
                break;
            case 5:
                content = "【" + projectName + "】" + projectName + "中，您的【" + task.getTaskName() + "】" +
                        "已被驳回，详情请查看您的任务。";
                title = "审核提醒";
                break;
            case 6:
                content = "【" + projectName + "】" + projectName + "中，您的【" + task.getTaskName() + "】" +
                        "已通过审核，详情请查看您的任务。";
                title = "审核提醒";
                break;
        }
        userIdList = userDao.findSendMessageUserIds(executorType, task.getId());
        if (StringUtils.isNotEmpty(content) && StringUtils.isNotEmpty(title) && userIdList != null && userIdList.size() > 0) {
            JSONObject json = new JSONObject();
            messageService.saveMessageUser(msgType, userIdList, title, type, 0, content, url,
                    projectId, task.getId(), null, json);
        }
    }

}
