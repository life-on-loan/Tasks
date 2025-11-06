import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Predicate;

public class FilteringIterator<T> implements Iterator<T> {
    private final Iterator<T> sourceIterator;
    private final Predicate<T> filter;
    private T nextElement;

    public FilteringIterator(Iterator<T> sourceIterator, Predicate<T> filter) {
        this.sourceIterator = Objects.requireNonNull(sourceIterator);
        this.filter = Objects.requireNonNull(filter);
    }

    @Override
    public boolean hasNext() {
        if (nextElement != null) {
            return true;
        }

        while (sourceIterator.hasNext()) {
            T candidate = sourceIterator.next();
            if (filter.test(candidate)) {
                nextElement = candidate;
                return true;
            }
        }

        return false;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        T result = nextElement;
        nextElement = null;
        return result;
    }
}
