package ca.mcgill.ecse321group1.gamestore.Service;

import ca.mcgill.ecse321group1.gamestore.model.*;
import ca.mcgill.ecse321group1.gamestore.repository.*;
import ca.mcgill.ecse321group1.gamestore.service.CustomerOrderService;
import ca.mcgill.ecse321group1.gamestore.service.CustomerService;
import ca.mcgill.ecse321group1.gamestore.service.VideoGameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CustomerOrderServiceTests {
    @Mock
    private CustomerOrderRepository repo;
    @Mock
    private VideoGameRepository gamerepo;
    @Mock
    private CustomerRepository custrepo;
    @Mock
    private CategoryRepository catrepo;
    @Mock
    private OfferRepository offerrepo;

    @InjectMocks
    private CustomerOrderService service;
    @InjectMocks
    private CustomerService custservice;
    @InjectMocks
    private VideoGameService vidservice;


    private final VideoGame[] games = new VideoGame[3];
    private final String[] fields = new String[]{"BobWilson", "Bobby@gmail.com", "Password123", "123 Mason Road, USA", "+2 248 893 524"};
    private Customer BOB;
    private Offer DigimonX, Spore;
    private Date cur;
    private String address;
    @BeforeEach
    public void preparationCustomerOrder() {
        Category action = new Category();
        action.setName("Action");
        action.setDescription("Boom!");
        Category dating = new Category();
        dating.setName("Dating");
        dating.setDescription("MCQ w/o answer key");
        action.setId(12);
        dating.setId(13);
        when(catrepo.save(action)).thenReturn(action);
        when(catrepo.save(dating)).thenReturn(dating);
        action = catrepo.save(action);
        dating = catrepo.save(dating);

        games[0] = new VideoGame();
        games[0].setName("Digimon X");
        games[0].setDescription("Newest Digimon game!");
        games[0].setQuantity(12);
        games[0].setPrice(32.23f);
        games[0].setStatus(VideoGame.Status.Active);
        games[0].setCategory(action);
        games[0].setDate(new Date(10000));
        games[0].setId(12);

        games[1] = new VideoGame();
        games[1].setName("Digimon XL");
        games[1].setDescription("Really The Newest Digimon game!");
        games[1].setQuantity(18);
        games[1].setPrice(362.23f);
        games[1].setStatus(VideoGame.Status.Pending);
        games[1].setCategory(action);
        games[1].setDate(new Date(100002));
        games[1].setId(13);

        games[2] = new VideoGame();
        games[2].setName("Spore");
        games[2].setDescription("Evolve or die.");
        games[2].setQuantity(122);
        games[2].setPrice(312.23f);
        games[2].setStatus(VideoGame.Status.Pending);
        games[2].setCategory(dating);
        games[2].setDate(new Date(1000089));
        games[2].setId(14);

        for (VideoGame game : games) {
            when(gamerepo.save(game)).thenReturn(game);
            when(gamerepo.existsById(game.getId())).thenReturn(true);
            when(gamerepo.findVideoGameById(game.getId())).thenReturn(game);
        }

        BOB = new Customer(87, fields[0], fields[1], PersonServiceTestHelper.hash_password(fields[2]), fields[3], fields[4]);
        when(custrepo.save(BOB)).thenReturn(BOB);
        when(custrepo.findCustomerById(BOB.getId())).thenReturn(BOB);
        when(custrepo.existsById(BOB.getId())).thenReturn(true);

        DigimonX = new Offer(12, "MAY DAY DIGIMON X SALE", "20% off on all Digimon Games", "20%", new Date(0), new Date(100000000000L));
        DigimonX.setVideoGame(games[0]);
        Spore = new Offer(18, "Spore off", "4.4$ off on all Digimon Games", "4.4", new Date(0), new Date(100000000000L));
        Spore.setVideoGame(games[2]);

        ArrayList<Offer> list = new ArrayList<>();
        list.add(DigimonX);
        list.add(Spore);
        when(offerrepo.findAll()).thenReturn(list);

        BOB = custservice.addToCart(BOB.getId(), games[0].getId(), 2);
        BOB = custservice.addToCart(BOB.getId(), games[1].getId(), 8);
        BOB = custservice.addToCart(BOB.getId(), games[2].getId(), 16);
        cur = new Date(123122312);
        address = "987 ABC Drive, WO, WA, WR, WN, WY, HW";
    }

    @Test
    public void testGenerateOrdersGetOrders(){//best to put together, since one is the stringified output of the other.
        List<CustomerOrder> orders = service.generateOrdersFromCustomerCart(BOB, cur, address, vidservice);
        assertEquals(3, orders.size());

        when(repo.findAll()).thenReturn(orders);
        String string = custservice.getPastOrdersString(orders.get(0).getSharedId());
        ArrayList<String> orders_str_lines = new ArrayList<>(List.of(string.split("\n")));
        String answer = "";
        //str.append(String.format("%dx \"%s\"%s for $%.2f\n", order.getQuantity(), order.getPurchased().getName(), offerStr, order.getPrice()));
        //str.append(String.format("\n#%s —— %s\n%s\n", first.getSharedId(), first.getDate(), first.getAddress()));
        //            str.append("--------------------\n");
        answer += String.format("%dx \"%s\" (%s -%s) for $%.2f\n", 2, games[0].getName(), DigimonX.getName(), DigimonX.getEffect(), games[0].getPrice() * 2 * 0.8);
        answer += String.format("%dx \"%s\"%s for $%.2f\n", 8, games[1].getName(), "", games[1].getPrice() * 8);
        answer += String.format("%dx \"%s\" (%s -%s) for $%.2f\n", 16, games[2].getName(), Spore.getName(), Spore.getEffect(), (games[2].getPrice() - 4.4f) * 16);
        answer += String.format("#%s —— %s\n%s\n", orders.get(0).getSharedId(), cur, address);
        answer += String.format("$%.2f", games[0].getPrice() * 2 * 0.8 + (games[2].getPrice() - 4.4f) * 16 + games[1].getPrice() * 8);

        for (String line : answer.split("\n")) assertTrue(orders_str_lines.contains(line));//check that they match
        assertEquals(String.format("%.2f", games[0].getPrice() * 2 * 0.8 + (games[2].getPrice() - 4.4f) * 16 + games[1].getPrice() * 8), String.format("%.2f", service.getTotalPrice(orders.get(0).getSharedId())));
    }

    @Test
    public void testGetOrders() {
        List<CustomerOrder> orders = service.generateOrdersFromCustomerCart(BOB, cur, address, vidservice);
        when(repo.findAll()).thenReturn(orders);//pretty stupid test but this is the only real way to do it with Mockito
        List<CustomerOrder> new_orders = service.getCustomerOrders(orders.get(0).getSharedId());
        //all in one order, now we match
        for (CustomerOrder order : orders) assertTrue(new_orders.contains(order));
        for (CustomerOrder order : new_orders) assertTrue(orders.contains(order));
    }

    @Test
    public void testDeleteOrder() {
        List<CustomerOrder> orders = service.generateOrdersFromCustomerCart(BOB, cur, address, vidservice);
        when(repo.findAll()).thenReturn(orders);
        service.deleteCustomerOrders(orders.get(0).getSharedId());
        List<CustomerOrder> new_orders = service.getCustomerOrders(orders.get(0).getSharedId());

        verify(repo, times(3)).deleteById(any(Integer.class));

    }

    @Test
    public void testSatisfaction () {
        List<CustomerOrder> orders = service.generateOrdersFromCustomerCart(BOB, cur, address, vidservice);
        orders.sort((a,b) -> (int)(a.getPrice() - b.getPrice()));//so I always know the order for testing purposes.

        when(repo.findAll()).thenReturn(orders);
        service.setSatisfied(true, List.of(orders.get(0), orders.get(2)));

        assertTrue(orders.get(0).getSatisfied());//spore
        assertFalse(orders.get(1).getSatisfied());//x
        assertTrue(orders.get(2).getSatisfied());//xl
        verify(repo, times(5)).save(any(CustomerOrder.class));//3 on creation, 2 on edit.
        service.setSatisfied(false, List.of(orders.get(2)));

        List<CustomerOrder> unsat1 = service.getAllUnsatisfied(games[0].getId()),//x
                unsat2 = service.getAllUnsatisfied(games[1].getId()),//xl
                unsat3 = service.getAllUnsatisfied(games[2].getId());//spore
        assertEquals(0, unsat1.size());//x
        assertEquals(1, unsat2.size());//xl
        assertEquals(1, unsat3.size());//spore

        assertEquals(orders.get(1), unsat2.get(0));
        assertEquals(orders.get(2), unsat3.get(0));

    }

}
