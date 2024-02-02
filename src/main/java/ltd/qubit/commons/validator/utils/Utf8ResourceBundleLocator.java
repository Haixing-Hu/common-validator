////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2023.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.validator.utils;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import org.hibernate.validator.spi.resourceloading.ResourceBundleLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ltd.qubit.commons.util.bundle.Utf8Control;

/**
 * 支持UTF-8编码的 .properties 文件的 {@link ResourceBundleLocator}.
 *
 * @author 胡海星
 * @see <a href="https://stackoverflow.com/questions/6421790/hibernate-validator-jsf-2-0-validationmessages-properties-in-utf-8">
 *   hibernate validator + jsf 2.0: ValidationMessages.properties in UTF-8</a>
 */
public class Utf8ResourceBundleLocator implements ResourceBundleLocator {

  private static final Logger logger = LoggerFactory.getLogger(Utf8ResourceBundleLocator.class);

  protected static final ResourceBundle.Control UTF8_CONTROL = new Utf8Control();

  private final String bundleName;

  public Utf8ResourceBundleLocator(final String bundleName) {
    this.bundleName = bundleName;
  }


  /**
   * Search current thread classloader for the resource bundle. If not found,
   * search validator (this) classloader.
   *
   * @param locale The locale of the bundle to load.
   * @return the resource bundle or <code>null</code> if none is found.
   */
  @Override
  public ResourceBundle getResourceBundle(final Locale locale) {
    ResourceBundle bundle = null;
    ClassLoader loader = GetClassLoader.fromContext();
    if (loader != null) {
      bundle = loadBundle(loader, locale,
              bundleName + " not found by thread local classloader");
    }
    if (bundle == null) {
      loader = GetClassLoader.fromClass(PlatformResourceBundleLocator.class);
      bundle = loadBundle(loader, locale,
              bundleName + " not found by validator classloader");
    }
    return bundle;
  }

  private ResourceBundle loadBundle(final ClassLoader classLoader,
      final Locale locale, final String message) {
    ResourceBundle bundle = null;
    try {
      bundle = ResourceBundle.getBundle(bundleName, locale, classLoader, UTF8_CONTROL);
    } catch (final MissingResourceException ignored) {
      logger.warn(message);
    }
    return bundle;
  }

  private static class GetClassLoader implements PrivilegedAction<ClassLoader> {

    private final Class<?> clazz;

    private static ClassLoader fromContext() {
      final GetClassLoader action = new GetClassLoader(null);
      if (System.getSecurityManager() != null) {
        return AccessController.doPrivileged(action);
      } else {
        return action.run();
      }
    }

    private static ClassLoader fromClass(final Class<?> clazz) {
      if (clazz == null) {
        throw new IllegalArgumentException("Class is null");
      }
      final GetClassLoader action = new GetClassLoader(clazz);
      if (System.getSecurityManager() != null) {
        return AccessController.doPrivileged(action);
      } else {
        return action.run();
      }
    }

    private GetClassLoader(final Class<?> clazz) {
      this.clazz = clazz;
    }

    @Override
    public ClassLoader run() {
      if (clazz != null) {
        return clazz.getClassLoader();
      } else {
        return Thread.currentThread().getContextClassLoader();
      }
    }
  }
}
