package com.endang.cvonline.repositories;

import com.endang.cvonline.models.Company;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyCVRepositories extends CrudRepository<Company,Integer> {

    @Query("select c from Company c where c.username = :username")
    List<Company> findCompaniesByUsername(@Param("username") String username);
}
