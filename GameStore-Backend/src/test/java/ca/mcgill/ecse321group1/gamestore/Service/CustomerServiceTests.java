package ca.mcgill.ecse321group1.gamestore.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.*;

import ca.mcgill.ecse321group1.gamestore.model.Category;
import ca.mcgill.ecse321group1.gamestore.model.VideoGame;
import ca.mcgill.ecse321group1.gamestore.repository.*;
import ca.mcgill.ecse321group1.gamestore.service.CustomerService;
import ca.mcgill.ecse321group1.gamestore.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerServiceTests {
    @Mock
    private CustomerRepository repo;
    @Mock
    private StaffRepository staff_repo;
    @Mock
    private OwnerRepository owner_repo;
    @Mock
    private VideoGameRepository gamerepo;
    @Mock
    private CategoryRepository catrepo;

    @InjectMocks
    private CustomerService service;


    private Customer BOB;
    private final int ID = 31;
    private final String[] fields = new String[]{"BobWilson", "Bobby@gmail.com", "Password123", "123 Mason Road, USA", "+2 248 893 524"};

    @BeforeEach
    public void preparationCustomer() {
        BOB = new Customer(ID, fields[0], fields[1], PersonServiceTestHelper.hash_password(fields[2]), fields[3], fields[4]);
        when(staff_repo.findAll()).thenReturn(new ArrayList<>()); // no existing staff
        when(owner_repo.findAll()).thenReturn(new ArrayList<>()); // no existing owner
    }

    @SuppressWarnings("null")
    @Test
    public void testCreateValidCustomer() {
        // Arrange
        when(repo.save(any(Customer.class))).thenReturn(BOB);
        when(repo.findAll()).thenReturn(new ArrayList<>()); // Simulating that BOB already exists

        // Act
        Customer createdCustomer = service.createCustomer(fields[0], fields[1], fields[2], fields[3], fields[4]);

        // Assert
        assertNotNull(createdCustomer);
        assertEquals(fields[0], createdCustomer.getUsername());
        assertEquals(fields[1], createdCustomer.getEmail());
        assertEquals(PersonServiceTestHelper.hash_password(fields[2]), createdCustomer.getPasswordHash());
        assertEquals(fields[3], createdCustomer.getAddress());
        assertEquals(fields[4], createdCustomer.getPhoneNumber());
        verify(repo, times(1)).save(BOB);//saved once, on creation
    }
    @SuppressWarnings("null")
    @Test
    public void testCreateInvalidCustomer() {
        // Arrange
        // Act
        // Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                service.createCustomer(fields[0], fields[1], "STRONG_PASSWORD", fields[3], fields[4]));
        assertEquals("Passwords must be at least 8 characters long, contain an uppercase and lowercase letter, and at least one number.", e.getMessage());
    }

    @Test
    public void testReadCustomerByValidId() {
        // Arrange
        when(repo.findCustomerById(ID)).thenReturn(BOB);

        // Act
        Customer retrieved = service.getCustomer(ID);

        // Assert
        assertNotNull(retrieved);
        assertEquals(fields[0], retrieved.getUsername());
        assertEquals(fields[1], retrieved.getEmail());
        assertEquals(PersonServiceTestHelper.hash_password(fields[2]), retrieved.getPasswordHash());
        assertEquals(fields[3], retrieved.getAddress());
        assertEquals(fields[4], retrieved.getPhoneNumber());
    }

    @Test
    public void testReadCustomerByInvalidId() {
        // Set up
        int id = 42;
        // Default is to return null, so you could omit this
        when(repo.findCustomerById(id)).thenReturn(null);

        // Act
        // Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.getCustomer(id));
        assertEquals("There is no customer with ID " + id + ".", e.getMessage());
    }

    @Test
    public void testDeleteCustomerByValidID() {
        // Arrange
        int id = 12;
        when(repo.existsById(id)).thenReturn(true);
        //when(repo.findCategoryById(id)).thenReturn();

        // Act
        service.deleteCustomer(id);

        // Assert
        verify(repo, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteCustomerByInvalidID() {
        // Arrange
        int id = 42;
        when(repo.existsById(id)).thenReturn(false);

        // Act
        // Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.deleteCustomer(id));
        assertEquals(id + " cannot be deleted as it does not correspond to an extant Customer!", e.getMessage());
    }

    @Test
    public void testCreateInvalidDuplicateCustomer() {
        // Arrange
        when(repo.findAll()).thenReturn(new ArrayList<>(Collections.singleton(BOB))); // Simulating that BOB already exists

        // Act

        // Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.createCustomer(fields[0], fields[1], fields[2], fields[3], fields[4]));
        assertEquals("Customer with username \"" + fields[0] + "\" already exists!", e.getMessage());
    }

    @Test
    public void testEditValidCustomer() {
        //Arrange
        String new_username = "Jane Doe";
        String new_email = "PullitzerPrize@WashingtonPst.com"; //not a scam


        // Mock behavior of repository
        when(repo.findCustomerById(ID)).thenReturn(BOB);
        when(repo.save(any(Customer.class))).thenReturn(BOB);

        // Act
        Customer edited = service.editCustomer(ID, new_username, new_email, fields[2], fields[3], fields[4]);

        // Assert
        assertNotNull(edited);
        assertEquals(new_username, edited.getUsername());
        assertEquals(new_email, edited.getEmail());
        assertEquals(PersonServiceTestHelper.hash_password(fields[2]), edited.getPasswordHash());
        assertEquals(fields[3], edited.getAddress());
        assertEquals(fields[4], edited.getPhoneNumber());
        verify(repo, times(1)).save(BOB);
    }

    @Test
    public void testAddToCartOrWishlist() {
        Category cat = new Category();
        cat.setName("Questing");
        cat.setDescription("Knights and stuff");
        cat.setId(12312);
        when(catrepo.save(cat)).thenReturn(cat);
        cat = catrepo.save(cat);
        VideoGame game = new VideoGame();
        game.setName("Far Cry XLI");
        game.setDescription("Far Cry XL II — XLI");
        game.setCategory(cat);
        game.setDate(new Date(100000000000L));
        game.setPrice(1.2F);
        game.setQuantity(10002);
        game.setStatus(VideoGame.Status.Active);
        game.setId(123132);
        when(gamerepo.save(game)).thenReturn(game);
        game = gamerepo.save(game);

        // Mock behavior of repository
        when(repo.findCustomerById(ID)).thenReturn(BOB);
        when(repo.save(any(Customer.class))).thenReturn(BOB);

        when(gamerepo.existsById(any(Integer.class))).thenReturn(true);
        when(gamerepo.findVideoGameById(game.getId())).thenReturn(game);

        // Act
        service.addToCart(BOB.getId(), game.getId(), 3);
        service.addToWishlist(BOB.getId(), game.getId());

        // Assert
        assertEquals(3, BOB.getCart().size());
        assertEquals(1, BOB.getWishlist().size());
        assertTrue(BOB.getCart().contains(game));
        assertTrue(BOB.getWishlist().contains(game));
        verify(repo, times(2)).save(BOB);
    }

    @Test
    public void testRemoveFromCartWishlist() {
        Category cat = new Category();
        cat.setName("Questing");
        cat.setDescription("Knights and stuff");
        cat.setId(12312);
        when(catrepo.save(cat)).thenReturn(cat);
        cat = catrepo.save(cat);
        VideoGame game = new VideoGame();
        game.setName("Far Cry XLI");
        game.setDescription("Far Cry XL II — XLI");
        game.setCategory(cat);
        game.setDate(new Date(100000000000L));
        game.setPrice(1.2F);
        game.setQuantity(10002);
        game.setStatus(VideoGame.Status.Active);
        game.setId(123132);
        when(gamerepo.save(game)).thenReturn(game);
        game = gamerepo.save(game);

        // Mock behavior of repository
        when(repo.findCustomerById(ID)).thenReturn(BOB);
        when(repo.save(any(Customer.class))).thenReturn(BOB);

        when(gamerepo.existsById(any(Integer.class))).thenReturn(true);
        when(gamerepo.findVideoGameById(game.getId())).thenReturn(game);

        service.addToCart(BOB.getId(), game.getId(), 3);
        service.addToWishlist(BOB.getId(), game.getId());

        // Act + Assert
        service.removeFromCart(BOB.getId(), game.getId());
        assertEquals(0, BOB.getCart().size());
        service.addToCart(BOB.getId(), game.getId(), 8);
        service.removeAllFromCart(BOB.getId());
        service.removeFromWishlist(BOB.getId(), game.getId());
        // Assert
        assertEquals(0, BOB.getWishlist().size());
        assertEquals(0, BOB.getCart().size());
        verify(repo, times(7)).save(BOB);
    }

    //getPastOrdersString
}