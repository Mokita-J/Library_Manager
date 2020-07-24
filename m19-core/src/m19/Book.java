package m19;

public class Book extends Work{

    private String _author;
    private int _ISBN;

    /**
     * Book's constructor.
     * @param id Book's ID.
     * @param title Book's title.
     * @param author Book's author.
     * @param price Book's price.
     * @param genre Book's genre.
     * @param ISBN Book's ISBN.
     * @param copies Number of copies.
     */
    public Book(int id, String title, String author, int price, Genre genre, int ISBN, int copies, String type){
        super(id, title, price, genre, copies, type);
        _author = author;
        _ISBN = ISBN;
    }

    /**
     * @return Book's author.
     */
    public String getAuthor(){
        return _author;
    }

    /**
     * @return Book's ISBN
     */
    public int getISBN(){
        return _ISBN;
    }

    @Override
    public String getName() {
        return _author;
    }

    @Override
    public int getNumber() {
        return _ISBN;
    }
    
}