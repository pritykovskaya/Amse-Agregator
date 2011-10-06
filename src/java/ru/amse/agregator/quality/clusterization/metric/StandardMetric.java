package ru.amse.agregator.quality.clusterization.metric;

import ru.amse.agregator.storage.DBWrapper;

/**
 *
 * @author pavel
 */
public class StandardMetric extends Metric {

    @Override
    public double compute(DBWrapper obj1, DBWrapper obj2) {
        double distance = 0.0;
        int parametersEvaluated = 0;

        //compare  names
        {
            String name1 = obj1.getName();
            String name2 = obj2.getName();
            if ((name1 != null) &&
                (name2 != null)) {

                double nameDistance;

                nameDistance = getNormalizedDistance(name1, name2);
                // regular expression for searchinh a substring without a case
                //b = string.matches("(?i).*i am.*");

                distance += nameDistance;
                parametersEvaluated += 1;
            } else {
                // objects that don't have names
                return 1.0f;
            }
        }
       
        //try to compare continent names
        {
            String continentName1 = obj1.getStaticContinentName();
            String continentName2 = obj2.getStaticContinentName();
            if ((continentName1 != null) &&
                (continentName2 != null)) {

                distance += getNormalizedDistance(continentName1, continentName2);
                parametersEvaluated += 1;
            }
        }

        //try to compare city names
        {
            String cityName1 = obj1.getStaticCityName();
            String cityName2 = obj2.getStaticCityName();
            if ((cityName1 != null) &&
                (cityName2 != null)) {

                distance += getNormalizedDistance(cityName1, cityName2);
                parametersEvaluated += 1;
            }
        }

        //try to compare country names
        {
            String countryName1 = obj1.getStaticCountryName();
            String countryName2 = obj2.getStaticCountryName();
            if ((countryName1 != null) &&
                (countryName2 != null)) {

                distance += getNormalizedDistance(countryName1, countryName2);
                parametersEvaluated += 1;
            }
        }

        double averageDistance = distance / parametersEvaluated;

        return averageDistance;
    }
}
