package m19;
import java.io.Serializable;

public class FaltosoBehaviour implements Behaviour, Serializable{
    /** Serial number for serialization. */
    private static final long serialVersionUID = 201901101348L;
    
    private int _returns = 0;
    private User _user;
    
    public FaltosoBehaviour(User user){
        _user = user;
    }

    @Override
    public int returnDay(Work work){
        return 2;
    }

    public void returnOnDate(boolean bool){
        if(bool)
            _returns++;
        else
            _returns = 0;

        if(_returns == 3){
            _user.setBehaviour(new NormalBehaviour(_user));
            return;
        }
    }

    @Override
    public boolean borrowLimit(){
        return _user.worksBorrowed() == 1;
    }

    @Override
    public boolean premiumPermission(){
        return false;
    }

    @Override
	public String toString(){
		return "FALTOSO";
	}
}