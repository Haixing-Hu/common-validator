////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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

import ltd.qubit.commons.i18n.bundle.Utf8Control;

/**
 * 支持UTF-8编码的 .properties 文件的 {@link ResourceBundleLocator}.
 *
 * @author 胡海星
 * @see <a href="https://stackoverflow.com/questions/6421790/hibernate-validator-jsf-2-0-validationmessages-properties-in-utf-8">
 *   hibernate validator + jsf 2.0: ValidationMessages.properties in UTF-8</a>
 */
public class Utf8ResourceBundleLocator implements ResourceBundleLocator {

  private static final Logger logger = LoggerFactory.getLogger(Utf8ResourceBundleLocator.class);

  /**
   * 用于加载 UTF-8 编码的 {@code .properties} 文件的 {@link ResourceBundle.Control} 实例。
   */
  protected static final ResourceBundle.Control UTF8_CONTROL = new Utf8Control();

  /**
   * 要加载的资源包的名称。
   */
  private final String bundleName;

  /**
   * 构造一个 {@code Utf8ResourceBundleLocator} 实例。
   *
   * @param bundleName
   *     要加载的资源包的名称，例如 "ValidationMessages"。
   */
  public Utf8ResourceBundleLocator(final String bundleName) {
    this.bundleName = bundleName;
  }


  /**
   * 首先在当前线程的类加载器中搜索资源包。如果未找到，
   * 则在验证器（此对象）的类加载器中搜索。
   *
   * @param locale 要加载的资源包的区域设置。
   * @return 资源包；如果未找到，则返回 {@code null}。
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

  /**
   * 使用指定的类加载器和区域设置加载资源包。
   *
   * @param classLoader 类加载器。
   * @param locale 区域设置。
   * @param message 如果加载失败且资源未找到时记录的警告信息。
   * @return 加载的资源包；如果发生 {@link MissingResourceException}，则返回 {@code null}。
   */
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

  /**
   * 一个 {@link PrivilegedAction} 实现，用于安全地获取类加载器。
   * <p>
   * 此类用于在启用安全管理器（Security Manager）的环境中获取上下文类加载器或指定类的类加载器。
   */
  private static class GetClassLoader implements PrivilegedAction<ClassLoader> {

    /**
     * 从其获取类加载器的目标类；如果为 {@code null}，则获取当前线程的上下文类加载器。
     */
    private final Class<?> clazz;

    /**
     * 获取当前线程的上下文类加载器。
     *
     * @return 当前线程的上下文类加载器。
     */
    private static ClassLoader fromContext() {
      final GetClassLoader action = new GetClassLoader(null);
      if (System.getSecurityManager() != null) {
        return AccessController.doPrivileged(action);
      } else {
        return action.run();
      }
    }

    /**
     * 获取指定类的类加载器。
     *
     * @param clazz 要获取其类加载器的类，不能为 {@code null}。
     * @return 指定类的类加载器。
     * @throws IllegalArgumentException 如果 {@code clazz} 为 {@code null}。
     */
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

    /**
     * 构造一个 {@code GetClassLoader} 动作。
     *
     * @param clazz
     *     要从中获取类加载器的类。如果为 {@code null}，则此动作将获取当前线程的上下文类加载器。
     */
    private GetClassLoader(final Class<?> clazz) {
      this.clazz = clazz;
    }

    /** {@inheritDoc} */
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
