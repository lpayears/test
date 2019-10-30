package com.lpa.test.repository;


import com.lpa.test.entity.MidManage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MidManageRepository extends JpaRepository<MidManage,Integer> {

    //    //三表查
    // @Query(value = "select p from MidManage p order by u.id")
//    List<MidManage> findAllWithDetails();
    @Query(value = "select p from MidManage p inner join fetch p.project pp where p.id is not null order by p.id")
    List<MidManage> findAllWithDetails();
}
