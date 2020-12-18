package cn.rookiex.analyze.service;

import cn.rookiex.analyze.dao.EventRepository;
import cn.rookiex.analyze.dao.UserRepository;
import cn.rookiex.analyze.entity.Event;
import cn.rookiex.analyze.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author rookiex
 * @date 2020/12/15 10:31
 * @des
 */
@Service
public class EventService {


    @Resource
    EventRepository eventRepository;


    public int create(Event event) {
        Event save = eventRepository.saveAndFlush(event);
        return save.getId();
    }

    public int updateEvent(String id, Event event) {
        Optional<Event> byId = eventRepository.findById(Integer.parseInt(id));
        if (byId.isPresent()){
            Event event1 = byId.get();
            event1.setEventComplete(event.isEventComplete());
            event1.setDescData(event.getDescData());
            event1.setEndDate(event.getEndDate());
            event1.setStartDate(event.getStartDate());
            event1.setType(event.getType());
            event1.setTitle(event.getTitle());
            eventRepository.save(event1);
            return event1.getId();
        }else {
            eventRepository.saveAndFlush(event);
        }
        return event.getId();
    }

    public void deleteEvent(int id) {
        eventRepository.deleteById(id);
    }

    public List<Event> findAllEvent() {
        return eventRepository.findAll();
    }
}
