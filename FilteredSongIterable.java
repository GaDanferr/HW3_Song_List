/**
 * This interface is used to define a restriction on a data structure that houses song
 * while  it has to support being able to be iterated over while filtering with certain filters
 *
 */

public interface FilteredSongIterable extends Iterable<Song>{
    void filterArtist(String artist);
    void filterGenre(Song.Genre genre);
    void filterDuration(int durationLimit);
}
