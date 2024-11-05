package ca.mcgill.ecse321group1.gamestore.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321group1.gamestore.model.Offer;

import ca.mcgill.ecse321group1.gamestore.model.VideoGame;
import ca.mcgill.ecse321group1.gamestore.repository.OfferRepository;
import jakarta.transaction.Transactional;

@Service
public class OfferService {
    @Autowired
    private OfferRepository offerRepo;

    /**Retrieves an Offer object based on lookup by id*/
    @Transactional
    public Offer getOffer(int cid) {
        Offer c = offerRepo.findOfferById(cid);
        if (c == null) {
            throw new IllegalArgumentException("There is no offer with ID " + cid + ".");
        }
        return c;
    }

    /**Creates a new Offer object with given attributes, and stores it into the databases*/
    @Transactional
    public Offer createOffer(String name, String description, String effect, Date start, Date end, VideoGame game) {
        if (name == null || name.length() < 3) {
            throw new IllegalArgumentException("Offer names must be at least 3 characters long!");
        }
        if (description == null || description.length() < 3) {
            throw new IllegalArgumentException("Offer descriptions must be at least 3 characters long!");
        }
        if (!effect.matches("\\d+(%)?|\\d+\\.\\d{0,2}"))
            throw new IllegalArgumentException("Discount format must either be \"XX%\" percent off or \"XX.YY\" flat decrease.");
        if (start.after(end))
            throw new IllegalArgumentException("Start date must be before end date!");

        Offer offer = new Offer();
        offer.setName(name);
        offer.setDescription(description);
        offer.setEffect(effect);
        offer.setStartDate(start);
        offer.setEndDate(end);
        offer.setVideoGame(game);//allowed to be null
        return offerRepo.save(offer);
    }

    /**Retrives an Offer by ID, and modifies its attributes according to editOffer's arguments before re-storing*/
    @Transactional
    public Offer editOffer(int id, String name, String description, String effect, Date start, Date end, VideoGame game) {
        Offer offer = getOffer(id);
        if (name == null || name.length() < 3) {
            throw new IllegalArgumentException("Offer names must be at least 3 characters long!");
        }
        if (description == null || description.length() < 3) {
            throw new IllegalArgumentException("Offer descriptions must be at least 3 characters long!");
        }
        if (!effect.matches("\\d+(%)?|\\d+\\.\\d{0,2}"))
            throw new IllegalArgumentException("Discount format must either be \"XX%\" percent off or \"XX.YY\" flat decrease.");
        if (start.after(end))
            throw new IllegalArgumentException("Start date must be before end date!");

        offer.setName(name);
        offer.setDescription(description);
        offer.setEffect(effect);
        offer.setStartDate(start);
        offer.setEndDate(end);
        offer.setVideoGame(game);//allowed to be null
        return offerRepo.save(offer);
    }
    /**Deletes an Offer, permanently.*/
    @Transactional
    public void deleteOffer(int id) {
        if (!offerRepo.existsById(id))
            throw new IllegalArgumentException(id + " cannot be deleted as it does not correspond to an extant Offer!");
        offerRepo.deleteById(id);//no error checking technically necessary but it's best to let people know when they are wrong
    }
}