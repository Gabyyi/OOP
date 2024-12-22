interface FormOperations{
    void move(int x, int y);
    void shrink();
    void enlarge();
    void changeInnerColor(String color);
    void changeFormColor(String color);
}

class GeometricalForm implements FormOperations{
    protected int xCoord;
    protected int yCoord;
    protected String iColor;
    protected String fColor;
    public GeometricalForm(int x, int y){
        this.xCoord=x;
        this.yCoord=y;
        this.iColor="white";
        this.fColor="white";
    }
    public int getXCoordinate(){
        return xCoord;
    }
    public int getYCoordinate(){
        return yCoord;
    }
    public String getInnerColor(){
        return iColor;
    }
    public String getFormColor(){
        return fColor;
    }
    public void move(int x, int y){
        this.xCoord=x;
        this.yCoord=y;
        System.out.println("GeometricalFrom moved to: "+xCoord+", "+yCoord);
    }
    public void shrink(){
        xCoord-=1;
        yCoord-=1;
        System.out.println("GeometricalFrom shrunk to: "+xCoord+", "+yCoord);
    }
    public void enlarge(){
        xCoord+=1;
        yCoord+=1;
        System.out.println("GeometricalFrom enlarged to: "+xCoord+", "+yCoord);
    }
    public void changeInnerColor(String color){
        this.iColor=color;
        System.out.println("GeometricalFrom inner color changed to: "+color);
    }
    public void changeFormColor(String color){
        this.fColor=color;
        System.out.println("GeometricalFrom form color changed to: "+color);
    }
}

class Rectangle extends GeometricalForm{
    private int width;
    private int height;
    public Rectangle(int x, int y, int width, int height){
        super(x, y);
        this.width=width;
        this.height=height;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public void move(int x, int y){
        this.xCoord=x;
        this.yCoord=y;
        System.out.println("Rectangle moved to: "+xCoord+", "+yCoord);
    }
    public void shrink(){
        this.width-=1;
        this.height-=1;
        System.out.println("Rectangle shrunk to: "+width+", "+height);
    }
    public void enlarge(){
        this.width+=1;
        this.height+=1;
        System.out.println("Rectangle enlarged to: "+width+", "+height);
    }
    public void changeInnerColor(String color){
        this.iColor=color;
        System.out.println("Rectangle inner color changed to: "+color);
    }
    public void changeFormColor(String color){
        this.fColor=color;
        System.out.println("Rectangle form color changed to: "+color);
    }
}

class Elipse extends GeometricalForm{
    private int hRadius;
    private int vRadius;
    public Elipse(int x, int y, int hr, int vr){
        super(x, y);
        this.hRadius=hr;
        this.vRadius=vr;
    }
    public int getHorizRadius(){
        return hRadius;
    }
    public int getVertRadius(){
        return vRadius;
    }
    public void move(int x, int y){
        this.xCoord=x;
        this.yCoord=y;
        System.out.println("Elipse moved to: "+xCoord+", "+yCoord);
    }
    public void shrink(){
        this.hRadius-=1;
        this.vRadius-=1;
        System.out.println("Elipse shrunk to: "+hRadius+", "+vRadius);
    }
    public void enlarge(){
        this.hRadius+=1;
        this.vRadius+=1;
        System.out.println("Elipse enlarged to: "+hRadius+", "+vRadius);
    }
    public void changeInnerColor(String color){
        this.iColor=color;
        System.out.println("Elipse inner color changed to: "+color);
    }
    public void changeFormColor(String color){
        this.fColor=color;
        System.out.println("Elipse form color changed to: "+color);
    }
}

class hw2problem1{
    public static void main(String[] args){
        GeometricalForm[] forms=new GeometricalForm[3];
        forms[0]=new GeometricalForm(0, 0);
        forms[1]=new Rectangle(0, 0, 15, 35);
        forms[2]=new Elipse(0, 0, 5, 10);

        for(int i=0;i<3;i++){
            System.out.println("Form "+(i+1)+":");
            System.out.println("XCoordinate: "+forms[i].getXCoordinate());
            System.out.println("YCoordinate: "+forms[i].getYCoordinate());
            System.out.println("InnerColor: "+forms[i].getInnerColor());
            System.out.println("OuterColor: "+forms[i].getFormColor());
            System.out.println();
        }

        forms[0].move(1, 1);
        forms[1].move(3, 3);
        forms[2].move(5, 5);
        
        System.out.println();
        forms[0].shrink();
        forms[1].shrink();
        forms[2].shrink();
        System.out.println();

        forms[0].enlarge();
        forms[1].enlarge();
        forms[2].enlarge();
        System.out.println();

        forms[0].changeInnerColor("orange");
        forms[1].changeInnerColor("red");
        forms[2].changeInnerColor("yellow");
        System.out.println();

        forms[0].changeFormColor("blue");
        forms[1].changeFormColor("green");
        forms[2].changeFormColor("purple");
        System.out.println();

        for(int i=0;i<3;i++){
            System.out.println("Form "+(i+1)+":");
            System.out.println("XCoordinate: "+forms[i].getXCoordinate());
            System.out.println("YCoordinate: "+forms[i].getYCoordinate());
            System.out.println("InnerColor: "+forms[i].getInnerColor());
            System.out.println("OuterColor: "+forms[i].getFormColor());
            System.out.println();
        }
    }
}