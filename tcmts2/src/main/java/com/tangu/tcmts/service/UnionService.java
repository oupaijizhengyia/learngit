package com.tangu.tcmts.service;

import com.tangu.tcmts.po.UnionDTO;

import java.util.List;

public interface UnionService {
    public int createAndCancelUnion(List<UnionDTO> unionDTOList) ;
}
