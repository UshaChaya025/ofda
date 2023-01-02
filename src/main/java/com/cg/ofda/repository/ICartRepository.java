package com.cg.ofda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cg.ofda.dto.FoodCartDto;

@Repository
public interface ICartRepository extends JpaRepository<FoodCartDto, String>{

}
