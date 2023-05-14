package dao;
import entity.Event;
import jdk.jshell.spi.ExecutionControl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EventDAO extends BaseDAO<Event>{
    public EventDAO(Connection connection) {
        super(connection);
    }

    @Override
    public boolean save(Event event) throws ExecutionControl.NotImplementedException, SQLException {
        request = "INSERT INTO event (name, date, hour, place_Id, price, tickets_sold) values (?,?,?,?,?,?)";
        statement = _connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, event.getName());
        statement.setDate(2, (Date) event.getDate());
        statement.setString(3, event.getHour());
        statement.setInt(4, event.getPlaceId());
        statement.setDouble(5, event.getPrice());
        statement.setInt(6,event.getTicketsSold());
        int nbRow = statement.executeUpdate();
        resultSet = statement.getGeneratedKeys();
        if(resultSet.next()) event.setId(resultSet.getInt(1));
        return nbRow == 1;
    }

    @Override
    public Event getById(int id) throws ExecutionControl.NotImplementedException, SQLException {
        Event event = null;
        request = "select * from event where id = ?";
        statement = _connection.prepareStatement(request);
        statement.setInt(1, id);
        resultSet = statement.executeQuery();
        if(resultSet.next()) {
            event = new Event(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getDate("date"),
                    resultSet.getString("hour"),
                    resultSet.getInt("place_Id"),
                    resultSet.getDouble("price"),
                    resultSet.getInt("tickets_sold"));
        }
        return event;
    }

    @Override
    public List<Event> getAll() throws ExecutionControl.NotImplementedException, SQLException {
        List<Event> result = new ArrayList<>();
        request = "select * from event";
        statement = _connection.prepareStatement(request);
        resultSet = statement.executeQuery();
        while(resultSet.next()){
           Event event = new Event(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getDate("date"),
                    resultSet.getString("hour"),
                    resultSet.getInt("place_Id"),
                    resultSet.getDouble("price"),
                    resultSet.getInt("tickets_sold"));
            result.add(event);
        }
        return result;
    }

    @Override
    public boolean update(Event event) throws ExecutionControl.NotImplementedException, SQLException {
        request = "UPDATE event set name = ?, date = ?, hour = ?, price = ?, ticketsSold = ? where id = ?";
        statement = _connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1,event.getName());
        statement.setDate(2, (Date) event.getDate());
        statement.setString(3,event.getHour());
        statement.setDouble(4,event.getPrice());
        statement.setInt(5,event.getTicketsSold());
        statement.setInt(6,event.getId());
        int nbRow = statement.executeUpdate();
        return nbRow ==1;
    }

    @Override
    public boolean delete(Event event) throws ExecutionControl.NotImplementedException, SQLException {
        request = "delete from event where id = ?";
        statement = _connection.prepareStatement(request);
        statement.setInt(1, event.getId());
        int nbRow = statement.executeUpdate();
        return nbRow ==1;
    }
}
