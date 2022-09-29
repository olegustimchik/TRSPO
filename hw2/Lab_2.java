package hw2 ;

import java.util.concurrent.Semaphore;
import java.util.concurrent.CountDownLatch;

class ThreadClass extends Thread{ 
    ForFirstHalf first;
    ForSecondHalf second;
    Semaphore mutex; 
    CountDownLatch startAll; 
    
    ThreadClass(ForFirstHalf first, ForSecondHalf second, CountDownLatch startAll){ 
        this.first = first ; 
        this.second = second; 
        this.mutex = new Semaphore(1); 
        this.startAll = startAll; 
    }

    @Override
    public void run(){
        try{
            this.mutex.acquire();
            System.out.println("first half thread");
            int k1 = (int)Lab_2.randomNumber(10000, 20000); 
            for(int i = 0; i < k1; i++){ 
                this.first.setNumber(this.first.getNumber() + Lab_2.randomNumber(2, 5));
                this.second.setSecondNumber(this.second.getSecondNumber() + Lab_2.randomNumber(2, 5));
            }
            this.startAll.countDown();
            this.mutex.release();

        }catch(InterruptedException e ){ 
            System.out.println(   " error ");
        }  

    }
}

class ThreadClass2 extends Thread{ 
    ForFirstHalf first;
    ForSecondHalf second;
    Semaphore mutex;
    CountDownLatch startAll; 
    
    ThreadClass2(ForFirstHalf first, ForSecondHalf second, CountDownLatch startAll){ 
        this.first = first ; 
        this.second = second; 
        this.mutex = new Semaphore(1);
        this.startAll = startAll; 
    }

    @Override
    public void run(){
        try{
            this.mutex.acquire();
            System.out.println("second half thread");
            int k1 = (int)Lab_2.randomNumber(10000, 20000); 
            for(int i = 0; i < k1; i++){ 
                this.first.setNumber(this.first.getNumber() + Lab_2.randomNumber(2, 5));
                this.second.setSecondNumber(this.second.getSecondNumber() + Lab_2.randomNumber(2, 5));
            }
            this.startAll.countDown();
            this.mutex.release();

        }catch(InterruptedException e ){ 
            System.out.println(" error ");
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
    public static void main(String[] args) throws InterruptedException{ 
        // ForFirstHalf obj = new ForFirstHalf(2.3); 
        int randNum = (int)randomNumber(10, 20);
        CountDownLatch startAll  = new CountDownLatch(randNum);

        int half = (int)randNum/2 ;
        long start = System.nanoTime();

        Thread[] firstThreads = new ThreadClass[half];
        Thread[] secondThreads = new ThreadClass2[randNum - half];

        ForFirstHalf fhalf =  new ForFirstHalf();
        ForSecondHalf shalf =  new ForSecondHalf();
        // ForFirstHalf fhalf2 =  new ForFirstHalf();
        // ForSecondHalf shalf2 =  new ForSecondHalf();

        firstThreads = initThreads_1(firstThreads, fhalf, shalf, startAll); 
        secondThreads = initThreads_2(secondThreads, fhalf, shalf, startAll); 
 

        executeThreads(firstThreads);
        executeThreads(secondThreads);

        startAll.await();

        long end = System.nanoTime();
        System.out.println(fhalf.getNumber());
        System.out.println(shalf.getSecondNumber());
        
        System.out.println("\ntime: " + (end - start));

    }

    public static Thread[] initThreads_1(Thread[] threads, ForFirstHalf first, ForSecondHalf second, CountDownLatch startAll){ 
        for (int i = 0; i < threads.length; i++ ){ 
            threads[i] = new ThreadClass(first, second, startAll);  
        }
        return threads; 
    }

    public static Thread[] initThreads_2(Thread[] threads, ForFirstHalf first, ForSecondHalf second, CountDownLatch startAll){ 
        for (int i = 0; i < threads.length; i++ ){ 
            threads[i] = new ThreadClass2(first, second, startAll);  
        }
        return threads; 
    }
    
    public static void executeThreads(Thread[] threads){ 
        for(int i = 0; i < threads.length; i++){ 
            threads[i].start();
        }
    }
   
    // public static void joinThreads(Thread[] threads){ 
    //     for(int i = 0; i < threads.length; i++){ 
    //         try{
    //             threads[i].join();
    //         } catch(InterruptedException e){ 
    //             System.out.println("Error");
    //         }
    //     }
    // }
    public static double randomNumber(int leastNumber, int upperBound){ 
        return Math.random()*(upperBound - leastNumber + 1 ) + leastNumber; 
    }
}