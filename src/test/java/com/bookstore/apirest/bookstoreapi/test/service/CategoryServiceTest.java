package com.bookstore.apirest.bookstoreapi.test.service;

import com.bookstore.apirest.bookstoreapi.dtos.CategoryDTO;
import com.bookstore.apirest.bookstoreapi.models.Category;
import com.bookstore.apirest.bookstoreapi.repositories.CategoryRepository;
import com.bookstore.apirest.bookstoreapi.services.CategoryService;
import com.bookstore.apirest.bookstoreapi.services.exceptions.ObjectNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.bookstore.apirest.bookstoreapi.test.builder.CategoryBuilder.createCategory;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CategoryServiceTest {

    @InjectMocks
    CategoryService categoryService;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Autowired
    private Category category;

    @Autowired
    private CategoryDTO categoryDTO;

    @Autowired
    private Optional<Category> optionalCategory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startCategory();
    }

    @Test
    void whenFindAllThenReturnAnListOfCategories() {

        when(categoryRepository.findAll()).thenReturn(Stream.of(createCategory().build()).collect(Collectors.toList()));

        List<Category> response = categoryService.listAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(Category.class, response.get(0).getClass());

        assertEquals(2L, response.get(0).getId());
        Assertions.assertEquals("Mangá", response.get(0).getName());
    }

    @Test
    void whenfindByIdTheReturnAnCategoryInstance() {

        when(categoryRepository.findById(Mockito.anyLong())).thenReturn(optionalCategory);

        Category response = categoryService.findById(2L);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(Category.class, response.getClass());
        Assertions.assertEquals(2L, response.getId());
        Assertions.assertEquals("Mangá", response.getName());

    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException(){

        when(categoryRepository.findById(anyLong())).thenThrow(new ObjectNotFoundException("Object not found"));

        try {
            categoryService.findById(2L);
        } catch (Exception exception) {
            assertEquals(ObjectNotFoundException.class, exception.getClass());
            assertEquals("Object not found", exception.getMessage());
        }
    }

    @Test
    void whenInsertThenReturnSuccess() {

        Category category = createCategory().build();
        categoryService.insert(category);

        when(categoryRepository.save(Mockito.any())).thenReturn(category);

        ArgumentCaptor<Category> categoryArgumentCaptor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository).save(categoryArgumentCaptor.capture());

        Category response = categoryArgumentCaptor.getValue();

        assertNotNull(response);
        assertEquals(Category.class, response.getClass());
        assertEquals(2L, response.getId());
        assertEquals("Mangá", response.getName());
    }

    @Test
    void whenUpdateThenReturnSuccess() {

        Category putCategoryRequest = createCategory()
                .id(2L)
                .name("Mangá")
                .build();

        when(categoryRepository.findById(anyLong())).thenReturn(optionalCategory);

        categoryService.update(CategoryDTO.from(putCategoryRequest), 2L);

        ArgumentCaptor<Category> categoryArgumentCaptor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository).save(categoryArgumentCaptor.capture());

        Category response = categoryArgumentCaptor.getValue();

        assertNotNull(response);
        assertEquals(Category.class, response.getClass());
        Assertions.assertEquals(2L, response.getId());
        Assertions.assertEquals("Mangá", response.getName());
    }

    @Test
    void whenUpdateThenReturnAnObjectNotFoundException(){

        when(categoryRepository.findById(anyLong())).thenThrow(new ObjectNotFoundException("Object not found"));

        try {
            categoryService.findById(2L);
        } catch (Exception exception) {
            assertEquals(ObjectNotFoundException.class, exception.getClass());
            assertEquals("Object not found", exception.getMessage());
        }
    }

    @Test
    void deleteWithSuccess() {

        when(categoryRepository.existsById(anyLong())).thenReturn(true);
        categoryService.delete(2L);
        verify(categoryRepository).existsById(anyLong());
        verify(categoryRepository).deleteById(anyLong());
    }

    @Test
    void deleteWithObjectNotFoundException(){

        when(categoryRepository.findById(anyLong())).thenThrow(new ObjectNotFoundException("Object not found"));
        try{
            categoryService.delete(2L);
        } catch (Exception exception){
            assertEquals(ObjectNotFoundException.class, exception.getClass());
            assertEquals("Object not found", exception.getMessage());
        }
    }

    public void startCategory(){

        Category category = new Category(2L, "Mangá");

        CategoryDTO categoryDTO = new CategoryDTO(2L, "Mangá");

        optionalCategory = Optional.of(new Category(2L, "Mangá"));
    }
}