package com.oui.sncf.applicationRunner;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.oui.sncf.items.BoxFiller;
import com.oui.sncf.items.ItemBox;

/**
 * Runner de l'application qui embarque le contexte Stpring
 *
 * @author Quentin
 *
 */
public class AppRunner {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AppRunner.class);
	private static final String ERROR_INSTRUCTION_LAUNCH = ", you need to Launch application with arguments 'arg1-arg2' ; arg1 Type:String reprensenting the chain of item numbers to fill in optimized boxes  ; arg2 Type:Boolean (value = 'true' or 'false') reprensenting Exercice exemple Case execution";
	private static final String BOX_SEPARATOR = "/";
	
	@Autowired
	private BoxFiller boxfiller;
	
	private String[] args;

	public AppRunner() {

	}
	
	
	
	/**
	 * @return the boxfiller
	 */
	public BoxFiller getBoxfiller() {
		return boxfiller;
	}



	/**
	 * @param boxfiller the boxfiller to set
	 */
	public void setBoxfiller(BoxFiller boxfiller) {
		this.boxfiller = boxfiller;
	}



	/**
	 * @return the args
	 */
	public String[] getArgs() {
		return args;
	}



	/**
	 * @param args the args to set
	 */
	public void setArgs(String[] args) {
		this.args = args;
	}



	public void runApp(){
		try {
			if (ArrayUtils.isEmpty(args)) {
				throw new IllegalArgumentException("No arguments Launching application" + ERROR_INSTRUCTION_LAUNCH);
			} else if (!args[0].contains(".\\-") && !(args[0].contains("true") || args[0].contains("false"))) {
				throw new IllegalArgumentException(
						"Inexitant argument separator " + ERROR_INSTRUCTION_LAUNCH + Arrays.toString(args));
			}
			String[] splitedArgs = args[0].split(".\\-");
			String itemChain = splitedArgs[0];
			Boolean launchExampleByDefault = args[0].contains("true");
			List<ItemBox> boxList = boxfiller.optimizedBoxFill(itemChain, launchExampleByDefault);
			//affichage du resultat
			StringBuilder sb= new StringBuilder();
			for (int i = 0; i<boxList.size();i++){
				sb.append(boxList.get(i));
				if (i!=boxList.size()-1){
					sb.append(BOX_SEPARATOR);
				}
			}
			String result = sb.toString();
			LOGGER.info(String.format("Result of optimisation : %s", result));

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	

}
