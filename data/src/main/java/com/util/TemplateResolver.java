package com.util;

import com.cat.entity.User;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor(staticName = "instance")
public class TemplateResolver {

    private static final String DEFAULT_ENCODING = StandardCharsets.UTF_8.toString();
    private static final Configuration configuration;
    private static final Map<String, Template> TEMPLATES = new LinkedHashMap<>();

    static {
        configuration = new Configuration(Configuration.VERSION_2_3_0);
        configuration.setDefaultEncoding(DEFAULT_ENCODING);
    }

    private final String name;
    private final String content;
    private final String encoding;
    private final Object data;

    public static void main(String[] args) throws Exception {
        final String name = "key";
//        final String content = "hello:${user}";
//        final String content = "${1+2}";
//        final String content = "<#if user?? && user?has_content>${user}<#else>error</#if>";
        final String content = "<#if name?? && name?has_content>${name}<#else>error</#if>";

        User user = new User();
        user.setName("zsy");
        Map<String, Object> data = new HashMap<>();
        data.put("user", "cjj");
//        data.put("flag", false);

        Object r = TemplateResolver.instance(name, content, null, user).parse();
        System.out.println(r);

    }

    public static void back(String[] args) {
//        Configuration cfg = new Configuration(Configuration.VERSION_2_3_0);
//
//        StringTemplateLoader templateLoader = new StringTemplateLoader();
//        templateLoader.putTemplate(name, content);
//
//        cfg.setTemplateLoader(templateLoader);
//
//        cfg.setDefaultEncoding(encoding);
//
//        Template template = cfg.getTemplate(name);
//
//        StringWriter writer = new StringWriter();
//        template.process(data, writer);
//        System.out.println(writer.toString());
    }

    private void setEncoding() {
        if (encoding != null) {
            configuration.setDefaultEncoding(encoding);
        }
    }

    private Template template() throws IOException {
        if (TEMPLATES.containsKey(name)) {
            return TEMPLATES.get(name);
        }

        StringTemplateLoader templateLoader = new StringTemplateLoader();
        templateLoader.putTemplate(name, content);
        configuration.setTemplateLoader(templateLoader);

        Template template = configuration.getTemplate(name);
        TEMPLATES.put(name, template);
        return template;
    }

    private Object handleData() {
//        if (data instanceof HashMap) {
//            return (Map<String, Object>) data;
//        }
////        Map<String, Object> map = new HashMap<>();
////        map.put("")//TODO
//        return null;
        return data;
    }

    private Object parse() throws IOException, TemplateException {
        setEncoding();

        Template template = template();
        StringWriter writer = new StringWriter();
        template.process(handleData(), writer);
        return writer.toString();
    }

}
