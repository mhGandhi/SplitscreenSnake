public class Pos {
    int x;
    int y;

    public Pos(int px,int py){
        x = px;
        y = py;
    }

    public Pos(double px,double py){
        this((int)Math.round(px),(int)Math.round(py));
    }

    public Pos(float px,float py){
        this((int)Math.round(px),(int)Math.round(py));
    }

    public Pos added(Pos pPos){
        return new Pos(this.x+pPos.x,this.y+pPos.y);
    }


    @Override
    public boolean equals(Object obj) {
        if(! (obj instanceof Pos))return false;
        Pos other = (Pos) obj;

        return other.x == this.x && other.y == this.y;
    }

    @Override
    public String toString() {
        return "["+x+"|"+y+"]";
    }
}
