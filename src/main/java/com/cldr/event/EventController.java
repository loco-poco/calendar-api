package com.cldr.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.List;
import java.time.ZonedDateTime;

import com.cldr.repository.EventRepository;

@RestController
public class EventController {
    @Autowired
    private EventRepository eventRepository;

    @GetMapping("/users/{user_id}/events")
    List<Event> getUserEvents(
        @PathVariable("user_id") Integer userId,
        @RequestParam(value="start_time", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime startTime,
        @RequestParam(value="end_time", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime endTime) {

        if (startTime == null && endTime == null ) {
            return eventRepository.findEventsByUser(userId);
        }
        if (startTime != null && endTime == null) {
            return eventRepository.findEventsByUserAndGreaterThenStartDate(userId, startTime);
        }
        if (startTime == null && endTime != null) {
            return eventRepository.findEventsByUserAndLessThenEndDate(userId, endTime);
        }

        return eventRepository.findEventsByUserAndDateRange(userId, startTime, endTime);
    }


    // Get event by id
    @GetMapping("/users/{user_id}/events/{id}")
    Event getEvent(@PathVariable("id") Integer id) {
        return eventRepository.findById(id).get();
    }
    @PostMapping("/users/{user_id}/events")
    public @ResponseBody Event createEvent(@PathVariable("user_id") Integer userId, @RequestBody Event event) {
        event.setUserId(userId);
        return eventRepository.save(event);
    }
}

