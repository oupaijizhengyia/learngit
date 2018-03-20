package com.tangu.tcmts.dao;

import com.tangu.tcmts.po.Recipient;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecipientMapper {
    int insert(Recipient record);

    List<Recipient> getRecipientList(Recipient recipient);

    int updateByPrimaryKeySelective(Recipient record);
}