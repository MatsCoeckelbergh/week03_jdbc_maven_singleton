package domain.service;

import domain.db.CountryDB;
import domain.db.CountryDBSQL;
import domain.model.Country;

import java.util.List;

public class CountryService {
    private CountryDB db = new CountryDBSQL();

    public void addCountry(Country country) {
        db.add(country);
    }

    public List<Country> getCountries() {
        return db.getAll();
    }

    public Country getMostPopularCountry() {
        int highestVotes = -1;
        Country mostPopular = null;
        List<Country> countryList = getCountries();
        for (int i = 0; i < countryList.size(); i++) {
            if (countryList.get(i).getVotes() > highestVotes) {
                mostPopular = countryList.get(i);
                highestVotes = mostPopular.getVotes();
            }
        }
        return mostPopular;
    }
}
