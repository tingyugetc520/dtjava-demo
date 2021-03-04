package com.github.tingyugetc520.dtjava.demo.handler;

import java.util.Map;

import com.github.tingyugetc520.ali.dingtalk.api.DtService;
import com.github.tingyugetc520.ali.dingtalk.bean.message.DtEventMessage;
import com.github.tingyugetc520.ali.dingtalk.bean.user.DtUser;
import com.github.tingyugetc520.ali.dingtalk.error.DtErrorException;
import com.github.tingyugetc520.ali.dingtalk.message.DtMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 通讯录用户添加事件处理
 */
@Component
@Slf4j
public class ContactUserAddHandler implements DtMessageHandler {

    @Override
    public Boolean handle(DtEventMessage message, Map<String, Object> context, DtService dtService) throws DtErrorException {
        log.info("contact user add:{}", message);

        String userId = (String) message.getAllFieldsMap().get("UserId");
        DtUser user = dtService.getUserService().getById(userId);
        log.info("user info:{}", user);
        return true;
    }

}
