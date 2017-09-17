package kr.co.ps119.config.common;

import java.io.Serializable;
import java.util.Locale;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class CommonCustomPhysicalNamingStrategyImpl extends PhysicalNamingStrategyStandardImpl implements Serializable {

	private static final long serialVersionUID = -7111198740289039327L;
	public static final CommonCustomPhysicalNamingStrategyImpl INSTANCE = new CommonCustomPhysicalNamingStrategyImpl();

	@Override
	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
		return new Identifier(addUnderscores(name.getText()), name.isQuoted());
	}

	@Override
	public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
		return new Identifier(addUnderscores(name.getText()), name.isQuoted());
	}

	protected static String addUnderscores(String name) {
		final StringBuilder buf = new StringBuilder(name.replace('.', '_'));
		for (int i = 1; i < buf.length() - 1; i++) {
			if (Character.isLowerCase(buf.charAt(i - 1)) &&
				Character.isUpperCase(buf.charAt(i)) &&
				Character.isLowerCase(buf.charAt(i + 1))) {
				buf.insert(i++, '_');
			}
		}
		return buf.toString().toLowerCase(Locale.ROOT);
	}

}