package com.zxsoft.toolkit.tips;

import java.util.Arrays;
import java.util.List;

/**
 * Created by xuwenjuan on 17/1/10.
 */
public class SparkTips {

    //合理利用first元素
    public void loopLink() {
        StringBuilder sb = new StringBuilder();
        List<String> strings = Arrays.asList("a", "d", "c");
        if (strings.size() > 0) {
            boolean first = true;//*****
            sb.append('<');
            for (String string : strings) {
                if (!first)
                    sb.append(',');
                sb.append(string);
                first = false;//*****
            }
            sb.append('>');
        }
    }

}
