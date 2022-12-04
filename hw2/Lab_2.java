package hw2 ;

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

class ForFirstHalf{ 
    private double number; 
    ForFirstHalf(double number){
        this.number = number ; 
        }

    ForFirstHalf(){ 
        this.number = 2.0 ; 
    }

    public double getNumber(){ 
        return this.number;
    }

    public void setNumber(double num){ 
        this.number = num; 
    }
} 

class ForSecondHalf{ 
    private double numberSecond; 
    
    ForSecondHalf(){ 
        this.numberSecond = 3.0 ; 
    }

    public double getSecondNumber(){ 
        return this.numberSecond; 
    }

    public void setSecondNumber( double num){
        this.numberSecond = num;
    }
    
}

public class Lab_2{ 
    public static void main(String[] args){ 
        // ForFirstHalf obj = new ForFirstHalf(2.3); 
        int randNum = (int)randomNumber(10, 20);

        int half = (int)randNum/2 ;

        Thread[] firstThreads = new ThreadClass[half];
        Thread[] secondThreads = new ThreadClass2[randNum - half];

        ForFirstHalf fhalf =  new ForFirstHalf();
        ForSecondHalf shalf =  new ForSecondHalf();
        // ForFirstHalf fhalf2 =  new ForFirstHalf();
        // ForSecondHalf shalf2 =  new ForSecondHalf();

        firstThreads = initThreads_1(firstThreads, fhalf, shalf); 
        secondThreads = initThreads_2(secondThreads, fhalf, shalf); 

        executeThreads(firstThreads);
        executeThreads(secondThreads);



    }

    public static Thread[] initThreads_1(Thread[] threads, ForFirstHalf first, ForSecondHalf second){ 
        for (int i = 0; i < threads.length; i++ ){ 
            threads[i] = new ThreadClass(first, second);  
        }
        return threads; 
    }

    public static Thread[] initThreads_2(Thread[] threads, ForFirstHalf first, ForSecondHalf second){ 
        for (int i = 0; i < threads.length; i++ ){ 
            threads[i] = new ThreadClass2(first, second);  
        }
        return threads; 
    }
    
    public static void executeThreads(Thread[] threads){ 
        for(int i = 0; i < threads.length; i++){ 
            threads[i].start();
        }
    }
    
    public static double randomNumber(int leastNumber, int upperBound){ 
        return Math.random()*(upperBound - leastNumber + 1 ) + leastNumber; 
    }
}