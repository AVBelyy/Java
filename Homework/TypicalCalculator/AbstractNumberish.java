import java.util.Map;
import java.util.HashMap;

public abstract class AbstractNumberish<T> implements Numberish<T> {
    protected final T value;
    private static final Map<String, Lambdish<?>> functions;
    static {
        functions = new HashMap<String, Lambdish<?>>();
    }

    public AbstractNumberish() {
        this.value = null;
    }

    public AbstractNumberish(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public Numberish<T> fromInteger(Integer n) {
        return fromString(String.valueOf(n));
    }
}
