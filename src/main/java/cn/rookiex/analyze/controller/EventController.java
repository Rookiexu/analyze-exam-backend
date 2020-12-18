package cn.rookiex.analyze.controller;

import cn.rookiex.analyze.constants.MessageErrCode;
import cn.rookiex.analyze.entity.Event;
import cn.rookiex.analyze.service.EventService;
import cn.rookiex.analyze.service.ResultService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author rookiex
 * @date 2020/12/16 16:31
 * @des
 */
@Slf4j
@Controller
@RequestMapping
public class EventController {


    @Autowired
    private ResultService resultService;

    @Autowired
    private EventService eventService;

    @RequestMapping(path = "/events")
    @ResponseBody
    public String findAllEvent() {
        List<Event> eventList = eventService.findAllEvent();
        return resultService.getResult(eventList);
    }

    @RequestMapping(path = "/event/delete/{id}")
    @ResponseBody
    public String delete(@PathVariable String id) {
        eventService.deleteEvent(Integer.parseInt(id));
        return resultService.getResult();
    }
    @RequestMapping(path = "/event/save/{id}")
    @ResponseBody
    public String save(@PathVariable String id, @RequestBody Event event) {
        if (event == null || event.getTitle() == null) {
            return resultService.getErrResult(MessageErrCode.EVENT_NOT_COMPLETE, "待办事件标题未设置");
        }

        if (event.getDescData() == null) {
            return resultService.getErrResult(MessageErrCode.EVENT_NOT_COMPLETE, "待办事件说明未设置");
        }

        if (event.getType() == 0) {
            return resultService.getErrResult(MessageErrCode.EVENT_NOT_COMPLETE, "待办事件类型未设置");
        }
        if (event.getStartDate() == null) {
            return resultService.getErrResult(MessageErrCode.EVENT_NOT_COMPLETE, "待办事件开始时间数据未设置");
        }
        if (event.getEndDate() == null) {
            return resultService.getErrResult(MessageErrCode.EVENT_NOT_COMPLETE, "待办事件结束时间数据未设置");
        }

        int idValue = 0;
        if ("0".equals(id)) {
            idValue = eventService.create(event);
        } else {
            idValue = eventService.updateEvent(id, event);
        }
        return resultService.getResult(idValue);
    }
}
