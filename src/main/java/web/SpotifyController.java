package web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
// import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.http.MediaType;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.model_objects.specification.Playlist;
// import java.io.IOException;
import java.util.List;
import spotify.SpotifyUser;
import spotify.SpotifyUtils;
import spotify.Song;
// import data.FilterOptions;
import repos.UserRepository;
import repos.QueueRepository;
import data.*;

/**
 * Controller which hadles all the requests after the user is already logged in.
 * Holds a session variable of spotifyUser. So that the user only needs to sign
 * in once and this state object is saved until the session is over or the user
 * logs out.
 */
@ComponentScan
@Controller
@Scope("session")
public class SpotifyController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    QueueRepository queueRepository;

    @Value("${spotify.clientId}")
    private String clientId;

    @Value("${spotify.clientSecret}")
    private String clientSecret;

    // Instance variable for the current spotifyUser who signed in.
    private SpotifyUser spotifyUser = null;
    private User dbUser = null;

    /*
        If no parameters are specified, redirect to home page.
    */
    @RequestMapping(value = "/playlistCreator")
    public ModelAndView playlistCreator() {
        return new ModelAndView(new RedirectView("/"));
    }

    /*
        Takes the users code returned from auth process and creates a spotify
        user object by signing in to the api using the code.
    */
    @RequestMapping(value = "/playlistCreator", params = "code")
    public ModelAndView playlistCreator(@RequestParam("code") String code, Model model) {
        spotifyUser = new SpotifyUser(clientId, clientSecret, code);
        String spotifyUsername = spotifyUser.getUserID();
        dbUser = userRepository.findByUsername(spotifyUsername);

        if (dbUser == null) {
            dbUser = new User(spotifyUsername);
            userRepository.save(dbUser);
        }

        return new ModelAndView(new RedirectView("/options"));
    }

    /*
        Options page to select action.
    */
    @RequestMapping(value = "/options")
    public String pickOptions() {
        if (spotifyUser == null) {
            return "errorPage";
        }
        return "options";
    }

    @RequestMapping(value = "/queues")
    public String queues(Model model) {
        if (spotifyUser == null) {
            return "errorPage";
        }
        return "queues";
    }

    @RequestMapping(value = "/create_queue", method = RequestMethod.GET)
    public RedirectView createQueue(Model model, RedirectAttributes attributes) {
        Queue q = new Queue("new_queue", dbUser);
        q = queueRepository.save(q);
        attributes.addAttribute("queueID", ""+q.getId());
        return new RedirectView("queue");
    }

    @RequestMapping(value = "/queue", params = "queueID", method = RequestMethod.GET)
    public String queue(@RequestParam("queueID") String queueID, Model model) {
        model.addAttribute("queueID", queueID);
        return "queue";
    }

    @RequestMapping(value = "/queue/add_component", params = "queueID", method = RequestMethod.GET)
    public String createQueueSubmit(@RequestParam("queueID") String queueID, Model model) {
        PlaylistSimplified[] playlists = spotifyUser.getUserPlaylists();
        model.addAttribute("playlists", playlists);
        model.addAttribute("queueID", queueID);
        model.addAttribute("queueInfo", new QueueInfo());
        return "add_component";
    }

    @RequestMapping(value = "/queue/add_component", params = "queueID", method = RequestMethod.POST)
    public RedirectView createQueueSubmit(@RequestParam("queueID") String queueID, @ModelAttribute QueueInfo queueInfo, Model model, RedirectAttributes attributes) {
        // Add queue info from submit to database
        attributes.addAttribute("queueID", queueID);
        return new RedirectView("/queue");
    }

    @RequestMapping(value = "/loadPlaylistData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody PlaylistInfo loadGenre(@RequestBody PlaylistCriteria playlistCriteria) {
        Playlist p = spotifyUser.getPlaylistByID(playlistCriteria.getPlaylistID());
        List<Song> songs = spotifyUser.getTracksFromPlaylist(p);
        SpotifyUtils su = new SpotifyUtils();
        List<String> genres = su.getGenres(songs);
        List<String> albums = su.getAlbums(songs);
        List<String> artists = su.getArtists(songs);
        PlaylistInfo pi = new PlaylistInfo();
        pi.setGenres(genres);
        pi.setAlbums(albums);
        pi.setArtists(artists);
        return pi;
    }

    /*
        Sets session variable to null and redirects to home page.
    */
    @RequestMapping(value = "/signout")
    public ModelAndView signout() {
        spotifyUser = null;
        return new ModelAndView(new RedirectView("/"));
    }

}
