package m19;
import java.io.Serializable;

public class NormalBehaviour implements Behaviour, Serializable{
    /** Serial number for serialization. */
    private static final long serialVersionUID = 201901101348L;
    
    private int _returns = 0;
    private User _user;
    
    public NormalBehaviour(User user){
        _user = user;
    }

    @Override
    public int returnDay(Work work){
        if(work.getCopies() == 1)
            return 3;
        
        if(work.getCopies() <= 5)
            return 8;
        
        else
            return 15;
    }

    @Override
    public void returnOnDate(boolean bool){
        if(bool && _returns >= 0)
            _returns++;
        
        else if(bool && _returns < 0)
            _returns = 1;
        
        else if(!bool && _returns >= 0)
            _returns = -1;
        
        else
            _returns--;

        if(_returns == 5){
            _user.setBehaviour(new CumpridorBehaviour(_user));
            return;
        }
        if(_returns == -3){
            _user.setBehaviour(new FaltosoBehaviour(_user));
            return;
        }
    }

    @Override
    public boolean borrowLimit(){
        return _user.worksBorrowed() == 3;
    }

    @Override
    public boolean premiumPermission(){
        return false;
    }

    @Override
	public String toString(){
		return "NORMAL";
	}
}