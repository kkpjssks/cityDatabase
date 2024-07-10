public class City
{
    // - data - //
    private String name;
    private float lat;
    private float lng;
    private int pop;

    // - getters - //
    public String getName() { return name; }		// public functions that allow us to access the data in city objects
    public float getLat() { return lat; }
    public float getLng() { return lng; }
    public int getPop() { return pop; }

    // - setters - //
    public void setName(String _name) { name = _name; }		// public functions that allow us to mutate the data in city objects
    public void setLat(float _lat) { lat = _lat; }
    public void setLng(float _lng) { lng = _lng; }
    public void setPop(int _pop) { pop = _pop; }

    // - constructor - //
    public City (String _name, float _lat, float _lng, int _pop)	// // public functions that allow us to instantiate city objects
    { 	name = _name; lat = _lat; lng = _lng; pop = _pop;	}

    // - toString - //
    public String toString()		// public function that overrides the class toString and returns a String obj to be printed to console
    {
        String str = "";

        str += name;

        if(name.length() < 8) {str += "			| "; }
        else if (name.length() < 16 ) {str += "		| ";}
        else {str += "	| "; }

        str += lat + "	| " + lng + "	| " + pop + "	      | ";
        str += System.lineSeparator();

        return str;
        //return (name + "     " + lat + "     " + lng + "     " + pop + "     " + System.lineSeparator());
    }
}






