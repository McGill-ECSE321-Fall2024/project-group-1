package ca.mcgill.ecse321group1.gamestore.service;

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

    /**Retrieves a set of CustomerOrder objects based on lookup by shared_id*/
    @Transactional
    public List<CustomerOrder> getCustomerOrders(int shared_id) {
        ArrayList<CustomerOrder> c = new ArrayList<>();
        repo.findAll().forEach(co -> {
            if (co.getSharedId() == shared_id) c.add(co);
        });
        if (c.size() == 0) {
            throw new IllegalArgumentException("There are no customer orders with ID " + shared_id + ".");
        }
        return c;
    }

    /**Given a Customer with a nonempty cart, creates a set of CustomerOrders and returns shared_id. Cart is unaffected.*/
    @Transactional
    public int generateOrdersFromCustomerCart(Customer customer, Date curDate, String address) {
        if (customer == null)
            throw new IllegalArgumentException("Customer must exist to generate orders form it.");
        if (customer.getCart().isEmpty())
            throw new IllegalArgumentException("Customer cart must be nonempty");
        List<Offer> offers = new ArrayList<>();
        offerRepo.findAll().iterator().forEachRemaining(offers::add);
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

        HashMap<VideoGame, Integer> counter = new HashMap<>();
        for (VideoGame game : customer.getCart()) counter.merge(game, 1, Integer::sum);
        for (VideoGame key : counter.keySet()) {
            int count = counter.get(key);
            float price = key.getPrice();
            Offer used = null;
            for (Offer offer : offers)// game specific offers
                if (offer.getVideoGame() != null && used == null &&
                        offer.getStartDate().before(curDate) && offer.getEndDate().after(curDate)) {
                    used = offer;
                    String effect = used.getEffect();
                    if (effect.endsWith("%")) price *= 1 - Float.parseFloat(effect.substring(0, effect.length() - 1));
                    else price -= Float.parseFloat(effect);
                }
            for (Offer offer : offers)//general offers
                if (offer.getVideoGame() == null && used == null &&
                        offer.getStartDate().before(curDate) && offer.getEndDate().after(curDate)) {
                    used = offer;
                    String effect = used.getEffect();
                    if (effect.endsWith("%")) price *= 1 - Float.parseFloat(effect.substring(0, effect.length() - 1));
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
            repo.save(temp);//save this.
        }
        return SHARED_ID;
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
}