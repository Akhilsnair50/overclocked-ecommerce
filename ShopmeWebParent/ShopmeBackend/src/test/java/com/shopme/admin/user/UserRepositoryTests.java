package com.shopme.admin.user;
import static org.assertj.core.api.Assertions.assertThat;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTests {
    @Autowired
    private UserRepository repository;

    @Autowired
    EntityManager entityManager;
    @Test
    public void toCreateUser(){
        Role roleAdmin =entityManager.find(Role.class,1);
        User userAkhil = new User("admin@gmail.com","akhil","s nair","1234");
        userAkhil.addRole(roleAdmin);

        User savedUser =repository.save(userAkhil);

        assertThat(savedUser.getId()).isGreaterThan(0);

    }
    @Test
    public void toCreate(){
        User userRavi = new User("Ravi@gmail.com","ravi","subru","1234");
        Role roleEditor = new Role(3);
        Role roleAssistant = new Role(5);

        userRavi.addRole(roleAssistant);
        userRavi.addRole(roleEditor);

        User savedUser =repository.save(userRavi);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers(){
        Iterable<User> listUsers = repository.findAll();
        listUsers.forEach(user -> System.out.println(user));

    }

    @Test
    public void testGetUserById(){
        User username = repository.findById(1).get();
        System.out.println(username);
        assertThat(username).isNotNull();
    }

    @Test
    public void testUpdateUser( ){
        User userName = repository.findById(1).get();
        userName.setEnabled(true);
        userName.setEmail("akhil@gmail.com");
        repository.save(userName);
    }

    @Test
    public void testToGetUserByEmail(){
        String email = "akhilsnair50@gmail.com";
        User user = repository.getUserByEmail(email);
        assertThat(user).isNotNull();
    }

}
