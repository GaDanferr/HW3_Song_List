import java.util.*;

public class Playlist implements Cloneable,OrderedSongIterable,FilteredSongIterable {
    private ArrayList<Song> songList;
    private int songCount;
    private String filterArtist;
    private Song.Genre filterGenre;
    private int filterDuration;
    private ScanningOrder currentOrder;

    public Playlist() {
        this.songCount = 0;
        this.songList = new ArrayList<>();
    }

    private Optional<Integer> songLocation(Song song) {
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

    public void addSong(Song newSong) {
        Optional<Integer> songLocation = songLocation(newSong);

        if(songLocation.isPresent()){
            throw new SongAlreadyExistsException();

        }
        else{
            songList.add(newSong);

        }
        songCount++;
    }

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

        return stringPlaylist += "(" + songList.get(counter) + ")" +  "]";

    }

    @Override
    public void filterArtist(String artist) {
        this.filterArtist = artist;

    }
    @Override
    public void filterGenre(Song.Genre genre) {
        this.filterGenre = genre;
    }

    @Override
    public void filterDuration(int durationLimit) {
        this.filterDuration = durationLimit;
    }
    public void deletePlaylist(){
        songCount = 0;
        songList = new ArrayList<Song>();
    }
    @Override
    public void setScanningOrder(ScanningOrder order) {
        this.currentOrder = order;
    }
    @Override
    public Playlist clone() {
        try {
            Playlist newPlayList = (Playlist) super.clone();
            newPlayList.deletePlaylist();
            PlaylistIterator iterator = new PlaylistIterator();
            while (iterator.hasNext()) {
                Song clonedSong = iterator.next().clone(); // added for better visibility
                newPlayList.addSong(clonedSong);
            }
            return newPlayList;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public int getSongCount() {
        return songCount;
    }

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

    @Override
    public PlaylistIterator iterator() {
        return new PlaylistIterator();
    }


    public class PlaylistIterator implements Iterator<Song> {
        int currentSongIndex;
        private PlaylistIterator(){
            this.currentSongIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return currentSongIndex < songCount;
        }

        @Override
        public Song next() {
            return songList.get(currentSongIndex++);
        }
        private void reset(){currentSongIndex = 0;}

    }
}
