package dao;

import entity.Place;
import entity.TicketsSales;
import jdk.jshell.spi.ExecutionControl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TicketsSalesDAO extends BaseDAO<TicketsSales>{
    public TicketsSalesDAO(Connection connection) {
        super(connection);
    }

    @Override
    public boolean save(TicketsSales ticketsSales) throws ExecutionControl.NotImplementedException, SQLException {
        request = "INSERT INTO tickets_sales (customer_id, event_id, number_of_tickets) values (?,?,?)";
        statement = _connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, ticketsSales.getCustomerId());
        statement.setInt(2, ticketsSales.getEventId());
        statement.setInt(3, ticketsSales.getNumOfTickets());
        int nbRow = statement.executeUpdate();
        resultSet = statement.getGeneratedKeys();
        if(resultSet.next()) ticketsSales.setId(resultSet.getInt(1));
        return nbRow == 1;
    }

    @Override
    public TicketsSales getById(int id) throws ExecutionControl.NotImplementedException, SQLException {
        TicketsSales ticketsSales = null;
        request = "select * from tickets_sales where id = ?";
        statement = _connection.prepareStatement(request);
        statement.setInt(1, id);
        resultSet = statement.executeQuery();
        if(resultSet.next()) {
            ticketsSales = new TicketsSales(resultSet.getInt("id"),
                    resultSet.getInt("customer_id"),
                    resultSet.getInt("event_id"),
                    resultSet.getInt("number_of_tickets"));
        }
        return ticketsSales;
    }

    @Override
    public List<TicketsSales> getAll() throws ExecutionControl.NotImplementedException, SQLException {
        List<TicketsSales> result = new ArrayList<>();
        request = "select * from tickets_sales";
        statement = _connection.prepareStatement(request);
        resultSet = statement.executeQuery();
        while(resultSet.next()){
            TicketsSales ticketsSales = new TicketsSales(resultSet.getInt("id"),
                    resultSet.getInt("customer_id"),
                    resultSet.getInt("event_id"),
                    resultSet.getInt("number_of_tickets"));
            result.add(ticketsSales);
        }
        return result;
    }

    @Override
    public boolean update(TicketsSales ticketsSales) throws ExecutionControl.NotImplementedException, SQLException {
        request = "UPDATE tickets_sales set customer_id = ?, event_id = ?, number_of_tickets = ? where id = ?";
        statement = _connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1,ticketsSales.getCustomerId());
        statement.setInt(2,ticketsSales.getEventId());
        statement.setInt(3,ticketsSales.getNumOfTickets());
        statement.setInt(4,ticketsSales.getId());
        int nbRow = statement.executeUpdate();
        return nbRow ==1;
    }

    @Override
    public boolean delete(TicketsSales ticketsSales) throws ExecutionControl.NotImplementedException, SQLException {
        request = "delete from tickets_sales where id = ?";
        statement = _connection.prepareStatement(request);
        statement.setInt(1, ticketsSales.getId());
        int nbRow = statement.executeUpdate();
        return nbRow ==1;
    }
}
