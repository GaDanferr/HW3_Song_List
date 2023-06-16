import java.util.*;

/**
 * This class is used to depict a playlist that uses an ArrayList to house Song objects
 * The Playlist is: cloneable , can be filtered and can be iterated in 3 separate modes.
 */
public class Playlist implements Cloneable,OrderedSongIterable,FilteredSongIterable {
    private ArrayList<Song> songList;
    private ArrayList<Song> orderedList;
    private int songCount;
    private String filterArtist;
    private Song.Genre filterGenre;
    private Optional<Integer> filterDuration;
    private ScanningOrder currentOrder;

    /**
     * Initializes a new Playlist object.
     */
    public Playlist() {
        this.songCount = 0;
        this.songList = new ArrayList<>();
        this.orderedList = new ArrayList<>();
        this.currentOrder = ScanningOrder.ADDING;
        filterDuration = Optional.empty();
    }

    /**
     * Locates the location of a song in the playlist
     * @param song is the song that is being located
     * @return an Optional that houses the location of the song in the playlist, empty if it doesn't exist.
     */
    private Optional<Integer> songLocation(Song song){//,ArrayList<Song> list) {
        PlaylistIterator iterator = new PlaylistIterator();
        Song currentSong;
        int i = 0;
        while(iterator.hasNext()){
            currentSong = iterator.next();
            if(currentSong.equals(song)){
                return Optional.of(i);
            }
            i++;
        }
        return Optional.empty();
    }

    /**
     * Adds a new song to the playlist if it's not already in the playlist
     * @param newSong the new song being added to the playlist.
     * @throws SongAlreadyExistsException if the song is already inside the playlist.
     */
    public void addSong(Song newSong) {
        Optional<Integer> songLocation = songLocation(newSong);

        if(songLocation.isPresent()){
            throw new SongAlreadyExistsException();

        }
        else{
            songList.add(newSong);
            addOrderedSong(newSong);

        }
        songCount++;
    }
    private void addOrderedSong(Song newSong){
        orderedList.add(newSong);

    }

