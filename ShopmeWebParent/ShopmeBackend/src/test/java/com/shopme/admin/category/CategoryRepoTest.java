//package com.shopme.admin.category;
//
//import com.shopme.common.entity.Category;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.annotation.Rollback;
//
//import java.util.List;
//import java.util.Set;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//
//@DataJpaTest(showSql = false)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Rollback(value = false)
//
//public class CategoryRepoTest {
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    @Test
//    public void testCreateRootCategory(){
//        Category category = new Category("Electronics");
//        Category savedCategory = categoryRepository.save(category);
//
//        assertThat(savedCategory.getId()).isGreaterThan(0);
//    }
//    @Test
//    public void testCreatesubcat(){
//        Category parent = new Category(1);
//        Category subcategory = new Category("Laptops",parent);
//        Category savedCat = categoryRepository.save(subcategory);
//
//    }
//    @Test
//    public void testCreate(){
//        Category parent = new Category(5);
//        Category Cameras = new Category("Dslr",parent);
//        Category batteries = new Category("Mirrorless" , parent);
//
//        categoryRepository.saveAll(List.of(Cameras,batteries));
//    }
//
//    @Test
//    public void testGet(){
//        Category category = categoryRepository.findById(2).get();
//        System.out.println(category.getName());
//
//        Set<Category> children = category.getChildren();
//        for(Category category1:children){
//            System.out.println(category1.getName());
//        }
//    }
//
//    @Test
//    public void testGetAll(){
//        Iterable<Category> categories = categoryRepository.findAll();
//
//        for (Category category: categories){
//            if (category.getParent()==null){
//                System.out.println(category.getName());
//
//                Set<Category> children = category.getChildren();
//                for (Category subCat: children){
//                    System.out.println("--"+ subCat.getName());
//                }
//            }
//        }
//    }
//    @Test
//    public void testListRootCat(){
//        List<Category> categories = categoryRepository.findRootCategories();
//        categories.forEach(cat-> System.out.println(cat.getName()));
//    }
//
//
//}
