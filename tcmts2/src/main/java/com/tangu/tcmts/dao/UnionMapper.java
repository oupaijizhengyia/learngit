package com.tangu.tcmts.dao;

import com.tangu.tcmts.po.UnionDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UnionMapper {

    int updateUnion(List<UnionDTO> unionDTOList);
}
