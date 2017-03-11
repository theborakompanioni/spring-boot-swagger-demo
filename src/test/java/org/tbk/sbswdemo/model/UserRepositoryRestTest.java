package org.tbk.sbswdemo.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.BasicJsonTester;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.tbk.sbswdemo.Application;
import org.tbk.sbswdemo.config.TestDbConfig;
import org.tbk.sbswdemo.demo.DemoService;
import org.tbk.sbswdemo.demo.DemoUser;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {Application.class, TestDbConfig.class}
)
public class UserRepositoryRestTest {
    @Autowired
    private DemoService demoService;

    @Autowired
    private TestRestTemplate restTemplate;

    private BasicJsonTester json = new BasicJsonTester(getClass());

    @Test
    public void getUsers() {
        DemoUser user = demoService.getOrCreateDemoUser();

        ResponseEntity<String> responseEntity = restTemplate
                .withBasicAuth(user.getName(), user.getPassword())
                .getForEntity("/user", String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        json.from(responseEntity.getBody())
                .assertThat()
                .extractingJsonPathNumberValue("page.totalElements")
                .isEqualTo(1);
    }


}
