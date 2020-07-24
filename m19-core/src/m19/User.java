package m19;

import java.util.Observable;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;
import java.util.Observer;

/**
 * This class represents Library's users.
 */
public class User implements Comparable <User>, Serializable, Observer{

    /** Serial number for serialization. */
    private static final long serialVersionUID = 201901101348L;
    
    private int _id = 0;
    private String _name;
    private String _email;
    private State _state = State.ACTIVO;
    private Behaviour _behaviour = new NormalBehaviour(this);
    private int _debt = 0;
    private HashMap <Integer, List<Work>> _works = new HashMap<Integer, List<Work>>();
    private List<String> _notifications = new ArrayList<String>();

    @Override
    public int compareTo(User user) {
        return _name.compareTo(user._name);
    }

    /**
	 * User's constrcutor. By default the _state is set as ACTIVE, 
     * the _behaviour is set as NORMAL and dept is set to zero
	 * @param id User's ID.
     * @param name User's name.
	 * @param email User's email.
	 */
    public User(int id, String name, String email){
        _id = id;
        _name = name;
        _email = email;
    }

    /**
	 * @return User's name.
	 */
    public String getName(){
        return _name;
    }

    /**
     * @return User's state.
     */
    public m19.State getState(){
        return _state;
    }

    /**
     * Alters state of User.
     * @param state new state.
     */
    public void setState(m19.State state){
        _state = state;
    }

    /**
     * @return User's behaviour.
     */
    public Behaviour getBehaviour(){
        return _behaviour;
    }

    /**
     * Alters behaviour of User.
     * @param behaviour new behaviour.
     */
    public void setBehaviour(Behaviour behaviour){
        _behaviour = behaviour;
    }

    /**
     * @return User's debt.
     */
    public int getDebt(){
        return _debt;
    }


    /**
     * Changes user's debt.
     * @param n New debt.
     */
    public void setDebt(int n){
        _debt = n;
    }

    /**
     * Changes the user's state based on the works he has or hasn't returned yet.
     * @param day Current day.
     */
    public void updateState(int day){
        for(int i : _works.keySet()){
            if (_works.get(i).size()!=0 && i < day) {
                this._state = m19.State.SUSPENSO;
                return;
            }
        }
        this._state = m19.State.ACTIVO;
    }


    /**
     * Calculates a work's debt
     * @param work
     * @param currentDay
     * @return
     */
    public int getWorkDebt(Work work, int currentDay){
        for (int i = 0; i <= currentDay; i++){
            List<Work> works = _works.get(i);
            if(works == null)
                continue;
            if(works.contains(work)){
                return _debt + (currentDay - i)*5;
            }

        }
        return _debt;
    }

    /**
     * Adds work to the list of borrowed works.
     * @param work work to be added.
     */
    public void borrowWork(Work work, int dueDate){

        if(_works.containsKey(dueDate)){
            if(!_works.get(dueDate).contains(work)){
                List<Work> workslist = _works.get(dueDate);
                workslist.add(work);
                _works.put(dueDate, workslist);
            }            

        } else {
            List<Work> workslist = new ArrayList<Work>();
            workslist.add(work);
            _works.put(dueDate, workslist);
        }
    }

    /**
     * Returns a work to the library. If the current day is bigger than the return day it changes the behavihour
     * @param work
     * @param day Current day
     */
    public void returnWork(Work work, int day){
        for (int key : _works.keySet()){
            if(_works.get(key).contains(work)){
                _works.get(key).remove(work);
                if(key<day) _behaviour.returnOnDate(false);
                else _behaviour.returnOnDate(true);
            }
        }
    }

    /**
     * Checks if user has borrowed a work.
     * @param work
     * @return True if it borrowed the work and false otherwise.
     */
    public boolean requestedWork(Work work){
        for (List<Work> works : _works.values()){
            if (works.contains(work)){
                return true;
            }
        }
        return false;
    }

    /**
     * @return number of works borrowed.
     */
    public int worksBorrowed(){
        int number = 0;
        for (List<Work> works : _works.values()){
            number = number + works.size();
        }
        return number;
    }

    /**
     * Cleans user's notifications.
     */
    public void cleanNotifications(){
        _notifications.clear();
    }


    /**
     * The string representation is "id - name - email - behaviour - state"
     */
    @Override
    public String toString(){
        if(_state==m19.State.SUSPENSO){
            return (_id + " - " + _name + " - " + _email + " - " + _behaviour.toString() + " - " + _state + " - EUR " + _debt);
        }
        return (_id + " - " + _name + " - " + _email + " - " + _behaviour.toString() + " - " + _state);
    }


    /**
     * Updates user's notifications.
     */
    @Override
    public void update(Observable observable, Object obj){
        String mode = String.valueOf(obj);
        Work work = (Work) observable;
        _notifications.add(mode + work.toString());
    }

    /**
     * @return User's notifications.
     */
    public String showNotifications(){
        String notifications = "";
        for(int i = 0; i<_notifications.size(); i++){
            notifications+= _notifications.get(i);
            if(i<_notifications.size()-1)
                notifications+= "\n";
        }
        return notifications;
    }

}