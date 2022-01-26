package basic_components;
public class Vector2D {
    public int x;
    public int y;
    public Vector2D(){
        x=0;
        y=0;
    }
    public Vector2D(int x_axis,int y_axis){
        x=x_axis;
        y=y_axis;
    }
    @Override
    public String toString() {

        return "X= "+x+" Y="+y;
    }
}
