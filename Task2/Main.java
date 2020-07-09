package ru.spbstu.main;

import ru.spbstu.main.shapes.Circle;
import ru.spbstu.main.shapes.Point;
import ru.spbstu.main.shapes.Rectangle;
import ru.spbstu.main.shapes.Shape;
import ru.spbstu.main.shapes.Triangle;

class RectPoint implements Point {
    private float x;
    private float y;

    RectPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getArea() {
        return 0;
    }
}

public class Main {
    public static void main(String[] args) {
        Shape[] shapes = null;
        shapes = new Shape[10];
        shapes[0] = new Circle(1);
        shapes[1] = new Circle(2);
        shapes[2] = new Circle(3);
        shapes[3] = new Circle(4);
        RectPoint p1 = new RectPoint(0, 0);
        RectPoint p2 = new RectPoint(10, 10);
        RectPoint p3 = new RectPoint(30, 30);
        shapes[4] = new Triangle(p1, p2, p3);
        shapes[5] = new Rectangle(2, 2);
        shapes[6] = new Rectangle(4, 4);
        shapes[7] = new Rectangle(8, 8);
        shapes[8] = new Rectangle(10, 10, 90);
        shapes[9] = new Rectangle(12, 12, 180);
        try {
            System.out.println(FindShapeWithMaxArea(shapes).getArea());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        /*
         * TODO: Выполнить действия над массивом 'shapes'
         *
         * 1. Проинициализировать переменную 'shapes' массивом
         *    содержащим 10 произвольных фигур. Массив должен
         *    содержать экземпляры классов Circle, Rectangle
         *    и Triangle.
         *
         * 2. Найти в массиве 'shapes' фигуру с максимальной
         *    площадью. Для поиска фигуры необходимо создать
         *    статический метод в текущем классе (Main).
         */
    }

    private static Shape FindShapeWithMaxArea(Shape[] shapes) {
        if (shapes.length == 0) {
            throw new IllegalArgumentException("0 size");
        }
        int index = 0;
        float maxArea = shapes[0].getArea();
        for (int i = 1; i < shapes.length; i++) {
            if (shapes[i].getArea() > maxArea) {
                index = i;
                maxArea = shapes[i].getArea();
            }
        }
        return shapes[index];
    }
}
