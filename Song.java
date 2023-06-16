
public class Song implements Cloneable {
    final private String name;
    final private String artist;
    final private Genre genre; //should these be private?
    private int duration;
    public Song(String name , String artist , Genre genre , int duration){
        this.name = name;
        this.artist = artist;
        this.genre = genre;
        this.duration = duration;
    }
    public boolean equals(Object other){     // iff song name and artist

        if(!(other instanceof  Song)){
            return false;
        }
        Song otherSong = (Song)other;
        return name.equals(otherSong.name) &&
                artist.equals(otherSong.artist);
    }
    @Override
    public Song clone() {
        try {
            return (Song) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
    public int hashCode() {
        return name.hashCode() + artist.hashCode();
    }

    public enum Genre {
        POP, ROCK, HIP_HOP, COUNTRY, JAZZ, DISCO
    }
    public void setDuration(int newDuration){
        this.duration = newDuration;
    }

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
