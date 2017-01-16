package std.eby.socialauth.base;

/**
 * Created by eby on 16/01/17.
 */

public interface BasePresenter<V> {
    void attach(V v);

    void detach();
}
