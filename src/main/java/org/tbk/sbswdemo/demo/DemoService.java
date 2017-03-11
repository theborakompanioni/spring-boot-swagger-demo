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
            .memoize(() -> createUserBuilder("demo", "demo"));
    private final Supplier<DemoUser.DemoUserBuilder> adminUserBuilderSupplier = Suppliers
            .memoize(() -> createUserBuilder("admin", "admin"));

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

    public void createDemoData() {
        getOrCreateAdminUser();
        getOrCreateDemoUser();
        createDemoDatabaseEntities();
    }

    private void createDemoDatabaseEntities() {
        if (!oemRepository.findAll(Application.standardPageRequest.get()).hasContent()) {
            Oem oem1 = new Oem("oem" + RandomStringUtils.randomAscii(5), "");
            Oem oem2 = new Oem("oem" + RandomStringUtils.randomAscii(5), "");
            Oem oem3 = new Oem("oem" + RandomStringUtils.randomAscii(5), "");
            Oem oem4 = new Oem("oem" + RandomStringUtils.randomAscii(5), "");
            Oem oem5 = new Oem("oem" + RandomStringUtils.randomAscii(5), "");
            List<Oem> oems = Lists.newArrayList(oem1, oem2, oem3, oem4, oem5);
            oemRepository.save(oems);

            Series series1 = new Series("series" + RandomStringUtils.randomAscii(5), oem1);
            Series series2 = new Series("series" + RandomStringUtils.randomAscii(5), oem2);
            Series series3 = new Series("series" + RandomStringUtils.randomAscii(5), oem3);
            Series series4 = new Series("series" + RandomStringUtils.randomAscii(5), oem4);
            Series series5 = new Series("series" + RandomStringUtils.randomAscii(5), oem5);
            List<Series> series = Lists.newArrayList(series1, series2, series3, series4, series5);
            seriesRepository.save(series);

            Model model1 = new Model("model" + RandomStringUtils.randomAscii(5), series1);
            Model model2 = new Model("model" + RandomStringUtils.randomAscii(5), series2);
            Model model3 = new Model("model" + RandomStringUtils.randomAscii(5), series3);
            Model model4 = new Model("model" + RandomStringUtils.randomAscii(5), series4);
            Model model5 = new Model("model" + RandomStringUtils.randomAscii(5), series5);

            List<Model> models = Lists.newArrayList(model1, model2, model3, model4, model5);
            modelRepository.save(models);
        }
    }

    public DemoUser getOrCreateAdminUser() {
        final User user = userRepository.findByName("admin", Application.standardPageRequest.get())
                .getContent()
                .stream()
                .findFirst()
                .orElseGet(() -> createUser(
                        adminUserBuilderSupplier.get(),
                        Lists.newArrayList("ROLE_ADMIN", "ROLE_USER")
                ).getOrigin());

        return demoUserBuilderSupplier.get()
                .origin(user)
                .build();
    }

    public DemoUser getOrCreateDemoUser() {
        final User user = userRepository.findByName("demo", Application.standardPageRequest.get())
                .getContent()
                .stream()
                .findFirst()
                .orElseGet(() -> createUser(
                        demoUserBuilderSupplier.get(),
                        Lists.newArrayList("ROLE_DEMO", "ROLE_USER")
                ).getOrigin());

        return demoUserBuilderSupplier.get()
                .origin(user)
                .build();
    }

    private DemoUser createUser(DemoUser.DemoUserBuilder demoUserBuilder, List<String> authorities) {
        requireNonNull(authorities);

        User demoUser = new User(demoUserBuilder.name(), demoUserBuilder.encryptedPassword(), authorities);

        userRepository.save(demoUser);
        log.info("Created demo user: {}", demoUser);

        return demoUserBuilder
                .origin(demoUser)
                .build();
    }

    private DemoUser.DemoUserBuilder createUserBuilder(String username, String password) {
        return DemoUser.builder()
                .name(username)
                .password(password)
                .encryptedPassword(passwordEncoder.encode(password));
    }


}
