package com.yxzh.hall.mapper;


import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface DisplayMapper {



    @Results({ @Result(property = "sendno", column = "SENDNO"),
            @Result(property = "windowno", column = "WINDOWNO"),
            @Result(property = "calldate", column = "CALLDATE",jdbcType = JdbcType.DATE),
            @Result(property = "floor", column = "FLOOR") ,
            @Result(property = "callstatus", column = "CALLSTATUS") })
    @Select("select sendno,windowno,calldate,floor,callstatus  from T_PD_PLAY_610402 where windowno = #{windowno}")
    List<HashMap<String,Object>> GetWindowInfo(@Param("windowno") String windowno );

}
