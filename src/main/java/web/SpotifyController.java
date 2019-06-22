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
// import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
// import org.springframework.web.bind.annotation.RequestMethod;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
// import com.wrapper.spotify.model_objects.specification.Playlist;
// import java.io.IOException;
// import java.util.List;
import spotify.SpotifyUser;
// import spotify.SpotifyUtils;
// import spotify.Song;
// import data.FilterOptions;
import repos.UserRepository;
import data.User;

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

    @Value("${spotify.clientId}")
    private String clientId;

    @Value("${spotify.clientSecret}")
    private String clientSecret;

    // Instance variable for the current spotifyUser who signed in.
    private SpotifyUser spotifyUser = null;

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
        User dbUser = userRepository.findByUsername(spotifyUsername);

        if (dbUser == null) {
            User newUser = new User(spotifyUsername);
            userRepository.save(newUser);
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
    public String addToPlaylist(Model model) {
        if (spotifyUser == null) {
            return "errorPage";
        }
        return "queues";
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
