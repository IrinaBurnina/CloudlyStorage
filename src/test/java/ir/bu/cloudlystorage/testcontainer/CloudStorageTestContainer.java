package ir.bu.cloudlystorage.testcontainer;

import ir.bu.cloudlystorage.dto.FileForRequestDto;
import ir.bu.cloudlystorage.dto.FileForResponseGetDto;
import ir.bu.cloudlystorage.dto.authDto.TokenDto;
import ir.bu.cloudlystorage.dto.authDto.UserDto;
import ir.bu.cloudlystorage.model.CloudUser;
import ir.bu.cloudlystorage.model.File;
import ir.bu.cloudlystorage.repository.FilesRepository;
import ir.bu.cloudlystorage.repository.UsersRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.testcontainers.containers.GenericContainer;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CloudStorageTestContainer {

    private static final GenericContainer<?> app = new GenericContainer<>("cloud_storage_test:latest")
            .withExposedPorts(5500);
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private FilesRepository filesRepository;

    private CloudUser cloudUser;

    @Test
    void contextLoads() {
    }

    @BeforeEach
    public void beforeEach() {
        cloudUser = CloudUser.builder()
                .login("kot")
                .password("kot")
                .token("00002222")
                .build();
        filesRepository.deleteAll();
        usersRepository.deleteAll();
        app.start();
    }

    @Test
    @Order(1)
    public void loginTest() {
        //arrange
        UserDto userDto = UserDto.builder()
                .login("kot")
                .password("123")
                .build();
        String url = "http://localhost:" + app.getMappedPort(8081) + "/login";
        //act
        ResponseEntity<TokenDto> forEntity = testRestTemplate.postForEntity(url,
                userDto,
                TokenDto.class);
        TokenDto token = forEntity.getBody();
        //assert
        Assertions.assertNotNull(token.getAuthToken());
        Assertions.assertFalse(token.getAuthToken().isEmpty());
    }

    @Test
    @Order(2)
    public void hiTest() {
        //arrange
        String url = "http://localhost:" + app.getMappedPort(8081) + "/hi";
        String authToken = "Bearer 0cdfdb12-9b52-4822-8a67-5d0ce2839778";
        HttpHeaders headers = new HttpHeaders();
        headers.add("auth-token", authToken);
        String expected = "OK";
        //act
        ResponseEntity<String> responseEntity = testRestTemplate.exchange(url
                , HttpMethod.GET
                , new HttpEntity<>(headers)
                , String.class);
        String actual = responseEntity.getBody();
        //assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Order(3)
    public void listTest() {
        //arrange
        String fileName = "text3.txt";
        String url = "http://localhost:" + app.getMappedPort(8081) + "/list?limit=3";
        String authToken = "Bearer 0cdfdb12-9b52-4822-8a67-5d0ce2839778";
        HttpHeaders headers = new HttpHeaders();
        headers.add("auth-token", authToken);

        //Save file in storage
        saveFile("http://localhost:" + app.getMappedPort(8081) + "/file?filename=" + fileName
                , headers);

        //act
        ResponseEntity<List<FileForRequestDto>> responseEntity = testRestTemplate.exchange(url
                , HttpMethod.GET
                , new HttpEntity<>(headers), new ParameterizedTypeReference<>() {
                });
        List<FileForRequestDto> files = responseEntity.getBody();
        //assert
        Assertions.assertNotNull(files);
        Assertions.assertFalse(files.isEmpty());
    }

    @Test
    @Order(4)
    public void filePostTest() {
        //arrange
        String url = "http://localhost:" + app.getMappedPort(5500) + "/file?filename=data.sql";
        String authToken = "Bearer 0cdfdb12-9b52-4822-8a67-5d0ce2839778";
        HttpHeaders headers = new HttpHeaders();
        headers.add("auth-token", authToken);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        Resource file = new FileSystemResource("src/main/resources/data.sql");
        body.add("file", file);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, headers);

        HttpStatus expected = HttpStatus.resolve(200);
        //act
        ResponseEntity<String> responseEntity = testRestTemplate.exchange(url
                , HttpMethod.POST
                , httpEntity
                , String.class);
        HttpStatus actual = HttpStatus.resolve(responseEntity.getStatusCode().value());
        //assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Order(5)
    public void fileGetTest() throws IOException {
        String fileName = "text5.txt";
        String url = "http://localhost:" + app.getMappedPort(8081) + "/file?filename=" + fileName;
        String authToken = "Bearer 0cdfdb12-9b52-4822-8a67-5d0ce2839778";
        HttpHeaders headers = new HttpHeaders();
        headers.add("auth-token", authToken);

        //Save file in storage
        Resource file = saveFile("http://localhost:"
                        + app.getMappedPort(5500)
                        + "/file?filename=" + fileName
                , headers);

        HttpStatus expected = HttpStatus.resolve(200);
        //act
        ResponseEntity<Resource> responseEntity = testRestTemplate.exchange(url
                , HttpMethod.GET
                , new HttpEntity<>(headers), Resource.class);
        HttpStatus actual = HttpStatus.resolve(responseEntity.getStatusCode().value());
        //assert
        Assertions.assertEquals(expected, actual);
        Assertions.assertArrayEquals(file.getContentAsByteArray(), responseEntity.getBody().getContentAsByteArray());
    }

    @Test
    @Order(6)
    public void fileUpdateTest() {
        //arrange
        String fileName = "text6.txt";
        String url = "http://localhost:" + app.getMappedPort(8081) + "/file?filename=" + fileName;
        String authToken = "Bearer 0cdfdb12-9b52-4822-8a67-5d0ce2839778";
        HttpHeaders headers = new HttpHeaders();
        headers.add("auth-token", authToken);

        FileForResponseGetDto fileUpdate = FileForResponseGetDto.builder()
                .fileName("data_update_rename.sql")
                .build();
        HttpEntity<FileForResponseGetDto> httpEntity = new HttpEntity<>(fileUpdate, headers);

        //Save file in storage
        saveFile("http://localhost:" + app.getMappedPort(8081) + "/file?filename=" + fileName
                , headers);

        HttpStatus expected = HttpStatus.resolve(200);
        //act
        ResponseEntity<String> responseEntity = testRestTemplate.exchange(url
                , HttpMethod.PUT
                , httpEntity
                , String.class);
        HttpStatus actual = HttpStatus.resolve(responseEntity.getStatusCode().value());
        //assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Order(7)
    public void fileDeleteTest() {
        String fileName = "text7.txt";
        String url = "http://localhost:" + app.getMappedPort(8081) + "/file?filename=" + fileName;
        String authToken = "Bearer 0cdfdb12-9b52-4822-8a67-5d0ce2839778";
        HttpHeaders headers = new HttpHeaders();
        headers.add("auth-token", authToken);

        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);

        //Save file in storage
        saveFile("http://localhost:" + app.getMappedPort(8081) + "/file?filename=" + fileName
                , headers);

        HttpStatus expected = HttpStatus.resolve(200);
        //act
        ResponseEntity<String> responseEntity = testRestTemplate.exchange(url
                , HttpMethod.DELETE
                , httpEntity
                , String.class);
        HttpStatus actual = HttpStatus.resolve(responseEntity.getStatusCode().value());
        //assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Order(8)
    public void logOutTest() {
        //arrange
        String url = "http://localhost:" + app.getMappedPort(8081) + "/logout";
        String authToken = "Bearer 0cdfdb12-9b52-4822-8a67-5d0ce2839778";
        HttpHeaders headers = new HttpHeaders();
        headers.add("auth-token", authToken);

        HttpStatus expected = HttpStatus.resolve(200);
        //act
        ResponseEntity<String> responseEntity = testRestTemplate.exchange(url
                , HttpMethod.POST
                , new HttpEntity<>(headers)
                , String.class);
        HttpStatus actual = HttpStatus.resolve(responseEntity.getStatusCode().value());
        //assert
        Assertions.assertEquals(expected, actual);
    }

    @NotNull
    private Resource saveFile(String url, HttpHeaders headers) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        Resource file = new FileSystemResource("src/main/resources/data.sql");
        body.add("file", file);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> responseSaveFile = testRestTemplate.exchange(url
                , HttpMethod.POST
                , httpEntity
                , String.class);
        return file;
    }

    @Test
    public void saveUser() {
        //arrange
        //act
        CloudUser savedUser = usersRepository.save(cloudUser);
        //assert
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(savedUser.getLogin(), cloudUser.getLogin());
    }

    @Test
    public void getUserByTokenTest() {
        //arrange
        String tokenExpected = "123456";
        CloudUser savedUser = usersRepository.save(cloudUser);
        //act
        Optional<CloudUser> cloudUserOptional = usersRepository.getUserByToken(tokenExpected);
        //assert
        Assertions.assertTrue(cloudUserOptional.isPresent());
        Assertions.assertEquals(tokenExpected, cloudUserOptional.get().getToken());
    }

    @Test
    public void saveFileTest() {
        //arrange
        CloudUser cloudUser1 = usersRepository.save(cloudUser);
        File file = File.builder()
                .fileName("test.bmp")
                .content(new byte[1])
                .data(LocalDate.now())
                .size(1)
                .user(cloudUser1)
                .build();
        //act
        File saveFile = filesRepository.save(file);
        //assert
        Assertions.assertNotNull(saveFile);
        Assertions.assertEquals(saveFile, file);
    }

    @Test
    public void findFileByUserTest() {
        CloudUser cloudUser1 = usersRepository.save(cloudUser);
        File file = File.builder()
                .fileName("test.bmp")
                .content(new byte[1])
                .data(LocalDate.now())
                .size(1)
                .user(cloudUser1)
                .build();
        File saveFile = filesRepository.save(file);
        //act
        List<File> files = filesRepository.findByUser(cloudUser1, PageRequest.of(0, 3));
        //assert
        Assertions.assertFalse(files.isEmpty());
    }
}
