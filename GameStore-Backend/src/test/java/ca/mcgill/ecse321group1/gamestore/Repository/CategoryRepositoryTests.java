@SpringBootTest
public class CategoryRepositoryTests {
    @Autowired
    private CategoryRepository categoryRepository;

    @AfterEach
    public void clearDatabase() { categoryRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadCategory() {

    }
}