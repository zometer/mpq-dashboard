package com.zometer.app.mpqdashboard.dataimport.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.zometer.app.mpqdashboard.dataimport.util.StringConverter;

import us.codecraft.xsoup.Xsoup;

public class WebImporter<T> {

    private Class<T> type ;
    private Map<String, String> propertyXpathMap = new HashMap<>();

    public WebImporter(Class<T> type) {
        this.type = type;
    }

    public T importFromUrl(String url) {
        try {
            T bean = type.newInstance();
            Document doc = Jsoup.connect(url).get();
            propertyXpathMap.entrySet().forEach( entry -> {
                String valueString = Xsoup.compile(entry.getValue()).evaluate(doc).get();
                try {
                    String propertyName = entry.getKey();
                    Object value = StringConverter.convert(valueString, PropertyUtils.getPropertyType(bean, propertyName));
                    PropertyUtils.setProperty(bean, propertyName, value);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            });
            return bean;
        } catch (IOException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateFromUrl(T bean, String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            propertyXpathMap.entrySet().forEach( entry -> {
                String valueString = Xsoup.compile(entry.getValue()).evaluate(doc).get();
                try {
                    String propertyName = entry.getKey();
                    Object value = StringConverter.convert(valueString, PropertyUtils.getPropertyType(bean, propertyName));
                    PropertyUtils.setProperty(bean, propertyName, value);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WebImporter<T> mapProperty(String propertyName, String xpath) {
        propertyXpathMap.put(propertyName, xpath);
        return this;
    }

    public Map<String, String> getPropertyXpathMap() {
        return this.propertyXpathMap;
    }

}
