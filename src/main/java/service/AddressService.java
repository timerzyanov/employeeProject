package service;

import bl.Util;
import dao.AddressDAO;
import entity.Address;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressService extends Util implements AddressDAO {

    Connection connection = getConnection();

    @Override
    public void add(Address address) throws SQLException {
        String sql = "INSERT INTO ADDRESS (ID, COUNTRY, CITY, STREET, POST_CODE) VALUES (?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, address.getId());
            preparedStatement.setString(2, address.getCountry());
            preparedStatement.setString(3, address.getCity());
            preparedStatement.setString(4, address.getStreet());
            preparedStatement.setString(5, address.getPostCode());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
        }
    }

    @Override
    public List<Address> getAll() throws SQLException {
        List<Address> addressList = new ArrayList<>();
        String sql = "SELECT ID, COUNTRY, CITY, STREET, POST_CODE FROM ADDRESS";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Address address = new Address();
                address.setId(resultSet.getLong("ID"));
                address.setCountry(resultSet.getString("COUNTRY"));
                address.setCity(resultSet.getString("CITY"));
                address.setStreet(resultSet.getString("STREET"));
                address.setPostCode(resultSet.getString("POST_CODE"));
                addressList.add(address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
        }
        return addressList;
    }

    @Override
    public Address getById(Long id) throws SQLException {
        Address address = new Address();
        String sql = "SELECT ID, COUNTRY, CITY, STREET, POST_CODE FROM ADDRESS WHERE ID=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            address.setId(resultSet.getLong("ID"));
            address.setCountry(resultSet.getString("COUNTRY"));
            address.setCity(resultSet.getString("CITY"));
            address.setStreet(resultSet.getString("STREET"));
            address.setPostCode(resultSet.getString("POST_CODE"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
        }
        return address;
    }

    @Override
    public void update(Address address) throws SQLException {
        String sql = "UPDATE ADDRESS SET COUNTRY=?, CITY=?, STREET=?, POST_CODE=? WHERE ID=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, address.getCountry());
            preparedStatement.setString(2, address.getCity());
            preparedStatement.setString(3, address.getStreet());
            preparedStatement.setString(4, address.getPostCode());
            preparedStatement.setLong(5, address.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
        }
    }

    @Override
    public void remove(Address address) throws SQLException {
        String sql = "DELETE FROM ADDRESS WHERE ID=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, address.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.close();
        }
    }
}