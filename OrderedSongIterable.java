/**
 * This interface is used to define a restriction on a data structure where it has
 * to support being able to be iterated over with a certain order.
 */
public interface OrderedSongIterable extends Iterable<Song>{
    void setScanningOrder(ScanningOrder order);
}
