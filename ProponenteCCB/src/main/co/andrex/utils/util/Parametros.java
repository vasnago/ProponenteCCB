package co.andrex.utils.util;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.Application;
import javax.inject.Named;
import javax.servlet.ServletContext;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;

@SuppressWarnings("serial")
@Named
@ApplicationScoped
public class Parametros implements Serializable {
	private static Logger log = LogManager.getLogger("Aplicacion");

	public Parametros() {
		try {
			String nombreAplicacion = ControladorContexto
					.getInitParameter("application.name");

			ServletContext sc = ControladorContexto.getServletContext();

			String path = sc.getRealPath("");
			String archivo = "\\" + nombreAplicacion + "_log_.log";
			String archivoRolling = "\\" + nombreAplicacion
					+ "_log-%i.log-%d{dd-MM-yyy}";

			LoggerContext ctx = (LoggerContext) LogManager.getContext(false);

			Configuration config = ctx.getConfiguration();

			Layout<? extends Serializable> layout = PatternLayout
					.newBuilder()
					.withConfiguration(config)
					.withPattern("%d{MMMM dd, yyyy HH:mm:ss} [%C] - %M : %m \n")
					.build();

			SizeBasedTriggeringPolicy policy = SizeBasedTriggeringPolicy
					.createPolicy("10MB");

			DefaultRolloverStrategy strategy = DefaultRolloverStrategy
					.createStrategy("1000", "1", "min", null, config);

			RollingFileAppender appender = RollingFileAppender.createAppender(
					path + archivo, path + archivoRolling, "false", "File",
					"false", "4000", "true", policy, strategy, layout, null,
					"false", "false", null, config);

			appender.start();
			config.addAppender(appender);
			AppenderRef ref = AppenderRef.createAppenderRef("File", null, null);
			AppenderRef[] refs = { ref };

			LoggerConfig loggerConfig = LoggerConfig.createLogger("true",
					Level.ALL, nombreAplicacion, "true", refs, null, config,
					null);

			loggerConfig.addAppender(appender, null, null);
			config.addLogger(nombreAplicacion, loggerConfig);
			ctx.updateLoggers();

			log = LogManager.getLogger(nombreAplicacion);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Logger getLog() {
		return log;
	}

	public static String getExceptionStackTraceAsString(Exception exception) {
		StringWriter sw = new StringWriter();
		exception.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}

	public static ValueExpression getValueExpression(String nombre) {
		Application app = ControladorContexto.getApplication();
		ELContext elContext = ControladorContexto.getELContext();
		ExpressionFactory factory = app.getExpressionFactory();
		ValueExpression ve = null;
		try {
			ve = factory.createValueExpression(elContext, "#{" + nombre + "}",
					String.class);
		} catch (Exception e) {
			ve = null;
		}
		return ve;
	}

	public static MethodExpression getMethodExpression(String nombre) {
		Application app = ControladorContexto.getApplication();
		ELContext elContext = ControladorContexto.getELContext();
		ExpressionFactory factory = app.getExpressionFactory();

		MethodExpression me = factory.createMethodExpression(elContext, "#{"
				+ nombre + "}", null, new Class[0]);

		return me;
	}
}
