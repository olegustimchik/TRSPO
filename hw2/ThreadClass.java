package hw2;
import hw2.*;

class ThreadClass extends Thread{ 
    ForFirstHalf first;
    ForSecondHalf second;
    
    ThreadClass(ForFirstHalf first, ForSecondHalf second){ 
        this.first = first ; 
        this.second = second; 
    }

    @Override
    public void run(){
        synchronized (this){
            System.out.println("first half thread");
            int k1 = (int)Lab_2.randomNumber(10000, 20000); 
            for(int i = 0; i < k1; i++){ 
                this.first.setNumber(this.first.getNumber() + Lab_2.randomNumber(2, 5));
                this.second.setSecondNumber(this.second.getSecondNumber() + Lab_2.randomNumber(2, 5));
            }
        } 

    }
}

class ThreadClass2 extends Thread{ 
    ForFirstHalf first;
    ForSecondHalf second;
    
    ThreadClass2(ForFirstHalf first, ForSecondHalf second){ 
        this.first = first ; 
        this.second = second; 
    }

    @Override
    public void run(){
        synchronized (this){ 
            System.out.println("second half thread");
            int k1 = (int)Lab_2.randomNumber(10000, 20000); 
            for(int i = 0; i < k1; i++){ 
                this.first.setNumber(this.first.getNumber() + Lab_2.randomNumber(2, 5));
                this.second.setSecondNumber(this.second.getSecondNumber() + Lab_2.randomNumber(2, 5));
            }
        }

    }

}