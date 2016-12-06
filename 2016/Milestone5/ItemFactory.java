
public class ItemFactory {

	public Item createWolfsbane() {
		return new Item(101, "Wolfsbane", "An ingredient for potion 1");
	}
	
	public Item createUnicornHair() {
		return new Item(102, "Unicorn Hair", "An ingredient for potion 1");
	}
	
	public Item createStudentsTears() {
		return new Item(103, "Students' Tears", "An ingredient for potion 1");
	}
	
	public PotionOne createPotionOne() {
		return new PotionOne();
	}
	
	public Item createEelEye() {
		return new Item(105, "Eel's Eye", "An ingredient for Potion 2");
	}
	
	public Item createNightShade() {
		return new Item(106, "Nightshade", "An ingredient for Potion 2");
	}
	
	public PotionTwo createPotionTwo() {
		return new PotionTwo();
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
	
	public PotionThree createPotionThree() {
		return new PotionThree();
	}
	
}
