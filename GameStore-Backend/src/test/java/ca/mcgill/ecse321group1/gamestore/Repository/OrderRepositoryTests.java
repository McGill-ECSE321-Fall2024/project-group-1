@SpringBootTest
public class OrderRepositoryTests {
    @Autowired
    private OrderRepository orderRepository;

    @AfterEach
    public void clearDatabase() {
        orderRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadOrder() {

    }
}