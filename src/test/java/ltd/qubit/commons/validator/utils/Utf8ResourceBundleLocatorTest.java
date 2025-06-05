package ltd.qubit.commons.validator.utils;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Utf8ResourceBundleLocator Tests")
class Utf8ResourceBundleLocatorTest {

    private static final String BUNDLE_BASE_NAME = "ltd.qubit.commons.validator.utils.TestMessages";
    private static final String NON_EXISTENT_BUNDLE_NAME = "ltd.qubit.commons.validator.utils.NonExistentMessages";

    private Utf8ResourceBundleLocator validLocator;
    private Utf8ResourceBundleLocator nonExistentLocator;

    private static Locale originalDefaultLocale;

    @BeforeAll
    static void saveAndSetDefaultLocale() {
        originalDefaultLocale = Locale.getDefault();
        Locale.setDefault(Locale.ROOT); // Set to a consistent locale for tests
    }

    @AfterAll
    static void restoreDefaultLocale() {
        Locale.setDefault(originalDefaultLocale);
    }

    @BeforeEach
    void setUp() {
        validLocator = new Utf8ResourceBundleLocator(BUNDLE_BASE_NAME);
        nonExistentLocator = new Utf8ResourceBundleLocator(NON_EXISTENT_BUNDLE_NAME);
    }

    @Test
    @DisplayName("Should load default bundle for ROOT locale")
    void getResourceBundle_rootLocale_bundleFound() {
        ResourceBundle bundle = validLocator.getResourceBundle(Locale.ROOT);
        assertNotNull(bundle, "Bundle should not be null for ROOT locale");
        assertEquals("Hello", bundle.getString("greeting"));
        assertEquals("Goodbye", bundle.getString("farewell"));
    }

    @Test
    @DisplayName("Should load default bundle for ENGLISH locale (fallback)")
    void getResourceBundle_englishLocale_bundleFound() {
        ResourceBundle bundle = validLocator.getResourceBundle(Locale.ENGLISH);
        assertNotNull(bundle, "Bundle should not be null for ENGLISH locale");
        assertEquals("Hello", bundle.getString("greeting"));
        assertEquals("Goodbye", bundle.getString("farewell"));
    }

    @Test
    @DisplayName("Should load Chinese bundle for CHINA locale with UTF-8 characters")
    void getResourceBundle_chinaLocale_bundleFound_withUtf8Chars() {
        ResourceBundle bundle = validLocator.getResourceBundle(Locale.CHINA); // Represents zh_CN
        assertNotNull(bundle, "Bundle should not be null for CHINA locale");
        assertEquals("你好", bundle.getString("greeting"));
        assertEquals("再见", bundle.getString("farewell"));
    }

    @Test
    @DisplayName("Should load Chinese bundle for SIMPLIFIED_CHINESE locale with UTF-8 characters")
    void getResourceBundle_simplifiedChineseLocale_bundleFound_withUtf8Chars() {
        // Locale.SIMPLIFIED_CHINESE is "zh_CN"
        ResourceBundle bundle = validLocator.getResourceBundle(Locale.SIMPLIFIED_CHINESE);
        assertNotNull(bundle, "Bundle should not be null for SIMPLIFIED_CHINESE locale");
        assertEquals("你好", bundle.getString("greeting"));
        assertEquals("再见", bundle.getString("farewell"));
    }
    
    @Test
    @DisplayName("Should load Chinese bundle for zh_CN locale with UTF-8 characters")
    void getResourceBundle_zhCNLocale_bundleFound_withUtf8Chars() {
        Locale zhCNLocale = new Locale("zh", "CN");
        ResourceBundle bundle = validLocator.getResourceBundle(zhCNLocale);
        assertNotNull(bundle, "Bundle should not be null for zh_CN locale");
        assertEquals("你好", bundle.getString("greeting"));
        assertEquals("再见", bundle.getString("farewell"));
    }


    @Test
    @DisplayName("Should return null for a non-existent bundle name")
    void getResourceBundle_bundleNotFound() {
        ResourceBundle bundle = nonExistentLocator.getResourceBundle(Locale.ROOT);
        assertNull(bundle, "Bundle should be null for a non-existent bundle name");
    }

    @Test
    @DisplayName("Should fallback to base bundle when specific locale bundle is not found")
    void getResourceBundle_fallbackToBaseBundle() {
        // Requesting French, should fallback to TestMessages.properties (default)
        ResourceBundle bundle = validLocator.getResourceBundle(Locale.FRENCH);
        assertNotNull(bundle, "Bundle should not be null for FRENCH locale (should fallback to base)");
        assertEquals("Hello", bundle.getString("greeting"));
        assertEquals("Goodbye", bundle.getString("farewell"));
    }

    @Test
    @DisplayName("Should throw MissingResourceException for missing key in existing bundle")
    void getResourceBundle_missingKey_throwsException() {
        ResourceBundle bundle = validLocator.getResourceBundle(Locale.ROOT);
        assertNotNull(bundle);
        assertThrows(MissingResourceException.class, () -> {
            bundle.getString("non_existent_key");
        }, "Should throw MissingResourceException for a non-existent key");
    }
} 