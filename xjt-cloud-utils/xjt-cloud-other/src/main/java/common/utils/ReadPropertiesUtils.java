package common.utils;

import java.util.Properties;

/**
 * 读取属性文件的单例类
 * 
 * @author Administrator
 *
 */
public class ReadPropertiesUtils {
	public static synchronized Properties getProperties() {
		try {
			if (properties == null) {
				properties = new Properties();
				properties.load(ReadPropertiesUtils.class.getClassLoader().getResourceAsStream("zone.properties"));

			} else {
				return properties;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return properties;
	}

	public static void setProperties(Properties properties) {
		ReadPropertiesUtils.properties = properties;
	}

	private static Properties properties = null;

}
