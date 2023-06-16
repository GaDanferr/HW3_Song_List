/**
 * This Interface is used to define all the methods that a Stack-like data structure must fulfil.
 * @param <E> any variable type that is upper-bounded by Cloneable
 */
public interface Stack<E extends Cloneable> extends Iterable<E>, Cloneable {
    void push(E element);
    E pop();
    E peek();
    int size();
    boolean isEmpty();
    Stack<E> clone();
}


