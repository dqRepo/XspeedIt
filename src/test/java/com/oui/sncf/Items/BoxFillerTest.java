package com.oui.sncf.Items;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.oui.sncf.items.BoxFiller;
import com.oui.sncf.items.ItemBox;
import com.oui.sncf.main.Main;

public class BoxFillerTest {

	@Test
	public void optimisedBoxTest() {
		BoxFiller boxFiller = new BoxFiller();
		String args[] = {};
		//traite l'exemple;
		List<ItemBox> result = boxFiller.optimizedBoxFill(null, true);
		assertEquals(8,result.size());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void throwsArgumentException1() {
		BoxFiller boxFiller = new BoxFiller();
		List<ItemBox> result = boxFiller.optimizedBoxFill("AZEAZRA", false);
	}

}
