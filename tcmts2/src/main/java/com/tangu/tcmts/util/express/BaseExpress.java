package com.tangu.tcmts.util.express;

import com.tangu.tcmts.po.SysLookup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaseExpress {

    public static final Logger logger = LoggerFactory.getLogger(BaseExpress.class);

    protected List<Map<String, String>> list = new ArrayList<Map<String, String>>();

    protected Map<String, String> map = null;

    private Integer recipeCode;

    public BaseExpress() {
    }

    public BaseExpress(Integer recipeCode) {
        this.recipeCode = recipeCode;
    }

    public List<Map<String, String>> searchExpress(SysLookup sysLookup, String logisticCode) throws Exception{
        return list;
    }

    public List<Map<String, String>> searchExpress(SysLookup sysLookup, String recipeCode,
                                                     String logisticCode, String hospitalCode) throws Exception{
        return list;
    }
}
