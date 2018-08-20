package com.oui.sncf.items;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

/**
 * Classe contenant l'algorithme principale. l'algorithme est s�par� en 2
 * parties (2 boucle) tant que la chaine de charact�re � traiter n'est pas vide:
 * <ul>
 * <li>une premi�re boucle qui prends deux valeur compl�mentaire dont la somme
 * est �gale � 10</li>
 * <li></li>
 * </ul>
 * 
 * @author Quentin
 *
 */
public class BoxFiller {

	private static final String DEFAULT_STRING_EXEMPLE = "163841689525773";
	private static final String ERROR_FORMAT_INPUT = "la list d'objet n'est pas correctement format�e , input:%s";
	private static final int MAX_VOLUME_IN_BOX = 10;
	private static final Logger LOGGER = LoggerFactory.getLogger(BoxFiller.class);

	@Autowired
	private ItemsGenerator generator;

	private ItemBox itemBox;

	private Integer actualItem;

	private Integer remainingVolume;

	private Integer nextItem;

	private static int actualFilledVolumeInBox;

	private static String generatedItemsString;

	/**
	 * Default Constructor
	 */
	public BoxFiller() {
		/* vide */
	}

	/**
	 * @param nextremainingVolume
	 * @return
	 */
	private List<Integer> getCandidatesListToFillBox(Integer nextremainingVolume) {
		Integer candidates;
		List<Integer> possibleItemsCandidateToFillBox = new ArrayList<>();
		if (nextremainingVolume == null) {
			return possibleItemsCandidateToFillBox;
		}
		candidates = nextremainingVolume;
		possibleItemsCandidateToFillBox = new ArrayList<>();
		for (candidates = nextremainingVolume; candidates > 0; candidates--) {
			possibleItemsCandidateToFillBox.add(candidates);
		}
		return possibleItemsCandidateToFillBox;
	}


	/**
	 * methode permettant de compl�ter une boite et en initialiser une nouvelle
	 * @param optimizedFilledBoxes
	 */
	private void treatCompletedBox(List<ItemBox> optimizedFilledBoxes) {
		if (!itemBox.getItemsInBox().isEmpty()) {
			actualFilledVolumeInBox = 0;
			remainingVolume = MAX_VOLUME_IN_BOX;
			optimizedFilledBoxes.add(itemBox);
			LOGGER.info(String.format("Box %s has been optimised and placed in a Box List", itemBox));
			itemBox = new ItemBox();
		}
	}

	/**
	 * M�thode permettant de selectionner le mode d'execution de l'application
	 * en se basant sur les arguments d'execution. *
	 * <ul>
	 * <li>arg1,<u><b>mandatory</u></b> : String,</li>
	 * <li>argument separator, <u><b>mandatory</u></b> : -</li>
	 * <li>arg2,<u><b>mandatory</u></b> : Boolean
	 * <ul>
	 * <li>valeur 'true' defini le lancement de Exercice exemple use case</li>
	 * <li>valeur 'false' execute l'application avec l'arg1</li>
	 * </ul>
	 * </ul>
	 * </p>
	 * 
	 * @param itemsVolumeChain
	 * @param treatExample
	 */
	private void checkArgumentsToTreat(String itemsVolumeChain, Boolean treatExample) {
		// si l'argument de traitement d'exemple est à true l'application se lance avec l'exemple de l'exercice
		if (treatExample) {
			LOGGER.info("Processing default exemple:", DEFAULT_STRING_EXEMPLE);
			generatedItemsString = DEFAULT_STRING_EXEMPLE;
		}
		// si l'argument est à false et que la chaine de caractère est vide ou nulle, autogénération d'une chaine de caractère.
		else if ((StringUtils.isBlank(itemsVolumeChain) || itemsVolumeChain == null) && !treatExample) {
			generatedItemsString = generator.generateRndItemsString();
			LOGGER.info("Processing Auto-generated ItemList by ItemsGenerator.Class :", generatedItemsString);	
		}
		// si l'argument est à false et que la chaine de caractère est défini.
		else if (!StringUtils.isBlank(itemsVolumeChain) || itemsVolumeChain == null) {
			generatedItemsString = itemsVolumeChain;
			LOGGER.info("Processing Argument ItemList :", generatedItemsString);
		}
		
		LOGGER.info("itemList to process : " + generatedItemsString);
		Pattern pattern = Pattern.compile("[0-9]+");
		Matcher matcher = pattern.matcher(generatedItemsString);
		// v�rification du typage de la chaine de charact�re en entr�e.
		if (!matcher.find()) {
			LOGGER.error(String.format(ERROR_FORMAT_INPUT, itemsVolumeChain));
			throw new IllegalArgumentException(String.format(ERROR_FORMAT_INPUT, itemsVolumeChain));
		}
		// suppression des potentiels '0' existant dans la Chaine de charact�res
		if (StringUtils.contains(generatedItemsString, '0')) {
			StringUtils.countMatches(generatedItemsString, '0');
			LOGGER.info(String.format("Deleting %s '0' in %s ", StringUtils.countMatches(generatedItemsString, '0'),
					generatedItemsString));
			generatedItemsString = StringUtils.remove(generatedItemsString, '0');
		}

	}

