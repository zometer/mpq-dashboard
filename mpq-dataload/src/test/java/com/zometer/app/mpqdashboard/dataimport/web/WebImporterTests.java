package com.zometer.app.mpqdashboard.dataimport.web;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.zometer.app.mpqdashboard.dataimport.model.domain.MpqCharacter;
import com.zometer.app.mpqdashboard.dataimport.web.WebImporter;

@DisplayName("com.zometer.sandbox.app.mpq.dataimport.web.WebImporter")
class WebImporterTests {

    @Nested
    @DisplayName("importFromUrl(url)")
    class ImportFromUrl {

        @Test
        @DisplayName("New MPQ Char Name and Style from mpq wiki")
        void testImportFromUrl() {
            WebImporter<MpqCharacter> importer = new WebImporter<>(MpqCharacter.class)
                .mapProperty("name", "//*[@id=\"mw-content-text\"]/aside/h2/text()")
                .mapProperty("style", "//*[@id=\"mw-content-text\"]/aside/div[1]/div/small/text()")
            ;

            MpqCharacter blackWidow = importer.importFromUrl("http://marvelpuzzlequest.wikia.com/wiki/Black_Widow_(Modern)");

            assertAll("Black Widow from wiki",
                () -> assertEquals("Black Widow", blackWidow.getName()),
                () -> assertEquals("Modern", blackWidow.getStyle())
            );
        }

    }

    @Nested
    @DisplayName("updateFromUrl(bean, url)")
    class UpdateFromUrl {

        @Test
        @DisplayName("Updating MPQ Char Name and Style from mpq wiki")
        void testUpdateFromUrl() {
            WebImporter<MpqCharacter> importer = new WebImporter<>(MpqCharacter.class)
                .mapProperty("name", "//*[@id=\"mw-content-text\"]/aside/h2/text()")
                .mapProperty("style", "//*[@id=\"mw-content-text\"]/aside/div[1]/div/small/text()")
            ;
            MpqCharacter blackWidow = new MpqCharacter();
            assertAll("empty bean",
                () -> assertNull(blackWidow.getName()),
                () -> assertNull(blackWidow.getStyle())
            );

            importer.updateFromUrl(blackWidow, "http://marvelpuzzlequest.wikia.com/wiki/Black_Widow_(Modern)");

            assertAll("Black Widow from wiki",
                () -> assertEquals("Black Widow", blackWidow.getName()),
                () -> assertEquals("Modern", blackWidow.getStyle())
            );
        }

    }

    @ParameterizedTest
    @DisplayName("mapProperty(propertyName, xpath)")
    @CsvSource({"name, '//*[@id=\"mw-content-text\"]/aside/h2/text()'"})
    void testMapProperty(String propertyName, String xpath) {
        WebImporter<?> importer = new WebImporter<>(Object.class);
        assertTrue(importer.getPropertyXpathMap().isEmpty(), "non-empty map");

        importer.mapProperty(propertyName, xpath);

        assertEquals(xpath, importer.getPropertyXpathMap().get(propertyName), "xpath is not expected value");
    }

}
