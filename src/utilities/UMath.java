package utilities;

import java.util.Collection;

/**
 * Created by gvoiron on 01/09/16.
 * Time : 15:41
 */
public final class UMath extends UAUninstantiable {

    public static double getStandardDeviation(Collection<? extends Collection> data) {
        double average = data.stream().mapToDouble(Collection::size).average().orElseGet(() -> 0);
        double variance = 0;
        for (Collection entry : data) {
            variance += (entry.size() - average) * (entry.size() - average);
        }
        variance = variance / data.size();
        return Math.sqrt(variance);
    }

}
