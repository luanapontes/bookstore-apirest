package com.bookstore.apirest.bookstoreapi.test.controller;

import com.bookstore.apirest.bookstoreapi.controllers.CategoryController;
import com.bookstore.apirest.bookstoreapi.dtos.CategoryDTO;
import com.bookstore.apirest.bookstoreapi.dtos.ClientDTO;
import com.bookstore.apirest.bookstoreapi.models.Category;
import com.bookstore.apirest.bookstoreapi.models.Client;
import com.bookstore.apirest.bookstoreapi.repositories.CategoryRepository;
import com.bookstore.apirest.bookstoreapi.services.CategoryService;
import com.bookstore.apirest.bookstoreapi.services.exceptions.ObjectNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static com.bookstore.apirest.bookstoreapi.test.builder.CategoryBuilder.createCategory;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
@ExtendWith(SpringExtension.class)
class CategoryControllerTest {

    private final String URL_CATEGORY = "/categories";

    @InjectMocks
    private CategoryController categoryController;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        MockitoAnnotations.openMocks(this);
        startCategory();
    }

    @Test
    void whenFindByIdThenReturnSuccess() throws Exception{

        when(categoryService.findById(anyLong())).thenReturn(createCategory().build());

        mockMvc.perform(get(URL_CATEGORY + "/{id}", 2L).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("Mangá")));

        verify(categoryService).findById(anyLong());

    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException() throws Exception{

        when(categoryService.findById(anyLong())).thenThrow(new ObjectNotFoundException("Object not found"));

        mockMvc.perform(get(URL_CATEGORY + "/{id}", 2L).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(categoryService).findById(2L);
    }


    @Test
    void whenFindAllThenReturnAnListOfCategories() throws Exception{
        when(categoryService.listAll()).thenReturn(Lists.newArrayList(
                createCategory().id(2L).build()));

        mockMvc.perform(get(URL_CATEGORY).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].name", is("Mangá")));

        verify(categoryService).listAll();
    }

    @Test
    void whenInsertThenReturnSuccess() throws Exception {

        Category category = createCategory()
                .id(2L)
                .name("Mangá")
                .build();

        when(categoryService.insert(any())).thenReturn(category);

        mockMvc.perform(post(URL_CATEGORY)
                        .content(objectMapper.writeValueAsString(category))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(categoryService).insert(any(Category.class));
    }

    @Test
    void whenUpdateThenReturnSuccess() throws Exception{

        Category category = createCategory()
                .id(2L)
                .name("Mangá")
                .build();

        when(categoryService.findById(2L)).thenReturn(category);
        when(categoryService.insert(any(Category.class))).thenReturn(category);

        mockMvc.perform(put(URL_CATEGORY + "/{id}", 2L)
                        .content(objectMapper.writeValueAsString(category))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(categoryService).update(any(CategoryDTO.class), eq(2L));
    }
    @Test
    void deleteWithSuccess() throws Exception{

        mockMvc.perform(delete(URL_CATEGORY + "/{id}", 2L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(categoryService).delete(anyLong());
    }

    private void startCategory(){

        Category category = new Category(2L, "Mangá");

        CategoryDTO categoryDTO = new CategoryDTO(2L, "Mangá");
    }
}