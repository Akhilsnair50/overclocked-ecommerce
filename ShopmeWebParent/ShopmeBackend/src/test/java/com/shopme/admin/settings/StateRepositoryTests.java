package com.shopme.admin.settings;
import static org.assertj.core.api.Assertions.assertThat;

import com.shopme.admin.setting.state.StateRepository;
import com.shopme.common.entity.State;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import com.shopme.common.entity.Country;
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class StateRepositoryTests {

    @Autowired
    StateRepository repo;
    @Autowired
    EntityManager entityManager;
    @Test
    public void testCreateStatesInIndia() {
        Integer countryId = 1;
        Country country = entityManager.find(Country.class, countryId);

//		State state = repo.save(new State("Kerala", country));
		State state = repo.save(new State("Punjab", country));
//		State state = repo.save(new State("Uttar Pradesh", country));
//        State state = repo.save(new State("West Bengal", country));

        assertThat(state).isNotNull();
        assertThat(state.getId()).isGreaterThan(0);
    }
}
