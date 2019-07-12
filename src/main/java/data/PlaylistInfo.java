package data;

import java.util.List;

public class PlaylistInfo {
    private List<String> genres;
    private List<String> artists;
    private List<String> albums;

    public List<String> getGenres() {
        return genres;
    }

    public List<String> getArtists() {
        return artists;
    }

    public List<String> getAlbums() {
        return albums;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public void setArtists(List<String> artists) {
        this.artists = artists;
    }

    public void setAlbums(List<String> albums) {
        this.albums = albums;
    }
}
