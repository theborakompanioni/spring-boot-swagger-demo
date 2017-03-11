package org.tbk.sbswdemo.model;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.tbk.sbswdemo.Application;
import org.tbk.sbswdemo.config.TestDbConfig;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        TestDbConfig.class
})
@Import(TestDbConfig.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
@Rollback
public class OemRepositoryTest {

    @Autowired
    private OemRepository oemRepository;

    @Autowired
    private UserRepository userRepository;

    User user1;
    User user2;

    List<User> users;
    Oem oem1;
    Oem oem2;

    List<Oem> oems;

    @Before
    public void setUp() {
        this.user1 = new User("test", "password1", Collections.emptyList());
        this.user2 = new User("test2", "password2", Collections.emptyList());

        final List<User> users = Lists.newArrayList(user1, user2);
        userRepository.save(users);
        this.users = users;


        this.oem1 = new Oem("oem1", "description1");
        this.oem2 = new Oem("oem2", "description2");

        final List<Oem> oems = Lists.newArrayList(oem1, oem2);
        oemRepository.save(oems);
        this.oems = oems;

        userRepository.flush();
        oemRepository.flush();
    }

    @Test
    public void findOne() throws Exception {
        Oem findOne = oemRepository.findOne(this.oem1.getId());
        assertThat(findOne, is(notNullValue()));
        assertThat(findOne.getName(), is(equalTo(oem1.getName())));
    }

    @Test
    public void findByName() throws Exception {
        final List<Oem> byUser = oemRepository.findByName(oem1.getName(), Application.standardPageRequest.get())
                .getContent();
        assertThat(byUser, hasSize(greaterThan(0)));
    }
}
