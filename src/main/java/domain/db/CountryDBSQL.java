package domain.db;

import domain.model.Country;
import util.DbConnectionService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryDBSQL implements CountryDB {
    private Connection connection;
    private String schema;

    public CountryDBSQL() {
        this.connection = DbConnectionService.getDbConnection();
        this.schema = DbConnectionService.getSchema();
        System.out.println(this.schema);
    }

    /**
     * Stores the given country in the database
     *
     * @param country The country to be added
     * @throws DbException if the given country is null
     * @throws DbException if the given country can not be added
     */
    @Override
    public void add(Country country) {
        if (country == null) {
            throw new DbException("Nothing to add.");
        }
        String sql = "INSERT INTO country (name, capital, inhabitants, votes) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statementSQL = connection.prepareStatement(sql);
            statementSQL.setString(1, country.getName());
            statementSQL.setString(2, country.getCapital());
            statementSQL.setInt(3, country.getNumberInhabitants());
            statementSQL.setInt(4, country.getVotes());
            statementSQL.execute();
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    @Override
    /**
     * Returns a list with all countries stored in the database
     * @return An arraylist with all countries stored in the database
     * @throws DbException when there are problems with the connection to the database
     */
    public List<Country> getAll() {
        List<Country> countries = new ArrayList<Country>();
        String sql = String.format("SELECT * FROM %s.country", this.schema);
        try {
            PreparedStatement statementSql = connection.prepareStatement(sql);
            ResultSet result = statementSql.executeQuery();
            while (result.next()) {
                String name = result.getString("name");
                String capital = result.getString("capital");
                int numberOfVotes = Integer.parseInt(result.getString("votes"));
                int numberOfInhabitants = Integer.parseInt(result.getString("inhabitants"));
                Country country = new Country(name, numberOfInhabitants, capital, numberOfVotes);
                countries.add(country);
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage(), e);
        }
        return countries;
    }
}
