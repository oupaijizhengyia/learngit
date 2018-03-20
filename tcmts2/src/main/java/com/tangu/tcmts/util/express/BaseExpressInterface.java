package com.tangu.tcmts.util.express;

public interface BaseExpressInterface<E extends BaseExpress> {
    E getIns(Integer integer);
    default BaseExpress getExpress(Integer expressCode){
        //顺丰
        final int SF_EXPRESS = 1;
        //中国邮政
        final int CHINA_POST = 2;
        //SPD
        final int SPD_EXPRESS = 5;
        //宅急便
        final int YAMATO_EXPRESS = 8;
        //顺真
        final int SZ_EXPRESS = 10;
        //海象
        final int WALRUS_EXPRESS = 13;
        switch (expressCode){
            case SF_EXPRESS:
                return new SFExpress();
            case CHINA_POST:
                return new ChinaPostExpress();
            case YAMATO_EXPRESS:
                return new YamatoExpress();
            case SZ_EXPRESS:
                return new SZExpress();
            case SPD_EXPRESS:
                return new SPDExpress();
            case WALRUS_EXPRESS:
                return new WalrusExpress();
        }
        return null;
    }
}
