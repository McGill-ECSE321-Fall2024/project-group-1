@SpringBootTest
public class ReplyRepositoryTests {
    @Autowired
    private ReplyRepository replyRepository;

    @AfterEach
    public void clearDatabase() {
        replyRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadReply() {

    }
}