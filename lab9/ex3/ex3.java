public class ex3 {
	public static void main(String[] args){
		Shared kitchen=new Shared();
		Consumer c1=new Consumer("cook 1",kitchen); c1.start();
		Consumer c2=new Consumer("cook 2",kitchen); c2.start();
		Consumer c3=new Consumer("cook 3",kitchen); c3.start();
		Consumer c4=new Consumer("cook 4",kitchen); c4.start();
		Consumer c5=new Consumer("cook 5",kitchen); c5.start();
		Producer w1=new Producer("waiter 1",kitchen); w1.start();
		Producer w2=new Producer("waiter 2",kitchen); w2.start();
		Producer w3=new Producer("waiter 3",kitchen); w3.start();
		
		
	}

}
