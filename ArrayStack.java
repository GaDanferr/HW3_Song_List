import java.util.Iterator;
import java.lang.reflect.*;

/**Class that implements a cloneable stack using an array
 *
 * @param <E> is some generic variable that is upperbound-ed by cloneable interface
 */
public class ArrayStack<E extends Cloneable> implements Stack <E> {
    private static final int EMPTY = 0;
    private Cloneable[] array;
    private  int stackHead;

    private final int capacity;

    /**Initializes an ArrayStack with some give capacity
     *
     * @param capacity defines the maximum capacity the stack can hold as it uses a static array
     * @throws NegativeCapacityException if attempted to initialize a new stack with a negative capacity.
     */
    public ArrayStack(int capacity) {
        if(capacity < EMPTY){
            throw new NegativeCapacityException();
        }
        this.array = new Cloneable[capacity];
        this.capacity = capacity;
        this.stackHead = EMPTY;
    }

    /**
     * Pushes a new element to the top of the stack.
     * @param element the element being pushed onto the stack.
     * @throws StackOverflowException if the stack was full when a user attempted to push a new element.
     */
    @Override
    public void push(E element){
        if (stackHead >= capacity) {
            throw new StackOverflowException();
        } else {
            this.array[stackHead++] = element;
        }
    }

    /**
     * Pops an element from the top of the stack removing it and returning it
     * @return the element that was popped
     * @throws EmptyStackException if the stack was empty when attempting to pop.
     */
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

    /**
     * Dumps the current array to allocate a new array for the ArrayStack while resetting the stackHead.
     * Used exclusively by the clone() function.
     */
    public void dump(){
        stackHead = EMPTY;
        array = new Cloneable[capacity];
    }

    /**
     * Checks what is the current element that is located on-top of the stack.
     * @return the element that is at the top of the stack.
     * @throws EmptyStackException if the stack is empty.
     */
    @Override
    public E peek() {
        if(this.isEmpty()){
            throw new EmptyStackException();
        }
        return (E) array[stackHead-1];
    }

    /**
     * This method is used to show the current amount of elements in the stack.
     * @return the amount of elements in the stack.
     */
    @Override
    public int size() {
        return stackHead;
    }

    /**
     * Checks whether the stack has any elements.
     * @return true if the stack is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return stackHead==EMPTY;
    }

    /**
     * This method performs a deep cloning of the ArrayStack cloning all of its attributes as well as
     * a clone of the elements in the stack.
     * @return a deep clone of the ArrayStack or NULL if the stack is not cloneable.
     */
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

    /**
     * This method generates a new iterator that is used by the stack to iterate over the stack.
     * @return a new iterator object.
     */
    @Override
    public StackIterator iterator() {
        return new StackIterator();
    }


    /**
     * This class depicts an iterator used by the stack to iterate over its objects without
     * performing any changes to the stack itself.
     */

    public class StackIterator implements Iterator {
        private int currentIndex;

        /**
         * Initializes a new StackIterator object.
         * CurrentIndex describes the current index of the top element of the stack.
         */
        public StackIterator() {
            this.currentIndex = stackHead;
        }

        /**
         * Checks if the stack has a new element to return
         * @return True if there is a new element to return , False if there is not.
         */
        @Override
        public boolean hasNext() {
            return currentIndex > EMPTY;
        }

        /**
         * Method that is used to return the next element in the stack.
         * @return the next element of the stack.
         */
        @Override
        public E next() {
            return (E) array[--currentIndex];
        }

    }
}
