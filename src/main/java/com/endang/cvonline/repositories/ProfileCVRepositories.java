package com.endang.cvonline.repositories;

import com.endang.cvonline.models.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileCVRepositories extends CrudRepository<Profile,String> {
}
