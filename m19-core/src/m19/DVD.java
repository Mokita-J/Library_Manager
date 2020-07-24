package m19;

public class DVD extends Work{

    /** Serial number for serialization. */
    private static final long serialVersionUID = 201901101348L;

    private String _producer;
    private int _IGAC;

    /**
     * DVD's contructor.
     * @param id DVD's ID.
     * @param title DVD's title.
     * @param producer DVD's producer.
     * @param price DVD's price.
     * @param genre DVD's genre.
     * @param IGAC DVD's IGAC.
     * @param copies Number of copies.
     */
    public DVD(int id, String title, String producer, int price, Genre genre, int IGAC, int copies, String type){
        super(id, title, price, genre, copies, type);
        _producer = producer;
        _IGAC = IGAC;
    }

    /**
     * @return DVD's producer.
     */
    public String getProducer(){
        return _producer;
    }

    /**
     * @return DVD's IGAC
     */
    public int getIGAC(){
        return _IGAC;
    }

    @Override
    public String getName() {
        return _producer;
    }

    @Override
    public int getNumber() {
        return _IGAC;
    }

    
}