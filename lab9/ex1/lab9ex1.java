import java.time.LocalTime;

// public class lab9ex1 extends Thread{
//     @Override
//     public void run(){
//         while (true){
//             System.out.println(LocalTime.now());
//             try{
//                 Thread.sleep(10000);
//             }catch (InterruptedException e){
//                 e.printStackTrace();
//             }
//         }
//     }

//     public static void main(String[] args){
//         lab9ex1 thread1=new lab9ex1();
//         lab9ex1 thread2=new lab9ex1();
//         lab9ex1 thread3=new lab9ex1();

//         thread1.start();
//         thread2.start();
//         thread3.start();
//     }
// }


public class lab9ex1 implements Runnable{
    @Override
    public void run(){
        while(true){
            System.out.println(LocalTime.now());
            try{
                Thread.sleep(10000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        Thread thread1=new Thread(new lab9ex1());
        Thread thread2=new Thread(new lab9ex1());
        Thread thread3=new Thread(new lab9ex1());

        thread1.start();
        thread2.start();
        thread3.start();
    }
}