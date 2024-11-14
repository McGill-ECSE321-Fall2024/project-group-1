package ca.mcgill.ecse321group1.gamestore.service;

import java.util.*;

import ca.mcgill.ecse321group1.gamestore.model.*;
import ca.mcgill.ecse321group1.gamestore.repository.OwnerRepository;
import ca.mcgill.ecse321group1.gamestore.repository.StaffRepository;
import ca.mcgill.ecse321group1.gamestore.repository.VideoGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321group1.gamestore.repository.CustomerRepository;
import jakarta.transaction.Transactional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private OwnerRepository ownerRepo;
    @Autowired
    private StaffRepository staffRepo;
    @Autowired
    private VideoGameRepository gamerepo;

    /**Retrieves a Customer object based on lookup by id*/
    @Transactional
    public Customer getCustomer(int cid) {
        Customer c = customerRepo.findCustomerById(cid);
        if (c == null) {
            throw new IllegalArgumentException("There is no customer with ID " + cid + ".");
        }
        return c;
    }

    /**Creates a new Customer object with given attributes, and stores it into the databases*/
    @Transactional
    public Customer createCustomer(String username, String email, String password, String address, String phonenumber) {
        //returns hashed password if it doesn't throw an exception.
        String hashed = PersonServiceHelper.attemptCreate(username, email, password, customerRepo, staffRepo, ownerRepo);

        Customer custToCreate = new Customer();
        custToCreate.setUsername(username);
        custToCreate.setEmail(email);
        custToCreate.setAddress(address);
        custToCreate.setPhoneNumber(phonenumber);
        custToCreate.setPasswordHash(hashed);
        return customerRepo.save(custToCreate);//no ID, gets set by this.
    }

    /**Retrieves a Customer by ID, and modifies its attributes according to editCustomer's arguments before re-storing*/
    @Transactional
    public Customer editCustomer(int id, String username, String email, String password, String address, String phonenumber) {
        Customer customer = getCustomer(id);

        String hashed = PersonServiceHelper.attemptCreate(username, email, password, customerRepo, staffRepo, ownerRepo);//throws exceptions if invalid, otherwise returns password hash

        customer.setUsername(username);
        customer.setEmail(email);
        customer.setAddress(address);
        customer.setPhoneNumber(phonenumber);
        customer.setPasswordHash(hashed);
        return customerRepo.save(customer);//already has an ID;
    }
    /**Deletes a Customer, permanently.*/
    @Transactional
    public void deleteCustomer(int id) {
        if (!customerRepo.existsById(id))
            throw new IllegalArgumentException(id + " cannot be deleted as it does not correspond to an extant Customer!");
        customerRepo.deleteById(id);//no error checking technically necessary but it's best to let people know when they are wrong
    }

    /**Adds quantity number of a single game to customer cart*/
    @Transactional
    public Customer addToCart(int customer_id, int game_id, int quantity) {
        if (!gamerepo.existsById(game_id))
            throw new IllegalArgumentException(game_id + " cannot be added to cart as it does not correspond to an extant VideoGame!");
        if (quantity < 1)
            throw new IllegalArgumentException("Quantity of games added must be positive!");
        Customer customer = getCustomer(customer_id);
        VideoGame game = gamerepo.findVideoGameById(game_id);
        for (int i = 0; i < quantity; i++) customer.addCart(game);
        return customerRepo.save(customer);
    }

    /**Adds a game to the wishlist. Must not already be there.*/
    @Transactional
    public Customer addToWishlist(int customer_id, int game_id) {
        Customer customer = getCustomer(customer_id);
        if (!gamerepo.existsById(game_id))
            throw new IllegalArgumentException(game_id + " cannot be added to wishlist as it does not correspond to an extant VideoGame!");
        List<VideoGame> wishlist = customer.getWishlist();
        for (VideoGame game : wishlist) if (game.getId() == game_id)
            throw new IllegalArgumentException(game_id + " is already in the wishlist!");
        VideoGame game = gamerepo.findVideoGameById(game_id);
        customer.addWishlist(game);
        return customerRepo.save(customer);
    }

    /**Removes all copies of a specific game from cart.*/
    @Transactional
    public Customer removeFromCart(int customer_id, int game_id) {
        Customer customer = getCustomer(customer_id);
        if (!gamerepo.existsById(game_id))
            throw new IllegalArgumentException(game_id + " cannot be removed from cart as it does not correspond to an extant VideoGame!");
        VideoGame game = gamerepo.findVideoGameById(game_id);
        while(customer.removeCart(game));
        return customerRepo.save(customer);
    }
    /**Removes all copies of a specific game from wishlist.*/
    @Transactional
    public Customer removeFromWishlist(int customer_id, int game_id) {
        Customer customer = getCustomer(customer_id);
        if (!gamerepo.existsById(game_id))
            throw new IllegalArgumentException(game_id + " cannot be removed from wishlist as it does not correspond to an extant VideoGame!");
        VideoGame game = gamerepo.findVideoGameById(game_id);
        customer.removeWishlist(game);
        return customerRepo.save(customer);
    }
    /**Returns the string value of every past order of the Customer.*/
    @Transactional
    public String getPastOrdersString(int customer_id) {
        if (!customerRepo.existsById(customer_id))
            throw new IllegalArgumentException(customer_id + "'s cart cannot be updated as it does not correspond to an extant Customer!");
        Customer customer = customerRepo.findCustomerById(customer_id);
        List<CustomerOrder> orders = customer.getCustomerOrders();
        HashMap<Integer, ArrayList<CustomerOrder>> map = new HashMap<>();
        for (CustomerOrder order : orders) {
            if (map.containsKey(order.getSharedId())) map.get(order.getSharedId()).add(order);
            else {
                ArrayList<CustomerOrder> new_arr = new ArrayList<>();
                new_arr.add(order);
                map.put(order.getSharedId(), new_arr);
            }
        }//group duplicate instances in a list together, ie so [gameA, gameA, gameB] becomes 2 gameA, 1 gameB
        StringBuilder str = new StringBuilder().append("------------------------------\n");
        for (ArrayList<CustomerOrder> arr : map.values()) {
            float subtotal = 0;
            for (CustomerOrder order : arr) {
                subtotal += order.getPrice();
                String offerStr = "";
                if (order.getOfferApplied() != null) offerStr = String.format(" (%s %s)", order.getOfferApplied().getName(), "-" + order.getOfferApplied().getEffect());
                str.append(String.format("%dx \"%s\"%s for $%.2f\n", order.getQuantity(), order.getPurchased().getName(), offerStr, order.getPrice()));
                // '3 "Sowing Simulator Seven" (May Day Super Sale -30%) $31.29'
            }
            str.append("$").append(String.format("%7.2f", subtotal));
            CustomerOrder first = arr.get(0);
            str.append(String.format("\n#%s —— %s\n%s\n", first.getSharedId(), first.getDate(), first.getAddress()));
            str.append("--------------------\n");//append address, sharedId and date information at end
        }
        return str.toString();
    }

    /**Clears cart of a customer.*/
    @Transactional
    public Customer removeAllFromCart (int customer_id) {
        Customer customer = getCustomer(customer_id);//ensure it exists
        HashSet<VideoGame> set = new HashSet<>(customer.getCart());
        for (VideoGame game : set) customer = removeFromCart(customer_id, game.getId());
        return customerRepo.save(customer);
    }
}