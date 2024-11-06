package ca.mcgill.ecse321group1.gamestore.service;

import ca.mcgill.ecse321group1.gamestore.model.Review;
import ca.mcgill.ecse321group1.gamestore.model.VideoGame;
import ca.mcgill.ecse321group1.gamestore.model.Category;
import ca.mcgill.ecse321group1.gamestore.repository.ReviewRepository;
import ca.mcgill.ecse321group1.gamestore.repository.VideoGameRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class VideoGameService {
    @Autowired
    private VideoGameRepository repo;
    @Autowired
    private ReviewRepository revrepo;

    /**Retrieves a VideoGame object based on lookup by id*/
    @Transactional
    public VideoGame getVideoGame(int cid) {
        VideoGame c = repo.findVideoGameById(cid);
        if (c == null) {
            throw new IllegalArgumentException("There is no video game with ID " + cid + ".");
        }
        return c;
    }

    /**Creates a new VideoGame object with given attributes, and stores it into the databases. Starts as Pending.*/
    @Transactional
    public VideoGame createVideoGame(String name, String description, float price, int quantity, Date date, Category category) {
        if (name == null || description == null)
            throw new IllegalArgumentException("VideoGame requires a name and description!");
        if (price < 0)
            throw new IllegalArgumentException("Price must be non-negative!");
        if (quantity < 0)
            throw new IllegalArgumentException("Initial quantity must be nonzero!");

        VideoGame game = new VideoGame();
        game.setName(name);
        game.setDescription(description);
        game.setPrice(price);
        game.setQuantity(quantity);
        game.setDate(date);
        game.setStatus(VideoGame.Status.Pending);
        game.setCategory(category);
        return repo.save(game);//no ID, gets set by this.
    }
    //TODO: approve game, change just quantity,

    /**Moves VideoGame from Pending to Active status*/
    @Transactional
    public VideoGame approveGame (int id) {
        VideoGame tba = getVideoGame(id);
        if(!tba.getStatus().equals(VideoGame.Status.Pending))
            throw new IllegalArgumentException("VideoGame " + id + " is not currently Pending!");
        tba.setStatus(VideoGame.Status.Active);
        return repo.save(tba);
    }

    /**Moves VideoGame to InActive from anything*/
    @Transactional
    public VideoGame deactivateGame (int id) {
        VideoGame tba = getVideoGame(id);
        tba.setStatus(VideoGame.Status.Inactive);
        return repo.save(tba);
    }

    /**Updates VideoGame quantity by a positive or negative number.*/
    @Transactional
    public VideoGame alterQuantity (int id, int amount) {
        VideoGame tba = getVideoGame(id);
        if(tba.getQuantity() + amount < 0)
            throw new IllegalArgumentException("VideoGame " + id + " only has " + tba.getQuantity() + " stock left!");
        tba.setQuantity(tba.getQuantity() + amount);
        return repo.save(tba);
    }

    /**Retrieves a VideoGame by ID, and modifies its attributes according to editVideoGame's arguments before re-storing*/
    @Transactional
    public VideoGame editVideoGame(int id, String name, String description, float price, Date date, VideoGame.Status status, Category category) {
        VideoGame game = getVideoGame(id);

        if (name == null || description == null)
            throw new IllegalArgumentException("VideoGame requires a name and description!");
        if (price < 0)
            throw new IllegalArgumentException("Price must be non-negative!");

        game.setName(name);
        game.setDescription(description);
        game.setPrice(price);
        game.setDate(date);
        game.setStatus(status);
        game.setCategory(category);
        return repo.save(game);//already has an ID;
    }
    /**Deletes a VideoGame, permanently.*/
    @Transactional
    public void deleteVideoGame(int id) {
        if (!repo.existsById(id))
            throw new IllegalArgumentException(id + " cannot be deleted as it does not correspond to an extant VideoGame!");
        repo.deleteById(id);//no error checking technically necessary but it's best to let people know when they are wrong
    }

    /**Returns all Pending VideoGames*/
    @Transactional
    public List<VideoGame> getAllPending() {
        ArrayList<VideoGame> tbr = new ArrayList<>();
        repo.findAll().iterator().forEachRemaining(VG -> {
            if (VG.getStatus().equals(VideoGame.Status.Pending)) tbr.add(VG);
        });
        return tbr;
    }

    /**Returns all VideoGames that have a name or description that match the keyword. Not case sensitive.*/
    @Transactional
    public List<VideoGame> searchByKeyword (String keyword) {
        ArrayList<VideoGame> tbr = new ArrayList<>();
        final String cleared = keyword.toLowerCase();
        repo.findAll().iterator().forEachRemaining(VG -> {
            if (VG.getName().toLowerCase().contains(cleared) || VG.getDescription().toLowerCase().contains(cleared)) tbr.add(VG);
        });
        return tbr;
    }

    /**Returns all VideoGames that have a given Category.*/
    @Transactional
    public List<VideoGame> searchByCategory (int categoryId) {
        ArrayList<VideoGame> tbr = new ArrayList<>();
        repo.findAll().iterator().forEachRemaining(VG -> {
            if (VG.getCategory() != null && VG.getCategory().getId() == categoryId) tbr.add(VG);
        });
        return tbr;
    }

    /**Gets average Rating of VideoGame*/
    public Review.Rating averageRatingOf(int gameId) {
        AtomicInteger val = new AtomicInteger();
        AtomicInteger count = new AtomicInteger();
        revrepo.findAll().iterator().forEachRemaining(r -> {
            if (r.getReviewed().getId() != gameId) return;
            count.getAndIncrement();
            val.addAndGet(r.getRating().ordinal());
        });
        if (count.get() == 0) return null;
        Review.Rating[] ratings = new Review.Rating[]{Review.Rating.oneStar, Review.Rating.twoStar, Review.Rating.threeStar, Review.Rating.fourStar, Review.Rating.fiveStar};
        return ratings[val.get() / count.get()];
    }

}