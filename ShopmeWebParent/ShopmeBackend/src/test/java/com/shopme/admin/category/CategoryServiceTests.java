package com.shopme.admin.category;
import static org.assertj.core.api.Assertions.assertThat;

import com.shopme.common.entity.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)

public class CategoryServiceTests {
    @MockBean
    private CategoryRepository repository;

    @InjectMocks
    private CategoryService service;

    @Test
    public void testCheckUniq(){
        Integer id = null;
        String name = "Computers";
        String alias ="abc";

        Category category = new Category(id,name,alias);

        Mockito.when(repository.findByName(name)).thenReturn(category);

        String result = service.checkUnique(id,name,alias);
        assertThat(result).isEqualTo("duplicated name");
    }

    @Test
    public void testCheckUniqueinEditModeReturnDuplicatename(){
        Integer id = 1 ;
        String name = "Computers";
        String alias ="abc";

        Category category = new Category(2,name,alias);

        Mockito.when(repository.findByName(name)).thenReturn(category);

        String result = service.checkUnique(id,name,alias);
        assertThat(result).isEqualTo("duplicated name");
    }
}
