@SpringBootTest
public class StaffRepositoryTests {
    @Autowired
    private StaffRepository staffRepository;

    @AfterEach
    public void clearDatabase() {
        staffRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadStaff() {

    }
}