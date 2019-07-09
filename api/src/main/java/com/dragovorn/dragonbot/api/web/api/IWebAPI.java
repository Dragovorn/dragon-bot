package com.dragovorn.dragonbot.api.web.api;

import com.dragovorn.dragonbot.api.bot.AbstractIRCBot;
import com.dragovorn.dragonbot.api.util.StringUtil;
import com.dragovorn.dragonbot.api.web.RequestType;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

public interface IWebAPI {

    default void mutateRequest(HttpRequestBase requestBase, String url, Map<String, Object> data) { }

    default JsonObject makeRequest(RequestType type, String url, Map<String, Object> data) throws IOException {
        HttpRequestBase request = type.construct();
        request.addHeader("content-type", "application/json");
        mutateRequest(request, url, data);

        List<String> remove = Lists.newLinkedList();

        data.forEach((k, v) -> {
            if (k.startsWith("meta.")) { // Remove metadata.
                remove.add(k);
            }
        });

        remove.forEach(data::remove);

        if (!data.isEmpty()) {
            url += StringUtil.convertToUrlParams(data);
        }

        request.setURI(URI.create(url));

        HttpResponse response = AbstractIRCBot.getInstance().getClient().execute(request);

        return AbstractIRCBot.getInstance().getGSON().fromJson(EntityUtils.toString(response.getEntity(), "UTF-8"), JsonObject.class);
    }
}
