package com.bookstore.apirest.bookstoreapi.services;

import com.bookstore.apirest.bookstoreapi.dtos.BuyBookDTO;
import com.bookstore.apirest.bookstoreapi.models.BuyBook;
import com.bookstore.apirest.bookstoreapi.repositories.BuyBookRepository;
import com.bookstore.apirest.bookstoreapi.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BuyBookService {

    private final BuyBookRepository buyBookRepository;

    public List<BuyBook> listAll(){
        return buyBookRepository.findAll();
    }

    public BuyBook findById(Long id){
        Optional<BuyBook> obj = buyBookRepository.findById(id);
        return obj.orElseThrow(
                () ->
                        new ObjectNotFoundException("Object not found"));
    }

    public BuyBook insert(BuyBook obj){
        return buyBookRepository.save(obj);
    }

    public void update(BuyBookDTO buyBookDTO, Long id){
        BuyBook buyBookSaved = buyBookRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("Object not found"));

        buyBookSaved.setId(buyBookDTO.getId());
        buyBookSaved.setAmountToPay(buyBookSaved.getAmountToPay());
        buyBookSaved.setBuyDay(buyBookDTO.getBuyDay());
        buyBookSaved.setStatus(buyBookDTO.getStatus());
        buyBookRepository.save(buyBookSaved);
    }

    public void delete(Long id){
        if(!buyBookRepository.existsById(id)){
            throw new ObjectNotFoundException("Object not found");
        }

        buyBookRepository.deleteById(id);
    }

}
