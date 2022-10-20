package hw3 ; 
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.lang.Runnable; 

class ThreadClass extends Thread{ 
    @Override
    public void run(){
        System.out.println("thread is running");
    }
}

class NumberInQueue{ 
    private Integer initialNumber ; 
    private Integer step ;  
    private Integer subsequentNumber; 

    public NumberInQueue(Integer initNum){ 
        this.initialNumber = initNum; 
        this.step = 0; 
        this.subsequentNumber = initNum; 
    }

    public void increaseStep(int step){ 
        this.step += step ; 
    }

    public Integer getStep(){ 
        return this.step ; 
    }

    public Integer getInitialNumber(){ 
        return this.initialNumber; 
    }

    public void setSubsequentNumber(int numb){
        this.subsequentNumber = numb;
    }

    public Integer getSubsequentNumber(){ 
        return this.subsequentNumber;
    }

}

interface counter{ 
    public char character(int n); 
}

class ThreadFactory_ implements ThreadFactory{ 
    @Override
    public Thread newThread(Runnable r) {
	    Thread t = new Thread(r);
	    t.setPriority(Thread.MAX_PRIORITY);
	    return t;
     }
}

public class Collatz{ 
    public static void main(String [] args) { 
        Queue<NumberInQueue> numbers = new LinkedList<NumberInQueue>(); 
        long start = System.currentTimeMillis(); 
        for(int i = 0; i < 100000; i++){ 
            numbers.add(new NumberInQueue((int)randomNumber(100, 10000))); 
        }

        ThreadFactory threadFactory = new ThreadFactory_();

        ExecutorService execService = Executors.newCachedThreadPool(threadFactory);
        int i = 0; 
        while( i < 4 ){ 
            Runnable proccedNum = () -> {   NumberInQueue obj ; 
                                            while(!numbers.isEmpty()){
                                            synchronized(numbers){ 
                                                if(!numbers.isEmpty()){ 
                                                    obj = numbers.remove(); 
                                                }
                                                else{ 
                                                    return ; 
                                                }

                                                if(obj.getSubsequentNumber() == 1){ 
                                                    // System.out.println("Number " + obj.getInitialNumber() + " steps " + obj.getStep() );
                                                    continue;
                                                }
                                                else if( obj.getSubsequentNumber() %  2 == 0) { 
                                                    obj.setSubsequentNumber(obj.getSubsequentNumber() / 2);
                                                    obj.increaseStep(1);
                                                    numbers.add(obj); 
                                                }
                                                else { 
                                                    obj.setSubsequentNumber( 3 * obj.getSubsequentNumber() + 1);
                                                    obj.increaseStep(1);
                                                    numbers.add(obj);
                                                }
                                            } 
                                        } 
                                    }; 
            i++ ;
            execService.submit(proccedNum); 
        }
        execService.shutdown();
        long end = System.currentTimeMillis(); 
        long times = end - start;
        System.out.println("All numbers have procced time " + times);


    }

    public static double randomNumber(int leastNumber, int upperBound){ 
        return Math.random()*(upperBound - leastNumber + 1 ) + leastNumber; 
    }
}