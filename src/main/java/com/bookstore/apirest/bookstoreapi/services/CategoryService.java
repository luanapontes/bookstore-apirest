package com.bookstore.apirest.bookstoreapi.services;

import com.bookstore.apirest.bookstoreapi.dtos.CategoryDTO;
import com.bookstore.apirest.bookstoreapi.models.Category;
import com.bookstore.apirest.bookstoreapi.repositories.CategoryRepository;
import com.bookstore.apirest.bookstoreapi.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryService {


        private final CategoryRepository categoryRepository;

        public List<Category> listAll(){
            return categoryRepository.findAll();
        }

        public Category findById(Long id){
            Optional<Category> obj = categoryRepository.findById(id);
            return obj.orElseThrow(
                    () ->
                            new ObjectNotFoundException("Object not found"));
        }

        public Category insert(Category obj){
            return categoryRepository.save(obj);
        }

        public void update(CategoryDTO categoryDTO, Long id ){
            Category categorySaved = categoryRepository.findById(id).orElseThrow(() ->
                    new ObjectNotFoundException("Object not found"));

            categorySaved.setName(categoryDTO.getName());

            categoryRepository.save(categorySaved);

        }

        public void delete(Long id){
            if(!categoryRepository.existsById(id)){
                throw new ObjectNotFoundException("Object not found");
            }

            categoryRepository.deleteById(id);
        }



}
