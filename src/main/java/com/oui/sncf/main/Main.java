package com.oui.sncf.main;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.oui.sncf.applicationRunner.AppRunner;

/**
 * <b>Classe Main de l'application <i><u>Optimisation de Rangement</i></u></b>
 * Charge l'AppRunner et execute la m�thode run.
 * @author Quentin
 *
 */
public class Main {

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

	

	private static final String ERROR_INSTRUCTION_LAUNCH = ", you need to Launch application with arguments 'arg1-arg2' ; arg1 Type:String reprensenting the chain of item numbers to fill in optimized boxes  ; arg2 Type:Boolean (value = 'true' or 'false') reprensenting Exercice exemple Case execution";

	/**
	 * <b><u>M�thode main de l'application:</u></b>
	 * </p>
	 * Contient la M�thode main(String[] args) et Lance l'execution de l'application de rangement optimiser d'objets
	 * dans des boites d'envoie. Les arguments de la M�thode main: 'arg1-arg2'
	 * <ul>
	 * 		<li>arg1,mandatory : String,</li>
	 * 		<li>argument separator : -</li>
	 * 		<li>arg2,mandatory : Boolean
	 * 			<ul>
	 * 				<li>valeur 'true' defini le lancement de Exercice exemple use case</li>
	 * 				<li>valeur 'false' execute l'application avec l'arg1</li>
	 * 			</ul>
	 * 	</ul>
	 * </p>
	 *
	 *
	 * 
	 * @param args
	 * @throws IllegalArgumentException
	 */
	public static void main(String[] args) throws IllegalArgumentException{
	
		 @SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-config.xml");
		
		try {
			LOGGER.info(String.format("Starting Application with arguments:" + args));
			if (ArrayUtils.isEmpty(args)) {
				throw new IllegalArgumentException("No arguments Launching application" + ERROR_INSTRUCTION_LAUNCH);
			} else if (!args[0].contains(".\\-") && !(args[0].contains("true") || args[0].contains("false"))) {
				throw new IllegalArgumentException(
						"Inexitant argument separator " + ERROR_INSTRUCTION_LAUNCH + Arrays.toString(args));
			}
			AppRunner runner = (AppRunner) context.getBean("AppRunner");
			runner.setArgs(args);
			runner.runApp();

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}
