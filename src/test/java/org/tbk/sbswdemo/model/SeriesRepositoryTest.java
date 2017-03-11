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
public class SeriesRepositoryTest {

    @Autowired
    private OemRepository oemRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private SeriesRepository sut;


    private Oem testOem;

    @Before
    public void setup() throws Exception {
        testOem = new Oem("TEST", "TEST");
        oemRepository.save(testOem);
    }

    @Test
    public void save() throws Exception {
        final Series series = new Series("list1", testOem);

        sut.saveAndFlush(series);

        final Series findOne = sut.findOne(series.getId());
        assertThat(findOne, is(notNullValue()));
    }

    @Test
    public void findByName() throws Exception {
        final Series series = new Series("list1", testOem);
        sut.saveAndFlush(series);

        final List<Series> byName = sut.findByName(series.getName(), Application.standardPageRequest.get())
                .getContent();
        assertThat(byName, hasSize(greaterThan(0)));
    }


    @Test
    public void findByOem() throws Exception {
        final Series series = new Series("list1", testOem);

        sut.saveAndFlush(series);

        final List<Series> byUser = sut.findByOem(testOem, Application.standardPageRequest.get())
                .getContent();
        assertThat(byUser, hasSize(greaterThan(0)));
    }

    @Test
    public void findByModel() throws Exception {

        final Series series = new Series("list1", testOem);

        sut.saveAndFlush(series);

        Model item1 = new Model("item1", series);
        Model item2 = new Model("item2", series);

        final List<Model> items = Lists.newArrayList(item1, item2);
        modelRepository.save(items);

        final List<Series> byUser = sut.findByModels(item1, Application.standardPageRequest.get())
                .getContent();
        assertThat(byUser, hasSize(greaterThan(0)));
    }
}
