package m19;

import java.util.Comparator;
import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;
import java.util.List;
import java.util.ArrayList;


/**
 * This class represents Library's works.
 * They can be either Books or DVDS.
 */
public abstract class Work extends Observable implements Serializable{

    /** Serial number for serialization. */
    private static final long serialVersionUID = 201901101348L;
    
    private int _id;
    private String _title;
    private int _price;
    private int _copies;
    private int _copiesAvailable;
    private boolean _isReference;
    private Genre _genre;
    private String _type;

    private List<Observer> _observersReturn = new ArrayList<>();
    private List<Observer> _observersRequest = new ArrayList<>();

    /**
     * A comparator for works: based on ID.
     * Use as: Work.ID_CMP
     */
    public final static Comparator<Work> ID_CMP = new IDComparator();

    /**
     * This is a private class implementing the comparator.
     */
    private static class IDComparator implements Comparator<Work> {

        /**
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        @Override
        public int compare(Work work1, Work work2) {
            return work1.getID() - work2.getID();
        }
        
    }

    /**
     * Work's constructor.
     * @param id Work's ID.
     * @param title Work's title.
     * @param price Work's price.
     * @param copies Number of copies of work.
     * @param genre Work's genre.
     */
    public Work(int id, String title, int price, Genre genre, int copies, String type){
        _id = id;
        _title = title;
        _price = price;
        _copies = _copiesAvailable = copies;
        _genre = genre;
        if (_genre == Genre.REFERENCE){
            _isReference = true;
        }
        else {
            _isReference = false;
        }
        _type = type;
    }

    /**
	 * @return Work's ID.
	 */
    public int getID(){
        return _id;
    }

    /**
     * @return Work's title.
     */
    public String getTitle(){
        return _title;
    }
    
    /**
     * @return Work's price.
     */
    public int getPrice(){
        return _price;
    }

    /**
     * @return Number of copies.
     */
    public int getCopies(){
        return _copies;
    }

    /**
     * @return Number of copies available.
     */
    public int getCopiesAvailable(){
        return _copiesAvailable;
    }

    public void setCopiesAvalailable(int i){
        _copiesAvailable = _copiesAvailable + i;
    }


    /**
     * @return True if book is a reference, false otherwise.
     */
    public boolean getReference(){
        return _isReference;
    }

    /**
     * @return Book's category.
     */
    public Genre getGenre(){
        return _genre;
    } 

    /**
     * Notifies the users interested in the return that are "observing" this work.
     */
    public void notifyUsersReturn(){

        for(Observer o : _observersReturn){
            User user = (User) o;
            String str = "ENTREGA: ";
            user.update(this, str);
        }
        _observersReturn.clear();
    }

    /**
     * Notifies the users interested in the request that are "observing" this work.
     */
    public void notifyUsersRequest(){

        for(Observer o : _observersRequest){
            User user = (User) o;
            String str = "REQUISIÇÃO: ";
            user.update(this, str);
        }
    }

    public abstract String getName();

    public abstract int getNumber();

     /**
     * The string's representation is
     * "id - copies available of total number of copies  - DVD - title - price - category - IGAC"
     */
    @Override
    public String toString(){
        return (getID() + " - " + getCopiesAvailable() + " de  " + getCopies() + " - " + _type + "- " + getTitle()
                + " - " + getPrice() + " - "  + getGenre().toString() + " - " + getName() + " - " + getNumber());
    }

    /**
     * Adds a user(observer) that wants notificationson the return of this book.
     * @param observer 
     */
    public void addObserverReturn(Observer observer){
        _observersReturn.add(observer);
    }

    /**
     * Adds a user(observer) that wants notificationson the return of this book.
     * @param observer
     */
    public void addObserverRequest(Observer observer){
        _observersRequest.add(observer);
    }

}