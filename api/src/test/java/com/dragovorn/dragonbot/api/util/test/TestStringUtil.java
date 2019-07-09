package com.dragovorn.dragonbot.api.util.test;

import com.dragovorn.dragonbot.api.util.StringUtil;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestStringUtil {

    @Test
    public void testConvertToUrlParams() {
        Map<String, Object> test = Maps.newHashMap();
        test.put("test", "testing");

        assertEquals("?test=testing", StringUtil.convertToUrlParams(test));

        test.put("more", "work");

        assertEquals("?test=testing&more=work", StringUtil.convertToUrlParams(test));

        test.put("number", 10);

        assertEquals("?number=10&test=testing&more=work", StringUtil.convertToUrlParams(test));
    }
}
