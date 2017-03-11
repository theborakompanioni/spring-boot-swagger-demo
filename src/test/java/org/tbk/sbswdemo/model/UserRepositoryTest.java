package org.tbk.sbswdemo.model;

import org.assertj.core.util.Lists;
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
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OemRepository oemRepository;

    @Test
    public void findByName() throws Exception {
        User user1 = new User("test", "", Collections.emptyList());
        User user2 = new User("test2", "", Collections.emptyList());

        final List<User> users = Lists.newArrayList(user1, user2);
        userRepository.save(users);

        final List<User> byName = userRepository.findByName(user2.getName(), Application.standardPageRequest.get())
                .getContent();
        assertThat(byName, hasSize(greaterThan(0)));
    }

}
