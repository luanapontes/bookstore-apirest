package com.bookstore.apirest.bookstoreapi.services;

import com.bookstore.apirest.bookstoreapi.dtos.ClientDTO;
import com.bookstore.apirest.bookstoreapi.models.Client;
import com.bookstore.apirest.bookstoreapi.repositories.ClientRepository;
import com.bookstore.apirest.bookstoreapi.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ClientService {

        private final ClientRepository clientRepository;

        public List<Client> listAll(){
            return clientRepository.findAll();
        }

        public Client findById(Long id){
            Optional<Client> obj = clientRepository.findById(id);
            return obj.orElseThrow(
                    () -> new ObjectNotFoundException("Object not found"));
        }

        public Client insert(Client obj){
            return clientRepository.save(obj);
        }

        public void update(ClientDTO clientDTO, Long id){
            Client clientSaved = clientRepository.findById(id).orElseThrow(() ->
                    new ObjectNotFoundException("Object not found"));

            clientSaved.setName(clientDTO.getName());
            clientSaved.setAge(clientDTO.getAge());
            clientSaved.setEmail(clientDTO.getEmail());
            clientSaved.setContact(clientDTO.getContact());
            clientSaved.setGender(clientDTO.getGender());
            clientRepository.save(clientSaved);

        }

        public void delete(Long id){
            if(!clientRepository.existsById(id)){
                throw new ObjectNotFoundException("Object not found");
            }

            clientRepository.deleteById(id);
        }

    }

