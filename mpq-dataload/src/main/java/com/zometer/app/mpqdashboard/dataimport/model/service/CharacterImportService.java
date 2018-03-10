package com.zometer.app.mpqdashboard.dataimport.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.zometer.app.mpqdashboard.dataimport.config.WikiConfig;
import com.zometer.app.mpqdashboard.dataimport.model.domain.MpqCharacter;
import com.zometer.app.mpqdashboard.dataimport.web.WebImporter;

@Service
@EnableConfigurationProperties
public class CharacterImportService {

    @Autowired
    private WikiConfig wikiConfig ;

    public Map<String, MpqCharacter> fetchCharacters() {
        Map<String, MpqCharacter> map = new TreeMap<>();
        return map;
    }

    public List<MpqCharacter> fetchCharactersFromWiki() {
        List<MpqCharacter> characters = new ArrayList<>();
        WebImporter<MpqCharacter> importer = new WebImporter<>(MpqCharacter.class)
            .mapProperty("name", "//*[@id=\"mw-content-text\"]/aside/h2/text()")
            .mapProperty("style", "//*[@id=\"mw-content-text\"]/aside/div[1]/div/small/text()")
        ;

        wikiConfig.getUrls().forEach(url -> {
            System.out.println(url);
        });

        return characters;

    }

}
