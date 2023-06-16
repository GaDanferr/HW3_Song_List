import java.util.Iterator;
import java.lang.reflect.*;
public class ArrayStack<E extends Cloneable> implements Stack <E> {
    private static final int EMPTY = 0;
    private Cloneable[] array;
    private  int stackHead;

    private final int capacity;

    public ArrayStack(int capacity) {
        if(capacity < EMPTY){
            throw new NegativeCapacityException();
        }
        this.array = new Cloneable[capacity];
        this.capacity = capacity;
        this.stackHead = EMPTY;
    }
    @Override
    public void push(E element){
        if (stackHead >= capacity) {
            throw new StackOverflowException();
        } else {
            this.array[stackHead++] = element;
        }
    }
    @Override
    public E pop() {
        if(stackHead<=EMPTY){
            throw new EmptyStackException();
        }
        else{
            E tmp = (E) array[stackHead-1];
            array[stackHead] = null;
            stackHead--;
            return tmp;

        }
    }
    public void dump(){
        stackHead = EMPTY;
        array = new Cloneable[capacity];
    }

    @Override
    public E peek() {
        if(stackHead <= EMPTY){
            throw new EmptyStackException();
        }
        return (E) array[stackHead-1];
    }

    @Override
    public int size() {
        return stackHead;
    }

    @Override
    public boolean isEmpty() {
        return stackHead==EMPTY;
    }

    @Override
    public ArrayStack<E> clone() {
        try{
            ArrayStack<E> clone = (ArrayStack<E>) super.clone();
            clone.dump();

            ArrayStack<E> tmp = new ArrayStack<>(capacity);
            tmp.dump();
            Method method;
            Iterator<E> copyIterator = (Iterator<E>) this.iterator();
            while(copyIterator.hasNext()){
                try {
                    Cloneable clonedElement = copyIterator.next();
                    method = clonedElement.getClass().getMethod("clone");
                    clonedElement = (Cloneable) method.invoke(clonedElement);
                    tmp.push((E) clonedElement);//need to do the invoke thing
                }
                catch (InvocationTargetException
                        | NoSuchMethodException
                        | IllegalAccessException e){
                    tmp.push(null);
                }
            }
            while(!(tmp.isEmpty())){
                clone.push(tmp.pop());
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            return null;
        }

    }

    @Override
    public StackIterator iterator() {
        return new StackIterator();
    }




    public class StackIterator implements Iterator {
        private int currentIndex;

        public StackIterator() {
            this.currentIndex = stackHead;
        }

        @Override
        public boolean hasNext() {
            return currentIndex > EMPTY;
        }

        @Override
        public E next() {
            return (E) array[--currentIndex];
        }

    }
}
