/**
 * @author William Fiset, Drew Chaboyer
 * Object Oriented Design - COMP 3721
 * Tick Attack milestone #5
 **/

public class ItemFactory {

	// These are the ID values of all the items the
	// factory is able to create
	public static final int WOLFS_BANE      = 101;
	public static final int UNICORN_HAIR    = 102;
	public static final int STUDENTS_TEARS  = 103;
	public static final int EEL_EYE         = 104;
	public static final int NIGHT_SHADE     = 105;
	public static final int DRAGON_SCALE    = 106;
	public static final int HIPSTER_COFFEE  = 107;
	public static final int MOUNTIE_PRIDE   = 108;

	public static final int[] FACTORY_ITEMS = {
		WOLFS_BANE,
		UNICORN_HAIR,
		STUDENTS_TEARS,
		EEL_EYE,
		NIGHT_SHADE,
		DRAGON_SCALE,
		HIPSTER_COFFEE,
		MOUNTIE_PRIDE
	};

	// Create an Item by ID
	public Item createItemById(int id) {
		switch(id) {

			case WOLFS_BANE: return createWolfsbane();
			case UNICORN_HAIR: return createUnicornHair();
			case STUDENTS_TEARS: return createStudentsTears();
			case EEL_EYE: return createEelEye();
			case NIGHT_SHADE: return createNightShade();
			case DRAGON_SCALE: return createDragonScale();
			case HIPSTER_COFFEE: return createHipsterCoffee();
			case MOUNTIE_PRIDE: return createMountiePride();

			default: return null;

		}
	}

	public Item createWolfsbane() {
		return new Item(WOLFS_BANE, "Wolfsbane", "An ingredient for potion 1");
	}
	
	public Item createUnicornHair() {
		return new Item(UNICORN_HAIR, "Unicorn Hair", "An ingredient for potion 1");
	}
	
	public Item createStudentsTears() {
		return new Item(STUDENTS_TEARS, "Students' Tears", "An ingredient for potion 1");
	}
	
	public PotionOne createPotionOne() {
		return new PotionOne();
	}
	
	public Item createEelEye() {
		return new Item(EEL_EYE, "Eel's Eye", "An ingredient for Potion 2");
	}
	
	public Item createNightShade() {
		return new Item(NIGHT_SHADE, "Nightshade", "An ingredient for Potion 2");
	}
	
	public PotionTwo createPotionTwo() {
		return new PotionTwo();
	}
	
	public Item createDragonScale() {
		return new Item(DRAGON_SCALE, "Dragon Scale", "An ingredient for Potion 3");
	}
	
	public Item createHipsterCoffee() {
		return new Item(HIPSTER_COFFEE, "Hipster Coffee", "An ingredient for Potion 3");
	}
	
	public Item createMountiePride() {
		return new Item(MOUNTIE_PRIDE, "Mountie Pride", "An ingredient for Potion 3");
	}
	
	public PotionThree createPotionThree() {
		return new PotionThree();
	}
	
}
