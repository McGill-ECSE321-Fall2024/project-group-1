package ca.mcgill.ecse321group1.gamestore.service;

import java.sql.Array;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import ca.mcgill.ecse321group1.gamestore.model.*;
import ca.mcgill.ecse321group1.gamestore.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class CustomerOrderService {
    @Autowired
    private CustomerOrderRepository repo;
    @Autowired
    private OfferRepository offerRepo;

    /**Uses specific ID. Meant for use in conjunction for conversion from DTO to Model.*/
    @Transactional
    public CustomerOrder getCustomerOrder(int specific_id) {
        CustomerOrder temp = repo.findCustomerOrderById(specific_id);
        if (temp == null) throw new IllegalArgumentException("There is no customer orders with specific ID " + specific_id + ".");
        return temp;
    }

    /**Retrieves a set of CustomerOrder objects based on lookup by shared_id*/
    @Transactional
    public List<CustomerOrder> getCustomerOrders(int shared_id) {
        ArrayList<CustomerOrder> c = new ArrayList<>();
        repo.findAll().forEach(co -> {
            if (co.getSharedId() == shared_id) c.add(co);
        });
        if (c.isEmpty()) throw new IllegalArgumentException("There are no customer orders with ID " + shared_id + ".");

        return c;
    }

    /**Given a Customer with a nonempty cart, creates a set of CustomerOrders and returns shared_id. Cart is unaffected.*/
    @Transactional
    public List<CustomerOrder> generateOrdersFromCustomerCart(Customer customer, Date curDate, String address, VideoGameService vidservice) {
        //the only long method in the service layer. Required due to the complex and unnecessary way we made our UML,
        // and have to abuse it to make a functional service layer.
        if (customer == null)
            throw new IllegalArgumentException("Customer must exist to generate orders form it.");
        if (customer.getCart().isEmpty())
            throw new IllegalArgumentException("Customer cart must be nonempty");
        List<Offer> offers = new ArrayList<>();
        offerRepo.findAll().iterator().forEachRemaining(o -> {
            if (o.getStartDate().before(curDate) && o.getEndDate().after(curDate)) offers.add(o);
        });//get all offers from repo, move to list. Only if valid date.
        int SHARED_ID;
        while (true) {//randomly search integers until a valid shared_id is found
            AtomicBoolean works = new AtomicBoolean(true);
            SHARED_ID = (int)(Integer.MAX_VALUE * Math.random());
            final int finalSHARED_ID = SHARED_ID;
            repo.findAll().forEach(co -> {
                if (co.getSharedId() == finalSHARED_ID) works.set(false);
            });
            if (works.get()) break;
        }
        //at this point we have an unused shared_id to label this set of orders with, and a set of date-valid offers.
        HashMap<VideoGame, Integer> counter = new HashMap<>();
        for (VideoGame game : customer.getCart()) counter.merge(game, 1, Integer::sum);
        //IntelliJ allows you to simplify a bunch of logic into the above.
        // Literally just makes a map that lets you look up a game, and get the number of times it is in the cart
        ArrayList<CustomerOrder> tbr = new ArrayList<>();
        for (VideoGame key : counter.keySet()) {
            int count = counter.get(key);
            if (key.getQuantity() < count) throw new IllegalArgumentException(count + " VideoGames requested but only " + key.getQuantity() + " in stock!");
            else vidservice.alterQuantity(key.getId(), -count);//exception checking probably not necessary since it's checked in alterQuantity.

            float price = key.getPrice();
            Offer used = null;
            for (Offer offer : offers)// game specific offers
                if (used != null) break;
                else if (offer.getVideoGame() != null && key.equals(offer.getVideoGame())) used = offer;

            for (Offer offer : offers)//general offers
                if (used != null) break;//offer already found for this game.
                else if (offer.getVideoGame() == null) used = offer;

            if (used != null) {
                String effect = used.getEffect();
                if (effect.endsWith("%")) price *= 1 - Float.parseFloat(effect.substring(0, effect.length() - 1)) / 100f;//percent deduction
                else price -= Float.parseFloat(effect);
            }
            price *= count;
            CustomerOrder temp = new CustomerOrder();
            temp.setPurchased(key);
            temp.setCustomer(customer);
            temp.setAddress(address);
            temp.setQuantity(count);
            temp.setOfferApplied(used);
            temp.setSharedId(SHARED_ID);
            temp.setPrice(price);
            temp.setDate(curDate);
            tbr.add(temp);
            repo.save(temp);//save this.
        }
        return tbr;
    }

    /**Deletes a set of CustomerOrders denoted by shared_id*/
    @Transactional
    public void deleteCustomerOrders(int shared_id) {
        repo.findAll().forEach(co -> {
            if (co.getSharedId() == shared_id) repo.deleteById(co.getId());
        });
    }

    /**Calculates total price of a set of CustomerOrder of shared_id*/
    @Transactional
    public float getTotalPrice(int shared_id) {
        AtomicReference<Float> total_sum = new AtomicReference<>(0f);
        repo.findAll().forEach(co -> {
            if (co.getSharedId() == shared_id) total_sum.updateAndGet(v -> v + co.getPrice());
        });
        return total_sum.get();
    }

    /**Get all unsatisfied orders of a game.*/
    @Transactional
    public List<CustomerOrder> getAllUnsatisfied (int game_id) {
        ArrayList<CustomerOrder> unsat = new ArrayList<>();
        repo.findAll().forEach(co -> {
            if (co.getPurchased().getId() == game_id && !co.getSatisfied()) unsat.add(co);
        });
        return unsat;
    }

    /**Mark given CustomerOrders as satisfied or not. Does not affect the remainder of the orders in the shared area.*/
    @Transactional
    public void setSatisfied (boolean satisfied, List<CustomerOrder> orders) {
        repo.findAll().forEach(co -> {
            if (orders.contains(co)) {
                co.setSatisfied(satisfied);
                repo.save(co);
            }
        });
    }

    /**Gets all customer orders of a Customer*/
    @Transactional
    public ArrayList<CustomerOrder> getAllOrders(int cid) {
        ArrayList<CustomerOrder> arr = new ArrayList<>();
        repo.findAll().forEach(co -> {
            if (co.getCustomer().getId() == cid) arr.add(co);
        });
        return arr;
    }
}