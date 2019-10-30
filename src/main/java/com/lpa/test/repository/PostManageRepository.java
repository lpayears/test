package com.lpa.test.repository;


import com.lpa.test.entity.PostManage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostManageRepository extends JpaRepository<PostManage,Integer> {

    //三表查询
    @Query(value = "select p from PostManage p inner join fetch p.project pp where p.id is not null order by p.id")
    List<PostManage> findAllWithDetails();
}
