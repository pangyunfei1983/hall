package com.yxzh.hall.service;


import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.*;

@Repository
public class CallInfoService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public int GetWait(String windowid,String floor)
    {
        String SQL=" select count(guid) guid  from t_pd_sendnoinfo_610402  where CALLSTATUS = '0' and floor = ?  and to_char(sysdate,'yyyy-MM-dd')= to_char(SENDDATE, 'yyyy-MM-dd') and    ? in (select column_value from table(mysplit(SERVWINDOWNO, ',')))  ";
        Integer total=jdbcTemplate.queryForObject(SQL,new Object[] {floor ,windowid},Integer.class);
        return total;
    }

    public  String DoCall(String windowid,String floor)
    {
        String json="";   //  json = "{'sendno':'" + sendno + "','wait':'" + wait+ "','servname':'"+ servname + "'}";
        ;
        json = (String) jdbcTemplate.execute(
                new CallableStatementCreator() {
                    public CallableStatement createCallableStatement(Connection con) throws SQLException {
                        String storedProc = "{call SL_GetCallNum (?,?,?,?,?)}";
                        CallableStatement cs = con.prepareCall(storedProc);

                        cs.setString("p_windowno", windowid);
                        cs.setString("p_floor", floor);
                        cs.registerOutParameter("p_sendno", Types.NVARCHAR);
                        cs.registerOutParameter("p_wait",Types.DECIMAL);
                        cs.registerOutParameter("p_servname",Types.NVARCHAR);
                        return cs;
                    }
                }, new CallableStatementCallback() {
                    public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                        cs.execute();

                        String sendno=cs.getString("p_sendno")==null ?"":cs.getString("p_sendno");
                        String servname=cs.getString("p_servname")==null ?"":cs.getString("p_servname");;
                        int wait=cs.getInt("p_wait");
                        JSONObject obj = new JSONObject();
                        obj.put("sendno", sendno);
                        obj.put("wait",wait);
                        obj.put("servname",servname);
                        System.out.println(obj.toJSONString());
                        return obj.toString();// 获取输出参数的值
                    }
                });

        return json;

    }

    public  String DoReCall(String windowid,String floor)
    {
        String json="";

        json=(String) jdbcTemplate.execute(  new CallableStatementCreator() {
            public CallableStatement createCallableStatement(Connection con) throws SQLException {
                String storedProc = "{call SL_ReCall (?,?)}";
                CallableStatement cs = con.prepareCall(storedProc);

                cs.setString("p_windowno", windowid);
                cs.setString("p_floor", floor);
                return cs;
            }
        },new CallableStatementCallback() {
            public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                cs.execute();
                JSONObject obj = new JSONObject();
                System.out.println(obj.toJSONString());

                System.out.println(windowid+" DoReCall");
                return obj.toJSONString();// 获取输出参数的值
            }
        });

        return  json;
    }


}
