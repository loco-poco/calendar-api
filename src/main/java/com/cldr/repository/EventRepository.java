package com.cldr.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.time.ZonedDateTime;

import com.cldr.event.Event;

public interface EventRepository extends CrudRepository<Event, Integer> {
    @Query("SELECT e FROM Event e WHERE e.userId = :user_id")
    List<Event> findEventsByUser(@Param("user_id") Integer userId);  

    @Query("SELECT e FROM Event e WHERE e.userId = :user_id and e.startTime >= :start_time")
    List<Event> findEventsByUserAndGreaterThenStartDate(@Param("user_id") Integer userId, @Param("start_time") ZonedDateTime startTime);

    @Query("SELECT e FROM Event e WHERE e.userId = :user_id and e.endTime <= :end_time")
    List<Event> findEventsByUserAndLessThenEndDate(@Param("user_id") Integer userId, @Param("end_time") ZonedDateTime endTime);

    @Query("SELECT e FROM Event e WHERE e.userId = :user_id and e.startTime >= :start_time and e.endTime <= :end_time")
    List<Event> findEventsByUserAndDateRange(@Param("user_id") Integer userId, @Param("start_time") ZonedDateTime startTime,@Param("end_time") ZonedDateTime endTime);
} 
