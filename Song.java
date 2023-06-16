/**
 * This class depicts a song.
 */
public class Song implements Cloneable {
    final private String name;
    final private String artist;
    final private Genre genre;
    private int duration;

    /**
     * Initializes a new song object with the parameters:
     * @param name describes the name of the song.
     * @param artist describes the name of the artist that composed the song.
     * @param genre describes the genre of the song restricted to the inner-class Genre enum.
     * @param duration describes the duration of the song.
     */
    public Song(String name , String artist , Genre genre , int duration){
        this.name = name;
        this.artist = artist;
        this.genre = genre;
        this.duration = duration;
    }

    /**
     *
     * @return the name of the song.
     */
    public String getName() {
        return name;
    }
    /**
     *
     * @return the name of the artist that composed the song.
     */
    public String getArtist() {
        return artist;
    }
    /**
     *
     * @return the genre of the song limited to the inner-class Genre enum.
     */
    public Genre getGenre() {
        return genre;
    }
    /**
     *
     * @return the duration of the song.
     */
    public int getDuration() {
        return duration;
    }

    /**Used to check whether two songs are equal based on their song name and artist name.
     *
     * @param other the object that the song is compared to.
     * @return true if the song are equal in the attributes: song name , artist name.
     */
    public boolean equals(Object other){

        if(!(other instanceof  Song)){
            return false;
        }
        Song otherSong = (Song)other;
        return name.equals(otherSong.name) &&
                artist.equals(otherSong.artist);
    }

    /**
     * Performs a cloning of the song object.
     * @return a new independent clone of the song.
     */
    @Override
    public Song clone() {
        try {
            return (Song) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    /**
     * Used to calculate the hash of the song based on the song name and artist name.
     * @return the hash value of the song.
     */
    public int hashCode() {
        return name.hashCode() + artist.hashCode();
    }

    /**
     * Enum class used to describes the possible genres a song might have.
     */
    public enum Genre {
        POP, ROCK, HIP_HOP, COUNTRY, JAZZ, DISCO
    }

    /**
     * Sets the duration of the song.
     * @param newDuration the new duration of the song.
     */
    public void setDuration(int newDuration){
        this.duration = newDuration;
    }

    /**
     * Converts the Song to a string format in the following format:
     * Name, artist, genre , mm:ss. Where-as seconds will be buffered by a 0 assuming if they are not two-digit.
     * @return a string presentation of the song.
     */
    @Override
    public String toString() {
        int minutes = duration/60;
        int seconds =  duration - minutes*60;
        String songString = name +", " + artist + ", " + genre +", " + minutes + ":";
        if(seconds < 10){
           return songString + 0 + seconds;
        }
        else{
            return songString  + seconds;
        }
    }
}
