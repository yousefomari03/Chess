package com.example.Chess.pieces;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;


@Getter
@Setter
@ToString
public class Position {

    private int x;
    private int y;

    public Position(int y, int x) {

        this.y= y;
        this.x = x;

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    public String getName(int x,int y ){
        if (0<=x && x<26){
          return (char)(x+'A')+""+(char) (y+1);

        } else{
            return (char)(x+1)+" "+(char) (y+1);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
