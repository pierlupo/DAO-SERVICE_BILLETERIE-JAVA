package service;

import dao.CustomerDAO;
import entity.Customer;
import jdk.jshell.spi.ExecutionControl;
import util.DataBaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CustomerService {

    private CustomerDAO customerDAO;
    private Connection connection;

    public CustomerService() {
        try {
            connection = new DataBaseManager().getConnection();
            customerDAO = new CustomerDAO(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addCustomer(String firstName, String lastName, String email) {
        Customer customer = new Customer(firstName, lastName, email);
        try {
            if(customerDAO.save(customer)) {
                return true;
            }
        } catch (SQLException | ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean updateCustomer(int id, String firstName, String lastName, String email, int nbTickets) {
        try {
            Customer customer = getCustomer(id);
            if(customerDAO.update(customer)) {
                return true;
            }
        } catch (SQLException | ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public Customer getCustomer(int id) {
        try {
            return customerDAO.getById(id);
        } catch (SQLException | ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteCustomer(int id) {
        Customer customer = null;
        try {
            customer = customerDAO.getById(id);
            if(customer != null) {
                return customerDAO.delete(customer);
            }
        } catch (SQLException | ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public List<Customer> getAllCustomers() {
        try {
            return customerDAO.getAll();
        } catch (SQLException | ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
    }
}
