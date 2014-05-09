
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    //
    // Public
    //

    // Globals
    public static final boolean DEBUGGING  = false; // Debugging flag.
    public static final int MAX_LOCALES = 10;    // Total number of rooms/locations we have in the game.
    public static Locale Locale = null;        // Player starts in locale 0.
    public static String command;               // What the player types as he or she plays the game.
    public static boolean stillPlaying = true; // Controls the game loop.
    public static int[][]  nav;                 // An uninitialized array of type int int.
    public static double moves = 0;                // Counter of the player's moves.
    public static double score = 0;                // Tracker of the player's score.
    public static Currency goldnotes = new Currency("Gold Notes");            // currency of the enigma.
    public static ListItem boughtItems;           // items purchased from Tadili
    public static ItemMan[] inventory;
    public static int num = 9999;
    public static double completion = 0;
    public static boolean tadiliHasBeenKilled = false;
    public static boolean hasDisk = false;
    public static boolean hasApple = false;
    public static boolean isVisiting = false;
    public static boolean visited = false; 
    public static ListMan lm1 = new ListMan();
    public static String selection = null;
    public static Scanner transactionDone = new Scanner(System.in); 
    static ArrayList<String> newInv = new ArrayList<String>();
    static ArrayList<String> oldInv = new ArrayList<String>();
    private static ListItem[] storeInventory = new ListItem[666];
    private static Stack traceUpDown= new Stack();
    private static Queue traceDownUp= new Queue();
    
    
    public static void shopInit(ListMan lm1) {
    	System.out.println("Yo I'm setting up my tent, hop off the merchandise! Talk to me in a sec..");
    	readMagicItemsFromFileToList("magic.txt", lm1);
    	readMagicItemsFromFileToArray("magic.txt", storeInventory);
    	selectionSort(storeInventory);
    	visited = true;
    }
    
    public static void transaction() {
    	System.out.println("Iight the shop is ready dawg.. What item are you looking for my dude??");
    	String selection = transactionDone.nextLine();
    	if(binarySearchArray(storeInventory, selection) == true){
    		goldnotes.subtract(boughtItems.getCost());
    		ItemMan anything = new ItemMan(num, boughtItems.getName(), true);
    		num++;
    		//inventory=new ItemMan[num];
    		//inventory[num-1]=anything;
    		System.out.println(boughtItems.getName() + " bought.");
    	}
    }	
 
    private static void readMagicItemsFromFileToList(String fileName,
            ListMan lm) {
File myFile = new File(fileName);
try {
Scanner input = new Scanner(myFile);
while (input.hasNext()) {
// Read a line from the file.
String itemName = input.nextLine();

// Construct a new list item and set its attributes.
ListItem fileItem = new ListItem();
fileItem.setName(itemName);
fileItem.setCost((int) (Math.random() * 100));
fileItem.setNext(null); // Still redundant. Still safe.

// Add the newly constructed item to the list.
lm.add(fileItem);
}
// Close the file.
input.close();
} catch (FileNotFoundException ex) {
System.out.println("File not found. " + ex.toString());
}

}

	public static void main(String[] args) {

        // Get the game started.
        init();
        try {
            traceUpDown.push(Locale);
			traceDownUp.enqueue(Locale);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        updateDisplay();

        // Game Loop
        while (stillPlaying) {
            getCommand();
            navigate();
            updateDisplay();
        }

        // We're done. Thank the player and exit.
        System.out.println("Thank you for playing.");
    }

    //
    // Private
    //
    
 
    
    private static void init() {
        // Initialize any uninitialized globals.
        command = new String();
        stillPlaying = true;   // TODO: Do we need this?
        System.out.println("Welcome to the Entrapment of the Enigma. Type help for assistance.. you're going to need it..");
        
      
        // Set up the location instances of the Locale class.
        Locale loc0 = new Locale(0);
        loc0.setName("Peripheral Sphere of Existence");
        loc0.setDesc("You are trapped within a sphere that radiates with energy.. You need to return back to your home... You can float down to see the lowest tier of the universe or up towards the surface of reality..... Would you like a map? Take one!");

        Space loc1 = new Space(1);
        loc1.setName("Lower Universe");
        loc1.setDesc("You are in the lowest tier of the Universe. The only place you can float is up.... Unless you have the the Disk of Returning, only then you can return to your true home.");
        loc1.setNearestPlanet("Home");

        Locale loc2 = new Locale(2);
        loc2.setName("Fabric of Reality");
        loc2.setDesc("You find yourself in a white space. There seems to be something more to this place, but you cannot put your finger on it.... You can float up or down, but theres nothing of use here.");
        
        Locale loc3 = new Locale(3);
        loc3.setName("Home");
        loc3.setDesc("The current plane looks unfamiliar and foreign, but it feels like home. You begin to feel the force of gravity. You can walk east, west or south from here...");
        
        Locale loc4 = new Locale(4);
        loc4.setName("Tadili's Magic Shop");
        loc4.setDesc("Welcome to Tadili's Magic Wares and Equipment! Type shop to browse my store! \n Or you can leave by going east.");
        
        
        Locale loc5 = new Locale(5);
        loc5.setName("Novice Pass");
        loc5.setDesc("A dirt path lined with tall reeds stretches as far as the eye can see. It seems that the only direction to proceed is east, but you can turn back and go west.");

        Locale loc6 = new Locale(6);
        loc6.setName("Cross Roads");
        loc6.setDesc("You step into a clearing. You see a signpost. It reads: NORTH: Death Plateau, WEST: Novice Pass, EAST: Thick Woods ");
        
        Locale loc7 = new Locale(7);
        loc7.setName("Death Plateau");
        loc7.setDesc("You have reached the Death Plateau. It smells like death, and it seems there's no way down. Turn back by going south.. ");
        
        Locale loc8 = new Locale(8);
        loc8.setName("Thick Woods");
        loc8.setDesc("The brush is so thick you can barely see the ahead. Unless you can get through, you must turn back by going west.. ");
        
        Locale loc9 = new Locale(9);
        loc9.setName("Deep Crater");
        loc9.setDesc("You find a large crater made in the ground. Perhaps this was an impact from a meteor. You can continue by going north, or turn back by going south..  ");
        
        Locale loc10 = new Locale(10);
        loc10.setName("Earth");
        loc10.setDesc("You find yourself back on Planet Earth! Congratultions you have escaped the Enigma!  ");
        
        Locale loc11 = new Locale(11);
        loc11.setName("Hero's Pass");
        loc11.setDesc("Only the most badass heros are allowed here. Since you have killed Tadili, you must be badass! ");
        
  
        
          
        
        // Linking up the Locales
        loc0.setNorth(loc2);
        loc0.setSouth(loc1);
        loc0.setEast(null);
        loc0.setWest(null);
        loc1.setNorth(loc0);
        loc1.setSouth(null);
        loc1.setEast(null);
        loc1.setWest(null);
        loc2.setNorth(loc3);
        loc2.setSouth(loc0);
        loc2.setEast(null);
        loc2.setWest(null);
        loc3.setNorth(null);
        loc3.setSouth(loc2);
        loc3.setEast(loc5);
        loc3.setWest(loc4);
        loc4.setNorth(null);
        loc4.setSouth(null);
        loc4.setEast(loc3);
        loc4.setWest(null);
        loc5.setNorth(null);
        loc5.setSouth(null);
        loc5.setEast(loc6);
        loc5.setWest(loc3);
        loc6.setNorth(loc9);
        loc6.setSouth(null);
        loc6.setEast(loc8);
        loc6.setWest(loc5);
        loc7.setNorth(null);
        loc7.setSouth(loc9);
        loc7.setEast(null);
        loc7.setWest(null);
        loc8.setNorth(null);
        loc8.setSouth(null);
        loc8.setEast(null);
        loc8.setWest(loc6);
        loc9.setNorth(loc7);
        loc9.setSouth(loc6);
        loc9.setEast(null);
        loc9.setWest(null);
       
        
        ItemMan item0 = new ItemMan(0);
        item0.setName("Map");
        item0.setDesc("This is the map of the Universe.");

        ItemMan item1 = new ItemMan(1);
        item1.setName("Retractable Shovel");
        item1.setDesc("Wow! A portable shovel!");

        ItemMan item2 = new ItemMan(2);
        item2.setName("Tadili's Machete");
        item2.setDesc("If you found this razor sharp machete, you must be either professor Labouseur, David, or a 1337h4x0r.");

        ItemMan item3 = new ItemMan(3);
        item3.setName("Novice Pass Brochure");
        item3.setDesc("You are a novice explorer on your way to ADVENTURE!");

        ItemMan item4 = new ItemMan(4);
        item4.setName("Climbing Boots");
        item4.setDesc("Boots fitted with climbing studs. This must make scaling easy...");

        ItemMan item5 = new ItemMan(5);
        item5.setName("Disk of Returning");
        item5.setDesc("You have found the disk of returning. Go to the lower universe and type home");
        


        inventory  = new ItemMan[7];
        inventory[0] = item0;
        inventory[1] = item1;
        inventory[2] = item2;
        inventory[3] = item3;
        inventory[4] = item4;
        inventory[5] = item5;
Locale=loc0;
    }

    
    private static void getInvy(){

        String LeatherBag="Your inventory contains: \n";
        System.out.println(LeatherBag + newInv);

    }
   
    
    private static void updateDisplay() {
        System.out.println(Locale.getText());
    }

    private static void getCommand() {
    	completion = score/moves + goldnotes.getSuperAmt()/4;
    	long l = (int)Math.round(completion * 100);
    	completion = l / 100.0;
        System.out.print("[" + moves + " moves, score " + score +" completion: "+ completion +" Gold Notes: "+ goldnotes.getAmt() +"] ");
        Scanner inputReader = new Scanner(System.in);
        command = inputReader.nextLine();  // command is global.
    }

    private static void navigate() {
    	int INVALID=-1;
        Locale newLocation=new Locale();
        newLocation=Locale;
        score = score + 5;
        
       try {
        if ( command.equalsIgnoreCase("north") || command.equalsIgnoreCase("n") ) {
        	if(Locale.getNorth()!=null){
                Locale = Locale.getNorth();
                moves+=1;
                traceUpDown.push(Locale);
                traceDownUp.enqueue(Locale);
                goldnotes.add(15);
                
            }else{
                System.out.println("You can't go that way");
            }
        } else if ( command.equalsIgnoreCase("south") || command.equalsIgnoreCase("s") ) {
        	if(Locale.getSouth()!=null){
                Locale = Locale.getSouth();
                moves+=1;
                traceUpDown.push(Locale);
                traceDownUp.enqueue(Locale);
                goldnotes.add(15);
            }else{
                System.out.println("You can't go that way");
            }
        } else if ( command.equalsIgnoreCase("east")  || command.equalsIgnoreCase("e") ) {
        	if(Locale.getEast()!=null){
                Locale = Locale.getEast();
                moves+=1;
                traceUpDown.push(Locale);
                traceDownUp.enqueue(Locale);
                goldnotes.add(15);
            }else{
                System.out.println("You can't go that way");
            }
        } else if ( command.equalsIgnoreCase("west")  || command.equalsIgnoreCase("w") ) {
        	if(Locale.getWest()!=null){
                Locale = Locale.getWest();
                moves+=1;
                traceUpDown.push(Locale);
                traceDownUp.enqueue(Locale);
                goldnotes.add(15);
            }else{
                System.out.println("You can't go that way");
            }
        } else if ( command.equalsIgnoreCase("Kill Tadili")) {
        	killTadili();
        } else if ( command.equalsIgnoreCase("Chop Brush")) {
        	enterHero();
        } else if ( command.equalsIgnoreCase("quit")  || command.equalsIgnoreCase("q")) {
            quit();
        } else if ( command.equalsIgnoreCase("help")  || command.equalsIgnoreCase("h")) {
            help();
        } else if ( command.equalsIgnoreCase("map")  || command.equalsIgnoreCase("m")) {
            map();
        } else if ( command.equalsIgnoreCase("take")  || command.equalsIgnoreCase("t")) {
            take();   
        } else if ( command.equalsIgnoreCase("home")) {
            home();   
        } else if ( command.equalsIgnoreCase("shop")  && Locale.getId()==4) {
        	if (visited == false) {
        		shopInit(lm1);	
        	} else {         		transaction();
        	}
        	
        } else if ( command.equalsIgnoreCase("inventory") || command.equalsIgnoreCase("i")) {
        	getInvy();
        };
       
       }catch(Exception ex){
    	   ex.getMessage();
       }
    }

	

	private static void enterHero() {
		// TODO Auto-generated method stub
    	if (Locale.getId()==8 && tadiliHasBeenKilled == true) { 
    		 Locale.setId(11);
    		 newInv.add(inventory[6].getName());
    		 hasApple = true;
    		
    	} else {
    		System.out.println("You either haven't killed Tadili or you aren't at the Thick Woods");
    	}
    		
	}

	private static void home() {
		
    	if (hasDisk == true) {
    		Locale.setId(10);
    		System.out.println("Congratulations on returning to your home, Planet Earth!");
    	}
    	else {
    		System.out.println("You never got the disk of returning, how do you expect to return????");
    	}
		
	}

	private static void killTadili() {
		// TODO Auto-generated method stub
    	if ((Locale.getId() == 4)){
    		newInv.add(inventory[2].getName());
    		System.out.println("You have killed Tadili and picked up his Machete. You can return back here and Tadili will magically reappear as if nothing happened!");
    		tadiliHasBeenKilled = true;
    	}
		
	}

	private static void take(){

        if ((Locale.getId() == 0)) {
        	newInv.add(inventory[0].getName());
            System.out.println("You find " + inventory[0].getName() + ", it was placed in your inventory.");
        }
        if ((Locale.getId() == 3)) {
        	newInv.add(inventory[1].getName());
            System.out.println("You find " + inventory[1].getName() + ", it was placed in your inventory.");
         
        }
        if ((Locale.getId() == 5)) {
        	newInv.add(inventory[3].getName());
            System.out.println("You find " + inventory[3].getName() + ", it was placed in your inventory.");
         
        }
        if ((Locale.getId() == 6)) {
        	newInv.add(inventory[4].getName());
            System.out.println("You find " + inventory[4].getName() + ", it was placed in your inventory.");
         
        }
        if ((Locale.getId() == 9)) {
        	newInv.add(inventory[5].getName());
            System.out.println("You find " + inventory[5].getName() + ", it was placed in your inventory.");
            hasDisk = true;
  
        }

        else if (((Locale.getId() == 1)) || ((Locale.getId() == 2)) || ((Locale.getId() == 4)) || ((Locale.getId() == 7)) || ((Locale.getId() == 8))) {
            System.out.println("There's nothing to take!");
        }
    }
	//map is intentionally messed up took 30 minutes to set up so that it would display properly in console >.<
	private static void map() {
			  if ((Locale.getId() == 0)) {
			 	System.out.println("                                                                                             ___________________                                                                                                                                                                                                                                                                   ");																		
		    	System.out.println("	                                       		                                    (    Deep Crater    )                                                                                                                                      															                                                        ");
				System.out.println("                                                                                            (_______|   |_______)                                                                                                                                                                                                                                                                 ");																		
		    	System.out.println("	                                       		                            		    |   |				                                                                                                                                      															                                                        ");
				System.out.println("                                                                                            ________|   |________                                                                                                                                                                                                                                                                  ");																		
		    	System.out.println("	                                       		                                   (    Death Plateau    )     					                                                                                                                      															                                                        ");
				System.out.println("                                                                                           (________|   |________)                                                                                                                                                                                                                                                                 ");																		
		    	System.out.println("	                                       		                            	            |   |		                                                                                                         															                                                        ");
				System.out.println("                                       _____________________________________________________________|___|_______________________________                                                                                                                                                                                                                       ");																		
		    	System.out.println("	                              (    Tadili            Home	       Novice Pass           Cross Roads           Thick Woods  )                                                                                                 															                                                        ");
				System.out.println("                                      (_____________________|	 |______________________________________________________________________)                                                                                                                                                                                                                     ");																		
		    	System.out.println("	                                            ________|    |________                                                                                                             															                                                        ");
				System.out.println("                                                   (	    Reality       )                                                                                                                                                                                                                                        ");																		
		    	System.out.println("	                                           (________|    |________)                                                                                                                     															                                                        ");
				System.out.println("                                                    ________|    |________                                                                                                                                                                                                                                         ");																		
		    	System.out.println("	                                           (	  *Existence*     )                                                                                                                     															                                                        ");
				System.out.println("                                                   (________|    |________)                                                                                                                                                                                                                                        ");																		
		    	System.out.println("	                                            ________|    |________                                                                                                                                              															                                                        ");
				System.out.println("                                                   (	    LowerU        )                                                                                                                                                                                                                                       ");																		
		    	System.out.println("	                                           (______________________)                                                                                        															                                                        ");
				
		  } else if (Locale.getId() == 1) {
			 	System.out.println("                                                                                             ___________________                                                                                                                                                                                                                                                                   ");																		
		    	System.out.println("	                                       		                                    (    Deep Crater    )                                                                                                                                      															                                                        ");
				System.out.println("                                                                                            (_______|   |_______)                                                                                                                                                                                                                                                                 ");																		
		    	System.out.println("	                                       		                            		    |   |				                                                                                                                                      															                                                        ");
				System.out.println("                                                                                            ________|   |________                                                                                                                                                                                                                                                                  ");																		
		    	System.out.println("	                                       		                                   (    Death Plateau    )     					                                                                                                                      															                                                        ");
				System.out.println("                                                                                           (________|   |________)                                                                                                                                                                                                                                                                 ");																		
		    	System.out.println("	                                       		                            	            |   |		                                                                                                         															                                                        ");
				System.out.println("                                       _____________________________________________________________|___|_______________________________                                                                                                                                                                                                                       ");																		
		    	System.out.println("	                              (    Tadili            Home	       Novice Pass           Cross Roads           Thick Woods  )                                                                                                 															                                                        ");
				System.out.println("                                      (_____________________|	 |______________________________________________________________________)                                                                                                                                                                                                                     ");																		
		    	System.out.println("	                                            ________|    |________                                                                                                             															                                                        ");
				System.out.println("                                                   (	    Reality       )                                                                                                                                                                                                                                        ");																		
		    	System.out.println("	                                           (________|    |________)                                                                                                                     															                                                        ");
				System.out.println("                                                    ________|    |________                                                                                                                                                                                                                                         ");																		
		    	System.out.println("	                                           (	   Existence      )                                                                                                                     															                                                        ");
				System.out.println("                                                   (________|    |________)                                                                                                                                                                                                                                        ");																		
		    	System.out.println("	                                            ________|    |________                                                                                                                                              															                                                        ");
				System.out.println("                                                   (	   *LowerU*       )                                                                                                                                                                                                                                       ");																		
		    	System.out.println("	                                           (______________________)                                                                                        															                                                        ");
				
		  }  else if (Locale.getId() == 2) {
			 	System.out.println("                                                                                             ___________________                                                                                                                                                                                                                                                                   ");																		
		    	System.out.println("	                                       		                                    (    Deep Crater    )                                                                                                                                      															                                                        ");
				System.out.println("                                                                                            (_______|   |_______)                                                                                                                                                                                                                                                                 ");																		
		    	System.out.println("	                                       		                            		    |   |				                                                                                                                                      															                                                        ");
				System.out.println("                                                                                            ________|   |________                                                                                                                                                                                                                                                                  ");																		
		    	System.out.println("	                                       		                                   (    Death Plateau    )     					                                                                                                                      															                                                        ");
				System.out.println("                                                                                           (________|   |________)                                                                                                                                                                                                                                                                 ");																		
		    	System.out.println("	                                       		                            	            |   |		                                                                                                         															                                                        ");
				System.out.println("                                       _____________________________________________________________|___|_______________________________                                                                                                                                                                                                                       ");																		
		    	System.out.println("	                              (    Tadili            Home	       Novice Pass           Cross Roads           Thick Woods  )                                                                                                 															                                                        ");
				System.out.println("                                      (_____________________|	 |______________________________________________________________________)                                                                                                                                                                                                                     ");																		
		    	System.out.println("	                                            ________|    |________                                                                                                             															                                                        ");
				System.out.println("                                                   (	   *Reality*      )                                                                                                                                                                                                                                        ");																		
		    	System.out.println("	                                           (________|    |________)                                                                                                                     															                                                        ");
				System.out.println("                                                    ________|    |________                                                                                                                                                                                                                                         ");																		
		    	System.out.println("	                                           (	   Existence      )                                                                                                                     															                                                        ");
				System.out.println("                                                   (________|    |________)                                                                                                                                                                                                                                        ");																		
		    	System.out.println("	                                            ________|    |________                                                                                                                                              															                                                        ");
				System.out.println("                                                   (	    LowerU        )                                                                                                                                                                                                                                       ");																		
		    	System.out.println("	                                           (______________________)                                                                                        															                                                        ");
				
		  }  else if (Locale.getId() == 3) {
			 	System.out.println("                                                                                             ___________________                                                                                                                                                                                                                                                                   ");																		
		    	System.out.println("	                                       		                                    (    Deep Crater    )                                                                                                                                      															                                                        ");
				System.out.println("                                                                                            (_______|   |_______)                                                                                                                                                                                                                                                                 ");																		
		    	System.out.println("	                                       		                            		    |   |				                                                                                                                                      															                                                        ");
				System.out.println("                                                                                            ________|   |________                                                                                                                                                                                                                                                                  ");																		
		    	System.out.println("	                                       		                                   (    Death Plateau    )     					                                                                                                                      															                                                        ");
				System.out.println("                                                                                           (________|   |________)                                                                                                                                                                                                                                                                 ");																		
		    	System.out.println("	                                       		                            	            |   |		                                                                                                         															                                                        ");
				System.out.println("                                       _____________________________________________________________|___|_______________________________                                                                                                                                                                                                                       ");																		
		    	System.out.println("	                              (    Tadili           *Home*       Novice Pass            Cross Roads                Thick Woods  )                                                                                                 															                                                        ");
				System.out.println("                                      (_____________________|	 |______________________________________________________________________)                                                                                                                                                                                                                     ");																		
		    	System.out.println("	                                            ________|    |________                                                                                                             															                                                        ");
				System.out.println("                                                   (	    Reality       )                                                                                                                                                                                                                                        ");																		
		    	System.out.println("	                                           (________|    |________)                                                                                                                     															                                                        ");
				System.out.println("                                                    ________|    |________                                                                                                                                                                                                                                         ");																		
		    	System.out.println("	                                           (	   Existence      )                                                                                                                     															                                                        ");
				System.out.println("                                                   (________|    |________)                                                                                                                                                                                                                                        ");																		
		    	System.out.println("	                                            ________|    |________                                                                                                                                              															                                                        ");
				System.out.println("                                                   (	    LowerU        )                                                                                                                                                                                                                                       ");																		
		    	System.out.println("	                                           (______________________)                                                                                        															                                                        ");
				
		  } else if (Locale.getId() == 4) {
			 	System.out.println("                                                                                             ___________________                                                                                                                                                                                                                                                                   ");																		
		    	System.out.println("	                                       		                                    (    Deep Crater    )                                                                                                                                      															                                                        ");
				System.out.println("                                                                                            (_______|   |_______)                                                                                                                                                                                                                                                                 ");																		
		    	System.out.println("	                                       		                            		    |   |				                                                                                                                                      															                                                        ");
				System.out.println("                                                                                            ________|   |________                                                                                                                                                                                                                                                                  ");																		
		    	System.out.println("	                                       		                                   (    Death Plateau    )     					                                                                                                                      															                                                        ");
				System.out.println("                                                                                           (________|   |________)                                                                                                                                                                                                                                                                 ");																		
		    	System.out.println("	                                       		                            	            |   |		                                                                                                         															                                                        ");
				System.out.println("                                       _____________________________________________________________|___|___________________________                                                                                                                                                                                                                      ");																		
		    	System.out.println("	                              (   *Tadili*           Home	       Novice Pass           Cross Roads       Thick Woods  )                                                                                                 															                                                        ");
				System.out.println("                                      (_____________________|	 |__________________________________________________________________)                                                                                                                                                                                                                     ");																		
		    	System.out.println("	                                            ________|    |________                                                                                                             															                                                        ");
				System.out.println("                                                   (	    Reality       )                                                                                                                                                                                                                                        ");																		
		    	System.out.println("	                                           (________|    |________)                                                                                                                     															                                                        ");
				System.out.println("                                                    ________|    |________                                                                                                                                                                                                                                         ");																		
		    	System.out.println("	                                           (	   Existence      )                                                                                                                     															                                                        ");
				System.out.println("                                                   (________|    |________)                                                                                                                                                                                                                                        ");																		
		    	System.out.println("	                                            ________|    |________                                                                                                                                              															                                                        ");
				System.out.println("                                                   (	    LowerU        )                                                                                                                                                                                                                                       ");																		
		    	System.out.println("	                                           (______________________)                                                                                        															                                                        ");
				
		  } else if (Locale.getId() == 5) {
			 	System.out.println("                                                                                             ___________________                                                                                                                                                                                                                                                                   ");																		
		    	System.out.println("	                                       		                                    (    Deep Crater    )                                                                                                                                      															                                                        ");
				System.out.println("                                                                                            (_______|   |_______)                                                                                                                                                                                                                                                                 ");																		
		    	System.out.println("	                                       		                            		    |   |				                                                                                                                                      															                                                        ");
				System.out.println("                                                                                            ________|   |________                                                                                                                                                                                                                                                                  ");																		
		    	System.out.println("	                                       		                                   (    Death Plateau    )     					                                                                                                                      															                                                        ");
				System.out.println("                                                                                           (________|   |________)                                                                                                                                                                                                                                                                 ");																		
		    	System.out.println("	                                       		                            	            |   |		                                                                                                         															                                                        ");
				System.out.println("                                       _____________________________________________________________|___|_______________________________                                                                                                                                                                                                                       ");																		
		    	System.out.println("	                              (    Tadili            Home	      *Novice Pass*          Cross Roads           Thick Woods  )                                                                                                 															                                                        ");
				System.out.println("                                      (_____________________|	 |______________________________________________________________________)                                                                                                                                                                                                                     ");																		
		    	System.out.println("	                                            ________|    |________                                                                                                             															                                                        ");
				System.out.println("                                                   (	    Reality       )                                                                                                                                                                                                                                        ");																		
		    	System.out.println("	                                           (________|    |________)                                                                                                                     															                                                        ");
				System.out.println("                                                    ________|    |________                                                                                                                                                                                                                                         ");																		
		    	System.out.println("	                                           (	   Existence      )                                                                                                                     															                                                        ");
				System.out.println("                                                   (________|    |________)                                                                                                                                                                                                                                        ");																		
		    	System.out.println("	                                            ________|    |________                                                                                                                                              															                                                        ");
				System.out.println("                                                   (	    LowerU        )                                                                                                                                                                                                                                       ");																		
		    	System.out.println("	                                           (______________________)                                                                                        															                                                        ");
				
		  } else if (Locale.getId() == 6) {
			 	System.out.println("                                                                                             ___________________                                                                                                                                                                                                                                                                   ");																		
		    	System.out.println("	                                       		                                    (    Deep Crater    )                                                                                                                                      															                                                        ");
				System.out.println("                                                                                            (_______|   |_______)                                                                                                                                                                                                                                                                 ");																		
		    	System.out.println("	                                       		                            		    |   |				                                                                                                                                      															                                                        ");
				System.out.println("                                                                                            ________|   |________                                                                                                                                                                                                                                                                  ");																		
		    	System.out.println("	                                       		                                   (    Death Plateau    )     					                                                                                                                      															                                                        ");
				System.out.println("                                                                                           (________|   |________)                                                                                                                                                                                                                                                                 ");																		
		    	System.out.println("	                                       		                            	            |   |		                                                                                                         															                                                        ");
				System.out.println("                                       _____________________________________________________________|___|_______________________________                                                                                                                                                                                                                       ");																		
		    	System.out.println("	                              (    Tadili            Home	       Novice Pass          *Cross Roads*          Thick Woods  )                                                                                                 															                                                        ");
				System.out.println("                                      (_____________________|	 |______________________________________________________________________)                                                                                                                                                                                                                     ");																		
		    	System.out.println("	                                            ________|    |________                                                                                                             															                                                        ");
				System.out.println("                                                   (	    Reality       )                                                                                                                                                                                                                                        ");																		
		    	System.out.println("	                                           (________|    |________)                                                                                                                     															                                                        ");
				System.out.println("                                                    ________|    |________                                                                                                                                                                                                                                         ");																		
		    	System.out.println("	                                           (	   Existence      )                                                                                                                     															                                                        ");
				System.out.println("                                                   (________|    |________)                                                                                                                                                                                                                                        ");																		
		    	System.out.println("	                                            ________|    |________                                                                                                                                              															                                                        ");
				System.out.println("                                                   (	    LowerU        )                                                                                                                                                                                                                                       ");																		
		    	System.out.println("	                                           (______________________)                                                                                        															                                                        ");
				
		  } else if (Locale.getId() == 7) {
			 	System.out.println("                                                                                             ___________________                                                                                                                                                                                                                                                                   ");																		
		    	System.out.println("	                                       		                                    (    Deep Crater    )                                                                                                                                      															                                                        ");
				System.out.println("                                                                                            (_______|   |_______)                                                                                                                                                                                                                                                                 ");																		
		    	System.out.println("	                                       		                            		    |   |				                                                                                                                                      															                                                        ");
				System.out.println("                                                                                            ________|   |________                                                                                                                                                                                                                                                                  ");																		
		    	System.out.println("	                                       		                                   (   *Death Plateau*   )     					                                                                                                                      															                                                        ");
				System.out.println("                                                                                           (________|   |________)                                                                                                                                                                                                                                                                 ");																		
		    	System.out.println("	                                       		                            	            |   |		                                                                                                         															                                                        ");
				System.out.println("                                       _____________________________________________________________|___|_______________________________                                                                                                                                                                                                                       ");																		
		    	System.out.println("	                              (    Tadili            Home	       Novice Pass           Cross Roads           Thick Woods  )                                                                                                 															                                                        ");
				System.out.println("                                      (_____________________|	 |______________________________________________________________________)                                                                                                                                                                                                                     ");																		
		    	System.out.println("	                                            ________|    |________                                                                                                             															                                                        ");
				System.out.println("                                                   (	    Reality       )                                                                                                                                                                                                                                        ");																		
		    	System.out.println("	                                           (________|    |________)                                                                                                                     															                                                        ");
				System.out.println("                                                    ________|    |________                                                                                                                                                                                                                                         ");																		
		    	System.out.println("	                                           (	   Existence      )                                                                                                                     															                                                        ");
				System.out.println("                                                   (________|    |________)                                                                                                                                                                                                                                        ");																		
		    	System.out.println("	                                            ________|    |________                                                                                                                                              															                                                        ");
				System.out.println("                                                   (	    LowerU        )                                                                                                                                                                                                                                       ");																		
		    	System.out.println("	                                           (______________________)                                                                                        															                                                        ");
				
		  }  else if (Locale.getId() == 8) {
			 	System.out.println("                                                                                             ___________________                                                                                                                                                                                                                                                                   ");																		
		    	System.out.println("	                                       		                                    (    Deep Crater    )                                                                                                                                      															                                                        ");
				System.out.println("                                                                                            (_______|   |_______)                                                                                                                                                                                                                                                                 ");																		
		    	System.out.println("	                                       		                            		    |   |				                                                                                                                                      															                                                        ");
				System.out.println("                                                                                            ________|   |________                                                                                                                                                                                                                                                                  ");																		
		    	System.out.println("	                                       		                                   (    Death Plateau    )     					                                                                                                                      															                                                        ");
				System.out.println("                                                                                           (________|   |________)                                                                                                                                                                                                                                                                 ");																		
		    	System.out.println("	                                       		                            	            |   |		                                                                                                         															                                                        ");
				System.out.println("                                       _____________________________________________________________|___|_______________________________                                                                                                                                                                                                                       ");																		
		    	System.out.println("	                              (    Tadili            Home	       Novice Pass           Cross Roads          *Thick Woods* )                                                                                                 															                                                        ");
				System.out.println("                                      (_____________________|	 |______________________________________________________________________)                                                                                                                                                                                                                     ");																		
		    	System.out.println("	                                            ________|    |________                                                                                                             															                                                        ");
				System.out.println("                                                   (	    Reality       )                                                                                                                                                                                                                                        ");																		
		    	System.out.println("	                                           (________|    |________)                                                                                                                     															                                                        ");
				System.out.println("                                                    ________|    |________                                                                                                                                                                                                                                         ");																		
		    	System.out.println("	                                           (	   Existence      )                                                                                                                     															                                                        ");
				System.out.println("                                                   (________|    |________)                                                                                                                                                                                                                                        ");																		
		    	System.out.println("	                                            ________|    |________                                                                                                                                              															                                                        ");
				System.out.println("                                                   (	    LowerU        )                                                                                                                                                                                                                                       ");																		
		    	System.out.println("	                                           (______________________)                                                                                        															                                                        ");
				
		  }  else if (Locale.getId() == 9) {
			 	System.out.println("                                                                                             ___________________                                                                                                                                                                                                                                                                   ");																		
		    	System.out.println("	                                       		                                    (   *Deep Crater*   )                                                                                                                                      															                                                        ");
				System.out.println("                                                                                            (_______|   |_______)                                                                                                                                                                                                                                                                 ");																		
		    	System.out.println("	                                       		                            		    |   |				                                                                                                                                      															                                                        ");
				System.out.println("                                                                                            ________|   |________                                                                                                                                                                                                                                                                  ");																		
		    	System.out.println("	                                       		                                   (    Death Plateau    )     					                                                                                                                      															                                                        ");
				System.out.println("                                                                                           (________|   |________)                                                                                                                                                                                                                                                                 ");																		
		    	System.out.println("	                                       		                            	            |   |		                                                                                                         															                                                        ");
				System.out.println("                                       _____________________________________________________________|___|_______________________________                                                                                                                                                                                                                       ");																		
		    	System.out.println("	                              (    Tadili            Home	       Novice Pass           Cross Roads           Thick Woods  )                                                                                                 															                                                        ");
				System.out.println("                                      (_____________________|	 |______________________________________________________________________)                                                                                                                                                                                                                     ");																		
		    	System.out.println("	                                            ________|    |________                                                                                                             															                                                        ");
				System.out.println("                                                   (	    Reality       )                                                                                                                                                                                                                                        ");																		
		    	System.out.println("	                                           (________|    |________)                                                                                                                     															                                                        ");
				System.out.println("                                                    ________|    |________                                                                                                                                                                                                                                         ");																		
		    	System.out.println("	                                           (	   Existence      )                                                                                                                     															                                                        ");
				System.out.println("                                                   (________|    |________)                                                                                                                                                                                                                                        ");																		
		    	System.out.println("	                                            ________|    |________                                                                                                                                              															                                                        ");
				System.out.println("                                                   (	    LowerU        )                                                                                                                                                                                                                                       ");																		
		    	System.out.println("	                                           (______________________)                                                                                        															                                                        ");
				
		  } else if (Locale.getId() == 10) {
			  System.out.println("This map doesn't apply to planet earth, you have your own maps here.");
		  } else if (Locale.getId() == 11) {
			  System.out.println("You think the hero's pass would be marked on the map? Get outta here!");
		  }
		  else {
			  System.out.println("You never picked up the map, fool.");
		  }
    }
	
    private static void help() {
        System.out.println("The commands are as follows:");
        System.out.println("   i/inventory");
        System.out.println("   n/north");
        System.out.println("   s/south");
        System.out.println("   q/quit");
        System.out.println("   t/take");
        System.out.println("   m/map");
        System.out.println("   home");
        
       
    }

    private static void quit() {
    	String walkthrough = transactionDone.nextLine();
    	try {
    		if(walkthrough.equalsIgnoreCase("f")){
    			while(moves>=0){
    				System.out.println(traceDownUp.dequeue());
    				moves--;
    			}
    		}
    		else if(walkthrough.equalsIgnoreCase("r")){
    			while(moves>=0){
    				System.out.println(traceUpDown.pop());
    				moves--;
    			}
    		}
    	}
    		catch(Exception ex){
    			System.out.println(ex.getMessage());
    		}
    	System.out.println("You have quit the Enigma.");
        stillPlaying = false;
        
    }
    
     
        
    final String fileName = "magic.txt";


      
    //
    // Private
    
 
    private static boolean binarySearchArray(ListItem[] items,
            String target) {
ListItem retVal = null;
System.out.println("Binary Searching for " + target + ".");
ListItem currentItem = new ListItem();
boolean isFound = false;
int counter = 0;
int low  = 0;
int high = items.length-1; // because 0-based arrays
while ( (!isFound) && (low <= high)) {
int mid = Math.round((high + low) / 2);
currentItem = items[mid];
if (currentItem.getName().equalsIgnoreCase(target)) {
// We found it!
isFound = true;
retVal = currentItem;
} else {
// Keep looking.
counter++;
if (currentItem.getName().compareToIgnoreCase(target) > 0) {
// target is higher in the list than the currentItem (at mid)
high = mid - 1;
} else {
// target is lower in the list than the currentItem (at mid)
low = mid + 1;
}
}
}
if (isFound) {
    System.out.println("Yo I heard you like " + currentItem + ". We got that good good in stock. Cough up " + currentItem.getCost() + "and its yours." ); //TODO: setup pricing system
    if (goldnotes.getAmt() >= currentItem.getCost()) {
        System.out.println("You've got the dough for it. Do you want to buy it?");
        command = transactionDone.nextLine();
    }
        else if (goldnotes.getAmt() < currentItem.getCost()) {
            System.out.println("Unfortunately, you don't have enough gold notes. Go acquire more, fool.");
        }
        if (command.equalsIgnoreCase("Yes")) {
			boughtItems = currentItem; 
			newInv.add(currentItem.getName()); //this adds an element to the list.
			return true;
		
		} else if (command.equalsIgnoreCase("no")) {
			System.out.println("Alright, goodbye then!");
			return false;
		} 
} else {
	System.out.println("I didn't find" + currentItem + ", try opening shop and searching again.");
	return false;

     
   
}


return false;
}



private static void readMagicItemsFromFileToArray(String fileName,
         ListItem[] items) {
File myFile = new File(fileName);
try {
int itemCount = 0;
Scanner input = new Scanner(myFile);

while (input.hasNext() && itemCount < items.length) {
// Read a line from the file.
String itemName = input.nextLine();

// Construct a new list item and set its attributes.
ListItem fileItem = new ListItem();
fileItem.setName(itemName);
fileItem.setCost((int) (Math.random() * 100));
fileItem.setNext(null); // Still redundant. Still safe.

// Add the newly constructed item to the array.
items[itemCount] = fileItem;
itemCount = itemCount + 1;
}
// Close the file.
input.close();
} catch (FileNotFoundException ex) {
System.out.println("File not found. " + ex.toString());
}
}

private static void selectionSort(ListItem[] items) {
for (int pass = 0; pass < items.length-1; pass++) {
// System.out.println(pass + "-" + items[pass]);
int indexOfTarget = pass;
int indexOfSmallest = indexOfTarget;
for (int j = indexOfTarget+1; j < items.length; j++) {
if (items[j].getName().compareToIgnoreCase(items[indexOfSmallest].getName()) < 0) {
indexOfSmallest = j;
}
}
ListItem temp = items[indexOfTarget];
items[indexOfTarget] = items[indexOfSmallest];
items[indexOfSmallest] = temp;
}
}

}





      








          

    


