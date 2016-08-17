package utilities;

/**
 * Created by gvoiron on 18/08/16.
 * Time : 00:55
 */
public final class UTuple<T1, T2> {

    private final T1 first;
    private final T2 second;

    public UTuple(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    public T1 getFirst() {
        return first;
    }

    public T2 getSecond() {
        return second;
    }

}
