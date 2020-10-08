package domain.db;

import domain.model.Country;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountryDBInMemory implements CountryDB {
    private Map<String, Country> countries = new HashMap<String, Country>();

    public CountryDBInMemory() {
        add(new Country("Belgie", 11000000, "Brussel", 4));
        add(new Country("Nederland", 20000000, "Amsterdam", 4));
    }


    @Override
    /**
     * Stores the given country in the list countries
     * @param country The country to be added
     * @throws DbException if the given country is null
     * @throws DbException if the given country can not be added
     */
    public void add(Country country) {
        if (country == null) {
            throw new DbException("Nothing to add.");
        }
        try {
            countries.put(country.getName(), country);
        } catch (Exception e) {
            throw new DbException(e);
        }

    }

    @Override
    /**
     * Returns a list with all countries stored in the database
     * @return An arraylist with all countries stored in the database
     */

    public List<Country> getAll() {
        return new ArrayList<Country>(countries.values());
    }
}
