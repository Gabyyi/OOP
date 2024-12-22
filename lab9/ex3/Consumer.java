public class Consumer extends Thread {
    private final Shared kitchen;
    private final String name;

    public Consumer(String name, Shared kitchen){
        this.name=name;
        this.kitchen=kitchen;
    }

    @Override
    public void run(){
        while(true){
            Pizza pizza=kitchen.remove();
            System.out.println("Pizza nr "+pizza.getId()+" was removed by "+name);
            try{
                Thread.sleep(10);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
