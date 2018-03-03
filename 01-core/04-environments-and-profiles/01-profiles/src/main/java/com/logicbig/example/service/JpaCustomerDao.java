package com.logicbig.example.service;


import com.logicbig.example.Customer;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class JpaCustomerDao implements Dao<Customer> {
    @Autowired
    private EntityManager entityManager;


    @PostConstruct
    private void postConstruct() {
        //just populate some test data if it's first time
        try {
            if (loadAll().size() == 0) {
                DataUtil.createTestData().forEach(this::saveOrUpdate);
            }
        } catch (Exception e) {
            DataUtil.createTestData().forEach(this::saveOrUpdate);
        }
    }


    @Override
    public void saveOrUpdate(Customer customer) {
        entityManager.getTransaction().begin();
        entityManager.persist(customer);
        entityManager.getTransaction().commit();
    }


    @Override
    public Customer load(long id) {
        Query query = entityManager.createQuery(
                "SELECT t FROM Customer where t.id = :id");
        query.setParameter("id", id);
        List resultList = query.getResultList();
        if (resultList.size() > 0) {
            return (Customer) resultList.get(0);
        }
        return null;
    }

    @Override
    public List<Customer> loadAll() {
        Query query = entityManager.createQuery(
                "SELECT t FROM Customer t");
        return query.getResultList();
    }
}