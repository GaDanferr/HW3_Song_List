import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public interface FilteredSongIterable extends Iterable<Song>{
    public void filterArtist(String artist);
    public void filterGenre(Song.Genre genre);
    public void filterDuration(int durationLimit);
}
