package service;

import dao.CustomerDAO;
import dao.PlaceDAO;
import entity.Customer;
import entity.Place;
import jdk.jshell.spi.ExecutionControl;
import util.DataBaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PlaceService {

    private PlaceDAO placeDAO;
    private Connection connection;

    public PlaceService() {
        try {
            connection = new DataBaseManager().getConnection();
            placeDAO = new PlaceDAO(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addPlace(String name, String address, int capacity) {
        Place place = new Place(name, address, capacity);
        try {
            if(placeDAO.save(place)) {
                return true;
            }
        } catch (SQLException | ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean updatePlace(int id, String name, String address, int capacity) {
        try {
            Place place = getPlace(id);
            if(placeDAO.update(place)) {
                return true;
            }
        } catch (SQLException | ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public Place getPlace(int id) {
        try {
            return placeDAO.getById(id);
        } catch (SQLException | ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deletePlace(int id) {
        Place place = null;
        try {
            place = placeDAO.getById(id);
            if(place != null) {
                return placeDAO.delete(place);
            }
        } catch (SQLException | ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public List<Place> getAllPlaces() {
        try {
            return placeDAO.getAll();
        } catch (SQLException | ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
    }
}
