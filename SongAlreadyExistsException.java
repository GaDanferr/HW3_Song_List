/**
 * This exception-class is thrown by Playlist's method add() to inidicate a song is already inside the
 * playlist.
 */
public class SongAlreadyExistsException extends RuntimeException{
    public SongAlreadyExistsException() {}
    public SongAlreadyExistsException(String message) {
        super(message);
    }
    public SongAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}
