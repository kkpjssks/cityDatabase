import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
    public static Scanner sk = new Scanner(System.in);				// public scanner accesible to all methods
    public static ArrayList<City> cities = new ArrayList<>();		// public array list of type City to hold our objs
    public static String path = null;								// directory path of our database txt
    public static File fl = null;									// our .txt file

    public static void main(String[] args) throws FileNotFoundException // main method / driver
    {
        FindFile();
        txtToObj();
        while(true)	{	mainMenu(); }								// infinite loop calling main menu
        //objToTxt();
    }

    // - database File Methods - //
    public static void FindFile() throws FileNotFoundException			// Find File Method
    {
        path = new File(".project").getAbsolutePath();					// find .project file in project
        path = path.replace(".project", "database.txt");				// replace the path portion of .project with database.txt
        fl = new File(path);											// set File = path

        if(!fl.exists()) {System.out.println("File Not found");}		// if could not locate file, say, file not found
    }

    public static void txtToObj() throws FileNotFoundException			// txt to object method
    {
        Scanner skf = new Scanner(fl);									// new scanner that takes fl as param
        while(skf.hasNextLine())										// while loop that keeps iterating as long as there is a next line
        {
            String[] obj = skf.nextLine().split("\\s+");				// a String array called obj, split each line anywhere where there is 1 or more spaces

            City c = null;												// instantiate a new city object set to null
            String cityName = "";										// String to store name
            float lat = 0, lng = 0;										// float to store lat and lng
            int pop = 0;												// int to store pop

            try															// try to parse String in array to data for object
            {
                cityName = obj[0];										// name = obj element in pos 0
                lat = Float.parseFloat(obj[1]);							// lat = obj element in pos 1
                lng = Float.parseFloat(obj[2]);							// lng = obj element in pos 2
                pop = Integer.parseInt(obj[3]);							// pop = obj element in pos 3
            }

            catch(Exception e)											// our catch block
            {
                System.out.println("Error during conversion: could not parse String objects");		// prints error to user
            }

            finally														// finally block to create city and store it in array list
            {
                c = new City(cityName, lat, lng, pop);					// create city using city constructor and data gathered from txt file
                cities.add(c);											// add created city to our array list of cities
            }
        }
    }

    public static void objToTxt() throws FileNotFoundException		// obj to txt method
    {
        String data = "";											// instantiate new string object to store our data in (to be written to file)

        for(int i = 0; i < cities.size(); i++)						// loop through all cities in our array list and gather data to store in data String
        {
            data += cities.get(i).getName() + " "
                    + cities.get(i).getLat() + " "
                    + cities.get(i).getLng() + " "
                    + cities.get(i).getPop() + " "
                    + System.lineSeparator();
        }

        try															// overwrite our database.txt file with new data gathered from loop
        {
            FileWriter writer = new FileWriter(path);				// create a new file writter obj
            writer.write(data);										// write our data to that file
            writer.close();											// close our writer
        }

        catch(Exception e) { e.printStackTrace(); }					// error handling catch block
    }

    // - helper methods - //
    public static void p(String _str) {	System.out.print(_str);	}	// simple print method

    public static void saveChanges()		// save chnages method
    {
        p("   1) save changes " + System.lineSeparator());			// ensure user wants to save chnages
        p("   2) discard changes " + System.lineSeparator());

        if(sk.nextInt() == 1)		// if user wants to save, then call obj to txt to save data
        {
            try {objToTxt(); } catch(FileNotFoundException e) {e.printStackTrace();}
        }
    }

    public static double deg2rad(double deg)
    {
        return deg*(Math.PI/180);
    }

    public static double getDistance(double _lat1, double _lat2, double _lng1, double _lng2)
    {
        double r = 6371;
        double latD = deg2rad(_lat1 - _lat2);
        double lngD = deg2rad(_lng1 - _lng2);

        double a = Math.sin(latD/2)*Math.sin(latD/2) + Math.cos(_lat1) *Math.cos(_lat2) * Math.sin(lngD/2) * Math.sin(lngD/2);
        double c = 2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = r*c;

        d = (Math.round(d*100));
        d = d/100;

        return d;
    }

    // - methods - //
    public static void mainMenu()									// main menu
    {
        p("Main Menu: " + System.lineSeparator()					// print statement
                + "   1) display all cities " + System.lineSeparator()
                + "   2) search for a city " + System.lineSeparator()
                + "   3) Insert a new city " + System.lineSeparator()
                + "   4) delete a city " + System.lineSeparator()
                + "   5) update a city " + System.lineSeparator()
                + "   6) find distance between 2 cities " + System.lineSeparator()
                + "   7) find nearby cities " + System.lineSeparator()
                + "   8) exit city database " + System.lineSeparator());


        boolean valid = false;										// boolean condition used in while

        while(!valid)												// invalid input loop
        {
            int input = sk.nextInt();								// accept user input

            valid = true;											// set valid to true

            switch(input)											// switch statement
            {
                case 1:												// to do: display all cities
                    displayAllCities();
                    break;

                case 2:												// to do: search for a city
                    searchForCity();
                    break;

                case 3:												// to do: insert a new city
                    insertNewCity();
                    break;

                case 4:												// to do: delete a city
                    deleteCity();
                    break;

                case 5:												// update a city
                    updateCity();
                    break;

                case 6:												// find distance between 2 cities
                    distance1();
                    break;

                case 7:												// find nearby cities
                    distance2();
                    break;

                case 8:												// exit database
                    System.exit(0);									// terminate program with exit code 0 = succesful termination
                    break;

                default:											// prints invalid input to console and throws us back in loop
                    p("Invalid Input" + System.lineSeparator());	// by setting valid = false
                    valid = false;
                    break;
            }
        }
    }

    public static void displayAllCities()			// loop through cities array list and print all values of city from city toString method
    {
        p("______________________________________________________________________"+System.lineSeparator());
        p("City:			| Latitude	| Longitude	| population  |"+System.lineSeparator());
        for(int i = 0; i < cities.size(); i++)
        {
            p(cities.get(i)+"");
        }
        p("______________________________________________________________________|"+System.lineSeparator()+System.lineSeparator());
    }

    public static void searchForCity()				// Search city method
    {
        p("Enter City Name: ");						// output to user

        String cn = sk.next().toLowerCase();		// City and city are not equal to each other, therefore, we lowercase them to match

        boolean exists = false;						// boolean to ensure city was found

        for(int i = 0; i < cities.size(); i++)		// loop through cities in array list to find city given by user
        {
            if(cities.get(i).getName().toLowerCase().contentEquals(cn))		// if content equals since we want to compare contents and not memory locations
            {
                exists = true;				// set exists = true since we found city

                System.out.println("City: " + cities.get(i).getName() + System.lineSeparator()
                        + "Latitude: " + cities.get(i).getLat() + System.lineSeparator()
                        + "Longitude: " + cities.get(i).getLng() + System.lineSeparator()
                        + "Population: " + cities.get(i).getPop()+System.lineSeparator());	// simple print
                break;	// break out of loop so that we do not keep iteration after we find city
            }
        }

        if(exists == false)		// if exists is still false at this point, we have not found city, so let user koww
        {
            p("City Does not Exist " + System.lineSeparator()+ System.lineSeparator());
        }
    }

    public static void insertNewCity()
    {
        p("Enter City Name: ");						// output to user

        String cn = sk.next().toLowerCase();		// City and city are not equal to each other, therefore, we lowercase them to match

        boolean exists = false;						// boolean to ensure city was found

        for(int i = 0; i < cities.size(); i++)		// loop through cities in array list to find city given by user
        {
            if(cities.get(i).getName().toLowerCase().contentEquals(cn))		// if content equals since we want to compare contents and not memory locations
            {
                exists = true;				// set exists = true since we found city
                p("City Already exists!");
                break;	// break out of loop so that we do not keep iteration after we find city
            }
        }

        if(exists == false)		// if exists is still false at this point, we have not found city, so let user koww
        {
            p("Enter Latitude: " + System.lineSeparator());		// ask user for lat, lng and pop
            float lat = sk.nextFloat();

            p("Enter Longitude: " + System.lineSeparator());
            float lng = sk.nextFloat();

            p("Enter population: " + System.lineSeparator());
            int pop = sk.nextInt();

            City c = new City(cn, lat, lng, pop);				// create new city using user input
            cities.add(c);										// add city to array list

            p(System.lineSeparator());

            saveChanges();										// save changes
        }
    }

    public static void deleteCity()					// delete city method
    {
        p("Enter City Name: ");						// output to user

        String cn = sk.next().toLowerCase();		// City and city are not equal to each other, therefore, we lowercase them to match

        boolean exists = false;						// boolean to ensure city was found

        for(int i = 0; i < cities.size(); i++)		// loop through cities in array list to find city given by user
        {
            if(cities.get(i).getName().toLowerCase().contentEquals(cn))		// if content equals since we want to compare contents and not memory locations
            {
                exists = true;				// set exists = true since we found city

                p("Nuke " + cities.get(i).getName() + "? "+ System.lineSeparator());
                p("   1) nuke");														// simple print
                p("   2) spare");

                if(sk.nextInt() == 1)
                {
                    cities.remove(i);			// remove city from arrayList
                    saveChanges();			// cal, save changed method
                }

                break;	// break out of loop so that we do not keep iteration after we find city
            }
        }

        if(exists == false)		// if exists is still false at this point, we have not found city, so let user koww
        {
            p("City does not exist" + System.lineSeparator()+System.lineSeparator());
        }
    }

    public static void updateCity()					// update city method
    {
        p("Enter City Name: ");						// output to user

        String cn = sk.next().toLowerCase();		// City and city are not equal to each other, therefore, we lowercase them to match

        boolean exists = false;						// boolean to ensure city was found

        for(int i = 0; i < cities.size(); i++)		// loop through cities in array list to find city given by user
        {
            if(cities.get(i).getName().toLowerCase().contentEquals(cn))		// if content equals since we want to compare contents and not memory locations
            {
                exists = true;				// set exists = true since we found city

                p("Update Name?        1) Y  2) N" + System.lineSeparator());	// ask user if he wants to change name
                if(sk.nextInt()==1)
                {
                    p("Name: ");
                    cities.get(i).setName(sk.next());							// update name
                    p(System.lineSeparator());
                }

                p("Update Population?        1) Y  2) N" + System.lineSeparator());
                if(sk.nextInt()==1)
                {
                    p("Population: ");
                    cities.get(i).setPop(sk.nextInt());
                    p(System.lineSeparator());
                }

                saveChanges();	// call save changes

                break;	// break out of loop so that we do not keep iteration after we find city
            }
        }

        if(exists == false)		// if exists is still false at this point, we have not found city, so let user koww
        {
            p("City does not exist" + System.lineSeparator()+System.lineSeparator());
        }
    }

    public static void distance1()					// get distance between 2 cities method
    {
        p("City 1: ");								// ask for city 1
        String cn1 = sk.next().toLowerCase();		// store input in cn1

        p("City 2: ");								// ask for city 2
        String cn2 = sk.next().toLowerCase();		// store input in cn2

        boolean c1 = false, c2 = false;				// c1 and c2 booleans

        double[] ls = new double[4];				// array of doubles that store lat1, lat2, lng1, lng2 (in this order)

        for(int i = 0; i<cities.size(); i++)		// loop through array list
        {
            if(cities.get(i).getName().toLowerCase().contentEquals(cn1))	// check if city 1 exists
            {
                c1 = true;													// set c1 = true
                ls[0] = cities.get(i).getLat();								// store latitude in ls pos 0
                ls[2] = cities.get(i).getLng();								// store longitude in ls pos 2
            }

            if(cities.get(i).getName().toLowerCase().contentEquals(cn2))	// check if city 1 exists
            {
                c2 = true;													// set c1 = true
                ls[1] = cities.get(i).getLat();								// store latitude in ls pos 1
                ls[3] = cities.get(i).getLng();								// store longitude in ls pos 2
            }
        }

        if(c1&&c2)	// if found both c1 and c2, print distance between them
        {
            p("Distance: " + getDistance(ls[0], ls[1], ls[2], ls[3]) + "KM" + System.lineSeparator() + System.lineSeparator());
        }

        else	// otherwise, print city not found
        {
            p("City does not exist " + System.lineSeparator() + System.lineSeparator());
        }
    }

    public static void distance2()					// find nearby cities method
    {
        p("City: ");								// prints city
        String cn = sk.next();						// store city name input

        p("Distance: ");							// prints distance
        float d = sk.nextFloat();					// stores distance input

        float l1 = 0, l2 = 0;						// floats lat and long set to 0

        boolean exists = false;						// exists boolean

        for(int i = 0; i < cities.size(); i++)		// loop through cities in array list to find city given by user
        {
            if(cities.get(i).getName().toLowerCase().contentEquals(cn))		// if content equals since we want to compare contents and not memory locations
            {
                exists = true;							// set exists = true since we found city
                l1 = cities.get(i).getLat();			// set l1 to city lat
                l2 = cities.get(i).getLng();			// set l2 to city lng
                break;									// break out of loop so that we do not keep iteration after we find city
            }
        }

        if(exists)	// if we find the city
        {
            for(int i = 0; i < cities.size(); i++)		// loop through array of cities
            {
                double d2 = getDistance(l1, cities.get(i).getLat(), l2, cities.get(i).getLng());	// get distance between city of choice and all other cities in array

                if(d2<=d && d2>0)	// if that distance is less than distance from user input, and not equal to zero, since we dont want distance to our own city
                {
                    p(cities.get(i).getName() + ": " + d2 + "KM " + System.lineSeparator());	// print that city and its distance
                }
            }

            p(System.lineSeparator());	// skip line
        }
        else // if exists is still false at this point, we have not found city, so let user koww
        {
            p("City does not exist" + System.lineSeparator()+System.lineSeparator());
        }
    }
}