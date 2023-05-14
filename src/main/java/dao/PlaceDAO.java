package dao;

import entity.Customer;
import entity.Place;
import jdk.jshell.spi.ExecutionControl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PlaceDAO extends BaseDAO<Place> {


    public PlaceDAO(Connection connection) {
        super(connection);
    }

    @Override
    public boolean save(Place place) throws ExecutionControl.NotImplementedException, SQLException {
        request = "INSERT INTO place (name, address, capacity) values (?,?,?)";
        statement = _connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, place.getName());
        statement.setString(2, place.getAddress());
        statement.setInt(3, place.getCapacity());
        int nbRow = statement.executeUpdate();
        resultSet = statement.getGeneratedKeys();
        if(resultSet.next()) place.setId(resultSet.getInt(1));
        return nbRow == 1;
    }

    @Override
    public Place getById(int id) throws ExecutionControl.NotImplementedException, SQLException {
        Place place = null;
        request = "select * from place where id = ?";
        statement = _connection.prepareStatement(request);
        statement.setInt(1, id);
        resultSet = statement.executeQuery();
        if(resultSet.next()) {
            place = new Place(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("address"),
                    resultSet.getInt("capacity"));
        }
        return place;
    }

    @Override
    public List<Place> getAll() throws ExecutionControl.NotImplementedException, SQLException {
        List<Place> result = new ArrayList<>();
        request = "select * from place";
        statement = _connection.prepareStatement(request);
        resultSet = statement.executeQuery();
        while(resultSet.next()){
            Place place = new Place(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("address"),
                    resultSet.getInt("capacity"));
            result.add(place);
        }
        return result;
    }

    @Override
    public boolean update(Place place) throws ExecutionControl.NotImplementedException, SQLException {
        request = "UPDATE place set name = ?, address = ?, capacity = ? where id = ?";
        statement = _connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1,place.getName());
        statement.setString(2,place.getAddress());
        statement.setInt(3,place.getCapacity());
        statement.setInt(4,place.getId());
        int nbRow = statement.executeUpdate();
        return nbRow ==1;
    }

    @Override
    public boolean delete(Place place) throws ExecutionControl.NotImplementedException, SQLException {
        request = "delete from place where id = ?";
        statement = _connection.prepareStatement(request);
        statement.setInt(1, place.getId());
        int nbRow = statement.executeUpdate();
        return nbRow ==1;
    }
}
