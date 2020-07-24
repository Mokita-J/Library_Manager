package m19;

import java.util.ArrayList;
import java.io.Serializable;

public class RuleBook implements Serializable{
    /** Serial number for serialization. */
    private static final long serialVersionUID = 201901101348L;

    private ArrayList<Rule> ruleBook;

    public RuleBook(){
        ruleBook = new ArrayList<Rule>();

        Rule1 r1 = new Rule1();
        Rule2 r2 = new Rule2();
        Rule3 r3 = new Rule3();
        Rule4 r4 = new Rule4();
        Rule5 r5 = new Rule5();
        Rule6 r6 = new Rule6();

        ruleBook.add(r1);
        ruleBook.add(r2);
        ruleBook.add(r3);
        ruleBook.add(r4);
        ruleBook.add(r5);
        ruleBook.add(r6);
    }

    public int checkAll(User user, Work work){
        for(int i = 0; i < ruleBook.size(); i++){
            if (!ruleBook.get(i).check(user, work))
                return i+1;
        }
        return 0;
    }

    public class Rule1 implements Rule{
        @Override
        public boolean check(User user, Work work){
            if(user.requestedWork(work)){
                return false;
            }
            return true;
        }
    }

    public class Rule2 implements Rule{
        @Override
        public boolean check(User user, Work work){
            if (user.getState() == m19.State.SUSPENSO)
                return false;
            return true;
        }
    }

    public class Rule3 implements Rule{
        @Override
        public boolean check(User user, Work work){
            if(work.getCopiesAvailable() > 0)
                return true;
            return false;
        }
    }

    public class Rule4 implements Rule{
        @Override
        public boolean check(User user, Work work){
            return !user.getBehaviour().borrowLimit();
        }
    }

    public class Rule5 implements Rule{
        @Override
        public boolean check(User user, Work work){
            if (work.getReference()){
                return false;
            }
            return true;
        }
    }

    public class Rule6 implements Rule{
        @Override
        public boolean check(User user, Work work){
            if(work.getPrice() > 25 && !user.getBehaviour().premiumPermission()){
                return false;
            }
            return true;
        }
    }
}