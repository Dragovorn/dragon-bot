package com.dragovorn.ircbot.api.util.test;

import com.dragovorn.ircbot.api.util.StringUtil;
import com.google.common.collect.Maps;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestStringUtil {

    @Test
    public void testConvertToUrlParams() {
        Map<String, Object> test = Maps.newHashMap();
        test.put("test", "testing");

        Assert.assertEquals("?test=testing", StringUtil.convertToUrlParams(test));

        test.put("more", "work");

        assertEquals("?test=testing&more=work", StringUtil.convertToUrlParams(test));

        test.put("number", 10);

        assertEquals("?number=10&test=testing&more=work", StringUtil.convertToUrlParams(test));
    }
}
