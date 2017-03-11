package org.tbk.sbswdemo.demo;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.tbk.sbswdemo.Application;
import org.tbk.sbswdemo.model.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class DemoService {
    private static final Logger log = LoggerFactory.getLogger(DemoService.class);

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final OemRepository oemRepository;
    private final SeriesRepository seriesRepository;
    private final ModelRepository modelRepository;

    private final Supplier<DemoUser.DemoUserBuilder> demoUserBuilderSupplier = Suppliers
            .memoize(this::createDemoUserBuilder);

    public DemoService(PasswordEncoder passwordEncoder,
                       UserRepository userRepository,
                       OemRepository oemRepository,
                       SeriesRepository seriesRepository,
                       ModelRepository modelRepository) {
        this.passwordEncoder = requireNonNull(passwordEncoder);
        this.userRepository = requireNonNull(userRepository);
        this.oemRepository = requireNonNull(oemRepository);
        this.seriesRepository = requireNonNull(seriesRepository);
        this.modelRepository = requireNonNull(modelRepository);
    }

    public void printGoalsOfDemoUser() {
        User demoUser = this.getOrCreateDemoUser().getOrigin();

        log.info("Goals of demo user:");
        log.info("-------------------------------");
        log.info("");
    }

    public DemoUser getOrCreateDemoUser() {
        final User user = userRepository.findByName("demo", Application.standardPageRequest.get())
                .getContent()
                .stream()
                .findFirst()
                .orElseGet(() -> createDemoUser().getOrigin());

        return demoUserBuilderSupplier.get()
                .origin(user)
                .build();
    }

    private DemoUser createDemoUser() {
        final DemoUser.DemoUserBuilder demoUserBuilder = demoUserBuilderSupplier.get();
        final ArrayList<String> authorities = Lists.newArrayList("ROLE_ADMIN", "ROLE_USER");

        User demoUser = new User(demoUserBuilder.name(), demoUserBuilder.encryptedPassword(), authorities);

        userRepository.save(demoUser);
        log.info("Created demo user: {}", demoUser);

        oemRepository.save(new Oem("Goal1", "Description1"));
        oemRepository.save(new Oem("Goal2", "Description2"));
        oemRepository.save(new Oem("Goal3", "Description3"));
        oemRepository.save(new Oem("Goal4", "Description4"));
        oemRepository.save(new Oem("Goal5", "Description5"));

        return demoUserBuilder
                .origin(demoUser)
                .build();
    }

    private DemoUser.DemoUserBuilder createDemoUserBuilder() {
        final String username = "demo";
        final String password = "demo";
        return DemoUser.builder()
                .name(username)
                .password(password)
                .encryptedPassword(passwordEncoder.encode(password));
    }


    public void createDemoData() {
        User demoUser = getOrCreateDemoUser().getOrigin();


        if (!oemRepository.findAll(Application.standardPageRequest.get()).hasContent()) {
            Oem oem1 = new Oem("list" + RandomStringUtils.randomAscii(5), "");
            Oem oem2 = new Oem("list" + RandomStringUtils.randomAscii(5), "");
            Oem oem3 = new Oem("list" + RandomStringUtils.randomAscii(5), "");
            Oem oem4 = new Oem("list" + RandomStringUtils.randomAscii(5), "");
            Oem oem5 = new Oem("list" + RandomStringUtils.randomAscii(5), "");
            List<Oem> oems = Lists.newArrayList(oem1, oem2, oem3, oem4, oem5);
            oemRepository.save(oems);

            Series list1 = new Series("list" + RandomStringUtils.randomAscii(5), oem1);
            Series list2 = new Series("list" + RandomStringUtils.randomAscii(5), oem2);
            Series list3 = new Series("list" + RandomStringUtils.randomAscii(5), oem3);
            Series list4 = new Series("list" + RandomStringUtils.randomAscii(5), oem4);
            Series list5 = new Series("list" + RandomStringUtils.randomAscii(5), oem5);
            List<Series> series = Lists.newArrayList(list1, list2, list3, list4, list5);
            seriesRepository.save(series);

            Model item1 = new Model("item" + RandomStringUtils.randomAscii(5), list1);
            Model item2 = new Model("item" + RandomStringUtils.randomAscii(5), list2);
            Model item3 = new Model("item" + RandomStringUtils.randomAscii(5), list3);
            Model item4 = new Model("item" + RandomStringUtils.randomAscii(5), list4);
            Model item5 = new Model("item" + RandomStringUtils.randomAscii(5), list5);

            List<Model> models = Lists.newArrayList(item1, item2, item3, item4, item5);
            modelRepository.save(models);
        }
    }

}
