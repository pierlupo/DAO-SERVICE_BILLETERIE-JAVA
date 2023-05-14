package dao;

import entity.Customer;
import entity.Event;
import jdk.jshell.spi.ExecutionControl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO extends BaseDAO<Customer>{
    public CustomerDAO(Connection connection) {
        super(connection);
    }

    @Override
    public boolean save(Customer customer) throws ExecutionControl.NotImplementedException, SQLException {
        request = "INSERT INTO customer (first_name, last_name, email) values (?,?,?)";
        statement = _connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, customer.getFirstName());
        statement.setString(2, customer.getLastName());
        statement.setString(3, customer.getEmail());
        int nbRow = statement.executeUpdate();
        resultSet = statement.getGeneratedKeys();
        if(resultSet.next()) customer.setId(resultSet.getInt(1));
        return nbRow == 1;
    }

    @Override
    public Customer getById(int id) throws ExecutionControl.NotImplementedException, SQLException {
        Customer customer = null;
        request = "select * from customer where id = ?";
        statement = _connection.prepareStatement(request);
        statement.setInt(1, id);
        resultSet = statement.executeQuery();
        if(resultSet.next()) {
            customer = new Customer(resultSet.getInt("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("email"));
        }
        return customer;
    }

    @Override
    public List<Customer> getAll() throws ExecutionControl.NotImplementedException, SQLException {
        List<Customer> result = new ArrayList<>();
        request = "select * from person";
        statement = _connection.prepareStatement(request);
        resultSet = statement.executeQuery();
        while(resultSet.next()){
            Customer customer = new Customer(resultSet.getInt("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("email"));
            result.add(customer);
        }
        return result;
    }

    @Override
    public boolean update(Customer customer) throws ExecutionControl.NotImplementedException, SQLException {
        request = "UPDATE customer set first_name = ?, last_name = ?, email = ? where id = ?";
        statement = _connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1,customer.getFirstName());
        statement.setString(2,customer.getLastName());
        statement.setString(3,customer.getEmail());
        statement.setInt(4,customer.getId());
        int nbRow = statement.executeUpdate();
        return nbRow ==1;
    }

    @Override
    public boolean delete(Customer customer) throws ExecutionControl.NotImplementedException, SQLException {
        request = "delete from customer where id = ?";
        statement = _connection.prepareStatement(request);
        statement.setInt(1, customer.getId());
        int nbRow = statement.executeUpdate();
        return nbRow ==1;
    }

    public List<Event> getEventsUser(int userId) throws SQLException {
        List<Event> events = new ArrayList<>();
        request = "SELECT e.id, e.name, e.date, e.hour, e.place_id, e.price, e.ticketsSold FROM billeterie2.event as e inner join billeterie2.tickets_sales as t on e.id = t.event_id where t.customer_id = ?";
        statement.setInt(1, userId);
        resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Event  event = new Event(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getDate("date"),
                    resultSet.getString("hour"),
                    resultSet.getInt("place_id"),
                    resultSet.getDouble("price"),
                    resultSet.getInt("tickets_Sold")
            );
            events.add(event);
        }

        return events;
    }
}
