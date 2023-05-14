package service;
import entity.Event;
import entity.Place;
import entity.TicketsSales;
import jdk.jshell.spi.ExecutionControl;
import util.DataBaseManager;
import dao.CustomerDAO;
import dao.TicketsSalesDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TicketsSalesService {
    private Connection connection;

    private TicketsSalesDAO ticketsSalesDAO;
    private CustomerDAO customerDAO;

    public TicketsSalesService() {
        try {
            connection = new DataBaseManager().getConnection();
            ticketsSalesDAO = new TicketsSalesDAO(connection);
            customerDAO = new CustomerDAO(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addTicketsSale(int customerId, int eventId, int numOfTickets) {
        TicketsSales ticketsSales = new TicketsSales(customerId, eventId, numOfTickets);
        try {
            if (ticketsSalesDAO.save(ticketsSales)) {
                return true;
            }
        } catch (SQLException | ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean updateTicketsSale(TicketsSales ticketsSales) {
        try {
            if (ticketsSalesDAO.update(ticketsSales)) {
                return true;
            }
        } catch (SQLException | ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public TicketsSales getTicketsSale(int id) {
        try {
            return ticketsSalesDAO.getById(id);
        } catch (SQLException | ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteTicketsSale(int id) {
        TicketsSales ticketsSales = null;
        try {
            ticketsSales = ticketsSalesDAO.getById(id);
            if (ticketsSales != null) {
                return ticketsSalesDAO.delete(ticketsSales);
            }
        } catch (SQLException | ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public List<TicketsSales> getAllTicketsSales() {
        try {
            return ticketsSalesDAO.getAll();
        } catch (SQLException | ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Event> getAllUserEvents(int id) {

        try {
            return customerDAO.getEventsUser(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}