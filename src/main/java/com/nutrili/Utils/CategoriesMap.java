package com.nutrili.Utils;

import org.bouncycastle.asn1.eac.BidirectionalMap;

import java.util.HashMap;
import java.util.Map;

public class CategoriesMap {
    public static final BidirectionalMap categoriesMap  = new BidirectionalMap() {{
        put( 1, "Fruta");
        put(2, "legumes");
        put(3, "verduras");
        put(4, "Cereais");
        put(5,"Raízes e Tubérculos");
        put(6,"Carnes e Ovos");
        put(7,"Feijões");
        put(8,"Leites e Derivados");
        put(9,"Óleos e Gorduras");
        put(10,"Oleaginosas");
        put(11,"Produtos Açucarados");
    }};
}
