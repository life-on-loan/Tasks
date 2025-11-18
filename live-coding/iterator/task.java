import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Predicate;

// TODO: Реализовать итератор
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

    }

    @Override
    public T next() {

    }
}
