
public class ItemFactory {

	public static final int[] FACTORY_ITEMS = { 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 11 };

	public Item createItemById(int id) {
		switch(id) {
			case 101: return createWolfsbane();
			case 102: return createUnicornHair();
			case 103: return createStudentsTears();
			case 104: return createPotionOne();
			case 105: return createEelEye();
			case 106: return createNightShade();
			case 107: return createPotionTwo();
			case 108: return createDragonScale();
			case 109: return createHipsterCoffee();
			case 110: return createMountiePride();
			case 111: return createPotion3();
			default: return null;
		}
	}

	public Item createWolfsbane() {
		return new Item(101, "Wolfsbane", "An ingredient for potion 1");
	}
	
	public Item createUnicornHair() {
		return new Item(102, "Unicorn Hair", "An ingredient for potion 1");
	}
	
	public Item createStudentsTears() {
		return new Item(103, "Students' Tears", "An ingredient for potion 1");
	}
	
	public Item createPotionOne() {
		return new Item(104, "Potion 1", "[INSERT DESCRIPTION HERE]");
	}
	
	public Item createEelEye() {
		return new Item(105, "Eel's Eye", "An ingredient for Potion 2");
	}
	
	public Item createNightShade() {
		return new Item(106, "Nightshade", "An ingredient for Potion 2");
	}
	
	public Item createPotionTwo() {
		return new Item(107, "Potion 2", "[INSERT DESCRIPTION HERE");
	}
	
	public Item createDragonScale() {
		return new Item(108, "Dragon Scale", "An ingredient for Potion 3");
	}
	
	public Item createHipsterCoffee() {
		return new Item(109, "Hipster Coffee, you wouldn't know what's in it", "An ingredient for Potion 3");
	}
	
	public Item createMountiePride() {
		return new Item(110, "Mountie Pride, at least we're not Acadia", "An ingredient for Potion 3");
	}
	
	public Item createPotion3() {
		return new Item(111, "Potion 3", "[INSERT DESCRIPTION HERE");
	}
	
}