	/**
	 * methode permettant de r�cup�rer l'�l�ment compl�mentaire dans la liste
	 * @param listToSearch
	 * @param complementaryVolumeToSearch
	 * @return Integer
	 */
	private Integer getComplementaryVolumeFromList(List<Integer> listToSearch, Integer complementaryVolumeToSearch) {
		Integer complementaryVolumeToreturn = null;
		for (int i = 0; i < listToSearch.size(); i++) {
			if (listToSearch.get(i) == complementaryVolumeToSearch) {
				complementaryVolumeToreturn = listToSearch.get(i);
			}
		}
		return complementaryVolumeToreturn;

	}

	/**
	 * Methode permettant de maximiser le nombre d'articles par carton
	 * 
	 * @param itemsVolumeChain
	 * @throws Exception
	 */
	public List<ItemBox> optimizedBoxFill(String itemsVolumeChain, Boolean treatExample)
			throws IllegalArgumentException {

		List<ItemBox> optimizedFilledBoxes = new ArrayList<>();
		//Controle des arguments en entr�e
		checkArgumentsToTreat(itemsVolumeChain, treatExample);

		LOGGER.info("Processing ...");
		
		actualFilledVolumeInBox = 0;
		//transformation de la chaine de charact�re en List d'Integer
		List<Integer> listOfItems = generatedItemsString.chars().mapToObj(e -> Character.getNumericValue(e))
				.collect(Collectors.toList());
		
		//initiation des valeurs utiles � l'algorithme
		actualItem = listOfItems.get(0);
		remainingVolume = MAX_VOLUME_IN_BOX - (actualFilledVolumeInBox + actualItem);
		Integer perfectMatch = 0;

		itemBox = new ItemBox();
		//iterration
		for (int i = 0; i < listOfItems.size(); i++) {
			actualItem = listOfItems.get(i);

			LOGGER.debug("(Loop) traited item: actualFirstItem in list[" + actualItem + "]");
			
			//cas ou l'ajout de l'objet ne peut �tre fait car le volume de l'objet est trop grand
			if (actualItem + actualFilledVolumeInBox > MAX_VOLUME_IN_BOX) {
				LOGGER.info("adding current item is impossible, itemVolume=" + actualItem + " / filled volume in box="
						+ actualFilledVolumeInBox);
				LOGGER.info("Looking for best candidate ...");
				//r�cup�ration de la liste de candidats possible existant dans la liste pour compl�ter la boite.
				List<Integer> possibleCandidate = getPerfectMatchIfExists(listOfItems, perfectMatch);
				
				//d�finition du meilleur candidat existant si le candidat n'existe pas, renvoi null
				perfectMatch = CollectionUtils.findFirstMatch(listOfItems, possibleCandidate);
				
				//le candidat existe
				if (perfectMatch != null) {
					LOGGER.info("Candidate Found:" + perfectMatch);
					actualFilledVolumeInBox += perfectMatch;
					remainingVolume = MAX_VOLUME_IN_BOX - (actualFilledVolumeInBox + perfectMatch);
					itemBox.add(perfectMatch);
					listOfItems.remove(perfectMatch);
					//retour au premier �l�ment de la liste
					i = -1;
				}
				// cas ou le candidat n'existe pas, la boite est compl�t�e
				if (perfectMatch == null) {
					LOGGER.info("Candidate Not Found ... and might be " + perfectMatch);
					treatCompletedBox(optimizedFilledBoxes);
					//récupération des pairs d'articles restants dont le poids est complémentaire tant que ces éléments existent. 				
					while(!listHasTwoMatchingElements(listOfItems).isEmpty()){
						LOGGER.info(String.format("adding Complementary values existing in list in box %s", itemBox ));
						List<Integer> matchingValues =listHasTwoMatchingElements(listOfItems);
						itemBox.add(matchingValues.get(0));
						itemBox.add(matchingValues.get(1));
						listOfItems.remove(matchingValues.get(0));
						listOfItems.remove(matchingValues.get(1));
						treatCompletedBox(optimizedFilledBoxes);
					}
					//retour au premier �l�ment de la liste	
					i = -1;
				}
			//cas ou le volume de l'objet ne d�passe pas le volume de la boite qui est en train d'�tre remplie
			} else if (actualItem + actualFilledVolumeInBox <= MAX_VOLUME_IN_BOX) {
				actualFilledVolumeInBox += actualItem;
				remainingVolume = MAX_VOLUME_IN_BOX - (actualFilledVolumeInBox + actualItem);
				itemBox.add(actualItem);
				listOfItems.remove(actualItem);
				//retour au premier �l�ment de la liste
				i = -1;
				//si la boite en train d'�tre remplie atteind le volume max, la boite est alors compl�t�e.
				if (actualFilledVolumeInBox == MAX_VOLUME_IN_BOX) {
					treatCompletedBox(optimizedFilledBoxes);
				}
				if (!itemBox.getItemsInBox().isEmpty() && listOfItems.isEmpty() ){
					treatCompletedBox(optimizedFilledBoxes);
				}
			}
			LOGGER.debug("iterated list:" + listOfItems);
			LOGGER.debug("list of boxes:" + optimizedFilledBoxes);
		}
		LOGGER.info("Processing succeed !");
		LOGGER.debug("optimsed boxes output:" + optimizedFilledBoxes.toString());
		return optimizedFilledBoxes;
	}

