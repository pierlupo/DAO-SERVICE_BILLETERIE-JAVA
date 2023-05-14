package service;


import dao.EventDAO;
import entity.Event;
import jdk.jshell.spi.ExecutionControl;
import util.DataBaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class EventService {

    private EventDAO eventDAO;
    private Connection connection;

    public EventService() {
        try {
            connection = new DataBaseManager().getConnection();
            eventDAO = new EventDAO(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addEvent(String name, Date date, String hour, int placeId, double price, int ticketsSold ) {
        Event event = new Event(name, date, hour, placeId, price, ticketsSold);
        try {
            if(eventDAO.save(event)) {
                return true;
            }
        } catch (SQLException | ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean updateEvent(int id, String name, Date date, String hour, int placeId, double price, int ticketsSold) {
        try {
            Event event = getEvent(id);
            if(eventDAO.update(event)) {
                return true;
            }
        } catch (SQLException | ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public Event getEvent(int id) {
        try {
            return eventDAO.getById(id);
        } catch (SQLException | ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteEvent(int id) {
        Event event = null;
        try {
            event = eventDAO.getById(id);
            if(event != null) {
                return eventDAO.delete(event);
            }
        } catch (SQLException | ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public List<Event> getAllEvent() {
        try {
            return eventDAO.getAll();
        } catch (SQLException | ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
    }
}