    /**
     * Removes a song from the playlist
     * @param removedSong the song that is attempted to be removed
     * @return True if successful in removing the song, False if the song doesn't exist in the playlist.
     */
    public boolean removeSong(Song removedSong) {
        Optional<Integer> location = songLocation(removedSong);
        if(location.isPresent()){
            int i = location.get();
            songList.remove(i);
            songCount--;
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Converts the playlist into a string based on the order the song appear in the
     * playlist as well as the following format given n songs:
     * [(Song 1), (Song 2)....(Song N)]
     * And each song is represented as:
     * name, artist, genre, duration
     * @return a string representation of the playlist.
     */
    @Override
    public String toString() {
        String stringPlaylist = "[";
        if(songCount == 0){
            return "(" +  stringPlaylist + ")" + "]";
        }
        int counter = 0;
        while(counter < songCount-1){
            stringPlaylist += "("+ songList.get(counter) +")"+ ", ";
            counter++;
        }

        return stringPlaylist + "(" + songList.get(counter) + ")" +  "]";

    }

    /**
     * Filters the playlist to show songs that contain a certain artist.
     * @param artist the artist that the playlist is filtered by.
     */
    @Override
    public void filterArtist(String artist) {
        this.filterArtist = artist;

    }
    /**
     * Filters the playlist to show songs that contain a certain genre.
     * @param genre the artist that the playlist is filtered by.
     */
    @Override
    public void filterGenre(Song.Genre genre) {
        this.filterGenre = genre;
    }
    /**
     * Filters the playlist to show songs that are as long as a certain limit or shorter.
     * @param durationLimit the time limit that the playlist is filtered by.
     */
    @Override
    public void filterDuration(int durationLimit) {
        this.filterDuration = Optional.of(durationLimit);
    }

    /**
     * Deletes the ArrayLists that are used to house the songs that the playlist contains.
     * Used exclusively when a cloning is performed.
     */
    public void deletePlaylist(){
        songCount = 0;
        songList = new ArrayList<>();
        orderedList = new ArrayList<>();
    }

    /**
     * Shuffles the playlist based on the currentOrder mode:
     * ADDING : shuffles based on first songs that were added being shown first.
     * NAME: Shuffles based on the lexicographic order of the song name , if the song names are
     * equal a second, comparison between the artist's name is being performed.
     * Duration : Shuffles the playlist to show the shortest songs first , if the songs are of equal length
     * a second evaluation is used based on the same logic as the NAME shuffle.
     */
    private void sortPlaylist(){
        switch (currentOrder){
            case ADDING:
                this.deletePlaylist();
                for(int i = 0 ; i < songCount ; i++){
                    songList.add(orderedList.get(i));
                }
                break;
            case NAME:
                /*Comparator<Song> comparator = new Comparator<Song>() {
                    @Override
                    public int compare(Song o1, Song o2) {
                        //if(o1.getName() > o2.getName())
                        return 0;
                    }
                }*/


            case DURATION:
        }

    };

    /**
     * Sets the shuffle mode the user wishes the playlist to be shuffled by.
     * @param order the shuffling mode.
     */
    @Override
    public void setScanningOrder(ScanningOrder order) {
        if(this.currentOrder != order) {
            this.currentOrder = order;
            sortPlaylist();
        }
    }

    /**
     * Performs a deep cloning of the playlist
     * @return a clone of the playlist.
     */
    @Override
    public Playlist clone() {
        try {
            Playlist newPlayList = (Playlist) super.clone();
            newPlayList.deletePlaylist();
            PlaylistIterator iterator = new PlaylistIterator();
            while (iterator.hasNext()) {
                Song clonedSong = iterator.next().clone();
                newPlayList.addSong(clonedSong);
                newPlayList.addOrderedSong(clonedSong);
            }
            return newPlayList;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    /**
     *
     * @return the amount of songs in the playlist.
     */
    public int getSongCount() {
        return songCount;
    }

    /**
     * Evaluates whether two playlists are the same based on the following definition:
     * Two playlists are equal iff they contain the same songs in any order.
     * @param other the other playlist that is being compared with.
     * @return True if the two playlists are the same, False otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Playlist)) {
            return false;
        }
        Playlist otherPlaylist = (Playlist) other;
        if(songCount!= otherPlaylist.getSongCount()){
            return false;
        }
        for(int i = 0; i < songCount; i++){
            Song currentSong = songList.get(i);
            Optional<Integer> pairLocation = otherPlaylist.songLocation(currentSong);
            if (!(pairLocation.isPresent())){
                return false;
            }
        }

        return true;
    }

    /**
     * Creates a hash value for the playlist based on the songs it has.
     * @return a hash value of the playlist.
     */
    @Override
    public int hashCode() {
        int hashCodeSum = 0;
        PlaylistIterator iterator = new PlaylistIterator();
        while (iterator.hasNext()){
            Song currentSong = iterator.next();
            hashCodeSum += currentSong.hashCode();
        }
        return hashCodeSum;
    }

    /**
     *
     * @return a new iterator to go over the songs of the playlist.
     */
    @Override
    public PlaylistIterator iterator() {
        return new PlaylistIterator();
    }

    /**
     * Inner class that is used by the Playlist in order to be able to iterate over the Songs that are inside
     * the playlist.
     */
    public class PlaylistIterator implements Iterator<Song> {
        int currentSongIndex;

        /**
         * Initializes a new iterator.
         */
        private PlaylistIterator(){
            this.currentSongIndex = 0;
        }

        /**
         * Checks whether there is a new song in the playlist based on the filters the playlist has.
         *
         * @return True if there is a next song, False otherwise.
         */
        @Override
        public boolean hasNext() {
            while(currentSongIndex < songCount){
                Song currentSong = songList.get(currentSongIndex);
                if(filterArtist != null){
                    if(!(filterArtist.equals(currentSong.getArtist()))){
                        currentSongIndex++;
                        continue;
                    }
                }
                if(filterGenre != null){
                    if(!(filterGenre == currentSong.getGenre())){
                        currentSongIndex++;
                        continue;
                    }
                }
                if(filterDuration.isPresent()){
                    if(currentSong.getDuration() > filterDuration.get() ){
                        currentSongIndex++;
                        continue;
                    }
                }

                return true;
            }


            return false;
        }

        /**
         *
         * @return the next song in the playlist.
         */
        @Override
        public Song next() {
            return songList.get(currentSongIndex++);
        }

    }
}
