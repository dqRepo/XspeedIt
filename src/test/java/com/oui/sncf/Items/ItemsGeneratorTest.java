package com.oui.sncf.Items;

import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;


@SuppressWarnings("deprecation")
@RunWith(JUnit4ClassRunner.class)
public class ItemsGeneratorTest {
	
	//ItemsGenerator itemsGenerator;

	@Before
	@Test
	public void test() {
		//itemsGenerator = new ItemsGenerator();

	}
//	
//	@Test
//	public void itemsGeneratorNumberChainStringTest(){
//		String rndStringNumberChain = itemsGenerator.generateRndItemsString();
//		Pattern pattern = Pattern.compile("[0-9]+");
//		Matcher matcher = pattern.matcher(rndStringNumberChain);
//		Assert.assertTrue(!matcher.find());
//	}
//	
//	@Test
//	public void generatedItemsRandomnessTest(){
//		Set<String> stringSet = new HashSet<>();
//		for (int i = 0; i< 10000000; i++){
//			Assert.assertTrue(stringSet.add(itemsGenerator.generateRndItemsString()));
//		}
//		//Assert.assertEquals(stringSet.size(),10000000);
//	}

}
