package com.sync.imgur.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sync.imgur.exception.RecordNotFoundException;
import com.sync.imgur.model.UserEntity;
import com.sync.imgur.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
 
@Service
public class UserService implements UserDetailsService{
     
    @Autowired
    UserRepository repository;
    
    
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<UserEntity> user = repository.findByUserName(userName);

        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));

        return user.map(MyUserDetails::new).get();
    }
     
    public List<UserEntity> getAll()
    {
        List<UserEntity> users = repository.findAll();
         
        if(users.size() > 0) {
            return users;
        } else {
            return new ArrayList<UserEntity>();
        }
    }
     
    public UserEntity getUserById(Long id) throws RecordNotFoundException
    {
        Optional<UserEntity> user = repository.findById(id);
         
        if(user.isPresent()) {
            return user.get();
        } else {
            throw new RecordNotFoundException("No User record exist for given id");
        }
    }
     
    public UserEntity createOrUpdate(UserEntity entity) throws RecordNotFoundException
    {
        Optional<UserEntity> user = repository.findById(entity.getId());
         
        if(user.isPresent())
        {
            UserEntity newEntity = user.get();
            newEntity.setEmail(entity.getEmail());
            newEntity.setFirstName(entity.getFirstName());
            newEntity.setLastName(entity.getLastName());
 
            newEntity = repository.save(newEntity);
             
            return newEntity;
        } else {
            entity = repository.save(entity);
             
            return entity;
        }
    }
     
    public void deleteById(Long id) throws RecordNotFoundException
    {
        Optional<UserEntity> user = repository.findById(id);
         
        if(user.isPresent())
        {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No user record exist for given id");
        }
    }
}