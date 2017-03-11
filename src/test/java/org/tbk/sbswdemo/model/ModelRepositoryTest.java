package org.tbk.sbswdemo.model;

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
public class ModelRepositoryTest {

    @Autowired
    private OemRepository oemRepository;

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private ModelRepository sut;

    private Oem testOem;
    private Series testSeries;


    @Before
    public void setup() throws Exception {
        testOem = new Oem("TEST", "TEST");
        oemRepository.save(testOem);

        testSeries = new Series("test1", testOem);
        seriesRepository.save(testSeries);
    }

    @Test
    public void save() throws Exception {
        final Model item = new Model("test1", testSeries);

        sut.saveAndFlush(item);

        final Model findOne = sut.findOne(item.getId());
        assertThat(findOne, is(notNullValue()));
    }


    @Test
    public void findBySeries() throws Exception {
        final Model item = new Model("test1", testSeries);

        sut.saveAndFlush(item);

        final List<Model> byUser = sut.findBySeries(testSeries, Application.standardPageRequest.get())
                .getContent();
        assertThat(byUser, hasSize(greaterThan(0)));
    }

}
