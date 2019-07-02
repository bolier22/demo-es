package com.wanma.demoes.mapper;

import com.wanma.demoes.ds.DynamicDataSource;
import com.wanma.demoes.entity.TblUserAdmin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TblUserAdminMapper {

    //使用ds的值作为key
    List<TblUserAdmin> getList(@Param("start") int start, @Param("count") int count);

    //使用“masterDB”作为key
    List<TblUserAdmin> getList2(@Param("start") int start, @Param("count") int count);
    
    //使用“slaveDB”作为key
    List<TblUserAdmin> getList3(@Param("start") int start, @Param("count") int count);


    void update1();
    void update2();
    void update3();
}