package com.bookstore.apirest.bookstoreapi.controllers;

import com.bookstore.apirest.bookstoreapi.dtos.CategoryDTO;
import com.bookstore.apirest.bookstoreapi.models.Category;
import com.bookstore.apirest.bookstoreapi.repositories.CategoryRepository;
import com.bookstore.apirest.bookstoreapi.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    private final CategoryService categoryService;

    private final CategoryRepository categoryRepository;

    @GetMapping
    public List<CategoryDTO> listAll(){
        return CategoryDTO.fromAll(categoryService.listAll());
    }

    @GetMapping("/{id}")
    public CategoryDTO findById(@PathVariable(value="id") Long id){
        return CategoryDTO.from(categoryService.findById(id));
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public void insert(@RequestBody CategoryDTO CategoryDTO) {
        categoryService.insert(Category.to(CategoryDTO));
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PutMapping(value = "/{id}")
    public void update(@RequestBody CategoryDTO categoryDTO, @PathVariable Long id){
        categoryService.update(categoryDTO, id);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id){
        categoryService.delete(id);
    }
}