	/** 
	 * Méthode permettant de récupérer 2 articles de poids complémentaire 
	 * @param listOfItems
	 * @return
	 */
	private List<Integer> listHasTwoMatchingElements(List<Integer> listOfItems) {
		List<Integer> matchingValues = new ArrayList<>();
		for (int i = 0; i<listOfItems.size();i++){
			for (int y = 0; y<listOfItems.size(); y++){
				if (listOfItems.get(i)+listOfItems.get(y)== MAX_VOLUME_IN_BOX){
					matchingValues.add(listOfItems.get(i));
					matchingValues.add(listOfItems.get(y));
					return matchingValues;
				}
			}
		}
		return matchingValues;
	}

	/**
	 * Methode permettant de r�cup�r� le candidat existant.
	 * si cet �l�ment n'existe pas, renvoie null.
	 * @param listOfItems
	 * @return
	 */
	private List<Integer> getPerfectMatchIfExists(List<Integer> listOfItems, Integer perfectMatch) {
		List<Integer> possibleItemsCandidateToFillBox;
		possibleItemsCandidateToFillBox = getCandidatesListToFillBox(
				getComplementaryVolumeFromList(listOfItems, MAX_VOLUME_IN_BOX - (actualFilledVolumeInBox)));
		return possibleItemsCandidateToFillBox;
	}

	/**
	 * @return the generator
	 */
	public ItemsGenerator getGenerator() {
		return generator;
	}

	/**
	 * @param generator
	 *            the generator to set
	 */
	public void setGenerator(ItemsGenerator generator) {
		this.generator = generator;
	}

	/**
	 * @return the itemBox
	 */
	public ItemBox getItemBox() {
		return itemBox;
	}

	/**
	 * @param itemBox
	 *            the itemBox to set
	 */
	public void setItemBox(ItemBox itemBox) {
		this.itemBox = itemBox;
	}

	/**
	 * @return the actualItem
	 */
	public Integer getActualItem() {
		return actualItem;
	}

	/**
	 * @param actualItem
	 *            the actualItem to set
	 */
	public void setActualItem(Integer actualItem) {
		this.actualItem = actualItem;
	}

	/**
	 * @return the remainingVolume
	 */
	public Integer getRemainingVolume() {
		return remainingVolume;
	}

	/**
	 * @param remainingVolume
	 *            the remainingVolume to set
	 */
	public void setRemainingVolume(Integer remainingVolume) {
		this.remainingVolume = remainingVolume;
	}

	/**
	 * @return the nextItem
	 */
	public Integer getNextItem() {
		return nextItem;
	}

	/**
	 * @param nextItem
	 *            the nextItem to set
	 */
	public void setNextItem(Integer nextItem) {
		this.nextItem = nextItem;
	}

	/**
	 * @return the actualFilledVolumeInBox
	 */
	public static int getActualFilledVolumeInBox() {
		return actualFilledVolumeInBox;
	}

	/**
	 * @param actualFilledVolumeInBox
	 *            the actualFilledVolumeInBox to set
	 */
	public static void setActualFilledVolumeInBox(int actualFilledVolumeInBox) {
		BoxFiller.actualFilledVolumeInBox = actualFilledVolumeInBox;
	}

	/**
	 * @return the generatedItemsString
	 */
	public static String getGeneratedItemsString() {
		return generatedItemsString;
	}

	/**
	 * @param generatedItemsString
	 *            the generatedItemsString to set
	 */
	public static void setGeneratedItemsString(String generatedItemsString) {
		BoxFiller.generatedItemsString = generatedItemsString;
	}

}
