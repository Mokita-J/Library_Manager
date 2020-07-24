package m19;
import java.io.Serializable;

public class CumpridorBehaviour implements Behaviour, Serializable{
    /** Serial number for serialization. */
    private static final long serialVersionUID = 201901101348L;

    private int _returns = 0;
    private User _user;
    
    public CumpridorBehaviour(User user){
        _user = user;
    }

    @Override
    public int returnDay(Work work){
        if(work.getCopies() == 1)
            return 8;
        
        if(work.getCopies() <= 5)
            return 15;
        
        else
            return 30;
    }

    @Override
    public void returnOnDate(boolean bool){
        if(bool)
            _returns = 0;

        else
            _returns--;

        if(_returns == -3){
            _user.setBehaviour(new FaltosoBehaviour(_user));
            return;
        }
    }

    @Override
    public boolean borrowLimit(){
        return _user.worksBorrowed() == 5;
    }

    @Override
    public boolean premiumPermission(){
        return true;
    }

    @Override
	public String toString(){
		return "CUMPRIDOR";
	}
}