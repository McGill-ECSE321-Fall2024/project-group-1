@SpringBootTest
public class VideoGameRepositoryTests {
    @Autowired
    private VideoGameRepository videoGameRepository;

    @AfterEach
    public void clearDatabase() {
        videoGameRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadVideoGame() {

    }
}